import React, { Component } from 'react';
import { FormErrors } from './FormErrors';
import axios from 'axios';
import { Link } from 'react-router-dom';

import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'semantic-ui-css/semantic.min.css';
import NavigationTab from './NavigationTab';


class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            formData: [],
            email: '',
            loggedInToken: '',
            password: '',
            formErrors: { email: '', password: '' },
            emailValid: false,
            passwordValid: false,
            formValid: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleUserInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({ [name]: value },
            () => { this.validateField(name, value) }
        );
    }
    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;

        switch (fieldName) {
            case 'email':
                emailValid = value.length >= 1;
                fieldValidationErrors.email = emailValid ? '' : ' must not be empty';
                break;
            case 'password':
                passwordValid = value.length >= 1;
                fieldValidationErrors.password = passwordValid ? '' : ' must not be empty';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            emailValid: emailValid,
            passwordValid: passwordValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ formValid: this.state.emailValid && this.state.passwordValid });
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error');
    }

    render() {
        return (
            <div>
                <NavigationTab></NavigationTab>
                <div id="booking" className="section">
                    <div className="section-center">
                        <div className="container">
                            <div className="row">
                                <div className="booking-form">
                                    <div className="form-header">
                                        <h1>Login</h1>
                                    </div>
                                    <form>
                                        <div className="panel panel-default">
                                            <FormErrors formErrors={this.state.formErrors} />
                                        </div>
                                        <div className={`form-group ${this.errorClass(this.state.formErrors.email)}`}>
                                            <span className="form-label">Email</span>

                                            <input type="email" required className="form-control" name="email"
                                                placeholder="Email"
                                                value={this.state.email}
                                                onChange={this.handleUserInput} />
                                        </div>
                                        <div className={`form-group ${this.errorClass(this.state.formErrors.password)}`}>
                                            <span className="form-label">Password</span>
                                            <input type="password" className="form-control" name="password"
                                                placeholder="Password"
                                                value={this.state.password}
                                                value2={this.state.password}
                                                onChange={this.handleUserInput} />
                                        </div>
                                        <div className="form-group">
                                            <button type="submit" className="btn btn-primary" onClick={this.handleSubmit} disabled={!this.state.formValid}>Sign up</button>
                                        </div>
                                        <div className="form-group">
                                            <span className="form-label">Don't have an account?</span>
                                            <div className="form-group">
                                                <Link to="/register">Register</Link>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    async handleSubmit() {
        const formData = {
            email: this.state.email,
            password: this.state.password
        }
        let postData = JSON.stringify(formData)

        const axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        };
        axios.post(
            'https://localhost:8085/user/login',
            postData, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('Status 200 ');
                    localStorage.setItem('token', response.data.token);
                    console.log(localStorage);
                }
            })
        if (localStorage.token !== null) {
            this.props.history.push("/homepage");
        }
    }
}
export default Login;
