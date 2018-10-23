import React, { Component } from "react";
import { Route, NavLink, HashRouter } from "react-router-dom";
import Home from "./Webpages/Home";
import Login from "./Webpages/Login";
import CVDashboard from "./Webpages/CVDashboard";

class NavBar extends Component {
  state = {};
  render() {
    return (
      <HashRouter>
        <div>
          <ul className="header">
            <li>
              <NavLink exact to="/">
                Home
              </NavLink>
            </li>
            <li>
              <NavLink to="/login">Login</NavLink>
            </li>
            <li>
              <NavLink to="/cvdashboard">CV Dashboard</NavLink>
            </li>
          </ul>
          <div className="content">
            <Route exact path="/" component={Home} />
            <Route path="/login" component={Login} />
            <Route path="/cvdashboard" component={CVDashboard} />
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default NavBar;
