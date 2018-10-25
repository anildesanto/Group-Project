import React from 'react';
import InfoBar from './InfoBar.js'
import UserTable from './UserTable.js'
import CVTable from './CVTable.js'
import UploadCv from './UploadCv.js'
import SearchUser from './SearchUser.js'
import SearchCV from './SearchCV.js'
import DeployDropDown from './DeployDropDown.js'
import axios from 'axios'
import LogOut from './LogOut.js'
import FilterFlag from './FilterFlag.js'
import CreateAccount from './CreateAccount.js'


class User extends React.Component {

  state = {
    visibility: 'visible',
  }
  componentDidMount() {
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.props.loginInfo[0]}/cv`)
      .then(response => {
        this.props.findCv(response.data);
        //console.log(response.data);
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
    if (this.props.loginInfo[4] === 1 || this.props.loginInfo[4] === 6) {
      return (
        <div id="trainee">
          <InfoBar userId={this.props.loginInfo[0]}
            firstName={this.props.loginInfo[1]}
            lastName={this.props.loginInfo[2]}
            role={this.props.loginInfo[5]} />
          <CVTable loginInfo= {this.props.loginInfo} viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility}  />
          <LogOut/>
        </div>
      );
    }
    else if (this.props.loginInfo == ""){
      return (
        <div id="noDep">
          <p>{this.props.loginInfo[4]}</p>
          <h1><a href="#/">Please log in.</a></h1>
        </div>
      );
    }
    else
      {
        return (
          <div id="trainer" >
            <InfoBar userId={this.props.loginInfo[1]}
              firstName={this.props.loginInfo[1]}
              lastName={this.props.loginInfo[2]}
              role={this.props.loginInfo[5]} />
            <SearchUser onSubmit={this.props.findUser} />
            <FilterFlag onChange={this.props.findCv} cvs={this.props.cvs}/>
            <LogOut/>
            <UserTable rows={this.props.rows} onClick={this.props.findCv} cvs={this.props.cvs} />
            <CVTable loginInfo= {this.props.loginInfo} viewerId= {this.props.loginInfo[4]} cvs={this.props.cvs} onClick ={this.props.findCv} visibility={this.state.visibility} />
            <CreateAccount viewerId= {this.props.loginInfo[4]} />
          </div>
        );
    }  
  }
}

export default User;