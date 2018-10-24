import React from 'react';

const Cvrow = (props) => {
  var downloadUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/download';
  var downIco = "https://image.flaticon.com/icons/svg/138/138601.svg"
  var deleteUrl = 'https://qacvmanager.azurewebsites.net/api/cv/'+props.cvId+'/delete';
  var delIco = "https://image.flaticon.com/icons/png/128/138/138767.png"
  
  
  return (
    <tbody>
    <tr className="row">
      {/* <td className="cell">{props.cvId}</td> */}
      <td className="cell">{props.status}</td>
      <td className="cell"><a href={downloadUrl}><img src={downIco} alt="Download"  /></a></td>
      <td className="cell"><a href={deleteUrl}><img src={delIco} alt="Delete"  /></a></td>
     </tr></tbody>
   
  );
};
export default Cvrow;

