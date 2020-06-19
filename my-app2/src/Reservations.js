import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Carousel, CarouselItem, Container, Card, Button, ButtonGroup } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import addDays from 'date-fns/addDays';
import './homepage.css';
import { format } from 'date-fns';
import ImageUploading from "react-images-uploading";
import './addImages.css'
import './MoreDetails.css';
import './ShoppingCart.css';

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class Reservations extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bundles: [],
        };
        this.handleOpenChat = this.handleOpenChat.bind(this);
    }

 componentDidMount(){

    axios.get('https://localhost:8085/schedule/reservations/user',axiosConfig)
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

 render() {
    let bundles = this.state.bundles;

    const mapCart = bundles.map((bundle) => {
        return <div key={bundle.bundleId}>
            <div>
            <h3>Status: {bundle.stateName}</h3>
            <Button value={bundle.bundleId} onClick={this.handleOpenChat}>Open Chat</Button>
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
export default Reservations;
