import React, { Component } from "react";
import axios from 'axios';
class Buttons extends Component {

   constructor(props) {
        super(props);
        this.uploadCv = (e) => {
            var setPostLink = "https://qacvmanager.azurewebsites.net/api/user/" +
           this.props.userId + "/upload";

           var formData = new FormData();
            var cvFile = document.querySelector('#file');
            formData.append("file", cvFile.files[0]);
            console.log("Sending ");
            if (window.confirm("Confirm Status")) {
                axios.post(setPostLink, formData,
                {
                  headers: {
                        'Content-Type': 'multipart/form-data'
                      }
                });
                    cvFile.value = null;
                //window.open(setStatus + this.statusSelector.value);
            }
        }
    }
  

  render() {
    return (
      <div>
          <input id="file" type="file" name="file" />
          <button type="submit" onClick = {this.uploadCv}>Upload</button>
      </div>
    );
  }
}


//   render() {
//     return (
//       <div>
//         <form action=
//            {"https://qacvmanager.azurewebsites.net/api/user/" +
//            this.props.userId + "/upload"}
          
//           method="post"
//           header="file"
//           enctype="multipart/form-data">
//           <input type="file" name="file" />
//           <input type="submit" value="Upload" />
//         </form>
//       </div>
//     );
//   }
// }

export default Buttons;
