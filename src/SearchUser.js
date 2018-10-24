import React from 'react';
import axios from 'axios';

class SearchUser extends React.Component {
  state = {  userName: '' }
  handleSubmit = (event) => {
    event.preventDefault();
    console.log("Search Event: onSubmit", this.state.userName);
    axios.get(`https://qacvmanager.azurewebsites.net/api/findbyname/${this.state.userName}&`)
      .then(response => {
        this.props.onSubmit(response.data);
        console.log(response.data);
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
      </div>
    );
  }
}

export default SearchUser;