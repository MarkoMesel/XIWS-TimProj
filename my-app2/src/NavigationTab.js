import React from "react";
import './MoreDetails.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { Navbar, Form, Nav } from "react-bootstrap";

function NavigationTab() {
    const isLoggedIn = localStorage.getItem("token");

    if (isLoggedIn===null) {
        return (

            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand href="/homepage">Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage">Search</Nav.Link>
                    <Nav.Link href="/postcar">Advertise(loggedin)</Nav.Link>

                </Nav>
                <Form inline>
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/register">Register</Nav.Link>
                </Form>
            </Navbar>
          
        );
    }else{  
        return (
        <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand href="/homepage">Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage">Search</Nav.Link>
                    <Nav.Link href="/postcar">Advertise(loggedin)</Nav.Link>

                </Nav>
                <Form inline>
                    <Nav.Link href="/cart"> My cart </Nav.Link>
                    <Nav.Link href="/homepage" onClick={ () => {localStorage.clear()}}>Logout</Nav.Link>
                </Form>
            </Navbar>
        );
    }
}



export default NavigationTab;