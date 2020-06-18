import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab';
import './ShoppingCart.css';
import './index.css';
import { Button, ButtonGroup, Card } from 'react-bootstrap';
import './MoreDetails.css';

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class ShoppingCart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bundles: [],
            publisherNameList: [],
            publisherIdList: [],
            pubIdList2: '',
            multipleOccurencesId: '',
        }
        this.handleRemoveFromCart = this.handleRemoveFromCart.bind(this);
        this.handleBundle = this.handleBundle.bind(this);
        this.handleUnbundle = this.handleUnbundle.bind(this);
        this.handleDupes = this.handleDupes.bind(this);
        this.handleSubmitCart = this.handleSubmitCart.bind(this);
    }

    componentDidMount() {

        if (localStorage.getItem('roleId') !== '1') {
            this.props.history.push("/login");
        }

        axios.get(
            'https://localhost:8085/schedule/cart', axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska u get cart');
                    const responsebundles = response.data;
                    this.setState({ bundles: responsebundles });

                    this.state.bundles.map((k) => {

                        const pubNameList = this.state.publisherNameList;
                        const pubIdList = this.state.publisherIdList;

                        pubNameList.push(k.publisherName);
                        pubIdList.push(k.publisherId);
                        this.setState({ pubIdList2: pubIdList });
                    });
                }
            }).catch(response => {
                console.log('greska u get cart');
            });
        this.setState({ state: this.state });
    }

    handleRemoveFromCart = (event) => {

        const reservationId = event.target.value;
        let deleteLink = 'https://localhost:8085/schedule/cart/' + event.target.value;

        axios.delete(deleteLink,
            axiosConfig,
            reservationId)
            .then(response => {
                if (response.status === 200) {
                    console.log('greska u remove from cart')
                }
            }).catch(response => {
                console.log("greska u remove from cart");
            });
    }
    handleBundle = (event) => {

        const bundleData = {
            publisherId: 1,
            publisherTypeId: 2
        }

        axios.post('https://localhost:8085/schedule/cart/bundle', bundleData, axiosConfig)
            .then(response => {
                console.log("bundle radi");
                window.location.reload(true);

            }).catch(response => {
                console.log("bundle ne radi");
            })
    }

    handleUnbundle = (event) => {
        const unbundleData = {
            publisherId: event.target.value,
            publisherTypeId: 2
        }
        axios.post('https://localhost:8085/schedule/cart/unbundle', unbundleData, axiosConfig)
            .then(response => {
                console.log("unbundle radi");
                window.location.reload(true);

            }).catch(response => {
                console.log("unbundle ne radi");
            })
    }

    handleDupes() {
        (function (a) {
            let map = new Map();

            a.forEach(e => {
                if (map.has(e)) {
                    let count = map.get(e);
                    console.log(count)
                    map.set(e, count + 1);
                } else {
                    map.set(e, 1);
                }
            });

            let hasDup = false;
            let dups = [];
            map.forEach((value, key) => {
                if (value > 1) {
                    hasDup = true;
                    dups.push(key);
                }
            });
            console.log(dups);
            sessionStorage.setItem("dups", dups);
        })(this.state.pubIdList2);
    }

    handleSubmitCart(){

    }

    render() {
        let dupes = sessionStorage.getItem('dupes');
        let bundles = this.state.bundles;

        const mapCart = bundles.map((bundle) => {
            return <div key={bundle.bundleId}>
                <Card>
                    {bundle.cars.map((car) =>
                        <div key={car.carId}>
                            <div className="column5">
                                <p> Agent ID: {car.publisherId}</p>
                                <p> Agent Type: {car.publisherTypeId}</p>

                                <p>Car name: {car.manufacturerName} {car.modelName} {car.carId}</p>
                                <p>Total Price: $ {car.totalPrice}</p>

                                <ButtonGroup>
                                    <Button className="buttonSend5" value={car.publisherId} disabled={!(bundle.cars.length === 1)} onClick={this.handleBundle}>Bundle</Button>
                                    <Button className="buttonSend5" value={car.publisherId} disabled={(bundle.cars.length === 1)} onClick={this.handleUnbundle}>Unbundle</Button>
                                    <Button className="buttonRemoveFromCart5" id={car.reservationId} value={car.reservationId} onClick={this.handleRemoveFromCart}>Remove</Button>
                                    <Button className="ttt" id={car.reservationId} value={car.reservationId} onClick={this.handleDupes}>Do list</Button>
                                </ButtonGroup>
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
                <h2 className="ttitle">Your Shopping cart:</h2>
                {mapCart}
                <Button className="ordersubmit" onClick={this.handleSubmitCart}> Confirm Order </Button>
            </div>
        );
    }
}
export default ShoppingCart;
