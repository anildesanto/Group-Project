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
        <Buttons />
        <InfoBar />
        <NavBar />
        <SearchBar />
      </div>
    );
  }
}

export default Main;
