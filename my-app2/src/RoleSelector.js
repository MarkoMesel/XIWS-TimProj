import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './addImages.css'
import './MoreDetails.css';
import './ShoppingCart.css';



class RoleSelector extends Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount() {
        const roleSwitch = localStorage.getItem('roleId');
        console.log(roleSwitch);
        console.log(localStorage.getItem('roleId'));
        switch (roleSwitch) {
            case '1':
                this.props.history.push('/homepage');
                break;
            case '2':
                this.props.history.push('/adminhome');
                break;
            case '3':
                this.props.history.push('/adminhome');
                break;
            default:
                this.props.history.push('/login');
                break;
        }
    }

    render() {

        return (
            <div className="body">
                test
            </div>
        );
    }
}
export default RoleSelector;
