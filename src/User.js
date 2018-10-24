import React from 'react';
import InfoBar from './InfoBar.js'
import UserTable from './UserTable.js'
import CVTable from './CVTable.js'
import Buttons from './Buttons.js'
import SearchUser from './SearchUser.js'
import SearchCV from './SearchCV.js'
import DeployDropDown from './DeployDropDown.js'
import axios from 'axios'


class User extends React.Component {

  state = {
    visibility: 'visible',
  }
  componentDidMount() {
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.props.loginInfo[0]}/cv`)
      .then(response => {
        this.props.findCv(response.data);
        console.log(response.data);
      })
  }
  showTable = () => {
    this.setState(prevState => ({
      visibility: 'visible'
    }));
  }
  hideTable = () => {
    this.setState(prevState => ({
      visibility: 'hidden'
    }));
  }
  render() {
    if (this.props.loginInfo[4] === 1) {
      return (
        <div id="trainee">
          <InfoBar userId={this.props.loginInfo[0]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]} />
          <Buttons userId={this.props.loginInfo[0]} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility}  />
        </div>
      );
    } else if (this.props.loginInfo[4] === 2) {
      return (
        <div id="trainer" >
          <InfoBar userId={this.props.loginInfo[1]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]} />
          <SearchUser onSubmit={this.props.findUser} />
          <UserTable rows={this.props.rows} onClick={this.props.findCv} cvs={this.props.cvs} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility} />
          {/* <SearchCV onSubmit={this.props.findCv}/> */}

        </div>
      );
    } else if (this.props.loginInfo[4] === 3) {
      return (
        <div id="manager">
          <InfoBar userId={this.props.loginInfo[1]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]}
          />

          <SearchUser onSubmit={this.props.findUser} />
          <UserTable rows={this.props.rows} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility} />
          <SearchCV onSubmit={this.props.findCv} />

        </div>
      );
    } else if (this.props.loginInfo[4] === 4) {
      return (
        <div id="sales">
          <InfoBar userId={this.props.loginInfo[1]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]}
          />

          <SearchUser onSubmit={this.props.findUser} />
          <UserTable rows={this.props.rows} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility} />
          <SearchCV onSubmit={this.props.findCv} />

        </div>
      );
    } else if (this.props.loginInfo[4] === 5) {
      return (
        <div id="soft">
          <InfoBar userId={this.props.loginInfo[1]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]}
          />
          <SearchUser onSubmit={this.props.findUser} />
          <UserTable rows={this.props.rows} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility}  />
          <SearchCV onSubmit={this.props.findCv} />

        </div>
      );
    } else if (this.props.loginInfo[4] === 6) {
      return (
        <div id="consultants">
          <InfoBar userId={this.props.loginInfo[1]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]}
          />
          <SearchUser onSubmit={this.props.findUser} />
          <UserTable rows={this.props.rows} />
          <CVTable viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility} />
          <SearchCV onSubmit={this.props.findCv} onClick={this.changeVisibility} />

        </div>
      );
    } else {
      return (
        <div id="noDep">
          <h1><a href="http://localhost:3000/#/login">Please log in.</a></h1>

        </div>
      );
    }
  }
}

export default User;