import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown } from 'semantic-ui-react';
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


class ClientSearchCar extends Component {

  constructor(props) {
    super(props)
    this.state = {
      manufacturers: [],
      models: [],
      fuel: [],
      transmission: [],
      carClass: [],
      location: [],
      pickupLocationKey: '',
      pickupLocation: '',
      manufacturerKey: '',
      modelKey: '',
      fuelKey: '',
      transmissionKey: '',
      carClassKey: '',
      priceFrom: 0,
      priceTo: 10,
      collisionWarranty: false,
      mileage: '',
      expectedMileage: '',
      childSeats: '',
      numberOfDaysRenting: 0,
      startDate: addDays(new Date(), +2),
      endDate: '',

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
    this.handleUserInputPriceTo = this.handleUserInputPriceTo.bind(this);
    this.handleUserInputPriceFrom = this.handleUserInputPriceFrom.bind(this);
    this.handleTimeChange = this.handleTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
    this.handlePickupLocationDropdownChange = this.handlePickupLocationDropdownChange.bind(this);

  }
  handleTimeChange(date) {
    this.setState({
      startDate: date
    })

  }
  handleEndTimeChange(date) {
    this.setState({
      endDate: date
    })
  }

  handleUserInputPriceTo = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value }
    );
  }
  handleUserInputPriceFrom = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value }
    );
  }

  handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value }
    );
  }

  componentDidMount() {

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

  handleManufacturerDropdownChange = (event, data) => {
    this.setState({
      manufacturerKey: data.value
    });
  }
  handleModelsDropdownChange = (event, data) => {
    this.setState({
      modelKey: data.value
    });
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
    })
  }

  render() {
    const { manufacturerKey, priceFrom, priceTo } = this.state;

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
                      <h1>Rent-a-car</h1>
                    </div>
                    <form>

                      <div className="form-group">
                        <Dropdown
                          placeholder='Pickup Location'
                          fluid
                          search
                          clearable
                          selection
                          options={location}
                          onChange={this.handlePickupLocationDropdownChange}
                        ></Dropdown>
                      </div>


                      <div className="form-group">

                        <DatePicker
                          className="ui selection dropdown"
                          placeholderText="Pickup date"
                          isClearable
                          selected={this.state.startDate}
                          onChange={this.handleTimeChange}
                          showTimeSelect
                          minDate={addDays(new Date(), +2)}

                          timeFormat="HH:mm"
                          timeIntervals={20}
                          timeCaption="time"
                        />

                        <DatePicker
                          className="ui fluid selection dropdown"
                          isClearable
                          placeholderText="Dropoff date"
                          disabled={!this.state.startDate}
                          selected={this.state.endDate}
                          onChange={this.handleEndTimeChange}
                          showTimeSelect
                          minDate={addDays(this.state.startDate, +1)}
                          timeIntervals={20}
                          timeCaption="time"
                        />
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
                            <span className="form-label">Price per day From</span>
                            <input className="form-control" type="number" name="priceFrom" max={priceTo} onChange={this.handleUserInputPriceFrom} value={this.state.priceFrom}></input>
                          </div>
                        </div>

                        <div className="col-sm-3">
                          <div className="form-group">
                            <span className="form-label">Price per day To</span>
                            <input className="form-control" type="number" name="priceTo" min={priceFrom} onChange={this.handleUserInputPriceTo} value={this.state.priceTo}></input>
                          </div>
                        </div>
                        <div className="col-sm-3">
                          <div className="form-group">
                            <span className="form-label">Collision Warranty</span>

                            <FormCheck type="checkbox" onChange={this.handleToggle} />
                          </div>
                        </div>
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
                            <span className="form-label">Expected mileage</span>
                            <input className="form-control" type="number" name="expectedMileage" onChange={this.handleUserInput} value={this.state.expectedMileage}></input>
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

                        <Button disabled={!this.state.manufacturerKey || !this.state.modelKey ||
                          !this.state.startDate || !this.state.endDate || !this.state.pickupLocationKey}
                          variant="primary" onClick={this.handleSubmit} size="lg">
                          Search
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

  async handleSubmit() {

    const formData = {
      manufacturerKey: this.state.manufacturerKey,
      modelKey: this.state.modelKey,
      fuel: this.state.fuelKey,
      transmission: this.state.transmissionKey,
      carClass: this.state.carClassKey,
      priceFrom: this.state.priceFrom,
      priceTo: this.state.priceTo,
      collisionWarranty: this.state.collisionWarranty,
      mileage: this.state.mileage,
      expectedMileage: this.state.expectedMileage,
      childSeats: this.state.childSeats,
      startDate: this.state.startDate.toISOString(),
      endDate: this.state.endDate.toISOString(),
      locationId: this.state.pickupLocationKey,
    }
    let searchData = JSON.stringify(formData)

    sessionStorage.setItem('searchData',searchData);
    this.props.history.push("/searchresults/");

  };
}
export default ClientSearchCar;
