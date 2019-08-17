import React, { Component } from 'react'
import AuthenticationService from '../service/AuthenticationService';

class LoginComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            username: 'stone',
            password: 'stone',
            hasLoginFailed: false,
            showSuccessMessage: false
        }

        this.handleChange = this.handleChange.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]
                    : event.target.value
            }
        )
    }

    loginClicked() {
        // hard-code authentication
        // if(this.state.username==='stone' && this.state.password==='stone'){
        //     //AuthenticationService.registerSuccessfulLogin(this.state.username,this.state.password)
        //     this.props.history.push(`/bulletin`)
        //     // this.setState({showSuccessMessage:true})
        //     // this.setState({hasLoginFailed:false})
        // }
        // else {
        //      this.setState({showSuccessMessage:false})
        //      this.setState({hasLoginFailed:true})
        // }

        // basic authentication
        // AuthenticationService.executeBasicAuthenticationService(this.state.username,this.state.password)
        // .then(() => {
        //     AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password)
        //     this.props.history.push(`/bulletin`)
        // }).catch(() => {
        //     this.setState({ showSuccessMessage: false })
        //     this.setState({ hasLoginFailed: true })
        // })

        //JWT authentication
        AuthenticationService.executeJWTAuthenticationService(this.state.username,this.state.password)
        .then((response) => {
            //let token = response.data.token;
            let token = response.headers['token'];
            let refreshToken = response.headers['refresh-token'];
            
            AuthenticationService.registerSuccessfulLoginForJWT(this.state.username, token, refreshToken);
            this.props.history.push(`/post`)
        }).catch(() => {
            this.setState({ showSuccessMessage: false })
            this.setState({ hasLoginFailed: true })
        })
    }

    render() {
        return (
            <div>
                <h2>Login</h2>

                {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                {this.state.showSuccessMessage && <div>Login Sucessful</div>}
                <div className="form-group">
                    <label>User Name:</label>
                    <input type="text" className="form-control" name="username" value={this.state.username} onChange={this.handleChange} />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" className="form-control" name="password" value={this.state.password} onChange={this.handleChange} />
                </div>
                <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
            </div>
        )
    }
}

export default LoginComponent