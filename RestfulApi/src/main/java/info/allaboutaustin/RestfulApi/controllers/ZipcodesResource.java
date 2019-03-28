package info.allaboutaustin.RestfulApi.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import info.allaboutaustin.RestfulApi.exception.ZipcodeNotFoundException;
import info.allaboutaustin.RestfulApi.models.FoodData;
import info.allaboutaustin.RestfulApi.models.SchoolData;
import info.allaboutaustin.RestfulApi.models.TrafficData;
import info.allaboutaustin.RestfulApi.models.Zipcode;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeAverageScoreComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeEducationComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeFoodComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeTotalScoreComparator;
import info.allaboutaustin.RestfulApi.models.ZipcodeComparators.ZipcodeTrafficComparator;
import info.allaboutaustin.RestfulApi.repositories.FoodsRepository;
import info.allaboutaustin.RestfulApi.repositories.ZipcodesRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")	// change to host name
@RequestMapping("/api/zipcodes")
public class ZipcodesResource {
	
	@Autowired
	ZipcodesRepository ZipcodeRepo;

	ZipcodeTotalScoreComparator tsc = new ZipcodeTotalScoreComparator();
	ZipcodeAverageScoreComparator ac = new ZipcodeAverageScoreComparator();
	ZipcodeFoodComparator fc = new ZipcodeFoodComparator();
	ZipcodeEducationComparator ec = new ZipcodeEducationComparator();
	ZipcodeTrafficComparator tc = new ZipcodeTrafficComparator();
	
	// Return All zipcodes information based on category order
	@GetMapping("")
	public List<Zipcode> getAllZipcodes(@RequestParam(name="sortBy", required=false, defaultValue="average") String sortBy,
										@RequestParam(name="order", required=false, defaultValue="desc") String order,
										@RequestParam(name="amount", required=false, defaultValue="") String amount){
		
		List<Zipcode> list = ZipcodeRepo.findAll();
		switch(sortBy) {
			case "food": Collections.sort(list, fc);		break;
			case "traffic": Collections.sort(list, tc);		break;
			case "education": Collections.sort(list, ec);	break;
			case "average": Collections.sort(list, ac);		break;
			default: Collections.sort(list, ac);
		}
		
		if(order.equals("asc"))	Collections.reverse(list);
		
//		try {
//			int number = Integer.valueOf(amount);
//			List newList = list.subList(0, number);
//			return newList;
//		}catch(Exception e){
//			throw e;	//TODOs: change to paramter error exception
//		}
//		
		return list;
	}
	
	// Return a specific zipcode information by 5-digits zipcode
	@GetMapping("/{zipcode}")
	public Zipcode getZipcode(@PathVariable String zipcode) {
		Zipcode zc = ZipcodeRepo.findByZipcode(zipcode);
		if(zc == null) {
			throw new ZipcodeNotFoundException("Zipcode "+zipcode+" not found.");
		}
		return zc;
	}
	
	// Return a ranking list of zipcodes based on user-assigned weight
	@GetMapping("/ranking")
	public List<Zipcode> searchZipcodeByParameters(
			@RequestParam(name="food", required=false, defaultValue="0") String food,
			@RequestParam(name="education", required=false, defaultValue="0") String education,
			@RequestParam(name="traffic", required=false, defaultValue="0") String traffic) {
		
		List<Zipcode> zipcodes = ZipcodeRepo.findAll();
		for(Zipcode zc: zipcodes) {
			Double totalScore = zc.getFoodScore() * Double.parseDouble(food) +
								zc.getEducationScore() * Double.parseDouble(education) +
								zc.getTrafficScore() * Double.parseDouble(traffic);
			zc.setTotalScore(totalScore);
		}
		Collections.sort(zipcodes, tsc);
		//TODO: only return top 10 results
		return zipcodes;
	}
	
	// Only for create fake data
	@PostMapping("/save")
	public void createZipCode() {
		ZipcodeRepo.deleteAll();
		FoodData food = new FoodData("78731", 5.2);
		TrafficData traffic = new TrafficData("78731");
		SchoolData school = new SchoolData("78731");
		Zipcode zc = new Zipcode("78705", "7.2", "8.4", "6.7","7.3", food, traffic, school);
		ZipcodeRepo.save(zc);
		zc = new Zipcode("78706", "6.3", "7.2", "4.3", "6.9", food, traffic, school);
		ZipcodeRepo.save(zc);
		zc = new Zipcode("78707", "8.9", "4.5", "7.5", "7.5", food, traffic, school);
		ZipcodeRepo.save(zc);
		zc = new Zipcode("78708", "7.8", "3.5", "9.2", "5.3", food, traffic, school);
		ZipcodeRepo.save(zc);
		zc = new Zipcode("78709", "5.3", "6.4", "7.3", "4.5", food, traffic, school);
		ZipcodeRepo.save(zc);
	}
	

}
