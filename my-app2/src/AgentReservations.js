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

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class AgentReservations extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bundles: [],
        };
        this.handleOpenChat = this.handleOpenChat.bind(this);
        this.handleApprove = this.handleApprove.bind(this);
        this.handleReject = this.handleReject.bind(this);
    }

 componentDidMount(){

    axios.get('https://localhost:8085/schedule/reservations/publisher',axiosConfig)
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

 handleOpenChat = (e)  =>{
    sessionStorage.setItem('chatId', e.target.value); 
    this.props.history.push('/messages');
 }

 handleApprove = (event) => {
    let approveLink = 'https://localhost:8085/schedule/bundle/' + event.target.value + '/approve';
    let body = null;

    axios.post(
        approveLink,body,axiosConfig
    ).then(response =>{
        if(response.status ===200){
            console.log("Approved");
            window.location.reload(true);
        }
    }).catch(response => {
        console.log('greska Approve');
    });
 }

 handleReject = (event) => {
    let rejectLink = 'https://localhost:8085/schedule/bundle/' + event.target.value + '/reject';
    let body = null;

    axios.post(
        rejectLink,body,axiosConfig
    ).then(response =>{
        if(response.status ===200){
            console.log("Rejected");
            window.location.reload(true);
        }
    }).catch(response => {
        console.log('greska Rejected');
    });
 }

 render() {
    let bundles = this.state.bundles;

    const mapCart = bundles.map((bundle) => {
        return <div key={bundle.bundleId}>
            <div>
            <h3>Status: {bundle.stateName}</h3>
            <ButtonGroup>
            <Button value={bundle.bundleId} disabled={!(bundle.stateName==="PAID"||bundle.stateName==="COMPLETED")} onClick={this.handleOpenChat}>Open Chat</Button>
            <Button value={bundle.bundleId} disabled={!(bundle.stateName==="PENDING")} onClick={this.handleReject}>Reject</Button>
            <Button value={bundle.bundleId} disabled={!(bundle.stateName==="PENDING")} onClick={this.handleApprove}>Approve</Button>
            </ButtonGroup>
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
                        <img alt={'https://tipkingdom.com/wp-content/uploads/2018/05/windows_bug6-100581894-primary-idge.jpg'} className="photo5" src={'https://localhost:8085/car/image/' + car.images[0]}></img>
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
export default AgentReservations;
