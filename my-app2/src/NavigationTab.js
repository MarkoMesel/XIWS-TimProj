import React from "react";
import './MoreDetails.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { Navbar, Form, Nav } from "react-bootstrap";

function NavigationTab() {
    const roleId = localStorage.getItem("roleId")
    if (roleId === '1') {
        return (
            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand>Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage">Search</Nav.Link>
                    <Nav.Link href="/carpost">Advertise</Nav.Link>
                    <Nav.Link href="/ratecar">Leave Feeback</Nav.Link>
                </Nav>
                <Form inline>
                    <Nav.Link href="/clientreservations"> My reservations </Nav.Link>
                    <Nav.Link href="/cart"> My cart </Nav.Link>
                    <Nav.Link href="/homepage" onClick={() => { localStorage.clear() || sessionStorage.clear() }}>Logout</Nav.Link>
                </Form>
            </Navbar>

        )
    } else if (roleId === '2') {
        return (
            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand>Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/postcar">Advertise</Nav.Link>
                    <Nav.Link href="/agentreservations">Manage Reservations</Nav.Link>
                    <Nav.Link href="/agentreports">Manage Reports</Nav.Link>

                </Nav>
                <Form inline>
                    <Nav.Link > Check comments </Nav.Link>
                    <Nav.Link href="/homepage" onClick={() => { localStorage.clear() || sessionStorage.clear() }}>Logout</Nav.Link>
                </Form>
            </Navbar>

        )
    } else if (roleId === '3') {
        return (
            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand>Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/admindb">Database</Nav.Link>
                    <Nav.Link href="/adminrate">Comments</Nav.Link>
                    <Nav.Link href="/adminuser">Users </Nav.Link>
                    <Nav.Link > manage registers </Nav.Link>
                </Nav>
                <Form inline>
                    <Nav.Link href="/homepage" onClick={() => { localStorage.clear() || sessionStorage.clear() }}>Logout</Nav.Link>
                </Form>
            </Navbar>

        )
    } else {
        return (

            <Navbar fixed="top" bg="dark" variant="dark">
                <Navbar.Brand>Rent-a-Car</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage">Search</Nav.Link>
                </Nav>
                <Form inline>
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/register">Register</Nav.Link>
                </Form>
            </Navbar>

        );
    }
}


export default NavigationTab;