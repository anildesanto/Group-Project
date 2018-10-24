import React from 'react';
import Row from './Row.js'

class UserTable extends React.Component {
    state = { userName: '', userId: '', prevId: '' }
    handleReset = (event) => {
        var empty = [];
        this.props.onClick(empty);
    };
    
    render() {
        return (
            <div className="tableLeft">

                        <table className="table">
                            <thead>
                                <tr className="row header" id="userTable">
                                    <th className="cell">User ID</th>
                                    <th className="cell">Name</th>
                                    <th className="cell">Email</th>
                                    <th className="cell">Role</th>
                               
                                </tr> </thead>
                                {this.props.rows.map(row => <Row key={row.userId} onClick={this.props.onClick} cvs={this.props.cvs} prevId={this.state.prevId} {...row} />)}
                            

                        </table>
                    </div>
        );
    }
}

export default UserTable;



