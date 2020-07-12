import React, { Component } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './addImages.css'
import NavigationTab from './NavigationTab';
import { Button, Card } from 'react-bootstrap';
import { Input } from 'semantic-ui-react';



const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

class MessageBoard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            message: '',
        };
        this.handleSendMessage = this.handleSendMessage.bind(this);
        this.handleMessageText = this.handleMessageText.bind(this);
    }

    componentDidMount(){
        let chatId = sessionStorage.getItem('chatId');
        let link = 'https://localhost:8080/schedule/bundle/' + chatId + '/chat';
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

    handleSendMessage = (event) => {
        let chatData = {
            message: this.state.message,
            bundleId: sessionStorage.getItem('chatId')
        }
        console.log(chatData);
        axios.post(
            "https://localhost:8080/schedule/chat", chatData, axiosConfig
        ).then(response => {
            if (response.status === 200) {
                console.log("Message Sent");
                window.location.reload(true);
            }
        }).catch(response => {
            console.log('greska Message sent');
        });
    }

    handleMessageText = (event) => {
        const text = event.target.value;
        this.setState({ message: text });
    }

    render() {
        let bundles = this.state.bundles;
        console.log(bundles);
        let mapCart = null;

        if(!(bundles===undefined)){
        
        mapCart = bundles.map((bundle) => {
            return <div>
            <Card className="messagesSize">
                <p>Timestamp: {bundle.date}</p>
                <p className="lead">

                <p> {bundle.publisherName} ID: {bundle.publisherId}</p>
                <p> {bundle.comment}</p>
                </p>
            </Card>
            </div>
        });
    }
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <h2 className="ttitle">Messages:</h2>
                <hr></hr>
                {mapCart}
                <hr></hr>
                <Input className="messagesSize" multiline onChange={this.handleMessageText} placeholder={"Write your message"}></Input>
                <Button className="messagesSize" value={this.state.chatId} onClick={this.handleSendMessage}>Send message</Button>
            </div>
        );
    }
}
export default MessageBoard;
