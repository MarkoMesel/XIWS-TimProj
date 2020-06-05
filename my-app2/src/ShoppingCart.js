import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab';
import BackupPhoto from './img/t2.jpg';
import { FormCheck } from 'react-bootstrap';
import './ShoppingCart.css';
import './index.css';


class ShoppingCart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            daysarray: '',
            cars: [],
            numberOfDays: sessionStorage.getItem('numberOfDays'),
            startDate: sessionStorage.getItem('StartDate'),
            endDate: sessionStorage.getItem('EndDate'),
            userMileage: sessionStorage.getItem('UserMileage'),

            carsConfirmed: [],
        }
        this.confirmedRent = this.confirmedRent.bind(this);

    }

    componentDidMount() {

        let selectedCars = sessionStorage.getItem("SelectedCars");
        selectedCars = JSON.parse(selectedCars);

        if (selectedCars === null) {
            return <h1>Your Cart is empty</h1>
        } else {
            var uniqueSelectedCars = selectedCars.filter(function (item, pos) {
                return selectedCars.indexOf(item) === pos;
            });

            uniqueSelectedCars.map((k) => {
                /*
                post umesto geta
                newformdata = startdate, enddate, k
                axios.post(newformdata,axiosconfig)
                
                */ 
                axios.get("https://localhost:8085/car/getCar" + k)
                    .then(res => {
                        const cars = res.data;
                        this.setState({ cars: this.state.cars.concat(cars) });
                    })
            });
        }
    }

    confirmedRent(event){

    }

    render() {

        let cars = this.state.cars;
        const userMileage = this.state.userMileage;

        console.log("user mileage" + userMileage);
        const mapCars = cars.map((car) => {
            return <div className="tcolummn" key={car.id}>
                <div className="tcolumn">
                    <img alt={BackupPhoto} className="tphoto" src={'http://localhost:8082/car/getImage/' + car.images[0]}></img>
                </div>

                <div className="tcolumn2">
                    <ul className="list-group">
                        <li >{car.manufacturerName} {car.modelName} ID: {car.id} </li>
                        <li>Price for {this.state.numberOfDays} days: ${car.pricePerDay * this.state.numberOfDays} </li>

                        {userMileage === null &&
                            <div>
                                <li>Mileage penalty: ${car.mileagePenalty} per km</li>
                                <li>Total price: $ {car.pricePerDay * this.state.numberOfDays}</li>
                            </div>
                        }
                        {userMileage !== null && car.mileageThreshold < userMileage &&
                            <div>
                                <li>Total mileage penalty: ${car.mileagePenalty * (userMileage - car.mileageThreshold)}</li>
                                <li>Total price: ${car.pricePerDay * this.state.numberOfDays + car.mileagePenalty * (userMileage - car.mileageThreshold)}</li>
                            </div>
                        }
                    </ul>
                    <div className="row">
                        <div className="col-sm-5">
                            <div className="form-group">
                                <span className="form-label">Confirm car</span>
                                <FormCheck type="checkbox" onChange={this.confirmedRent} />
                            </div>
                        </div>
                        <div className="col-sm-5">
                            <div className="form-group">
                                <span className="form-label">Bundle car</span>
                                <FormCheck type="checkbox" onChange={this.handleToggle} />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        });



        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Your Shopping cart:</h2>
                {mapCars}
            </div>
        );
    }
}
export default ShoppingCart;
