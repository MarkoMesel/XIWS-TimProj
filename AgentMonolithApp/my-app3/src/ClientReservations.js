import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Card, Button } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './addImages.css'
import './MoreDetails.css';
import './ShoppingCart.css';
import t1 from './img/backup.png';

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class ClientReservations extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bundles: [],
        };
        this.handleOpenChat = this.handleOpenChat.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }

    componentDidMount() {

        axios.get('https://localhost:8080/schedule/reservations/user', axiosConfig)
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

    handleOpenChat = (e) => {
        sessionStorage.setItem('chatId', e.target.value);
        this.props.history.push('/messages');
    }
    
    handleCancel = (event) => {
        let cancelLink = 'https://localhost:8080/schedule/bundle/' + event.target.value + '/cancel';
        let body = null;

        axios.post(
            cancelLink, body, axiosConfig
        ).then(response => {
            if (response.status === 200) {
                console.log("Cancelled");
                window.location.reload(true);
            }
        }).catch(response => {
            console.log('greska cancel');
        });
    }

    render() {
        let bundles = this.state.bundles;

        const mapCart = bundles.map((bundle) => {
            return <div key={bundle.bundleId}>
                <div>
                    <h3>Status: {bundle.stateName}</h3>
                    <Button value={bundle.bundleId} onClick={this.handleOpenChat} disabled={!(bundle.stateName === "PAID" || bundle.stateName === "COMPLETED")}>Open Chat</Button>
                    <Button value={bundle.bundleId} disabled={!(bundle.stateName === "PENDING")} onClick={this.handleCancel}>Cancel Order</Button>
                </div>
                <Card>
                    {bundle.cars.map((car) =>
                        <div key={car.carId}>
                            <div className="column5">
                                <p> Agent ID: {car.publisherId}</p>
                                <p> Agent Type: {car.publisherTypeId}</p>

                                <p>Car name: {car.manufacturerName} {car.modelName} {car.carId}</p>
                                <p>Total Price: $ {car.totalPrice}</p>

                            </div>

                            <div className="column25">
                                <img alt={t1} className="photo5" src={'https://localhost:8080/car/image/' + car.images[0]}></img>
                            </div>
                        </div>

                    )}
                </Card>
            </div>
        });
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Your reservations:</h2>
                <hr></hr>
                {mapCart}
            </div>
        );
    }
}
export default ClientReservations;
