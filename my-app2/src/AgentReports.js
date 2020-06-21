import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Card, Button, ButtonGroup } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './addImages.css'
import './MoreDetails.css';
import './ShoppingCart.css';
import { Input } from 'semantic-ui-react';


const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class AgentReports extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bundles: [],
            mileage: '',
            comment: '',
        };
        this.handleReport = this.handleReport.bind(this);
        this.handleMileage = this.handleMileage.bind(this);
        this.handleMessageText = this.handleMessageText.bind(this);
    }

    componentDidMount() {

        axios.get('https://localhost:8085/schedule/reservations/report/pending', axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('Nije greska u mount');
                    const responsebundles = response.data;
                    this.setState({ bundles: responsebundles });
                }
            }).catch(response => {
                console.log('greska u mount');
            });
    }



    handleReport = (event) => {
        let reportData = {
            reservationId: event.target.value,
            mileage: this.state.mileage,
            commnet: this.state.comment
        };

        if (this.state.mileage !== null) {
            axios.post(
                'https://localhost:8085/schedule/reservations/report', reportData, axiosConfig
            ).then(response => {
                if (response.status === 200) {
                    console.log("Reported");
                    /*window.location.reload(true);*/
                    console.log(reportData);
                }
            }).catch(response => {
                console.log('greska Report');
            });
        }
    }

    handleMileage = (event) => {
        const text = event.target.value;
        this.setState({ mileage: text });
    }

    handleMessageText = (event) => {
        const text = event.target.value;
        this.setState({ comment: text });
    }

    render() {
        let cars = this.state.bundles;

        const mapCart = cars.map((car) => {
            return <div key={car.reservationId}>

                <Card>

                    <div className="group">
                        <p className="messagesSize">Car ID: {car.carId}</p>
                        <p className="messagesSize">Car name: {car.manufacturerName} {car.modelName} </p>
                        <p className="messagesSize">Old Mileage: {car.mileage}</p>

                        <Input className="messagesSize" onChange={this.handleMileage} placeholder={"Mileage"}></Input>
                        <Input className="messagesSize"  onChange={this.handleMessageText} placeholder={"Comment"}></Input>
                        <br></br>
                        <Button className="messagesSize" value={car.reservationId} onClick={this.handleReport}>Report</Button>

                    </div>
                </Card>
            </div>
        });
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Your reservations:</h2>
                {mapCart}
                <hr></hr>

            </div>
        );
    }
}
export default AgentReports;
