import React from 'react';
import axios from 'axios';

class CreateAccount extends React.Component
{
 createAccount = (event) => {
   event.preventDefault();
   if(window.confirm("Confirm Account Creation"))
   {
       console.log("Creating Account");
       var department  = document.getElementById("selectDpt");
       console.log("Dpt ID: "+ department.value);

       var firstName  = document.getElementById("firstName");
       console.log("First Name: "+ firstName.value);

       var lastName  = document.getElementById("lastName");
       console.log("Nast Name: "+ lastName.value);

       var email  = document.getElementById("email");
       console.log("Email: "+ email.value);
       axios.post("https://qacvmanager.azurewebsites.net/api/department/"+department.value+ "/user",
       {
           "firstName" : firstName.value,
           "lastName": lastName.value,
           "email" : email.value,
           "password": "defaultPass"
       })
       firstName.value ="";
       lastName.value ="";
       email.value ="";
       console.log("Account Created!");
   }
 };
 render() {
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
}

export default CreateAccount;

