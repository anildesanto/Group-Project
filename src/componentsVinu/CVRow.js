import React, { Component } from "react";
import axios from 'axios';
import pdfImg from './pdf.png';
import statusImg from './status.png';
import dwnldImg from './download.png';
import deleteImg from './delete.png';
import wordImg from './word.png';
import labelImg from './label.png';


class Buttons extends Component {

  constructor(props) {
    super(props);
    this.state = {
      cvModel: 
    {
        "cvId": 2,
        "cvLink": {
            "binaryStream": {}
        },
        "fileType": "application/pdf",
        "fileName": "CV Project.pdf",
        "creationDate": "2018-10-21T18:25:29.066+0000",
        "status": "Gray",
        "user": {
            "userId": 1,
            "firstName": "Aizen",
            "lastName": "Hisoka",
            "email": "anime",
            "password": "lives",
            "handler": {},
            "department": {
                "departmentId": 2,
                "role": "Trainer",
                "handler": {},
                "hibernateLazyInitializer": {}
            },
            "hibernateLazyInitializer": {}
        }
    }
    };
  }



  chooseFileType () {
    if (this.state.cvModel.fileType=="application/pdf")
      return pdfImg;
    else
      return wordImg;
  }
  render() {
    return (
        <tr class = "row">
            <td><img src = {this.chooseFileType()}/> </td>
              <td><label>
              {this.state.cvModel.fileName}</label>
              </td>   
            <td><img src = {statusImg} style={{backgroundColor:this.state.cvModel.status}}/></td>
            <td>
              <a href = {"https://qacvmanager.azurewebsites.net/api/cv/" +this.state.cvModel.cvId + "/download"}>
              <img src = {dwnldImg}/>
              </a>
            </td>
            <td>
              <a href = {"https://qacvmanager.azurewebsites.net/api/cv/" +this.state.cvModel.cvId + "/delete"}>
              <img src = {deleteImg}/>
              </a>
            </td> 
        </tr>
    );
  }
}

export default Buttons;
