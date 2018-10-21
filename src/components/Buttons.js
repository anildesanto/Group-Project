import React, { Component } from "react";

class Buttons extends Component {
  state = {};
  render() {
    return (
      <div>
        {/* <form
          action="https://qacvmanager.azurewebsites.net/api/user/1/upload"
          method="post"
          header="file"
          enctype="multipart/form-data"
        >
          <input type="file" name="file">
            {" "}
          </input>
          <input type="submit" value="upload">
            {" "}
          </input>
        </form> */}
        <button href="https://qacvmanager.azurewebsites.net/api/cv/3/download">
          Download CV
        </button>
      </div>
    );
  }
}

export default Buttons;
