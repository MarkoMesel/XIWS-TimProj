import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import './App.css';
import Login from './Login.js';
import Register from './Register.js';


import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import 'react-bootstrap/Accordion';
import 'semantic-ui-css/semantic.min.css';
import Homepage from './Homepage.js';
import SearchResults from './SearchResults';
import ProductListing from './ProductListing';
import ShoppingCart from './ShoppingCart';
import RegistrationInfo from './RegistrationInfo';

class App extends Component {
  render() {
    return (
        <Router>
          <div className="App">
            <Switch>
              <Route exact path='/login' component={Login}></Route>
              <Route exact path='/register' component={Register}></Route>
              <Route exact path={["/", "/home", "/homepage"]} component={Homepage}></Route>
              <Route exact path='/searchresults' component={SearchResults}></Route>
              <Route path='/productlisting/:carID' component={ProductListing}></Route>
              <Route path= '/cart' component={ShoppingCart}></Route>
              <Route exact path='registrationinfo' component={RegistrationInfo}></Route>
            </Switch>
          </div>
        </Router>
    );
  }
}



export default App;
