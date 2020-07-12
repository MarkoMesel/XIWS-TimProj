import React, { Component } from 'react';
import { FormErrors } from './FormErrors';
import axios from 'axios';
import './index.css';
import 'semantic-ui-css/semantic.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import NavigationTab from './NavigationTab';
import { Link } from 'react-router-dom';
import './SearchResults.css';


class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData: [],
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            confirmpassword: '',
            phone: '',
            formErrors: { email: '', password: '', firstName: '', lastName: '', confirmpassword: '', phone: '', },
            emailValid: false,
            passwordValid: false,
            firstnameValid: false,
            lastnameValid: false,
            confirmpasswordValid: false,
            phoneValid: false,
            formValid: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount(){
        if (localStorage.getItem('roleId') !== null) {
            this.props.history.push("/login");
        }
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
        let firstnameValid = this.state.firstnameValid;
        let lastnameValid = this.state.lastnameValid;
        let confirmpasswordValid = this.state.confirmpasswordValid;
        let phoneValid = this.state.phoneValid;


        switch (fieldName) {
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                fieldValidationErrors.email = emailValid ? '' : ' is invalid';
                break;
            case 'password':
                passwordValid = value.length >= 5;
                fieldValidationErrors.password = passwordValid ? '' : ' is too short';
                break;
            case 'firstName':
                firstnameValid = value.length >= 3;
                fieldValidationErrors.firstName = firstnameValid ? '' : ' is too short';
                break;
            case 'lastName':
                lastnameValid = value.length >= 3;
                fieldValidationErrors.lastName = lastnameValid ? '' : ' is too short';
                break;
            case 'confirmpassword':
                confirmpasswordValid = value === this.state.password;
                fieldValidationErrors.confirmpassword = confirmpasswordValid ? '' : ' does not match password';
                break;
            case 'phone':
                phoneValid = value.match(/^[0-9\b]+$/i);
                fieldValidationErrors.phone = phoneValid ? '' : 'number must contain numbers only';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            emailValid: emailValid,
            passwordValid: passwordValid,
            firstnameValid: firstnameValid,
            lastnameValid: lastnameValid,
            confirmpasswordValid: confirmpasswordValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ formValid: this.state.emailValid && this.state.passwordValid && this.state.firstnameValid && this.state.lastnameValid && this.state.confirmpasswordValid });
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error');
    }

    render() {
        return (
            <div className="body">
                <NavigationTab></NavigationTab>
                <div className="container">
                    <div className="row">
                        <div className="booking-form">
                            <div className="form-header">
                                <h1>Register</h1>
                            </div>
                            <form>
                                <div className="panel panel-default">
                                    <FormErrors formErrors={this.state.formErrors} />
                                </div>


                                <div className={`form-group ${this.errorClass(this.state.formErrors.firstName)}`}>
                                    <span className="form-label">First Name</span>
                                    <input type="text" required className="form-control" name="firstName"
                                        placeholder="Enter First Name"
                                        value={this.state.firstName}
                                        onChange={this.handleUserInput} />
                                </div>
                                <div className={`form-group ${this.errorClass(this.state.formErrors.lastName)}`}>
                                    <span className="form-label">Last Name</span>
                                    <input type="text" required className="form-control" name="lastName"
                                        placeholder="Enter Last Name"
                                        value={this.state.lastName}
                                        onChange={this.handleUserInput} />
                                </div>

                                <div className={`form-group ${this.errorClass(this.state.formErrors.email)}`}>
                                    <span className="form-label">Email</span>
                                    <input type="email" required className="form-control" name="email"
                                        placeholder="Email"
                                        value={this.state.email}
                                        onChange={this.handleUserInput} />
                                </div>

                                <div className={`form-group ${this.errorClass(this.state.formErrors.phone)}`}>
                                    <span className="form-label">Phone Number</span>
                                    <input type="text" required className="form-control" name="phone"
                                        placeholder="Enter Phone Number"
                                        value={this.state.phone}
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
                                    <span className="form-label">Confirm Password</span>
                                    <input type="password" className="form-control" name="confirmpassword"
                                        value={this.state.confirmpassword} placeholder="Enter password again"
                                        onChange={this.handleUserInput} />
                                </div>
                                <div className="form-group">
                                    <button type="submit" className="btn btn-primary" onClick={this.handleSubmit} disabled={!this.state.formValid}>Sign up</button>
                                </div>

                                <div className="form-group">
                                    <span className="form-label">Already have an account?</span>
                                    <div className="form-group">
                                        <Link to="/login">Login</Link>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    async handleSubmit() {


        const formData = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            password: this.state.password,
            phone: this.state.phone,
            email: this.state.email,
        }
        let requestData = JSON.stringify(formData)


        const axiosConfig = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        };
        axios.post(
            'https://localhost:8080/user/register',
            requestData, axiosConfig)
            .then(response => {
                if (response.status === 200) {
                    console.log('nije greska');
                    
                }
            }).catch(response => {
                console.log('greska');
            });
        this.props.history.push("/registrationinfo");


    };

}

export default Register;