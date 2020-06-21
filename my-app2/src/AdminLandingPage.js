import React, { Component } from 'react';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import "react-datepicker/dist/react-datepicker.css";



class MessageBoard extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <NavigationTab></NavigationTab>
            );
    }
}
export default MessageBoard;
