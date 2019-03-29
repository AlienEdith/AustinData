import React, { Component } from 'react';
import { connect } from 'react-redux';

import SearchBar from '../searchAndSort/SearchBar';
import HeatMap from '../heatmap/HeatMap';
import RankingList from '../rankingList/RankingList';

import { GetTop10ZipcodeByCategory } from '../../actions';
import SortForm from '../searchAndSort/SortForm'

class Traffic extends Component {

    componentDidMount() {
        this.props.GetTop10ZipcodeByCategory("traffic", "traffic");
    }

    onSubmit = (value) => {
        this.props.GetTop10ZipcodeByCategory("traffic", value.sortByCategory, value.sortByOrder);
    }

    render(){
        return(
            <div style={{marginTop: '30px'}}>
                <h1>Traffic</h1>
                <div className="row mt-4">
                    <div className="col-lg-3">
                        <SearchBar onSearchBarSubmit={this.onSearchBarSubmit}/>
                    </div>
                    <div className="col-lg-5"></div>
                    <div className="col-lg-4">
                        <SortForm defaultCategory="traffic" onSubmit={this.onSubmit}/>
                    </div>
                </div>
                
                <div className="row mt-5">
                    <div className="col-lg-8">
                        <HeatMap />
                    </div>
                    <div className="col-lg-4">
                        <RankingList data={this.props.zipcodes} category={this.props.category} order={this.props.order}/>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        zipcodes: Object.values(state.zipcodes)
    }
}

export default connect(mapStateToProps, {
    GetTop10ZipcodeByCategory
})(Traffic);
