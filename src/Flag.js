import React, { Component } from "react";
import axios from 'axios';
class Flag extends Component {
    constructor(props) {
        super(props);
        this.statusSelector = null;
        this.setStatusSelector = element => {
            
            this.statusSelector = element;
            if(this.statusSelector != null)
            {
                this.statusSelector.value = this.props.status;
                console.log("Viewer ID:  " + this.props.viewerId);
                if (this.props.viewerId == 1 | this.props.viewerId == 6) {
                    this.statusSelector.disabled = true;
                }
                console.log("Element : " + element);
            }
        }
        this.updateStatus = (e) => {
            var setStatus = "https://qacvmanager.azurewebsites.net/api/cv/" + this.props.cvId + "/status/";
            console.log("Obj: " + this.statusSelector.value);
            if (window.confirm("Confirm Status")) {
                axios.get(setStatus + this.statusSelector.value);
                //window.open(setStatus + this.statusSelector.value);
            }
        }
    }
    render() {
        return (
            <div>
                <select onChange={this.updateStatus} ref={this.setStatusSelector}  >
                    <option value="Gray" >Gray</option>
                    <option value="Green">Green</option>
                    <option value="Red">Red</option>
                </select>
            </div>
        );
    }

}
export default Flag;
