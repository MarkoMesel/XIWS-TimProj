import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown, Input } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button, ButtonGroup, Card } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import addDays from 'date-fns/addDays';
import './homepage.css';
import { format } from 'date-fns';
import { TextField } from 'material-ui';
import './MoreDetails.css';

const axiosConfig = {
    headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'token': localStorage.getItem('token')
    }
};

class AdminRateManage extends Component {

    constructor(props) {
        super(props)
        this.state = {

        }

    }

    componentDidMount() {

        if (localStorage.getItem('roleId') !== '3') {
            this.props.history.push("/login");
        }
        axios.get(
            'https://localhost:8085/schedule/comments/pending', axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska u ucitavanju admin komentara');
                    const responsebundles = response.data;
                    this.setState({ reviews: responsebundles });
                }
            }).catch(response => {
                console.log('greska admin comment load');
            });

        this.setState({ state: this.state });
    }

    render() {
        return (
            <div>test</div>
        );
    }
} export default AdminRateManage;