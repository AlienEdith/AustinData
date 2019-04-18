package info.allaboutaustin.RestfulApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.allaboutaustin.RestfulApi.exception.ParameterNotValidException;
import info.allaboutaustin.RestfulApi.models.User;
import info.allaboutaustin.RestfulApi.repositories.UsersRepository;

@RestController
@CrossOrigin(origins = "*")	
@RequestMapping("/user")
public class UserController {

	@Autowired
	UsersRepository userRepo;
	
	@PostMapping("")
	public void createUser(@RequestBody String googleId) {
		googleId = googleId.substring(0, googleId.length()-1);
		User user = userRepo.findByGoogleId(googleId);
		if(user==null) {
			User newUser = new User(googleId);
			userRepo.save(newUser);
		}
	}
	
	@GetMapping("/{googleId}")
	public List<String> GetUserZipcodes(@PathVariable String googleId) {
		User user = userRepo.findByGoogleId(googleId);
		if(user==null) {
			throw new ParameterNotValidException("change to user not exists exception");
		}
		return user.getLikedZipcodes();
	}
	
	@PostMapping("/{googleId}")
	public void addLikedZipcode(@RequestBody String zipcode, @PathVariable String googleId) {
		User user = userRepo.findByGoogleId(googleId);
		if(user==null) {
			throw new ParameterNotValidException("change to user not exists exception");
		}
		zipcode = zipcode.substring(0, zipcode.length()-1);
		if(!user.containZipcode(zipcode)) {
			user.addZipcodes(zipcode);
		}
		userRepo.save(user);
	}
	
	@PostMapping("/{googleId}/delete")
	public void removeLikedZipcode(@RequestBody String zipcode, @PathVariable String googleId) {
		User user = userRepo.findByGoogleId(googleId);
		if(user==null) {
			throw new ParameterNotValidException("change to user not exists exception");
		}
		zipcode = zipcode.substring(0, zipcode.length()-1);
		if(user.containZipcode(zipcode)) {
			user.removeZipcodes(zipcode);
		}
		userRepo.save(user);
	}
}
