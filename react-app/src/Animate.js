import React from 'react';
import { Navigate } from 'react-router-dom';
import './Animate.css';

const textData = "~ $ WEBSOLE ^C";

class Animate extends React.Component 
{
    constructor(props)
    {
        super(props);
        this.state = {text:"",redirect:false};
        this.addText=this.addText.bind(this);
    }

    componentDidMount() 
    {
        var intervalId = setInterval(this.addText, 250);
        this.setState({intervalId: intervalId});
    }
    
    componentWillUnmount() 
    {
        clearInterval(this.state.intervalId);
    }

    addText() 
    {
        if((this.state.text).length < textData.length)
        {
            this.setState({text:this.state.text+textData.charAt((this.state.text).length)});
        }
        else
        {
           this.setState({redirect:true});
        }
    }

    render()
    {
        if (this.state.redirect === true) {
            return <Navigate to="/websole" />;
        }
        return <>
            <div id = "ani_back"> 
                <div id="ani_box">{this.state.text}</div>
            </div>
            </>;
    }
}

export default Animate;
