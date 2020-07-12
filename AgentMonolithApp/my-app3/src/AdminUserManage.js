import React, { Component } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
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
            'https://localhost:8080/schedule/comments/pending', axiosConfig)
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