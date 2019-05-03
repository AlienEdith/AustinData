package info.allaboutaustin.RestfulApi.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.allaboutaustin.RestfulApi.exception.ParameterNotValidException;
import info.allaboutaustin.RestfulApi.exception.ZipcodeNotFoundException;
import info.allaboutaustin.RestfulApi.models.Zipcode;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeAverageScoreComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeEducationComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeFoodComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeTotalScoreComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeTrafficComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodesCamparatorFactory;
import info.allaboutaustin.RestfulApi.repositories.ZipcodesRepository;

@RestController
@CrossOrigin(origins = "*")	// change to host name
@RequestMapping("/filter/zipcodes")
public class FilterController {

	@Autowired
	ZipcodesRepository ZipcodeRepo;
	
	ZipcodesCamparatorFactory zcf = new ZipcodesCamparatorFactory();
	ZipcodeTotalScoreComparator tsc = new ZipcodeTotalScoreComparator();
	
	//Helper Method: sorting list by specific category order
	private void sortByCategory(List<Zipcode> list, String category){
		Collections.sort(list, zcf.createComparator(category));
	}
	
	// 
	@GetMapping("")
	public List<Zipcode> filterZipcodeByParameters (
							@RequestParam(name="foodGt", required=false, defaultValue="0") String foodGt,
							@RequestParam(name="trafficGt", required=false, defaultValue="0") String trafficGt,
							@RequestParam(name="educationGt", required=false, defaultValue="0") String educationGt,
							@RequestParam(name="regions", required=false, defaultValue="") String regions,
							@RequestParam(name="hospitals", required=false, defaultValue="false") String hospitals,
							@RequestParam(name="cinemas", required=false, defaultValue="false") String cinemas,
							@RequestParam(name="sortBy", required=false, defaultValue="average") String sortBy,
							@RequestParam(name="order", required=false, defaultValue="desc") String order) {
		
		Set<Zipcode> zipcodesSet = new HashSet<Zipcode>(ZipcodeRepo.findAll());
		
		Integer foodGtNum = 0;
		Integer trafficGtNum = 0;
		Integer educationGtNum = 0;
		
		try {
			foodGtNum = Integer.parseInt(foodGt);
			trafficGtNum = Integer.parseInt(trafficGt);
			educationGtNum = Integer.parseInt(educationGt);
		} catch (Exception e) {
			throw new ParameterNotValidException("Category Limitation must be Positive Integer Number between 0-10, please verify your input URL");
		}
		
		if((foodGtNum<0 || foodGtNum>10) || (trafficGtNum<0 || trafficGtNum>10) || (educationGtNum<0 || educationGtNum>10)) {
			throw new ParameterNotValidException("Category Limitation must be Positive Integer Number between 0-10, please verify your input URL");
		}
		
		Set<Zipcode> zipcodesByRating = new HashSet<Zipcode>(ZipcodeRepo.findByCategoryScoreGreaterThanQuery(foodGtNum, trafficGtNum, educationGtNum));
		zipcodesSet.retainAll(zipcodesByRating);
		
		
		String[] regionArr = regions.split(",");
		if(regions.length() > 1) {
			Set<Zipcode> zipcodesByRegion = new HashSet<Zipcode>();
			for(String region: regionArr) {
				List<Zipcode> list = ZipcodeRepo.findByRegion(region);
				zipcodesByRegion.addAll(new HashSet(list));
			}
			zipcodesSet.retainAll(zipcodesByRegion);
		}

		if(!hospitals.equals("true") && !hospitals.equals("false")) {
			throw new ParameterNotValidException("Hospitals must be 'true' or 'false', please verify your input URL");
		}
		
		if(hospitals.equals("true")) {
			Set<Zipcode> zipcodesByHospital = new HashSet<Zipcode>(ZipcodeRepo.findByNumOfHospitals(0));
			zipcodesSet.retainAll(zipcodesByHospital);
		}
		
		if(!cinemas.equals("true") && !cinemas.equals("false")) {
			throw new ParameterNotValidException("Cinemas must be 'true' or 'false', please verify your input URL");
		}
		
		if(cinemas.equals("true")) {
			Set<Zipcode> zipcodesByCinemas = new HashSet<Zipcode>(ZipcodeRepo.findByNumOfCinemas(0));
			zipcodesSet.retainAll(zipcodesByCinemas);
		}
		
		List<Zipcode> zipcodes = new ArrayList<Zipcode>(zipcodesSet);
		
		sortByCategory(zipcodes, sortBy);
		if(order.equals("asc"))	Collections.reverse(zipcodes);
		
		return zipcodes;
	}
	
	
}
