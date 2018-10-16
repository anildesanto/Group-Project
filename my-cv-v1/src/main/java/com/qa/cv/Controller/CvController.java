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
import com.qa.cv.Model.CvModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.CvRepository;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;

@RestController
@RequestMapping("api/")
public class CvController {
	
	@Autowired
	CvRepository cvRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	
	//Method to Post a Cv
	@PostMapping("/department/{departmentId}/user/{userId}/cv")
	public CvModel addCv(@PathVariable(value = "departmentId") Long departmentId,
			@PathVariable(value = "userId") Long userId, @Valid @RequestBody CvModel cvModel, UsersDataModel usersDataModel) {
			return userRepository.findById(userId).map(userModel -> {
				cvModel.setUserId(userModel);
				return cvRepository.save(cvModel);
			}).orElseThrow(() -> new ResourceNotFoundException("User", "id", usersDataModel));
	
	}
	
	//Method to Get a Cv
	@GetMapping("/cv/{id}")
	public CvModel getCvbyID(@PathVariable(value = "id")Long cvID) {
		return cvRepository.findById(cvID).orElseThrow(()-> new ResourceNotFoundException("CvModel", "id", cvID));
	}
	
	//Method to Get all Cvs
	@GetMapping("/cv")
	public List<CvModel> getAllcv(){
		return cvRepository.findAll();
	}
	
	// Method to Edit a Cv
	@PutMapping("/department/{departmentId}/user/{userId}/cv/{cvId}")
	public CvModel updateUser(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "cvId") Long cvId,
			@Valid @RequestBody CvModel cvRequest) {
		
		if(!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("Department", "id", cvRequest);
		}
		
		return cvRepository.findById(cvId).map( cv -> {
			cv.setCvLink(cvRequest.getCvLink());
			cv.setStatus(cvRequest.getStatus());
			return cvRepository.save(cv);
		}).orElseThrow(() -> new ResourceNotFoundException("CV", "id", cvRequest));
	}

	// Method to remove a user
		@DeleteMapping("/department/{departmentId}/user/{userId}/cv/{cvId}")
		public ResponseEntity<?> deleteCv(@PathVariable(value = "userId") Long userId,
				@PathVariable(value = "cvId") Long cvId) {
			
			if(!userRepository.existsById(userId)) {
				throw new ResourceNotFoundException("Department", "id", userId);
			}
			
			return cvRepository.findById(cvId).map(cv -> {
				cvRepository.delete(cv);
				return ResponseEntity.ok().build();
						}).orElseThrow(() -> new ResourceNotFoundException("UserId", userId.toString(), null));

		

		}
	
}
