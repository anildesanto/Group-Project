import React, { Component } from "react";
import Flag from './Flag';
import axios from 'axios';
class Cvrow extends Component
{
  state ={
    test:''
  }
 constructor(props) {
       super(props);
       this.downloadUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/download';
       this.downIco = "https://image.flaticon.com/icons/svg/138/138601.svg"
       this.deleteUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/delete';
       this.delIco = "https://image.flaticon.com/icons/png/128/138/138767.png"
      }
   updateStatus = (e) =>
  {
    e.preventDefault();
    if(window.confirm("Confirm Delete"))
    {
        var req = axios.get(this.deleteUrl);
       req.then(response => {
          this.handleCV(e);
      })
        
    }
  }

   handleCV = (event) => {
    event.preventDefault();
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.props.user.userId}/cv`)
      .then(response => {
        this.props.onClick(response.data);
      })
  };

  render()
  {
    return (
        <tbody>
        <tr className="row">
           {/* <td className="cell">{this.props.user.userId}</td>  */}
          <td className="cell">{this.props.fileName}</td>
           <td><Flag changed={this.testState} userId={this.props.user.userId} viewerId = {this.props.viewerId} cvId = {this.props.cvId} status ={this.props.status} findCv={this.findCv}/></td>
          <td className="cell"><a href={this.downloadUrl}><img src={this.downIco} alt="Download"  /></a></td>
          <td className="cell"><a onClick ={(e) => { this.updateStatus(e)}}><img src={this.delIco} alt="Delete"  /></a></td>
        </tr></tbody>
      
      );
    }
}
export default Cvrow;

