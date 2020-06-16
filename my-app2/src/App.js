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
              <Route path= '/cart' component={ShoppingCart}></Route>
              <Route exact path='registrationinfo' component={RegistrationInfo}></Route>
              <Route exact path="/postcar" component={CarPost}></Route>
              <Route exact path='/admindb' component={AdminDbManage}></Route>
            </Switch>
          </div>
        </Router>
    );
  }
}



export default App;
