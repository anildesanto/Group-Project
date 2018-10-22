import React, { Component } from "react";
import Buttons from "./Buttons";
import InfoBar from "./InfoBar";
import NavBar from "./NavBar";
import SearchBar from "./SearchBar";

class Main extends Component {
  state = {
    userId: "1",
    cvId: "12"
  };
  render() {
    return (
      <div>
        <h3>Buttons Component:</h3>
        {/* <Buttons upload={(userId = "1")} /> */}
        <Buttons userId={this.props.userId} cvId={this.props.cvId} />
        <br />
        <br />
        <br />
        <br />
        <br />
        <h3>Info Bar Component:</h3>
        <InfoBar />
        <br />
        <br />
        <br />
        <br />
        <br />
        <h3>Nav Bar Component:</h3>
        <NavBar />
        <br />
        <br />
        <br />
        <br />
        <br />
        <h3>Search Bar Component:</h3>
        <SearchBar />
        <br />
        <br />
        <br />
        <br />
        <br />
      </div>
    );
  }
}

export default Main;
