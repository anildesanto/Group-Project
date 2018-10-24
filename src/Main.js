import React from 'react';
import LogIn from './LogIn.js'
import User from './User.js'
import { Route, NavLink, HashRouter } from "react-router-dom";
import axios from 'axios'

class Main extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loginInfo: [],
      // "userId": 0,
      // "firstName": "",
      // "lastName": "",
      // "email": "",
      // "department": ""
      cvs: [],
      rows: []
    };
    this.baseState = this.state;
  }
  findUser = (userInfo) => {
    this.setState(prevState => ({
      rows: userInfo
    }));
  }
  findCv = (userCV) => {
    this.setState(prevState => ({
      cvs: userCV
    }));
  }
  resetCV = (info) => {
    this.setState(prevState => ({
      cvs: info
    }));
    console.log("Reset CVs to " + info);
  }

  logIn = (userInfo) => {
    this.setState(prevState => ({
      loginInfo: userInfo
    }));
  }
  refreshCV = () => {
    this.setState(prevState => ({
    }));
  }

  render() {
    return (
      <HashRouter>
        <div>
          <ul className="header">
            <li>
              <NavLink exact to="/user">User</NavLink>
            </li>
            <li>
              <NavLink exact to="/login">Log in</NavLink>
            </li>

          </ul>
          <div className="content">
            {/* <Route exact path="/user" component={User} /> */}
            <Route exact path="/login" render={(props) => <LogIn {...props} onSubmit={this.logIn} loginInfo={this.state.loginInfo} />} />
            <Route path='/user' render={(props) => <User {...props} 
            loginInfo={this.state.loginInfo} rows={this.state.rows} 
            cvs={this.state.cvs} resetCV={this.resetCV} findUser={this.findUser} 
            findCv={this.findCv} />} />
          </div>
        </div>
      </HashRouter>
    );
  }
}
export default Main;