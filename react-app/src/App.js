import React from 'react';
import './App.css';

class App extends React.Component 
{
  constructor(props) 
    {
      super(props);
      if(this.props.jwt)
      {
        this.state = {jwt:this.props.jwt,websole:true};
      }
      else this.state = {websole:false};
    }
  myData = [];

  async send(e)
  {
    if (e.keyCode === 13) 
    {
      var cmd = document.getElementById("input_cmd").value;
      document.getElementById("input_cmd").value = "";
      
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ "data":cmd,"jwt":this.state.jwt })
      };
      const response = await fetch('/websole', requestOptions);
      const obj_res = await response.json();
      
      if(String(obj_res.msg) === "success")
      {
        var obj={};
        obj.cmd = String(cmd);
        obj.res = obj_res.data;
        obj.key = (this.myData).length +1;
        (this.myData).push(obj);
        this.forceUpdate();
      }
      else this.setState({ websole:false});
    }
  }
  componentDidUpdate() {
    var terminalDiv = document.getElementById("terminal");
    if(terminalDiv) terminalDiv.scrollTop = terminalDiv.scrollHeight;
  }
  render()
  { 
    if(this.state.websole === false)
    {
      window.location.reload();
    }
    var children = this.myData.map((val) => (
      <div key={val.key}>
          <p className="cmd"  >
            <span className="cmd_head" >websole@22:- $ &nbsp;</span>
            <span className="cmd_text">{val.cmd}</span>
          </p>
          <div className="cmd_res">{val.res}</div>
      </div>
    ));

    return <>   
    <div id="back" style={{ backgroundImage: "url(/images/Websole.png)" }}>
      <div id="terminal_box" >
          <div id="thead">
            <div id = "terminal_title"></div>
            <div id="exit_terminal"><img src="/images/exit.png" alt="Avatar" /></div>
          </div>
          <div id="terminal">
            {children}
          </div>
          <div id = "input_bar">
            <input id="input_cmd" onKeyDown={(event)=>this.send(event)}></input>
          </div>
      </div>
    </div>
    </>;
  }
}

export default App;
