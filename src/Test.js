import React from 'react';

class Test extends React.Component {

  render() {
      return (
        <h1>{this.props.loginInfo[0]}</h1>
      );
  }
}

export default Test;