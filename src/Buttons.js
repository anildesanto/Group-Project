import React, { Component } from "react";
class Buttons extends Component {

  render() {
    return (
      <div>
        <form action={
            "https://qacvmanager.azurewebsites.net/api/user/" +
            this.props.userId + "/upload"
          }
          method="post"
          header="file"
          enctype="multipart/form-data">
          <input type="file" name="file" />
          <input type="submit" value="Upload" />
        </form>
      </div>
    );
  }
}

export default Buttons;
