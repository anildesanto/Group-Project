import React from 'react';
import axios from 'axios';

var setStatus = "https://qacvmanager.azurewebsites.net/api/department/"

class FilterFlag extends React.Component {
  state = { deptId: '' }
  constructor(props) {
    super(props);
    this.statusSelector = null;
    this.setStatusSelector = element => {

      this.statusSelector = element;
      //console.log("Element : " + element);
    }
  }


  handleDept = (event) => {
    event.preventDefault();
    //console.log("CV Event: onSubmitCV", this.statusSelector);
    var path = "";
    if(this.statusSelector.value === '')
      path = `https://qacvmanager.azurewebsites.net/api/cv/`;
    else 
      path = `https://qacvmanager.azurewebsites.net/api/cv/status/${this.statusSelector.value}/`;

      axios.get(path)
        .then(response => {
          //console.log(response.data);
          this.props.onChange(response.data);
        })
    }

  render() {
    return (
      <div>
        
        <select  onChange={this.handleDept} ref={this.setStatusSelector} >
          <option value="">All</option>
          <option value="red" >Red</option>
          <option value="green">Green</option>
          <option value="gray">Gray</option>
        </select>
      </div>
    );


  }
}

export default FilterFlag;