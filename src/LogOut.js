import React, { Component } from "react";
import LogIn from './LogIn.js'
import { Route, NavLink, HashRouter } from "react-router-dom";

class LogOut extends Component {

      onSubmit = () => {
        // return (
        //     <HashRouter>
        //         <div className="content">
        //             <Route exact path="/login" component={LogIn} />
        //         </div>
        //     </HashRouter>
        //     );
            window.location.replace("http://localhost:3000/user#/login")
      }

    render() {
        return (
            <div>
          <button
            id="btnLogout"
            className="button"
            onClick={() => this.onSubmit()}>
            Log Out
          </button>
          </div>
        );
    }
}

export default LogOut;