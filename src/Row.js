import React from 'react';
import axios from 'axios';

class Row extends React.Component {
  state = {
    color: ''
  }
  // handleClick = (event) => {
  //   event.preventDefault();
  //   console.log("Clicked");
  //   this.props.onClick(this.props.userId);
  //   console.log(this.props.userId);
  // };

  handleCV = (event) => {
    event.preventDefault();
    //console.log("New CVS:", this.props.userId);
    this.setState({ color: '#AAAAAA' });
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.props.userId}/cv`)
      .then(response => {
        this.props.onClick(response.data);
      })
  };
  render() {
    return (
      <tbody>
        <tr className="row" onClick={this.handleCV} style={{ backgroundColor: this.state.color }}>
          <td className="cell" >{this.props.userId}</td>
          <td className="cell" >{this.props.firstName} {this.props.lastName}</td>
          <td className="cell">{this.props.email} </td>
          <td>{this.props.department.role} </td>
        </tr>
      </tbody>
    );
  }
}
export default Row;

