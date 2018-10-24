import React, { Component } from "react";

class InfoBar extends Component {
  render() {
    return (
      <div>
        <h4>ID: {this.props.userId}</h4>
        <h4>Name: {this.props.firstName} {this.props.lastName}</h4>
        <h4>Department: {this.props.role}</h4>
      </div>
    );
  }
}

export default InfoBar;
