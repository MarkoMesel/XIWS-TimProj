import React from "react";
import './ProductListing.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { Navbar, Form, Nav } from "react-bootstrap";

function NavigationTab(localStorage){
    return (
   
            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand href="/homepage">Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage">Home</Nav.Link>
                </Nav>
                <Form inline>
                    <Nav.Link href="/cart"> My cart </Nav.Link>
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/register">Register</Nav.Link>
                </Form>
            </Navbar>
    );
}

export default NavigationTab;