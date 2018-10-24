import React from 'react';
import axios from 'axios';

var setStatus  = "https://qacvmanager.azurewebsites.net/api/department/"

class DeployDropDown extends React.Component {
  state = {  deptId: '' }
   constructor(props) {
       super(props);
       this.statusSelector = null;
       this.setStatusSelector = element =>
       {
           this.statusSelector = element;
          console.log("Element : " +element);
       }
       this.updateStatus = (e) =>
       {
           if(window.confirm("Confirm Status"))
           {
             
              // window.open(setStatus + this.statusSelector.value + "/user");
           }
       }
   }

     handleDept = (event) => {
    event.preventDefault();
    console.log("CV Event: onSubmitCV", this.statusSelector.value);
    axios.get(`https://qacvmanager.azurewebsites.net/api/department/${this.statusSelector.value}/user`)
      .then(response => {
        console.log(response.data);
        this.props.onChange(response.data);
      })
  };

  render() {
    return (
      <div>
        <select  onChange = {this.handleDept} ref={this.setStatusSelector} >
               <option value ="1" >Trainee</option>
               <option value="6">Consultant</option>
        </select>
        {/* <form onSubmit={this.handleSubmit}>
          <input type="text"
            value={this.state.userName}
            onChange={(event) => this.setState({ userName: event.target.value })}
            placeholder="Enter user" />
          <button type="submit">Find user</button>
        </form>
        <form onSubmit={this.handleCV}>
          <input type="text"
            value={this.state.userId}
            onChange={(event) => this.setState({ userId: event.target.value })}
            placeholder="Enter ID" />
          <button type="submit">Find CVs</button>
        </form> */}
      </div>
    );

               
  }
}

export default DeployDropDown;