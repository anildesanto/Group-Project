import React from 'react';
import axios from 'axios';
import CryptoJS from "crypto-js";

class CreateAccount extends React.Component
{
 createAccount = (event) => {
   event.preventDefault();
   if(window.confirm("Confirm Account Creation"))
   {
       var department  = document.getElementById("selectDpt");
       var firstName  = document.getElementById("firstName");
       var lastName  = document.getElementById("lastName");
       var email  = document.getElementById("email");

       axios.post("https://qacvmanager.azurewebsites.net/api/department/"+department.value+ "/user",
       {
           "firstName" : firstName.value,
           "lastName": lastName.value,
           "email" : email.value,
           "password": CryptoJS.MD5("defaultPass").toString()
       })
       firstName.value ="";
       lastName.value ="";
       email.value ="";
   }
 };
 render() {
     if(this.props.viewerId === 3)
    {
    return (
        <div>
            <h1>Create Account</h1>
            <form onSubmit ={this.createAccount}>
                <select id = "selectDpt">
                    <option value = "1">Trainee</option>
                    <option value = "2">Trainer</option>
                    <option value = "3">Training Manager</option>
                    <option value = "4">Sales</option>
                    <option value = "5">Soft Skills</option>
                    <option value = "6">Consultant</option>
                </select>
                <br/><label> First Name </label>
                <input type = "text" id = "firstName" placeholder="Enter Last Name" />
                <br/><label> Last Name </label>
                <input type = "text" id = "lastName" placeholder="Enter Last Name" />
                <br/><label> Email </label>
                <input type = "email" id = "email" placeholder="Enter email" /> <br/>
            <button type="submit">Create</button>
            </form>
        </div>
    );
    }
    else
        {return (null)}
    }
}

export default CreateAccount;

