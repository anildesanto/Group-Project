import React, { Component } from "react";

class CVDashboard extends Component {
  render() {
    return (
      <form>
        <div>
          <button className="btn btn-secondary btm-sm m-2">Browse</button>

          <button className="btn btn-secondary btm-sm m-2">Upload CV</button>

          <button className="btn btn-secondary btm-sm m-2">Flagged CV's</button>

          <button className="btn btn-secondary btm-sm">Q</button>

          <input type="text" name="name" />
        </div>

        <div>
          <button className="btn btn-secondary btm-sm m-2">Download CV</button>

          <button className="btn btn-secondary btm-sm m-2">
            Flag / Unflag CV
          </button>

          <button className="btn btn-danger btm-sm m-2">Delete CV</button>
        </div>
      </form>
    );
  }
}

export default CVDashboard;
