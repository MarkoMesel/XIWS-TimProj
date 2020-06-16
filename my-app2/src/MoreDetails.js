import React, { Component } from 'react';
import { Carousel, CarouselItem, Container, Card} from "react-bootstrap";

import './MoreDetails.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import NavigationTab from "./NavigationTab";
import axios from 'axios';


class MoreDetails extends Component {
    constructor(props) {
        super(props)
        this.state = {
            car: [],
            images: [],
            rating: '',
            reviews: [],
        };
        this.ratingChanged = this.ratingChanged.bind(this);
    }

    componentDidMount() {

        const axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        };

        const carData = {
            id: sessionStorage.getItem('carDetailsId'),
            startDate: sessionStorage.getItem('StartDate'),
            endDate: sessionStorage.getItem('EndDate'),
            plannedMileage: sessionStorage.getItem('plannedMileage')
        }
        
        let postData = JSON.stringify(carData);


        axios.post(
            'https://localhost:8085/car/getCar',
            postData, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska');
                    const car = response.data;
                    this.setState({ car });
                    const images = response.data.images;
                    this.setState({ images });
                }
            }).catch(response => {
                console.log('greska');
            });
    }

    ratingChanged = (newRating) => {
        console.log(newRating)
        this.setState({
            rating: newRating
        })
    }

    render() {
        let car = this.state.car;
        let images = this.state.images;
        let reviews = this.state.reviews;

        const reviewsMap = reviews.map((r) => {
            return <div key={r}>
                <Card className="review-card">
                <p className="lead">Rating: {r.rating}</p>
                <p className="lead">Comment: {r.comment}</p>
                </Card>
            </div>

        });

        const renderImages = images.map((im) => {
            return <CarouselItem key={im}>
                <img
                    className="d-block w-100"
                    src={'https://localhost:8085/car/image/' + im}
                    alt="First slide" />
            </CarouselItem>

        });

        return (
            <div>
                <NavigationTab></NavigationTab>
                <div  className="mt-5 pt-4">
                    <div className="container dark-grey-text mt-5">


                        <div className="row wow fadeIn">

                            <div className="col-md-6 mb-4">
                                <Container>
                                    <Carousel
                                        activeitem={1}
                                        length={images.length}
                                        showcontrols="true"
                                        showindicators="true"
                                        className="carousel-size">

                                        {renderImages}

                                    </Carousel>
                                </Container>
                            </div>

                            <div className="col-md-6 mb-4">

                                <p className="lead font-weight-bold" size="large">{car.manufacturerName} {car.modelName}</p>

                                <p className="lead">
                                    <span>Starting at ${car.price} per day</span>
                                </p>

                                <p >This vehicle runs on {car.fuelTypeName} and has {car.transmissionTypeName} transmission. </p>

                                <p >Take the advantage of its {car.carClassName} design!</p>

                                <p >Mileage: {car.mileage}km</p>
                                <p >Mileage Threshold: {car.mileageThreshold}km</p>
                                <p >You will incur additional fee of  ${car.mileagePenalty} per kilometer over the mileage threshold</p>
                                <p >Number of child seats: {car.childSeats} </p>
                                <p >Collision warranty cost: {car.collisionWaranty} </p>
                                <p > Offered by {car.publisherName} in {car.locationName}</p>
                                <p >Average user Score: {car.carRating} </p>



                            </div>
                        </div>
                    </div>
                </div>
                <hr></hr>
                <h2 className="review-title">Reviews:</h2>
                {reviewsMap}
            </div>
        );

    }
}

export default MoreDetails;