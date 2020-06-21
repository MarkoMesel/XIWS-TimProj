import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown, Input } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './MoreDetails.css';
import { Card } from "react-bootstrap";


const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class ClientRateCar extends Component {

    constructor(props) {
        super(props)
        this.state = {
            pendingRating: [],
            rating: null,
            clientComment: null,
        }
        this.handleRatingChange = this.handleRatingChange.bind(this);
        this.handleCommentChange = this.handleCommentChange.bind(this);
        this.postComment = this.postComment.bind(this);
    }

    componentDidMount() {
        if (localStorage.getItem('roleId') !== '1') {
            this.props.history.push("/login");
        }

        axios.get(
            'https://localhost:8085/schedule/ratings/pending', axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska u get pending');

                    const pendingRating = response.data;
                    this.setState({ pendingRating: pendingRating,
                    });

                }
            }).catch(response => {
                console.log('greska u get pending');
            });
        this.setState({ state: this.state });
    }
   
    
    handleRatingChange = (event, data) =>{
        let rating = data.value;
        this.setState({rating: rating});
    }

    handleCommentChange = (event, data) =>{
        let c = data.value;
        this.setState({clientComment: c});
    }
    
    postComment = (event) => {
        const commentData = {
            reservationId: event.target.value,
            rating: this.state.rating,
            comment: this.state.clientComment
        }
        axios.post('https://localhost:8085/schedule/ratings', 
        commentData, 
        axiosConfig).then(response =>{
            console.log("komentar ostavljen");
            window.location.reload(true);
        }).catch(response =>{
            console.log("ne radi comment ostavljanje");
        })
    }

    render() {
        const abc = [{ "name": 1}, { "name": 2}, { "name": 3},{ "name": 4},{ "name": 5} ];
        let ratingOptions = abc.map(rate =>({key: rate.name, value: rate.name, text:rate.name}));
        let pendingRating = this.state.pendingRating;
        const mapPending = pendingRating.map((rate) => {
            return <div key={rate.reservationId}>
                <Card className="review-card">
                    <ul>Car: {rate.manufacturerName} {rate.modelName} {rate.carId}</ul>
                    <ul>Owner: {rate.publisherName} {rate.publisherId} {rate.publisherTypeName}</ul>
                    <ul>Owner's role: {rate.publisherTypeName}</ul>
                    <Input className="nestopad" onChange={this.handleCommentChange}></Input>
                    <hr></hr>
                    <Dropdown
                    key={rate.reservationId}
                    placeholder="Select your rating"
                    selection
                    options={ratingOptions}
                    onChange={this.handleRatingChange}>
                        
                    </Dropdown>

                   <Button value={rate.reservationId} disabled={this.state.rating===null||!(this.state.clientComment)} onClick={this.postComment}>Post</Button>

                </Card>
            </div>

        });
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Cars pending rating:</h2>
                {mapPending}
            </div>
        )
    }
} export default ClientRateCar;