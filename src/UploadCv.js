import React, { Component } from "react";
import axios from 'axios';
class UploadCv extends Component {

   constructor(props) {
        super(props);
        this.uploadCv = (e) => {
            var setPostLink = "https://qacvmanager.azurewebsites.net/api/user/" +
           this.props.userId + "/upload";

           var formData = new FormData();
            var cvFile = document.querySelector('#file');
            formData.append("file", cvFile.files[0]);
            if (window.confirm("Confirm Status")) {
               var req = axios.post(setPostLink, formData,
                {
                  headers: {
                        'Content-Type': 'multipart/form-data'
                      }
                });
                req.then(response => {
                    this.handleCV(e);
                })
                cvFile.value = null;
            }
        }
    }
    handleCV = (event) => {
    event.preventDefault();
    axios.get(`https://qacvmanager.azurewebsites.net/api/user/${this.props.userId}/cv`)
      .then(response => {
        this.props.onClick(response.data);
      })
  }
  render() {
    if(this.props.viewerId == 1 | this.props.viewerId == 6)
    {
        return (
        <div>
            <input id="file" type="file" name="file" />
            <button type="submit" onClick = {this.uploadCv}>Upload</button>
        </div>
        );
    }
    else
    {
        return (null);
    }
  }
}
export default UploadCv;
