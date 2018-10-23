import React from 'react';
import Row from './Row.js'
import Cvrow from './Cvrow.js'

class Table extends React.Component {
    state = { userName: '', userId: '' }
    handleReset = (event) => {
        var empty = [];
        this.props.onClick(empty);
    };
    render() {
        return (
            <div className="tableContainer">
                <table className="table left">
                    <tbody>
                        <tr>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                        </tr>
                        {this.props.rows.map(row => <Row key={row.userId} {...row} />)}
                        </tbody>
                </table>
                <button onClick={this.handleReset}>Back</button>
                <table className="table right">
                    <tbody>
                        <tr>
                            <th>CV ID</th>
                            <th>Status</th>
                            <th>Download</th>
                            <th>Delete</th>
                        </tr>
                        {this.props.cvs.map(cv => <Cvrow key={cv.cvId} {...cv} />)}
                    </tbody>
                </table>

            </div>
        );
    }
}

export default Table;



