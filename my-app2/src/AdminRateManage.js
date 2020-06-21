import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button, ButtonGroup, Card } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './MoreDetails.css';

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class AdminRateManage extends Component {

    constructor(props) {
        super(props)
        this.state = {
            reviews: [],

        }
        this.handleApprove = this.handleApprove.bind(this);
        this.handleDecline = this.handleDecline.bind(this);
    }

    componentDidMount() {

        if (localStorage.getItem('roleId') !== '3') {
            this.props.history.push("/login");
        }
        axios.get(
            'https://localhost:8085/schedule/comments/pending', axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska u ucitavanju admin komentara');
                    const responsebundles = response.data;
                    this.setState({ reviews: responsebundles });
                }
            }).catch(response => {
                console.log('greska admin comment load');
            });

        this.setState({ state: this.state });
    }

    handleApprove = (event) =>{
        let approveLink = 'https://localhost:8085/schedule/comments/' + event.target.value + '/approve';

        console.log(approveLink);
        console.log(axiosConfig);
        let body = null;
        axios.post(
            approveLink, body, axiosConfig
        ).then(response =>{
            if(response.status ===200){
                console.log("approved");
                window.location.reload(true);
            }
        }).catch(response => {
            console.log('greska approve');
        });
    }

    handleDecline = (event) =>{
        let body = null;

        let rejectLink = 'https://localhost:8085/schedule/comments/' + event.target.value + '/reject'
        axios.post(
            rejectLink,body,axiosConfig
        ).then(response =>{
            if(response.status ===200){
                console.log("rejected");
                window.location.reload(true);
            }
        }).catch(response => {
            console.log('greska reject');
        });
    }
    render() {
        let reviews = this.state.reviews;

        const reviewsMap = reviews.map((review) => {
            return <div key={review.id}>
                <Card className="review-card">
                    <p className="lead">Publisher Name: {review.publisherName}</p>
                    <p className="lead">Publisher Type: {review.publisherTypeName}</p>
                    <p className="lead">Comment: {review.comment}</p>
                    <ButtonGroup>
                        <Button value={review.id} onClick={this.handleApprove}>Approve</Button>
                        <Button value={review.id} onClick={this.handleDecline}>Reject</Button>
                    </ButtonGroup>
                </Card>
            </div>
        });
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                {reviewsMap}

            </div>
        );
    }
} export default AdminRateManage;