import React, { Component } from 'react';
import axios from 'axios';
import NavigationTab from './NavigationTab.js';
import { Dropdown, Input } from 'semantic-ui-react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import { Button, Card } from 'react-bootstrap';
import 'semantic-ui-css/semantic.min.css';
import "react-datepicker/dist/react-datepicker.css";
import './homepage.css';
import './MoreDetails.css';

const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token')
    }
};

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
            carClassAdd: '',
            carManufacturer: '',
            carManufacturerAdd: '',
            location: '',
            locationAdd: '',
            fuelType: '',
            fuelTypeAdd: '',
            transmissionType: '',
            transmissionTypeAdd: '',
            model: '',
            modelAdd: '',

            carClassKey: '',
            carManufacturerKey: '',
            locationKey: '',
            fuelTypeKey: '',
            transmissionTypeKey: '',
            modelKey: '',
            carManufacturerModel: '',
            carManufacturerModelKey: '',

        }
        this.carClassDropdownChange = this.carClassDropdownChange.bind(this);
        this.carManufacturerDropdownChange = this.carManufacturerDropdownChange.bind(this);

        this.carManufacturerDropdownChange2 = this.carManufacturerDropdownChange2.bind(this)
        
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

        this.handleCarClass = this.handleCarClass.bind(this);
        this.handleAddCarClass = this.handleAddCarClass.bind(this);

        this.handleCarManufacturer = this.handleCarManufacturer.bind(this);
        this.handleAddCarManufacturer = this.handleAddCarManufacturer.bind(this);

        this.handleLocation = this.handleLocation.bind(this);
        this.handleAddLocation = this.handleAddLocation.bind(this);

        this.handleFuelType = this.handleFuelType.bind(this);
        this.handleAddFuelType = this.handleAddFuelType.bind(this);

        this.handleTransmissionType = this.handleTransmissionType.bind(this);
        this.handleAddTransmissionType = this.handleAddTransmissionType.bind(this);

        this.handleModel = this.handleModel.bind(this);
        this.handleAddModel = this.handleAddModel.bind(this);
    }

    componentDidMount() {

        if (localStorage.getItem('roleId') !== '3') {
            this.props.history.push("/login");
        }


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
        this.setState({carClass: value});
        this.setState({carClassKey: key});
        console.log(value, key);

    }

    carManufacturerDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
        this.setState({carManufacturer: value});
        this.setState({carManufacturerKey: key});
        console.log(value, key);
    }

    carManufacturerDropdownChange2 = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
        this.setState({carManufacturerModel: value});
        this.setState({carManufacturerModelKey: key});
        console.log(value, key);
    }

    locationsDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        this.setState({location: value});
        this.setState({locationKey:  key});
        console.log(value, key);
    }

    modelsDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
        this.setState({model: value});
        this.setState({modelKey:  key});
        console.log("Model key is"+ this.state.modelKey)
    }

    fuelTypesDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
        this.setState({fuelType:value});
        this.setState({fuelTypeKey: key});
        console.log(value,key);
    }

    transmissionTypesDropdownChange = (event, data) => {
        const { value } = data;
        const { key } = data.options.find(o => o.value === value);
        console.log(value, key);
        this.setState({transmissionType:value});
        this.setState({transmissionTypeKey: key});
        console.log(value,key);
    }



    deleteCarClass() {
        let link = 'https://localhost:8085/car/classes/'+ this.state.carClassKey;

        axios.delete(link,axiosConfig)
        .then(response => {
            console.log("Obrisan car class");
            window.location.reload(true);

        }).catch(response => {
            console.log("Brisanje car class ne radi");
        })

    }
    deleteCarManufacturers() {
        console.log(this.state.models);
        let manKey = this.state.carManufacturerKey;


        let listOfManId = '';
        let t = [];
        this.state.models.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.manufacturerId);
            listOfManId = pubNameList;
        });

        if (!(listOfManId.includes(manKey))) {
            console.log("Nema modela pod ovim man");
            let link = 'https://localhost:8085/car/manufacturers/' + manKey;
            axios.delete(link, axiosConfig)
            .then(response => {
                console.log("Obrisan manufacturer");
                window.location.reload(true);
    
            }).catch(response => {
                console.log("Brisanje manufacturer ne radi");
            })
        }
        else{
            console.log("Ima modela pod ovim");
        }
    }

    deleteLocation() {
        let link = 'https://localhost:8085/car/locations/'+ this.state.locationKey;

        axios.delete(link, axiosConfig)
        .then(response => {
            console.log("Obrisan location");
            window.location.reload(true);

        }).catch(response => {
            console.log("Brisanje location ne radi");
        })
        
    }

    deleteModel() {
        let link = 'https://localhost:8085/car/models/'+ this.state.modelKey;

        axios.delete(link, axiosConfig)
        .then(response => {
            console.log("Obrisan model");
            window.location.reload(true);

        }).catch(response => {
            console.log("Brisanje model ne radi");
        })
    }

    deleteFuelType() {
        let link = 'https://localhost:8085/car/fuelTypes/'+ this.state.fuelTypeKey;

        axios.delete(link, axiosConfig)
        .then(response => {
            console.log("Obrisan fuel");
            window.location.reload(true);

        }).catch(response => {
            console.log("Brisanje fuel ne radi");
        })
    }

    deleteTransmissionType() {
        let link = 'https://localhost:8085/car/transmissionTypes/'+ this.state.transmissionTypeKey;

        axios.delete(link, axiosConfig)
        .then(response => {
            console.log("Obrisan trans");
            window.location.reload(true);

        }).catch(response => {
            console.log("Brisanje trans ne radi");
        })
    }


    handleCarClass = (event) => {
        const carClassText = event.target.value;
        this.setState({ carClassAdd: carClassText });
    }

    handleAddCarClass = (event) => {
        const textCheck = this.state.carClassAdd;

        const carClassText = { name: this.state.carClassAdd };
        let listOfCarClassNames = '';
        let t = [];
        this.state.carClasses.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfCarClassNames = pubNameList;
        });
        if (!(listOfCarClassNames.includes(textCheck))) {
            axios.post(
                'https://localhost:8085/car/classes', carClassText, axiosConfig)
                .then(response => {
                    console.log("add Car Class radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add Car Class ne radi");
                })
        }
    }

    handleCarManufacturer = (event) => {
        const text = event.target.value;
        this.setState({ carManufacturerAdd: text });
    }

    handleAddCarManufacturer = (event) => {
        const textCheck = this.state.carManufacturerAdd;

        const text = { name: this.state.carManufacturerAdd };
        let listOfNames = '';
        let t = [];
        this.state.carManufacturers.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfNames = pubNameList;
        });

        if (!listOfNames.includes(textCheck)) {
            axios.post(
                'https://localhost:8085/car/manufacturers', text, axiosConfig)
                .then(response => {
                    console.log("add man radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add man ne radi");
                })
        }
    }

    handleLocation = (event) => {
        const text = event.target.value;
        this.setState({ locationAdd: text });
    }

    handleAddLocation = (event) => {
        const textCheck = this.state.locationAdd;

        const text = { name: this.state.locationAdd };
        let listOfNames = '';
        let t = [];
        this.state.locations.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfNames = pubNameList;
        });

        if (!listOfNames.includes(textCheck)) {
            axios.post(
                'https://localhost:8085/car/locations', text, axiosConfig)
                .then(response => {
                    console.log("add loc radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add loc ne radi");
                })
        }
    }

    handleFuelType = (event) => {
        const text = event.target.value;
        this.setState({ fuelTypeAdd: text });
    }

    handleAddFuelType = (event) => {
        const textCheck = this.state.fuelTypeAdd;

        const text = { name: this.state.fuelTypeAdd };
        let listOfNames = '';
        let t = [];
        this.state.fuelTypes.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfNames = pubNameList;
        });

        if (!listOfNames.includes(textCheck)) {
            axios.post(
                'https://localhost:8085/car/fuelTypes', text, axiosConfig)
                .then(response => {
                    console.log("add fuel radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add fuel ne radi");
                })
        }
    }

    handleTransmissionType = (event) => {
        const text = event.target.value;
        this.setState({ transmissionTypeAdd: text });
    }

    handleAddTransmissionType = (event) => {
        const textCheck = this.state.transmissionTypeAdd;
        const text = { name: this.state.transmissionTypeAdd };
        let listOfNames = '';
        let t = [];
        this.state.transmissionTypes.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfNames = pubNameList;
        });
        if (!listOfNames.includes(textCheck)) {
            axios.post(
                'https://localhost:8085/car/transmissionTypes', text, axiosConfig)
                .then(response => {
                    console.log("add trans radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add trans ne radi");
                })
        }else{
            console.log("postoji takav");
        }
    }

    handleModel = (event) => {
        const text = event.target.value;
        this.setState({ modelAdd: text });
    }

    handleAddModel = (event) => {

        const textCheck = this.state.modelAdd;
        const text = { name: this.state.modelAdd,
                    manufacturerId: this.state.carManufacturerModelKey
                    };
        let listOfNames = '';
        let t = [];
        this.state.models.map((k) => {
            const pubNameList = t;
            pubNameList.push(k.name);
            listOfNames = pubNameList;
        });
        if (!listOfNames.includes(textCheck)) {
            axios.post(
                'https://localhost:8085/car/models', text, axiosConfig)
                .then(response => {
                    console.log("add model radi");
                    window.location.reload(true);

                }).catch(response => {
                    console.log("add model ne radi");
                })
        }else{
            console.log("postoji takav");
        }
    }


    render() {
        let carClasses = this.state.carClasses.map(carClass => ({ key: carClass.id, value: carClass.name, text: carClass.name }));
        let carManufacturers = this.state.carManufacturers.map(carManufacturer => ({ key: carManufacturer.id, value: carManufacturer.name, text: carManufacturer.name }));
        let locations = this.state.locations.map(location => ({ key: location.id, value: location.name, text: location.name }));
        let models = this.state.models.map(model => ({ key: model.modelId, value: model.modelName, text: model.modelName }));
        let fuelTypes = this.state.fuelTypes.map(fuel => ({ key: fuel.id, value: fuel.name, text: fuel.name }));
        let transmissionTypes = this.state.transmissionTypes.map(transmission => ({ key: transmission.id, value: transmission.name, text: transmission.name }));
        return (

            <div className="section-center">
                <NavigationTab></NavigationTab>


                <div className="admindb-form">

                    <Card className="review-card">
                        <Dropdown
                            placeholder='Car Classes'
                            fluid
                            search
                            selection
                            options={carClasses}
                            onChange={this.carClassDropdownChange}
                        ></Dropdown>
                        <Button className="deleteCarClassButton" disabled={!this.state.carClass} onClick={this.deleteCarClass}>Delete Car Class</Button>
                        <Input onChange={this.handleCarClass} placeholder={"Add Car Class"}></Input>
                        <Button className="addCarClass" disabled={!this.state.carClassAdd} onClick={this.handleAddCarClass}>Add Car Class</Button>
                    </Card>

                    <Card className="review-card">
                        <Dropdown
                            placeholder='Car Manufacturer'
                            fluid
                            search
                            selection
                            options={carManufacturers}
                            onChange={this.carManufacturerDropdownChange}
                        ></Dropdown>
                        <Button className="carManufacturerButton" disabled={!this.state.carManufacturer} onClick={this.deleteCarManufacturers}>Delete Manufacturer</Button>
                        <Input onChange={this.handleCarManufacturer} placeholder={"Add Manufacturer"}></Input>
                        <Button className="addCarManufacturer" disabled={!this.state.carManufacturerAdd} onClick={this.handleAddCarManufacturer}>Add Manufacturer</Button>
                    </Card>


                    <Card className="review-card">
                        <Dropdown
                            placeholder='Model'
                            fluid
                            search
                            selection
                            options={models}
                            onChange={this.modelsDropdownChange}
                        ></Dropdown>
                        <Button className="modelButtonDelete" disabled={!this.state.model} onClick={this.deleteModel}>Delete Model</Button>
                        <Dropdown
                            placeholder='Car Manufacturer'
                            fluid
                            search
                            selection
                            options={carManufacturers}
                            onChange={this.carManufacturerDropdownChange2}
                        ></Dropdown>
                        <Input onChange={this.handleModel} placeholder={"Add Model"}></Input>
                        <Button className="modelButtonAdd" disabled={!this.state.modelAdd||!this.state.carManufacturerModel} onClick={this.handleAddModel}>Add Model</Button>


                    </Card>

                    <Card className="review-card">
                        <Dropdown
                            placeholder='Location'
                            fluid
                            search
                            selection
                            options={locations}
                            onChange={this.locationsDropdownChange}
                        ></Dropdown>
                        <Button className="locationButton" disabled={!this.state.location} onClick={this.deleteLocation}>Delete Location</Button>
                        <Input onChange={this.handleLocation} placeholder={"Add Location"}></Input>
                        <Button className="addLocation" disabled={!this.state.locationAdd} onClick={this.handleAddLocation}>Add Location</Button>
                    </Card>

                    <Card className="review-card">
                        <Dropdown
                            placeholder='Fuel'
                            fluid
                            search
                            selection
                            options={fuelTypes}
                            onChange={this.fuelTypesDropdownChange}
                        ></Dropdown>
                        <Button className="fuelButton" disabled={!this.state.fuelType} onClick={this.deleteFuelType}>Delete Fuel Type</Button>
                        <Input onChange={this.handleFuelType} placeholder={"Add Fuel Type"}></Input>
                        <Button className="addFuelType" disabled={!this.state.fuelTypeAdd} onClick={this.handleAddFuelType}>Add Fuel Type</Button>
                    </Card>
                    <Card className="review-card">
                        <Dropdown
                            placeholder='Transmission'
                            fluid
                            search
                            selection
                            options={transmissionTypes}
                            onChange={this.transmissionTypesDropdownChange}
                        ></Dropdown>
                        <Button className="transmissionButton" disabled={!this.state.transmissionType} onClick={this.deleteTransmissionType}>Delete Transmission Type</Button>
                        <Input onChange={this.handleTransmissionType} placeholder={"Add Transmission Type"}></Input>
                        <Button className="addTransmissionType" disabled={!this.state.transmissionTypeAdd} onClick={this.handleAddTransmissionType}>Add Transmission Type</Button>
                    </Card>

                </div>

            </div>

        );
    }
} export default AdminDbManage;

