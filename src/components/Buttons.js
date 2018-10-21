import React, { Component } from "react";

class Buttons extends Component {
  // constructor(props) {
  //   super(props);
  //   this.state = {
  //     upload: props.upload
  //   };
  // }
  // state = {};
  render() {
    return (
      <div>
        <form
          action="https://qacvmanager.azurewebsites.net/api/user/1/upload"
          method="post"
          header="file"
          enctype="multipart/form-data"
        >
          <input type="file" name="file" />

          <input type="submit" value="Upload" />
        </form>
        <a href="https://qacvmanager.azurewebsites.net/api/cv/12/download">
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
