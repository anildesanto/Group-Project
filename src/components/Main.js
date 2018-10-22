import React, { Component } from "react";
import Buttons from "./Buttons";
import InfoBar from "./InfoBar";
import NavBar from "./NavBar";
import SearchBar from "./SearchBar";
import CVRow from "./CVRow";

class Main extends Component {
  static defaultProps = {
    userID: "1",
    cvID: "12"
  };
  render() {
    return (
      <div>
        <h3>Buttons Component:</h3>
        {/* <Buttons upload={(userId = "1")} /> */}
        <Buttons cvID={this.props.cvID} />
        <br />
        <br />
        <br />
        <br />
        <br />
        <h3> CVRow Component:</h3>
        <CVRow />
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
