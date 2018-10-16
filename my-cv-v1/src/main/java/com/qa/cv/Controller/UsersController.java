package com.qa.cv.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.cv.Exceptions.ResourceNotFoundException;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.UserRepository;

@RestController
@RequestMapping("api/")
public class UsersController {
	
	@Autowired
	UserRepository userRepository;
	
	//Method to Create Users
	@PostMapping("/user")
	public UsersDataModel createuser(@Valid @RequestBody UsersDataModel mSDM) {
		return userRepository.save(mSDM);
	}
	
	//Method to Get a user
	@GetMapping("/user/{id}")
	public UsersDataModel getUserbyID(@PathVariable(value = "id")Long userID) {
		return userRepository.findById(userID).orElseThrow(()-> new ResourceNotFoundException("UsersDataModel", "id", userID));
	}
		
	//Method to Get all Users
	@GetMapping("/user")
	public List<UsersDataModel> getAllDepartment(){
		return userRepository.findAll();
	}
	
	//Method to Edit a user
	@PutMapping("/user/{id}")
	public UsersDataModel updateUser(@PathVariable(value = "id") Long userID,
			@Valid @RequestBody UsersDataModel userDetails) {
		
		UsersDataModel mSDM = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
		
		mSDM.setFirstName(userDetails.getFirstName());
		mSDM.setLastName(userDetails.getLastName());
		mSDM.setEmail(userDetails.getEmail());
		mSDM.setPassword(userDetails.getPassword());
		mSDM.setDepartmentID(userDetails.getDepartmentID());
		
		
		UsersDataModel updateData = userRepository.save(mSDM);
		return updateData;
	}
	
	//Method to remove a department
	@DeleteMapping("user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value ="id")Long userID){
		UsersDataModel mSDM = userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
		
		userRepository.delete(mSDM);
		return ResponseEntity.ok().build();
		
	
		
	}

}
