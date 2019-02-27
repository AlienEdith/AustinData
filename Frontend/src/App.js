import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';

import Landing from './components/landing/Landing'
import About from './components/about/About'
import Survey from './components/survey/Survey'
import './App.css';



class App extends Component {

  render() {
    return (
      <div>
        <BrowserRouter>
            <Route path="/" exact component={Landing} />
        </BrowserRouter>
        <BrowserRouter>
            <Route path="/about" exact component={About} />
        </BrowserRouter>
        <BrowserRouter>
            <Route path="/survey" exact component={Survey} />
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
