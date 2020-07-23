import React, { useState, useEffect } from 'react';
import { DataBase } from '../../apis/DataBase';
import ZipCodeCardComponent from '../zipcode/ZipCodeCardComponent'

const fetchResult = (weight) => {
  const [result, setResult] = useState([])

  const fetchData= async () => {
    const response = 
      await DataBase.get('/zipcodes/ranking', {
        crossdomain: true,
        params: {
          food: weight.food,
          traffic: weight.traffic, 
          education: weight.education
        }
      })
      const resultList = response.data.map((zipcode) => {
        return(
          <div key={zipcode.zipcode} className="col-lg-3">
            <ZipCodeCardComponent zipcode={zipcode}/>
          </div>
        )
      })
      setResult(resultList)
    }

    useEffect(() => {
        fetchData();
    }, [weight.food, weight.traffic, weight.education]);

    return result;
}

export default fetchResult
