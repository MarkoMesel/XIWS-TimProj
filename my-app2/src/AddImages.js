import React, { Component } from 'react';
import NavigationTab from './NavigationTab.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Card, Button, ButtonGroup } from "react-bootstrap";
import 'semantic-ui-css/semantic.min.css';
import './homepage.css';
import ImageUploading from "react-images-uploading";
import './addImages.css'

const maxNumber = 5;
const maxMbFileSize = 1 * 1024 * 1024; // 1Mb

class AddImages extends Component {

    constructor(props) {
        super(props);
        this.state = {

        };
    }

    componentDidMount(){
        if (localStorage.getItem('roleId') === '3') {
            this.props.history.push("/login");
        }
        else if(localStorage.getItem('token') === null){
            this.props.history.push("/login");
        }
    }

    onChange = (imageList) => {
        // data for submit
        console.log(imageList);

    };


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
                    acceptType={["jpg", "gif", "png"]}
                >
                    {({ imageList, onImageUpload, onImageRemoveAll }) => (
                        // write your building UI
                        <div>
                        
                            <ButtonGroup>
                                <Button className="mainButtons" onClick={onImageUpload}>Upload images</Button>
                                <Button className="mainButtons" onClick={onImageRemoveAll}>Remove all images</Button>
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
            </div>
        );
    }
}
export default AddImages;
