import React from 'react';
import './App.css';

class App extends React.Component 
{
  myData = [];
  send()
  {
    var cmd = document.getElementById("input_cmd").value;
    document.getElementById("input_cmd").value = "";
    var res = cmd+"~response";
    var obj={};
    obj.cmd = String(cmd);
    obj.res = res;
    obj.key = (this.myData).length +1;
    (this.myData).push(obj);
    this.forceUpdate();
  }
  render(){

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
            <div id="nt"><img src="/images/nt.png" alt="Avatar" /></div>
            <div id = "terminal_title">websole@22</div>
            <div id="exit_terminal"><img src="/images/exit.png" alt="Avatar" /></div>
          </div>
          <div id="terminal">
            {children}
          </div>
          <div id = "input_bar">
            <input id="input_cmd"></input>
            <button id = "send" onClick={()=>this.send()}>Send</button>
          </div>
      </div>
    </div>
    </>;
  }
}

export default App;
