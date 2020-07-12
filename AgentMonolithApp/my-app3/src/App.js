import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import './App.css';
import Login from './Login.js';
import Register from './Register.js';


import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import 'semantic-ui-css/semantic.min.css';
import ClientCarSearch from './ClientCarSearch.js';
import SearchResults from './SearchResults';
import MoreDetails from './MoreDetails';
import ShoppingCart from './ShoppingCart';
import RegistrationInfo from './RegistrationInfo';
import CarPost from './CarPost';
import AdminDbManage from './AdminDbManage';
import ClientRateCar from './ClientRateCar';
import AdminRateManage from './AdminRateManage';
import AdminUserManage from './AdminUserManage';
import AddImages from './AddImages'
import MessageBoard from './MessageBoard'
import Reservations from './ClientReservations';
import AdminLandingPage from './AdminLandingPage';
import RoleSelector from './RoleSelector';
import AgentReservations from './AgentReservations';
import AgentReports from './AgentReports';


class App extends Component {
  render() {
    return (
        <Router>
          <div className="App">
            <Switch>
              <Route exact path='/login' component={Login}></Route>
              <Route exact path='/register' component={Register}></Route>
              <Route exact path={["/", "/home", "/homepage"]} component={ClientCarSearch}></Route>
              <Route exact path='/searchresults' component={SearchResults}></Route>
              <Route exact path='/moredetails' component={MoreDetails}></Route>
              <Route exact path= '/cart' component={ShoppingCart}></Route>
              <Route exact path='registrationinfo' component={RegistrationInfo}></Route>
              <Route exact path="/carpost" component={CarPost}></Route>
              <Route exact path='/admindb' component={AdminDbManage}></Route>
              <Route exact path="/ratecar" component={ClientRateCar}></Route>
              <Route exact path="/adminrate" component={AdminRateManage}></Route>
              <Route exact path="/adminuser" component={AdminUserManage}></Route>
              <Route exact path="/AddImages" component={AddImages}></Route>
              <Route exact path="/messages" component={MessageBoard}></Route>
              <Route exact path="/clientreservations" component={Reservations}></Route>
              <Route exact path="/adminhome" component={AdminLandingPage}></Route>
              <Route exact path="/roleselector" component={RoleSelector}></Route>
              <Route exact path="/agentreservations" component={AgentReservations}></Route>
              <Route exact path="/agentreports" component={AgentReports}></Route>
            </Switch>
          </div>
        </Router>
    );
  }
}



export default App;
