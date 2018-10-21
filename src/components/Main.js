import React, { Component } from "react";
import Buttons from "./Buttons";
import InfoBar from "./InfoBar";
import NavBar from "./NavBar";
import SearchBar from "./SearchBar";

class Main extends Component {
  state = {};
  render() {
    return (
      <div>
        <h3>Buttons Component:</h3>
        <Buttons />
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
