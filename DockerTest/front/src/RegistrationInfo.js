import React, { Component } from 'react';
import NavigationTab from './NavigationTab';
import './SearchResults.css';


class RegistrationInfo extends Component {
    render() {
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <div><p>Your application has been submitted. Please wait for our administration team to contact you via email.</p></div>
            </div>
                
            
        );
    }
}



export default RegistrationInfo;
