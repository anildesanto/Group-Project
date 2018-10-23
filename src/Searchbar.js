import React from 'react';
import axios from 'axios';

class Searchbar extends React.Component {
  state = {  userName: '', userId: '' }
  handleSubmit = (event) => {
    event.preventDefault();
    console.log("Search Event: onSubmit", this.state.userName);
    axios.get(`https://qacvmanager.azurewebsites.net/api/findbyname/${this.state.userName}&`)
      .then(response => {
        this.props.onSubmit(response.data);
        console.log(response.data);
      })
  };
  handleCV = (event) => {
    event.preventDefault();
    console.log("CV Event: onSubmitCV", this.state.userId);
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.state.userId}/cv`)
      .then(response => {
        console.log(response.data);
        this.props.onSubmit(response.data);
      })
  };
  render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <input type="text"
            value={this.state.userName}
            onChange={(event) => this.setState({ userName: event.target.value })}
            placeholder="Enter user" />
          <button type="submit">Find user</button>
        </form>
        <form onSubmit={this.handleCV}>
          <input type="text"
            value={this.state.userId}
            onChange={(event) => this.setState({ userId: event.target.value })}
            placeholder="Enter ID" />
          <button type="submit">Find CVs</button>
        </form>
      </div>
    );
  }
}

export default Searchbar;