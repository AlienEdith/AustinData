import React, { Component } from 'react';
import SliderBar from './SliderBar'

class Survey extends Component {

    state = {
            Food: 50,
            Traffic: 50,
            Education: 50
    }

    onSliderBarFocusOut = (category, value) => {
        console.log(category+ " "+ value);
        this.setState({
                [category]: value
        })
    }

    render() {
        console.log(this.state);
        return(
            <div style={{marginTop: '20vh'}}>
                <form id="survey-form" action="" method="GET">
                    <SliderBar category="Food" onSliderBarFocusOut={this.onSliderBarFocusOut}/>
                    <SliderBar category="Education" onSliderBarFocusOut={this.onSliderBarFocusOut}/>                        <SliderBar category="Traffic" onSliderBarFocusOut={this.onSliderBarFocusOut}/>
                    <button type="button" className="btn btn-outline-success btn-lg btn-block">Submit</button>
                    <p>Selected Value stored in this.state</p>
                    <p>Food: {this.state.Food}, Education: {this.state.Education}, Traffic: {this.state.Traffic}</p>
                </form>
            </div>
        )
    }
}
export default Survey;

