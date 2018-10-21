import React, { Component } from "react";

class InfoBar extends Component {
  state = {
    name: "Vinu",
    dept: "Trainee"
  };
  render() {
    return (
      <div>
        <h4>Name: {this.state.name}</h4>
        <h4>Department: {this.state.dept}</h4>
      </div>
    );
  }
}

export default InfoBar;
