import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';


class CarPost extends Component {

  constructor(props) {
    super(props)
    this.state = {
      manufacturers: [],
      models: [],
      fuel: [],
      transmission: [],
      carClass: [],
      location: [],
      pickupLocationKey: null,
      pickupLocation: '',
      manufacturerKey: null,
      modelKey: null,
      fuelKey: null,
      transmissionKey: undefined,
      carClassKey: null,
      priceFrom: null,
      priceTo: null,
      collisionWarranty: false,
      mileage: null,
      expectedMileage: null,
      childSeats: null,

      modelName: null,
      manufacturerName: null,
      fuelTypeName: null,
      transmissionTypeName: null,
      carClassName: null,

      manufacturerKeyValid: false,
      modelKeyValid: false,
      formErrors: { manufacturerKey: '', modelKey: '' },
      formValid: false,

    };
    this.handleManufacturerDropdownChange = this.handleManufacturerDropdownChange.bind(this);
    this.handleModelsDropdownChange = this.handleModelsDropdownChange.bind(this);
    this.handleFuelDropdownChange = this.handleFuelDropdownChange.bind(this);
    this.handleTransmissionDropdownChange = this.handleTransmissionDropdownChange.bind(this);
    this.handleCarClassDropdownChange = this.handleCarClassDropdownChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleToggle = this.handleToggle.bind(this);
    this.handlePickupLocationDropdownChange = this.handlePickupLocationDropdownChange.bind(this);
  }


  componentDidMount() {
    let role = localStorage.getItem('roleId');
    console.log(role)



    let manufacturerRequest = "https://localhost:8080/car/manufacturers";
    let modelRequest = "https://localhost:8080/car/models";
    let fuelRequest = "https://localhost:8080/car/fuelTypes";
    let carClassRequest = "https://localhost:8080/car/classes";
    let transmissionRequest = "https://localhost:8080/car/transmissionTypes";
    let locationRequest = "https://localhost:8080/car/locations";

    const requestManufacturer = axios.get(manufacturerRequest);
    const requestModel = axios.get(modelRequest);
    const requestFuel = axios.get(fuelRequest);
    const requestCarClass = axios.get(carClassRequest);
    const requestTransmission = axios.get(transmissionRequest);
    const requestLocation = axios.get(locationRequest);


    axios
      .all([requestManufacturer, requestModel, requestFuel, requestCarClass, requestTransmission, requestLocation])
      .then(
        axios.spread((...responses) => {
          const manufacturers = responses[0].data;
          this.setState({ manufacturers });

          const models = responses[1].data;
          this.setState({ models });

          const fuel = responses[2].data;
          this.setState({ fuel });

          const carClass = responses[3].data;
          this.setState({ carClass });

          const transmission = responses[4].data;
          this.setState({ transmission });

          const location = responses[5].data;
          this.setState({ location });
        })
      )
      .catch(errors => {
        console.log("Greska Homepage Getteri")
      });
  }



  handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value }
    );
  }
  handleManufacturerDropdownChange = (event, data) => {
    this.setState({
      manufacturerKey: data.value,
      manufacturerName: data.text
    });
    console.log(this.state.manufacturerKey, this.state.manufacturerName)
  }
  handleModelsDropdownChange = (event, data) => {
    this.setState({
      modelKey: data.value,
      modelName: data.text
    });
    console.log(this.state.modelKey, this.state.modelName)
  }
  handleFuelDropdownChange = (event, data) => {
    this.setState({
      fuelKey: data.value
    })
  }
  handleTransmissionDropdownChange = (event, data) => {
    this.setState({
      transmissionKey: data.value
    })
  }
  handleCarClassDropdownChange = (event, data) => {
    this.setState({
      carClassKey: data.value
    })
  }
  handleToggle() {
    this.setState({
      collisionWarranty: !this.state.collisionWarranty
    });
  }

  handlePickupLocationDropdownChange = (event, data) => {
    this.setState({
      pickupLocationKey: data.value
    });
  }

  handleSubmit() {
    sessionStorage.setItem('LocationKey', this.state.pickupLocationKey);
    sessionStorage.setItem('ModelKey', this.state.modelKey);
    sessionStorage.setItem('fuelKey', this.state.fuelKey);
    sessionStorage.setItem('transmissionKey', this.state.transmissionKey);
    sessionStorage.setItem('carClassKey', this.state.carClassKey);
    sessionStorage.setItem('mileageKey', this.state.mileage);
    sessionStorage.setItem('childSeatsKey', this.state.childSeats);
    this.props.history.push("/addImages");
  }



  render() {
    const { manufacturerKey } = this.state;

    let manufacturers = this.state.manufacturers.map(manufacturer => ({ key: manufacturer.id, value: manufacturer.id, text: manufacturer.name }));
    let fuel = this.state.fuel.map(fuel => ({ key: fuel.id, value: fuel.id, text: fuel.name }));
    let transmission = this.state.transmission.map(transmission => ({ key: transmission.id, value: transmission.id, text: transmission.name }));
    let carClass = this.state.carClass.map(carclass => ({ key: carclass.id, value: carclass.id, text: carclass.name }));
    let location = this.state.location.map(loc => ({ key: loc.id, value: loc.id, text: loc.name }));

    let modelsfiltered = this.state.models.filter(function (model) {
      return model.manufacturerId === manufacturerKey;
    }).map(model => ({ key: model.modelId, value: model.manufacturerId, text: model.modelName }));

    return (

      <div>
        <div>
          <NavigationTab></NavigationTab>

          <div id="booking" className="section">
            <div className="section-center">
              <div className="container">
                <div className="row">
                  <div className="booking-form">
                    <div className="form-header">
                      <h1>Advertise</h1>
                    </div>
                    <form>
                      <div className="form-group">
                        <Dropdown
                          placeholder='Car Location'
                          fluid
                          search
                          clearable
                          selection
                          options={location}
                          onChange={this.handlePickupLocationDropdownChange}
                        ></Dropdown>
                      </div>

                      <div className="form-group">
                        <Dropdown
                          name="key"
                          placeholder='Manufacturer'
                          fluid
                          required
                          search
                          selection
                          options={manufacturers}
                          onChange={this.handleManufacturerDropdownChange}
                        />
                      </div>

                      <div className="form-group">
                        <Dropdown
                          placeholder='Model'
                          fluid
                          search
                          required
                          selection
                          options={modelsfiltered}
                          onChange={this.handleModelsDropdownChange}
                        />
                      </div>

                      <div className="form-group">
                        <Dropdown
                          placeholder='Fuel'
                          fluid
                          search
                          clearable
                          selection
                          options={fuel}
                          onChange={this.handleFuelDropdownChange}
                        ></Dropdown>
                      </div>
                      <div className="form-group">
                        <Dropdown
                          placeholder='Transmission'
                          fluid
                          search
                          clearable
                          selection
                          options={transmission}
                          onChange={this.handleTransmissionDropdownChange}
                        ></Dropdown>
                      </div>
                      <div className="form-group">
                        <Dropdown
                          placeholder='Class'
                          fluid
                          search
                          selection
                          clearable
                          options={carClass}
                          onChange={this.handleCarClassDropdownChange}
                        ></Dropdown>
                      </div>

                      <div className="row">
                        <div className="col-sm-3">
                          <div className="form-group">
                            <span className="form-label">Mileage</span>
                            <input className="form-control" type="number" name="mileage" onChange={this.handleUserInput} value={this.state.mileage}></input>
                          </div>
                        </div>
                        <div className="col-sm-3">
                          <div className="form-group">
                            <span className="form-label">Child seats</span>
                            <input className="form-control" type="number" name="childSeats" onChange={this.handleUserInput} value={this.state.childSeats}></input>
                          </div>
                        </div>
                      </div>
                      <div className="mb-2">

                        <Button disabled={!this.state.manufacturerKey || !this.state.modelKey || !this.state.pickupLocationKey || !this.state.fuelKey
                          || !this.state.transmissionKey || !this.state.carClassKey || !this.state.mileage || !this.state.childSeats}
                          variant="primary" onClick={this.handleSubmit} size="lg">
                          Next
                        </Button>

                      </div>

                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div >
    );
  }

}
export default CarPost;
