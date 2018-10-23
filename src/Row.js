import React from 'react';

const Row = (props) => {
  return (
    <tr>
      <td>{props.userId}</td>
      <td>{props.firstName} {props.lastName}</td>
      <td>{props.email} </td>
      <td>{props.department.role} </td>
    </tr>
  );
};
export default Row;

