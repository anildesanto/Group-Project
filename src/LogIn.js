import React, { Component } from "react";
import axios from 'axios';
import CryptoJS from "crypto-js";



export default class LogIn extends Component {

  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
    };
  }
componentDidMount()
{
    this.passField = document.getElementById("loginPasswordfield");
    this.emailField = document.getElementById("loginEmailField");
    this.emailField.focus();
}

  onSubmit = (e) => {
    e.preventDefault()
    axios.get(`https://qacvmanager.azurewebsites.net/api/login/${this.state.email}&${this.state.password}`)
      .then(
        response => {
          //console.log(response.data[0])
          if (response.status === 200) {
            //console.log("Login Sucessful");
            this.props.loginInfo.push(response.data[0].userId);
            this.props.loginInfo.push(response.data[0].firstName);
            this.props.loginInfo.push(response.data[0].lastName);
            this.props.loginInfo.push(response.data[0].email);
            this.props.loginInfo.push(response.data[0].department.departmentId);
            this.props.loginInfo.push(response.data[0].department.role);
            //console.log(this.props.loginInfo[4]);

            this.props.onSubmit(this.props.loginInfo);
            window.location.replace("#/user");
          }
        })
      .catch((error) => {
        window.alert("Invalid Details");
        this.passField.value = "";
        this.emailField.value = "";
         this.emailField.focus();
      })
  }
  getEmail = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    })
  }
  hashPass = (e) => {
    e.preventDefault();
    this.setState({
      // [e.target.name]: e.target.value
      [e.target.name]: CryptoJS.MD5(e.target.value).toString()
    })
  }
  render() {
    return (
      <div id="login-page">
        <h2 id="loginTitle">Login to continue</h2>
        <div className="form">
          <form className="login-form" onSubmit={e => this.onSubmit(e)}>
            <label
              id="loginEmailLabel"
              htmlFor="email">Email </label>
            <br />
            <input
              id="loginEmailField"
              className="loginForm"
              name="email"
              type="email"
              placeholder="Enter email"
              value={this.state.email}
              onChange={e => this.getEmail(e)} />
            <br />
            <label
              id="loginPasswordLabel"
              htmlFor="password">Password</label>
            <br />
            <input
              id="loginPasswordfield"
              name="password"
              className="loginForm"
              type="password"
              placeholder="Enter password"
              value={this.state.passwordInput}
              onChange={e => this.hashPass(e)} />
            <br />
            <button type = "submit"
              id="btnLogin"
              className="button"
              >
              login
            </button>
            <p className="message">Forgot password? <a href="">Reset password.</a></p>
          </form>
        </div>
      </div>
    );
  }
}


