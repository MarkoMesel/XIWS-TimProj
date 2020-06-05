import React, { Component } from 'react';
import { Carousel, CarouselItem, Container, Card} from "react-bootstrap";

import './ProductListing.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import NavigationTab from "./NavigationTab";
import axios from 'axios';
class ProductListing extends Component {
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

        console.log(this.props.match.params);
        let carID = this.props.match.params;
        console.log(carID);


        axios.get("https://localhost:8085/car/getCar" + carID.carID)
            .then(res => {
                const car = res.data;
                this.setState({ car });
                const images = res.data.images;
                this.setState({ images });
            })
            axios.get("http://localhost:8084/schedule/car/" + carID.carID + "/comments")
            .then(res => {
                const reviews = res.data;
                this.setState({ reviews });

            })

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
                    src={'http://localhost:8082/car/getImage/' + im}
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
                                    <span>Starting at ${car.pricePerDay} per day</span>
                                </p>

                                <p >This vehicle runs on {car.fuelType} and has {car.transmission} transmission. </p>

                                <p >Take the advantage of its {car.carClass} design!</p>

                                <p >Mileage: {car.mileage}km</p>
                                <p >Mileage Threshold: {car.mileageThreshold}km</p>
                                <p >You will incur additional fee of  ${car.mileagePenalty} per kilometer over the mileage threshold</p>
                                <p >Number of child seats: {car.childSeats} </p>
                                <p >Collision Warranty: {String(car.collisionWaranty)} </p>
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

export default ProductListing;