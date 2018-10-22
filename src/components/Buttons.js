import React, { Component } from "react";

class Buttons extends Component {
  constructor(props) {
    super(props);
    this.state = {
      upload: props.upload
    };
  }

  // static defaultProps = {
  //   userID: "1",
  //   deptID: "1"
  // };

  render() {
    return (
      <div>
        <form
          action={
            "https://qacvmanager.azurewebsites.net/api/user/" +
            this.props.userID +
            "/upload"
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
            this.props.cvID +
            "/download"
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
