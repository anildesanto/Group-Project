import React, { Component } from "react";
import axios from 'axios';
class Buttons extends Component {

  constructor(props) {
    super(props);
    this.state = {
      cvModel: 
      {
  "cvId": 3,
  "cvLink": {
    "binaryStream": {
      
    }
  },
  "fileType": "application\/pdf",
  "fileName": "QAC_Consultant_Learning_Platform_Brochure.pdf",
  "creationDate": "2018-10-21T17:47:04.200+0000",
  "status": "Gray",
  "user": {
    "userId": 1,
    "firstName": "Aizen",
    "lastName": "Hisoka",
    "email": "anime",
    "password": "lives",
    "handler": {
      
    },
    "department": {
      "departmentId": 2,
      "role": "Trainer",
      "handler": {
        
      },
      "hibernateLazyInitializer": {
        
      }
    },
    "hibernateLazyInitializer": {
      
    }
  }
}
    };
  }
  render() {
    return (
      <div>
        <p>{this.state.cvModel + " test"}</p>
        {/* <p>{"userI" + this.state.cvModel.user.userId}</p> */}
        <p>{"userI" + this.state.cvModel.user.userId}</p>
        <form
          action={
            "https://qacvmanager.azurewebsites.net/api/user/" +
            this.state.cvModel.user.userId + "/upload"
          }
          method="post"
          header="file"
          enctype="multipart/form-data"
        >
          <input type="file" name="file" />
          <input type="submit" value="Upload" />
        </form>
        <a
          href={
            "https://qacvmanager.azurewebsites.net/api/cv/" +
            this.state.cvModel.cvId + "/download"
          }
        >
          Download
        </a>

        <form
          action="https://qacvmanager.azurewebsites.net/api/user/1"
          method="delete"
          header="file"
          enctype="multipart/form-data"
        >
          <input type="submit" value="Delete" />
        </form>
      </div>
    );
  }
}

export default Buttons;
