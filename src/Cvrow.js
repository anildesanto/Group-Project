import React from 'react';

const Cvrow = (props) => {
  var downloadUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/download';
  var downIco = "https://image.flaticon.com/icons/svg/138/138601.svg"
  var deleteUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/delete';
  var delIco = "https://image.flaticon.com/icons/png/128/138/138767.png"
  
  return (
    <tr>
      <td>{props.cvId}</td>
      <td>{props.status}</td>
      <td><a href={downloadUrl}><img src={downIco} alt="Download"  /></a></td>
      <td><a href={deleteUrl}><img src={delIco} alt="Delete"  /></a></td>
    </tr>
  );
};
export default Cvrow;

