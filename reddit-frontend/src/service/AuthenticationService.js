import axios from 'axios'
import createAuthRefreshInterceptor from 'axios-auth-refresh';

const API_URL = 'http://localhost:8080'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
const TOKEN_NAME = 'auth_token'
const REFRESH_TOKEN_NAME = 'refresh_token'

class AuthenticationService {

    // basic authentication
    executeBasicAuthenticationService(username, password) {
        return axios.get(`${API_URL}/basicauth`,
            { headers: { authorization: this.createBasicAuthToken(username, password) } })
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password) {
        //let basicAuthHeader = 'Basic ' +  window.btoa(username + ":" + password)
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        sessionStorage.setItem(TOKEN_NAME, this.createBasicAuthToken(username, password))
    }

    // JWT authentication
    executeJWTAuthenticationService(username, password){
        //return axios.post(`${API_URL}/authentication`,
        
        return axios.post(`${API_URL}/accounts-ws/users/login`,        
            {username:username,  password:password}
        );
    }

    refreshJWTAuthenticationService(){
        //return axios.post(`${API_URL}/authentication`,

        let refreshToken = sessionStorage.getItem(REFRESH_TOKEN_NAME)
        
        return axios.post(`${API_URL}/accounts-ws/users/token`,        
            {refreshToken:refreshToken}
        );
    }

    registerSuccessfulLoginForJWT(username, token, refreshToken) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        sessionStorage.setItem(TOKEN_NAME, token)
        sessionStorage.setItem(REFRESH_TOKEN_NAME, refreshToken)

        this.setupAxiosInterceptors();
    }

    /*
    setAuthenticationState(){
        let token = sessionStorage.getItem(TOKEN_NAME);
        if(token){
            this.setupAxiosInterceptors(this.createJWTAuthToken(token))
        }
    }
    */

    createJWTAuthToken(token) {
        return 'Bearer ' + token
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getUsername(){
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
    }

    setupAxiosInterceptors() {
        let refreshAuthLogic = failedRequest => 
            axios.post(`${API_URL}/accounts-ws/users/token`
                        ,{refreshToken:sessionStorage.getItem(REFRESH_TOKEN_NAME)})
                .then(tokenRefreshResponse => {
                        sessionStorage.setItem(TOKEN_NAME, tokenRefreshResponse.headers.token)
                        failedRequest.response.config.headers['Authentication'] = 'Bearer ' + tokenRefreshResponse.headers.token;
                        return Promise.resolve();
                });

        // Instantiate the interceptor (you can chain it as it returns the axios instance)
        createAuthRefreshInterceptor(axios, refreshAuthLogic, {statusCodes: [ 403 ]});

        axios.interceptors.request.use(
            (config) => {
                let token = sessionStorage.getItem(TOKEN_NAME)

                if (token) {
                    config.headers.authorization = 'Bearer ' + token
                }
                return config
            }
        )

        axios.interceptors.response.use(
            function (response) {
                return response;
            }, function (error) {
                if(error.response){
                    if (401 === error.response.status) {
                        AuthenticationService.logout();
                        alert('Your session has expired. please login again.');
                        window.location = '/login';
                    }if (504 === error.response.status) {
                        alert('Connection timeout.');
                        return Promise.reject(error);
                    }else{
                        return Promise.reject(error);
                    }
                }else{
                    return Promise.reject(error);
                }
            }
        );
    }

    logout() {
        sessionStorage.clear();
        // sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        // sessionStorage.removeItem(TOKEN_NAME);
        // sessionStorage.removeItem(REFRESH_TOKEN_NAME);
    }
}

let authService = new AuthenticationService()
authService.setupAxiosInterceptors();

export default authService