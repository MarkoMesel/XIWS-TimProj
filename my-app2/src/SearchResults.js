import React, { Component } from 'react';
import './SearchResults.css';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-bootstrap/Accordion';
import BackupPhoto from './img/t2.jpg';
import 'semantic-ui-css/semantic.min.css';
import NavigationTab from './NavigationTab';
import { Button, ButtonGroup } from 'react-bootstrap';
import axios from 'axios';

const axiosConfig = {
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    }
};


class SearchResults extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cars: [],
            singleCar: [],
            currentPage: 1,
            carsPerPage: 4,
            selectedCars: [],
            roleId: localStorage.getItem("roleId"),
            startDateStored: sessionStorage.getItem("startDate"),
            endDateStored: sessionStorage.getItem("endDate"),
            collisionWarrantStored: sessionStorage.getItem("collisionWarranty"),

            carsBundle: [],
            cartCarId: [],
            cartCarId2: '',

        }
        this.handleClick = this.handleClick.bind(this);
        this.moreDetailsPressed = this.moreDetailsPressed.bind(this);
        this.sortByPrice = this.sortByPrice.bind(this);
        this.sortbyMileage = this.sortbyMileage.bind(this);
        this.handleAddToCartPressed = this.handleAddToCartPressed.bind(this);
        this.sortbyRating = this.sortbyRating.bind(this);
    }

    componentDidMount() {



        let searchData = sessionStorage.getItem("searchData");       
        axios.post(
            'https://localhost:8085/car/search',
            searchData, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('Nije greska u mount');

                    const cars = response.data;
                    this.setState({ cars });
                }
            }).catch(response => {
                console.log('greska u mount');
            });

    }

    handleAddToCartPressed = (event) => {

        const cartData = {
            startDate: this.state.startDateStored,
            endDate: this.state.endDateStored,
            collisionWaranty: this.state.collisionWarrantStored,
            carId: event.target.value,
        } 
        const axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'token': localStorage.getItem("token")
            }
        };

        axios.post('https://localhost:8085/schedule/cart', 
            cartData, axiosConfig)
            .then(response => {
                if (response.status === 204) {
                    console.log('Nije greska u add to cart');
                }
            }).catch(response => {
                console.log('greska u add to cart');
            });

        const selectedCars = this.state.selectedCars;
        selectedCars.push(event.target.value);
        sessionStorage.setItem("SelectedCars", JSON.stringify(selectedCars))

    }

    handleClick(event) {
        this.setState({
            currentPage: event.target.id
        });
    }

    moreDetailsPressed = (event) => {
        this.setState({
            chosenCar: event.target.value
        })
        sessionStorage.setItem('carDetailsId', event.target.value);
        window.open("/moredetails/");
    }

    sortByPrice() {
        this.setState({
            cars: this.state.cars.sort((a, b) => a.totalPrice - b.totalPrice)
        })
    }

    sortbyMileage() {
        this.setState({
            cars: this.state.cars.sort((a, b) => a.mileage - b.mileage)
        })
    }

    sortbyRating() {
        this.setState({
            cars: this.state.cars.sort((a, b) => b.rating - a.rating)
        })
    }

    render() {
        let cars = this.state.cars;
        const { currentPage, carsPerPage } = this.state;
        const indexofLastCar = currentPage * carsPerPage;
        const indexofFirstCar = indexofLastCar - carsPerPage;
        const currentCars = cars.slice(indexofFirstCar, indexofLastCar);

        const renderCars = currentCars.map((car) => {
            return <div className="colummn" key={car.id}>
                <div className="column">
                    <img alt={BackupPhoto} className="photo" src={'https://localhost:8085/car/image/' + car.images[0]}></img>
                </div>

                <div className="column2">
                    <ul className="list-group">
                        <li className="list-group-item">Collision waranty price: {car.collisionWaranty}</li>
                        <li className="list-group-item">Number of child seats: {car.childSeats}</li>
                        <li className="list-group-item">Mileage: {car.mileage} km</li>
                        <li className="list-group-item">Mileage threshold: {car.mileageThreshold} km</li>
                        <li className="list-group-item">Mileage penalty: ${car.mileagePenalty} per km</li>
                        <li className="list-group-item">Estimated penalty: ${car.estimatedPenalty} per km</li>
                        <li className="list-group-item">Discount: $ {car.discount}</li>
                    </ul>

                    <Button variant="primary" value={car.id} size="md" onClick={this.moreDetailsPressed} >
                        More details
                            </Button>
                    <Button variant="primary" disabled={this.state.roleId!=='1'} id={car.id} value={car.id} className="btn" size="md" onClick={this.handleAddToCartPressed} >
                        Add to Cart
                            </Button>
                </div>

                <div className="column2">
                    <ul className="list-group">
                        <li className="list-group-item">{car.manufacturerName} {car.modelName} ID:{car.id}</li>
                        <li className="list-group-item">Class: {car.carClassName}</li>
                        <li className="list-group-item">Transmission: {car.transmissionTypeName}</li>
                        <li className="list-group-item">Fuel type: {car.fuelTypeName}</li>
                        <li className="list-group-item">Price: ${car.price}</li>
                        <li className="list-group-item">Total price: ${car.totalPrice}</li>
                        <li className="list-group-item">Average rating: {car.rating}</li>
                        <li className="list-group-item">Rented by: {car.publisherName}</li>
                    </ul>
                </div>
            </div>
        });

        // Logic for displaying page numbers
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(cars.length / carsPerPage); i++) {
            pageNumbers.push(i);
        }

        const renderPageNumbers = pageNumbers.map(number => {
            return (
                <div className="buttons-nav" key={number} >
                    <Button size="sm" key={number}
                        id={number}
                        onClick={this.handleClick}>{number}</Button>
                </div>
            );
        });

        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <ButtonGroup className="button-group" aria-label="Basic example">
                    <Button size="sm" onClick={this.sortByPrice}> Sort by Price</Button>
                    <Button size="sm" onClick={this.sortbyMileage}> Sort by Mileage</Button>
                    <Button size="sm" onClick={this.sortbyRating}> Sort by Rating</Button>
                </ButtonGroup>
                {renderCars}
                <nav aria-label="pagination-tab">
                    <ul className="pagination">
                        {renderPageNumbers}
                    </ul>
                </nav>
            </div >
        );
    }
}
export default SearchResults;

