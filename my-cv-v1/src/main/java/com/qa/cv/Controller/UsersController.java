package com.qa.cv.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;

@RestController
@RequestMapping("api/")
public class UsersController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	// Method to Create Users
	@PostMapping("/department/{departmentId}/user")
	public UsersDataModel createuser(@PathVariable(value = "departmentId") Long departmentId,
			@Valid @RequestBody UsersDataModel usersDataModel) {
		return departmentRepository.findById(departmentId).map(departmentModel -> {
			usersDataModel.setDepartmentID(departmentModel);
			return userRepository.save(usersDataModel);
		}).orElseThrow(() -> new ResourceNotFoundException("Department", "id", usersDataModel));
	}

	// Method to get a user
	@GetMapping("/user/{userId}")
	public UsersDataModel getUserByUserId(@PathVariable(value = "userId") Long userId, Pageable pageable) {
		Optional<UsersDataModel> user = userRepository.findById(userId);
		System.out.println(user.get().getDepartmentId().getDepartmentId().toString());
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("UserModel", "id", userId));
	}

	// Method to get user with email and password (Log in)
	@GetMapping("/user/{email}&{password}")
	public Page<UsersDataModel> getAllUsersByEmail(@PathVariable(value = "email") String email,
			@PathVariable(value = "password") String password, Pageable pageable) {

		Page<UsersDataModel> user = userRepository.findByEmail(email, pageable);
		if (!user.getContent().get(0).getPassword().toString().equals(password)) {
			throw new ResourceNotFoundException(email, email, null);
		}

		return userRepository.findByEmail(email, pageable);
	}

	// Method to Get all Users in a given department
	@GetMapping("/department/{departmentId}/user")
	public Page<UsersDataModel> getAllUsersByDepartmentId(
			@PathVariable(value = "departmentId") DepartmentModel departmentId, Pageable pageable) {
		return userRepository.findByDepartmentId(departmentId, pageable);
	}

	// Method to get all users
	@GetMapping("/user")
	public List<UsersDataModel> getAlluser() {
		return userRepository.findAll();
	}

	// Method to Edit a user
	@PutMapping("/department/{departmentId}/user/{userId}")
	public UsersDataModel updateUser(@PathVariable(value = "departmentId") Long departmentId,
			@PathVariable(value = "userId") Long userId, @Valid @RequestBody UsersDataModel userRequest) {

		if (!departmentRepository.existsById(departmentId)) {
			throw new ResourceNotFoundException("Department", "id", userRequest);
		}

		return userRepository.findById(userId).map(user -> {
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setEmail(userRequest.getEmail());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User", "id", userRequest));
	}

	// Method to remove a user
	@DeleteMapping("/department/{departmentId}/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "departmentId") Long departmentId,
			@PathVariable(value = "userId") Long userId) {

		if (!departmentRepository.existsById(departmentId)) {
			throw new ResourceNotFoundException("Department", "id", departmentId);
		}

		return userRepository.findById(userId).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("UserId", userId.toString(), null));

	}

}
