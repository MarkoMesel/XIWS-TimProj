import React, { Component } from 'react';
import { Carousel, CarouselItem, Container, Card } from "react-bootstrap";

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
                'Content-Type': 'application/json;charset=utf-8',
                'token': localStorage.getItem('token')
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
            'https://localhost:8080/car/getCar',
            postData, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska u getcar');
                    const car = response.data;
                    this.setState({ car });
                    const images = response.data.images;
                    this.setState({ images });
                }
            }).catch(response => {
                console.log('greska getcar');
            });
        let commentLink = 'https://localhost:8080/schedule/car/' + carData.id + '/comments'

        axios.get(
            commentLink, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska comments');
                    const responsebundles = response.data;
                    this.setState({ reviews: responsebundles });

                }
            }).catch(response => {
                console.log('greska comments');
            });

        this.setState({ state: this.state });
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
        let reviewBundles = this.state.reviews;
        console.log(car);

        const reviewsMap = reviewBundles.map((bundle) => {
            return <div key={bundle}>
                <Card className="review-card">
                    <p className="lead">Rating: {bundle.rating}</p>
                    <p className="lead">Comment: {bundle.comment}</p>
                    {bundle.replies.map((reply) =>
                        <p>{reply.publisherName} {reply.publisherId} : {reply.comment}</p>
                    )}
                </Card>
            </div>
        });

        const renderImages = images.map((im) => {
            return <CarouselItem key={im}>
                <img
                    className="d-block w-100"
                    src={'https://localhost:8080/car/image/' + im}
                    alt="First slide" />
            </CarouselItem>

        });

        return (
            <div>
                <NavigationTab></NavigationTab>
                <div className="mt-5 pt-4">
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
                                    <p >This vehicle runs on {car.fuelTypeName} and has {car.transmissionTypeName} transmission. </p>

                                    <p >Take the advantage of its {car.carClassName} design!</p>
                                    <p >It comes with {car.childSeats} child seats. </p>
                                    <p >Overall rating: {car.rating} </p>
                                    <p > Offered by {car.publisherName} in {car.locationName}</p>
                                </p>





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