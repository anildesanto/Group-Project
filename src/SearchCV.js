import React from 'react';
import axios from 'axios';

class SearchCV extends React.Component {
  state = {  userId: ''}

  handleCV = (event) => {
    event.preventDefault();
    console.log("CV Event: onSubmitCV", this.state.userId);
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.state.userId}/cv`)
      .then(response => {
        this.props.onSubmit(response.data);
        console.log(response.data);
      })
  };
  render() {
    return (
      <div>
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

export default SearchCV;