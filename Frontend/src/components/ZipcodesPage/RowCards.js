import React, { Component } from 'react';

class RowCards extends Component {

    renderList = (data) => {
      if(data){
        var names = [];
        for(var property in data){
          names.push(property)
        }
  
        return (names.map((name) => {
            return (
              <li key={name}>{name}: {data[name]}</li>
            )
          })
        )  
      }
      
      return "No data"
    }

    render() {
        return(
          <div className="card-deck">
            <div className="card-deck col-lg-4  mt-3 align-items-stretch">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Food</h5>
                  <p className="card-text">
                    {this.renderList(this.props.food.resturaunts)}
                  </p>
                </div>
              </div>
            </div>
            <div className="card-deck col-lg-4 mt-3 align-items-stretch">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Traffic</h5>
                  <p className="card-text">Placeholder, future will have indicator for traffic congestion in zip code</p>
                </div>
              </div>
            </div>
            <div className="card-deck col-lg-4 mt-3 align-items-stretch">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Education</h5>
                  <p className="card-text">{this.renderList(this.props.education.schools)}</p>
                </div>
              </div>
            </div>
          </div>
        )
  }
}

export default RowCards;


