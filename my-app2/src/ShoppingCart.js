import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab';
import BackupPhoto from './img/t2.jpg';
import { FormCheck } from 'react-bootstrap';
import './ShoppingCart.css';
import './index.css';
import { Button, ButtonGroup } from 'react-bootstrap';

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
        }
        this.handleRemoveFromCart = this.handleRemoveFromCart.bind(this);
        this.handleBundle = this.handleBundle.bind(this);
        this.handleUnbundle = this.handleUnbundle.bind(this);
        this.handleDupes = this.handleDupes.bind(this);
    }

    componentDidMount() {

        if (localStorage.getItem('token') === null) {
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
                        this.setState({pubIdList2: pubIdList});
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
            publisherId: event.target.value,
            publisherTypeId: 2
        }

        axios.post('https://localhost:8085/schedule/cart/bundle', axiosConfig, bundleData)
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

        axios.post('https://localhost:8085/schedule/cart/unbundle', axiosConfig, unbundleData)
            .then(response => {
                console.log("unbundle radi");
                window.location.reload(true);

            }).catch(response => {
                console.log("unbundle ne radi");
            })
    }

    handleDupes = (event) =>{
        (function(a){
            let map = new Map();
          
            a.forEach(e => {
              if(map.has(e)) {
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
              if(value > 1) {
                hasDup = true;
                dups.push(key);
              }
            });
             console.log(dups);
             return hasDup;
           })(this.state.pubIdList2);
    }

    render() {
        let bundles = this.state.bundles;

        const mapCart = bundles.map((bundle) => {
            return <div className="colummn5" key={bundle.bundleId}>

                {bundle.cars.map((car) =>
                    <div className="colummn5" key={car.carId}>
                        <div className="group">
                            <ul> Agent ID: {car.publisherId}</ul>
                            <ul> Agent Type: {car.publisherTypeId}</ul>

                            <ul>Car name: {car.manufacturerName} {car.modelName} {car.carId}</ul>
                            <ul>Total Price: $ {car.totalPrice}</ul>
                            <Button className="buttonSend5" value={car.publisherId} disabled={!(bundle.cars.length === 1)} onClick={this.handleBundle}>Bundle</Button>
                            <Button className="buttonSend5" value={car.publisherId} disabled={(bundle.cars.length === 1)} onClick={this.handleUnbundle}>Unbundle</Button>
                            <Button className="buttonRemoveFromCart5" id={car.reservationId} value={car.reservationId} onClick={this.handleRemoveFromCart}>Remove</Button>
                            <Button className="ttt" id={car.reservationId} value={car.reservationId} onClick={this.handleDupes}>Do list</Button>

                        </div>
                    </div>
                )}
            </div>
        });
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Your Shopping cart:</h2>
                {mapCart}
            </div>
        );
    }
}
export default ShoppingCart;
