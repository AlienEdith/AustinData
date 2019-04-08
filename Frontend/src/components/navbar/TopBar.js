import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import CategorySwitch from "./CategorySwitch";
import BasicControls from "./BasicControls";

class TopBar extends Component {

    render() {
        return(
            <nav className="navbar fixed-top navbar-expand-lg navbar-light bg-light" style={{zIndex: 1}}>
                <div className="container">
                    <Link className="navbar-brand" to="/"><i className="fas fa-city"></i> AAA</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon" />
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <CategorySwitch />
                        <BasicControls />
                    </div>
                </div>
            </nav>            
        )
    }
}
export default TopBar;

