import React, { Component } from 'react';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Card, Button, ButtonGroup } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import './homepage.css';
import ImageUploading from "react-images-uploading";
import './addImages.css'
import axios from 'axios';
import { Input } from 'semantic-ui-react';


const axiosConfig = {
    headers: {
        'token': localStorage.getItem('token'),
    }
};
const axiosConfig2 = {
    headers: {
        'token': localStorage.getItem('token'),
        'content-type': 'multipart/form-data'
    }
};

const maxNumber = 5;
const maxMbFileSize = 1 * 1024 * 1024; // 1Mb

class AddImages extends Component {

    constructor(props) {
        super(props);
        this.state = {
            images: [],
            file: null,
        };
        this.handleSubmitCar = this.handleSubmitCar.bind(this);
        this.onFormSubmit = this.onFormSubmit.bind(this)
        this.onChange2 = this.onChange2.bind(this)
    }

    componentDidMount() {
        if (localStorage.getItem('roleId') === '3') {
            this.props.history.push("/login");
        }
        else if (localStorage.getItem('token') === null) {
            this.props.history.push("/login");
        }
    }

    onChange = (imageList) => {

        const data = new FormData();
        data.append('image', '/C:/Users/kusic/Desktop/m2.jpg');
        console.log(data);
        // data for submit
        console.log(imageList);
        this.setState({ images: imageList });
        console.log(imageList);
        console.log(this.state.images);

    };

    handleSubmitCar() {
        if (this.state.images !== null) {

            const carData = {
                locationId: sessionStorage.getItem('LocationKey'),
                modelId: sessionStorage.getItem('ModelKey'),
                fuelTypeId: sessionStorage.getItem('fuelKey'),
                transmissionTypeId: sessionStorage.getItem('transmissionKey'),
                carClassId: sessionStorage.getItem('carClassKey'),
                mileage: sessionStorage.getItem('mileageKey'),
                childSeats: sessionStorage.getItem('childSeatsKey')
            }
            axios.post(
                'https://localhost:8085/car/add', carData, axiosConfig
            ).then(response => {
                if (response.status === 200) {
                    console.log("Added part 1");
                    sessionStorage.setItem('carAddNewKey', response.data);
                }
            }).catch(response => {
                console.log('greska Add first part');
            });

            let imageLink = 'https://localhost:8085/car/' + sessionStorage.getItem('carAddNewKey') + '/image';
            let image = this.state.images.map((k) => {
                const formData = new FormData();

                formData.append('image', this.state.file);

                let ImgData = {
                    image: k.dataURL
                }
                axios.post(
                    imageLink, formData, axiosConfig2
                ).then(response => {
                    if (response.status === 200) {
                        console.log("Added image");
                    }
                }).catch(response => {
                    console.log('greska image add');
                });
            })


        }
    }

    onFormSubmit(e){
        e.preventDefault() // Stop form submit
        this.fileUpload(this.state.file).then((response)=>{
          console.log(response.data);
        })
      }
    
      onChange2(e) {
        this.setState({file:e.target.files[0]})
      }

    render() {
        return (
            <div>
                <NavigationTab></NavigationTab>
                <h2 className="imageTitle">Choose up to 5 images:</h2>


                <ImageUploading
                    onChange={this.onChange}
                    maxNumber={maxNumber}
                    multiple
                    maxFileSize={maxMbFileSize}
                    acceptType={["jpg", "png"]}
                >
                    {({ imageList, onImageUpload, onImageRemoveAll }) => (
                        // write your building UI
                        <div>

                            <ButtonGroup>
                                <Button className="mainButtons" onClick={onImageUpload}>Upload images</Button>
                                <Button className="mainButtons" onClick={onImageRemoveAll}>Remove all images</Button>
                                <form onSubmit={this.onFormSubmit}>
                                    <Input type="file" onChange={this.onChange2} />
                                    <Button type="submit">Upload</Button>
                                </form>
                            </ButtonGroup>
                            <Card>
                                {imageList.map((image) => (
                                    <div key={image.key}>
                                        <div>
                                            <img className="addImage" src={image.dataURL} />
                                        </div>
                                        <ButtonGroup className="buttonGroupSmall">
                                            <Button className="uploadButtons" onClick={image.onUpdate}>Update</Button>
                                            <Button className="uploadButtons" onClick={image.onRemove}>Remove</Button>
                                        </ButtonGroup>
                                    </div>
                                ))}
                            </Card>
                        </div>
                    )}
                </ImageUploading>
                <Button onClick={this.handleSubmitCar}>Submit your car</Button>
            </div>
        );
    }
}
export default AddImages;
