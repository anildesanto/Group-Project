import React, { Component } from "react";
import axios from 'axios';
//import CryptoJS from "crypto-js";
import User from './User.js'
import { Route, NavLink, HashRouter, Redirect } from "react-router-dom";


export default class LogIn extends Component {

  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      departmentId: "",
      loggedIn: false
    };
  }


  onSubmit = (e) => {
    e.preventDefault()
    axios.get(`https://qacvmanager.azurewebsites.net/api/login/${this.state.email}&${this.state.password}`)
      .then(
      response => {
        console.log(this.state.loggedIn)
        console.log(response.data[0])
        if (response.status === 200) {
          console.log("Login Sucessful");
          // this.props.loginInfo.push(response.data[0].userId);
          // this.props.loginInfo.push(response.data[0].firstName);
          // this.props.loginInfo.push(response.data[0].lastName);
          // this.props.loginInfo.push(response.data[0].email);
          // this.props.loginInfo.push(response.data[0].department.departmentId);
          console.log("Quick Test: ", response.data[0].department.departmentId)
          if (response.data[0].department.departmentId===1) {
            
          }
          else if (response.data[0].department.departmentId===2) {
            window.location.replace("http://localhost:3000/user#/user")

          }
          else if (response.data[0].department.departmentId===3) {
            
          }
          else if (response.data[0].department.departmentId===4) {
            
          }
          else if (response.data[0].department.departmentId===5) {
            
          }

        }
      })
      .catch((error) => {
        console.log(error)
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
      // [e.target.name]: CryptoJS.MD5(e.target.value).toString()
      [e.target.name]: e.target.value
    })
  }
  render() {

    return (
      <div className="formGroup">
        <h2 id="loginTitle">Log in</h2>
        <br />
        <label
          id="loginEmailLabel"
          htmlFor="email">Email: </label>
        <input
          id="loginEmailField"
          className="loginForm"
          name="email"
          type="text"
          value={this.state.email}
          onChange={e => this.getEmail(e)} />
        <br />
        <br />
        <label
          id="loginPasswordLabel"
          htmlFor="password">Password: </label>
        <input
          id="loginPasswordfield"
          name="password"
          className="loginForm"
          type="password"
          value={this.state.passwordInput}
          onChange={e => this.hashPass(e)} />
        <br />
        <br />
        <button
          id="btnLogin"
          className="button"
          onClick={e => this.onSubmit(e)}>
          Submit
            </button>
      </div>
      
    )
    }
    
  }


