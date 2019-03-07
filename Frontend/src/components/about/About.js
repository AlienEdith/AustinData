import React, { Component } from 'react';

import TopBar from "../navbar/TopBar";
import WebsiteDesc from './WebsiteDesc';
import GithubInfo from './GithubInfo';
import GithubStats from './GithubStats';
import Datasource from './Datasource';
import Stack from './Stack';


import './About.css'
class About extends Component {

    render() {
        return(
            <div>
                <div id="website-desc">
                    <WebsiteDesc />
                </div>
                <div id="GithubInfo">
                    <GithubInfo />
                </div>
                <div className="row">
                    <div className="col-md-4 my-3">
                        <GithubStats />
                    </div>
                    <div className="col-md-8 my-3">
                        <Datasource />
                    </div>
                </div>
                <div id="stack">
                        <Stack />                   
                </div>
            </div>
        )
    }
}
export default About;

