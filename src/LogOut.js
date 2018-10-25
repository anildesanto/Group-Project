import React, { Component } from "react";
import { Redirect  } from 'react-router'


class LogOut extends Component {

state = {toHome: false}

    handleSubmit = (event) => {
        event.preventDefault();
          window.location.assign("#/")
          window.location.reload("#/")
    }

    render() {

        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                <button
                    id="btnLogout"
                    className="button"
                    onClick={(event) => this.setState({toHome: true})}>
                    Log Out
                </button>
                </form>
            </div>
        );
    }
}

export default LogOut;