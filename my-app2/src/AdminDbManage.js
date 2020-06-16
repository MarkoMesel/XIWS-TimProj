import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown, Input } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button, FormCheck } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import addDays from 'date-fns/addDays';
import './homepage.css';
import { format } from 'date-fns';
import { TextField } from 'material-ui';


class AdminDbManage extends Component {

    constructor(props) {
        super(props)
        this.state = {
            carClasses: [],
            carManufacturers: [],
            locations: [],
            models: [],
            fuelTypes: [],
            transmissionTypes: [],

            carClass: '',
            carManufacturer: '',
            location: '',
            fuelType: '',
            transmissionType: '',
            model: '',
        }
        this.carClassDropdownChange = this.carClassDropdownChange.bind(this);
        this.carManufacturerDropdownChange = this.carManufacturerDropdownChange.bind(this);
        this.locationsDropdownChange = this.locationsDropdownChange.bind(this);
        this.modelsDropdownChange = this.modelsDropdownChange.bind(this);
        this.fuelTypesDropdownChange = this.fuelTypesDropdownChange.bind(this);
        this.transmissionTypesDropdownChange = this.transmissionTypesDropdownChange.bind(this);
        this.deleteCarManufacturers = this.deleteCarManufacturers.bind(this);

        this.deleteCarClass = this.deleteCarClass.bind(this);
        this.deleteCarManufacturers = this.deleteCarManufacturers.bind(this);
        this.deleteLocation = this.deleteLocation.bind(this);
        this.deleteModel = this.deleteModel.bind(this);
        this.deleteFuelType = this.deleteFuelType.bind(this);
        this.deleteTransmissionType = this.deleteTransmissionType.bind(this);
    }

    componentDidMount() {
        let getAllCarClasses = 'https://localhost:8085/car/classes';
        let getAllCarManufacturers = 'https://localhost:8085/car/manufacturers';
        let getAllLocations = 'https://localhost:8085/car/locations';
        let getAllModels = 'https://localhost:8085/car/models';
        let getAllFuelTypes = 'https://localhost:8085/car/fuelTypes';
        let getAllTransmissionTypes = 'https://localhost:8085/car/transmissionTypes';

        const requestCarClasses = axios.get(getAllCarClasses);
        const requestCarManufacturers = axios.get(getAllCarManufacturers);
        const requestLocations = axios.get(getAllLocations);
        const requestModels = axios.get(getAllModels);
        const requestFuelTypes = axios.get(getAllFuelTypes);
        const requestTransmissionTypes = axios.get(getAllTransmissionTypes);

        axios
            .all([requestCarClasses, requestCarManufacturers, requestLocations, requestModels, requestFuelTypes, requestTransmissionTypes])
            .then(
                axios.spread((...responses) => {

                    const carClasses = responses[0].data;
                    this.setState({ carClasses });

                    const carManufacturers = responses[1].data;
                    this.setState({ carManufacturers });

                    const locations = responses[2].data;
                    this.setState({ locations });

                    const models = responses[3].data;
                    this.setState({ models });

                    const fuelTypes = responses[4].data;
                    this.setState({ fuelTypes });

                    const transmissionTypes = responses[5].data;
                    this.setState({ transmissionTypes });
                })
            ).catch(errors => {
                console.log("Greska Admin DB Getteri")
            });
        this.setState({ state: this.state });
    }

    carClassDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    carManufacturerDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    locationsDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    modelsDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    fuelTypesDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    transmissionTypesDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
    }

    deleteCarClass() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }
    deleteCarManufacturers() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }

    deleteLocation() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }

    deleteModel() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }

    deleteFuelType() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }

    deleteTransmissionType() {
        /*axios delete na osnovu podataka napravljenih gore*/
    }

    render() {
        let carClasses = this.state.carClasses.map(carClass => ({ key: carClass.id, value: carClass.name, text: carClass.name }));
        let carManufacturers = this.state.carManufacturers.map(carManufacturer => ({ key: carManufacturer.id, value: carManufacturer.name, text: carManufacturer.name }));
        let locations = this.state.locations.map(location => ({ key: location.id, value: location.name, text: location.name }));
        let models = this.state.models.map(model => ({ key: model.id, value: model.modelName, text: model.modelName }));
        let fuelTypes = this.state.fuelTypes.map(fuel => ({ key: fuel.id, value: fuel.name, text: fuel.name }));
        let transmissionTypes = this.state.transmissionTypes.map(transmission => ({ key: transmission.id, value: transmission.name, text: transmission.name }));
        return (
            <div className="section-center">
                <div className="container">
                    <div className="row">
                        <div className="booking-form">
                            <div className="form-header">
                                <div className="form-group">
                                    <Dropdown
                                        placeholder='Car Classes'
                                        fluid
                                        search
                                        selection
                                        options={carClasses}
                                        onChange={this.carClassDropdownChange}
                                    ></Dropdown>
                                    <Button className="deleteCarClassButton" disabled={!this.state.carClass} onclick={this.deleteCarClass}>Delete</Button>
                                    <div className="form-group">
                                    <Input></Input>
                                    <Button className="addCarClass" >Add</Button>

                                    </div>
                                </div>

                                <div className="form-group">
                                    <Dropdown

                                        placeholder='Car Manufacturer'
                                        fluid
                                        search
                                        selection
                                        options={carManufacturers}
                                        onChange={this.carManufacturerDropdownChange}
                                    ></Dropdown>
                                    <Button className="carManufacturerButton" disabled={!this.state.carManufacturer} onclick={this.deleteCarManufacturers}>Delete</Button>
                                </div>

                                <div className="form-group">
                                    <Dropdown
                                        placeholder='Location'
                                        fluid
                                        search
                                        selection
                                        options={locations}
                                        onChange={this.locationsDropdownChange}
                                    ></Dropdown>
                                    <Button className="locationButton" disabled={!this.state.location} onclick={this.deleteLocation}>Delete</Button>
                                </div>

                                <div className="form-group">
                                    <Dropdown
                                        placeholder='Model'
                                        fluid
                                        search
                                        selection
                                        options={models}
                                        onChange={this.modelsDropdownChange}
                                    ></Dropdown>
                                    <Button className="modelButton" disabled={!this.state.model} onclick={this.deleteModel}>Delete</Button>
                                </div>

                                <div className="form-group">
                                    <Dropdown
                                        placeholder='Fuel'
                                        fluid
                                        search
                                        selection
                                        options={fuelTypes}
                                        onChange={this.fuelTypesDropdownChange}
                                    ></Dropdown>
                                    <Button className="fuelButton" disabled={!this.state.fuelType} onclick={this.deleteFuelType}>Delete</Button>
                                </div>

                                <div className="form-group">
                                    <Dropdown
                                        placeholder='Transmission'
                                        fluid
                                        search
                                        selection
                                        options={transmissionTypes}
                                        onChange={this.transmissionTypesDropdownChange}
                                    ></Dropdown>
                                    <Button className="transmissionButton" disabled={!this.state.transmissionType} onclick={this.deleteTransmissionType}>Delete</Button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
} export default AdminDbManage;