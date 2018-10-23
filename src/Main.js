import React from 'react';
//import Searchbar from './Searchbar.js'
//import Table from './Table.js'
import LogIn from './LogIn.js'
import LogOut from './LogOut.js'
import User from './User.js'
// import './App.css';
import { Route, NavLink, HashRouter } from "react-router-dom";

class Main extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userPath: '',
      loggedIn: this.props.loggedIn,
      loginInfo: [],
        // "userId": 0,
        // "firstName": "",
        // "lastName": "",
        // "email": "",
        // "department": ""
      cvs: [],
      rows: [],
      testInfo: {}
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
  // componentDidMount() {
  //   let request = new XMLHttpRequest();
  //   request.open("GET", "https://qacvmanager.azurewebsites.net/api/user/");
  //   request.setRequestHeader('Content-Type', 'application/json');
  //   request.setRequestHeader('Access-Control-Allow-Origin', '*');
  //   request.responseType = 'json';
  //   request.send();

  //   request.onload = () => {
  //     this.setState({ rows: request.response });
  //   }
  //   let request2 = new XMLHttpRequest();
  //   request2.open("GET", "https://qacvmanager.azurewebsites.net/api/user/1/cv");
  //   request2.setRequestHeader('Content-Type', 'application/json');
  //   request2.setRequestHeader('Access-Control-Allow-Origin', '*');
  //   request2.responseType = 'json';
  //   request2.send();
  //   request2.onload = () => {
  //     this.setState({ cvs: request2.response });
  //   }
  // }
   logIn = (userInfo) => {
    this.setState(prevState => ({
      loginInfo: userInfo
    }));
  }

  render() {
    // if (this.state.loggedIn) {
    //   return (
    //     <div>
    //       {/* <Searchbar onSubmit={this.findCv} /> */}
    //       <Table rows={this.state.rows} cvs={this.state.cvs} onClick={this.resetCV} />
    //     </div>
    //   );
    // } else {
    //   return (
    //     <LogIn onSubmit={this.logIn} loginInfo={this.state.loginInfo}/>
    //   );
    // }

     return (
      <HashRouter>
        <div>
          <ul className="header">
            <li>
              <NavLink exact to="/user">
                User
              </NavLink>
            </li>
            <li>
              <NavLink to="/login">LogIn</NavLink>
            </li>
            {/* <li>
              <NavLink to="/logout">LogOut</NavLink>
            </li> */}
          </ul>
          <div className="content">
            <Route exact path="/user" component={User} />
            <Route path="/login" component={LogIn} />
            {/* <Route path="/logout" component={LogOut} /> */}
          </div>
        </div>
      </HashRouter>
    );
  }
}
export default Main;