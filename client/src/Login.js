import React from 'react';
import './Login.css';
import App from './App';

class Login extends React.Component 
{
    constructor(props) 
    {
        super(props);
        this.state = {type:"Login",error:"",websoleRedirect:false,jwt:"",username:"",password:""};  
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handlePwChange = this.handlePwChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.changeType = this.changeType.bind(this);
    }
    changeType()
    {
        if(this.state.type === "Login") this.setState({type:"Sigin"});
        else this.setState({type:"Login"});
    }
    handleNameChange(event)
    {
        this.setState({
            username: event.target.value
        });
    }
    handlePwChange(event)
    {
        this.setState({
            password: event.target.value
        });
    }
    async handleSubmit(event) 
    {
        event.preventDefault();
        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ "username":this.state.username,"password":this.state.password })
        };
        const response = await fetch("/"+this.state.type.toLowerCase(), requestOptions);
        const resObj = await response.json();
        if(resObj.response === "success")
        {
            this.setState(
                {
                    websoleRedirect:true,
                    jwt:resObj.jwt
                }
            )
        }
        else
        {
            this.setState(
                {
                    username:"",
                    password:"",
                    error:resObj.response
                }
            )
        }
        
    }
    render()
    {
        if(this.state.websoleRedirect ===true)
        {
            return <App jwt = {this.state.jwt} />;
        }

        return <>
            <div id = "log_back">
                <div id="login_box">
                    <div id = "log_head">WEBSOLE</div>
                    <div id ="login_head" onClick={()=>this.changeType()}>{this.state.type}</div>
                    <form onSubmit={this.handleSubmit}>
                        <div className="input_text_div">
                            <input className="input_text" name="username" value={this.state.username} type ="text" placeholder='username' onChange={this.handleNameChange}></input>
                        </div>
                        <div className="input_text_div">
                            <input className="input_text" name="password" value={this.state.password} type="password" placeholder='password'onChange={this.handlePwChange} ></input>
                        </div>
                        <div className="input_text_div">
                            <input id = "submit_button" value="Go" type ="submit"></input>
                        </div>
                    </form>
                    <div id ="error">{this.state.error}</div>
                </div>
            </div>
            </>;
    }
}

export default Login;
