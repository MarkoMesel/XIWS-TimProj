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

class SearchResults extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cars: [],
            singleCar: [],
            currentPage: 1,
            carsPerPage: 4,
            selectedCars: [],
        }
        this.handleClick = this.handleClick.bind(this);
        this.handleButtonPressed = this.handleButtonPressed.bind(this);
        this.sortByPrice = this.sortByPrice.bind(this);
        this.sortbyMileage = this.sortbyMileage.bind(this);
        this.handleAddToCartPressed = this.handleAddToCartPressed.bind(this);
        this.sortbyRating = this.sortbyRating.bind(this);
    }

    componentDidMount() {
        axios.get(`http://localhost:8082/car/search`)
            .then(res => {
                const cars = res.data;
                this.setState({ cars });
              
            })
    }

    handleAddToCartPressed = (event) => {

        const selectedCars = this.state.selectedCars;
    
        selectedCars.push(event.target.value);
        sessionStorage.setItem("SelectedCars",JSON.stringify(selectedCars))
        
    }

    handleClick(event) {
        this.setState({
            currentPage: event.target.id
        });
    }

    handleButtonPressed = (event) => {
        this.setState({
            chosenCar: event.target.value
        })
        let moreDetailsLink = "/productlisting/" + event.target.value;
        window.open(moreDetailsLink, "_blank");
    }

    sortByPrice() {
        this.setState({
            cars: this.state.cars.sort((a, b) => a.pricePerDay - b.pricePerDay)
        })
    }

    sortbyMileage() {
        this.setState({
            cars: this.state.cars.sort((a, b) => a.mileage - b.mileage)
        })   
    }

    sortbyRating() {
        this.setState({
            cars: this.state.cars.sort((a, b) => b.carRating - a.carRating)
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
                    <img alt={BackupPhoto} className="photo" src={'http://localhost:8082/car/getImage/' + car.images[0]}></img>
                </div>

                <div className="column2">
                    <ul className="list-group">
                        <li className="list-group-item">Collision waranty: {String(car.collisionWaranty)}</li>
                        <li className="list-group-item">Number of child seats: {car.childSeats}</li>
                        <li className="list-group-item">Mileage: {car.mileage} km</li>
                        <li className="list-group-item">Mileage threshold: {car.mileageThreshold} km</li>
                        <li className="list-group-item">Mileage penalty: ${car.mileagePenalty} per km</li>
                    </ul>
                    <Button variant="primary" value={car.id} size="md" onClick={this.handleButtonPressed} >
                        More details
                            </Button>
                    <Button variant="primary" value={car.id} className="btn" ref={btn => { this.btn = btn; }} disabled={false}
                        size="md" onClick={this.handleAddToCartPressed} >
                        Add to Cart
                            </Button>
                </div>
                <div className="column2">
                    <ul className="list-group">
                        <li className="list-group-item">{car.manufacturerName} {car.modelName} ID: {car.id}</li>
                        <li className="list-group-item">Class: {car.carClass}</li>
                        <li className="list-group-item">Transmission: {car.transmission}</li>
                        <li className="list-group-item">Fuel type: {car.fuelType}</li>
                        <li className="list-group-item">Price per day: ${car.pricePerDay}</li>
                        <li className="list-group-item">Average rating: {car.carRating}</li>

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

