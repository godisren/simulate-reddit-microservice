import React from 'react'
import axios from 'axios'

const API_URL = 'http://localhost:8080'

class Signup extends React.Component {

    constructor(props){
        super(props);

        this.state = {
            message: null,
            member:{
                username: null,
                password: null,
                firstName: null,
                lastName: null,
            }
            
        }

        this.handleChange = this.handleChange.bind(this);
        this.registerClicked = this.registerClicked.bind(this);
    }

    handleChange(event) {
        const { member } = { ...this.state };
        const { name, value } = event.target;
        member[name] = value;

        this.setState(
            {
                member:member
            }
        )
    }

    registerClicked(){
        axios.post(`${API_URL}/accounts-ws/users/register`,
            this.state.member
        ).then((response) => {
            alert("Register Successfully. please login.");
            this.props.history.push(`/`)
        }).catch((error) => {
            if (error.response && error.response.data) {
                this.setState({message : error.response.data.message});
            }else{
                this.setState({message: 'unknow error. (' + error +')'});
            }
        });
    }

    render(){
        return (
            <div>
                <h2>Sign Up</h2>
                {this.state.message && <div className="alert alert-danger">{this.state.message}</div>}
                
                <div className="form-group">
                    <label>User Name:</label>
                    <input type="text" className="form-control" name="username" value={this.state.username} onChange={this.handleChange} />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" className="form-control" name="password" value={this.state.password} onChange={this.handleChange} />
                </div>
                <div className="form-group">
                    <label>Name:</label>
                    <div className="row">
                        
                        <div className="col">
                        <input type="text" className="form-control" placeholder="First name" />
                        </div>
                        <div className="col">
                        <input type="text" className="form-control" placeholder="Last name" />
                        </div>
                    </div>
                </div>
                <button className="btn btn-success" onClick={this.registerClicked}>submit</button>
            </div>
        )
    }
}

export default Signup
