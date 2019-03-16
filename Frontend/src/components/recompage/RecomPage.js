import React, { Component } from 'react';
import { connect } from 'react-redux';

import { DataBase } from '../../apis/DataBase';

class RecomPage extends Component {

    state = {
        zipcodes: []
    }

    async componentDidMount(){
        const response = 
            await DataBase.get('/zipcodes/ranking', {
                crossdomain: true,
                params: {
                    food: this.props.weight.food,
                    traffic: this.props.weight.traffic, 
                    education: this.props.weight.education
                }
            })
        this.setState({
            zipcodes: response.data
        })
    }

    renderZipcodeList = () => {
        if(this.state.zipcodes){
            return this.state.zipcodes.map( (zipcode) => {
                return <div key={zipcode.zipcode}>{zipcode.zipcode}</div>
            })
        }

    }

    render(){
        if(!this.props.weight){
            return <div>Loading</div>
        }
        return(
            <div>
                Recommendation Page
                <ul>
                    <li>Food Weight: {this.props.weight.food}</li>
                    <li>Traffic Weight: {this.props.weight.traffic}</li>
                    <li>Education Weight: {this.props.weight.education}</li>
                </ul>
                {this.renderZipcodeList()}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        weight: state.weight
    }
}

export default connect(mapStateToProps)(RecomPage)