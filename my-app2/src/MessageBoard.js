import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Carousel, CarouselItem, Container, Card, Button, ButtonGroup } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import addDays from 'date-fns/addDays';
import './homepage.css';
import { format } from 'date-fns';
import ImageUploading from "react-images-uploading";
import './addImages.css'

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class MessageBoard extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount(){
        let chatId = sessionStorage.getItem('chatId');
        let link = 'https://localhost:8085/schedule/bundle/' + chatId + '/chat';
        axios.get(link, axiosConfig)
        .then(response => {
            if (response.status === 200) {
                console.log('Nije greska u mount');
                const responsebundles = response.data;
                this.setState({ bundles: responsebundles });
            }
        }).catch(response => {
            console.log('greska u mount');
        });
   
    }

    render() {
        return (
          <div>Test test</div>
        );
    }
}
export default MessageBoard;
