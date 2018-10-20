package com.qa.cv.Controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	private SessionFactory sessionFactory;
	// Method to Post a Cv
	@PostMapping("/user/{userId}/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public CvModel addCv(@PathVariable(value = "userId") Long userId,
			UsersDataModel usersDataModel, @FormParam ("file") MultipartFile file){
		CvModel cvModel = new CvModel();
		return userRepository.findById(userId).map(userModel -> {
			
			cvModel.setStatus("Gray");
			try {
				cvModel.setFileType(file.getContentType());
				cvModel.setUser(userModel);
				SerialBlob b = new SerialBlob(file.getBytes());
				cvModel.setCvLink(b);
			
			} catch (IOException | SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return cvRepository.save(cvModel);
		}).orElseThrow(() -> new ResourceNotFoundException("User", "id", usersDataModel));
	}
	// Method to Get a Cv
	@GetMapping("/cv/{cvid}")
	public Blob getCvbyID(@PathVariable(value = "cvid") Long cvId) {
		Optional<CvModel> cv = cvRepository.findById(cvId);
		System.out.println(cv.get().getUser().getUserId().toString());
		return cv.get().getCvLink();
	}
	// Method to Get a Cv
	@GetMapping("/cv/{cvid}/download")
	public Blob downloadCv(@PathVariable(value = "cvid") Long cvId) {
		Optional<CvModel> cv = cvRepository.findById(cvId);
		System.out.println(cv.get().getUser().getUserId().toString());
		return cv.get().getCvLink();
	}
	// Method to Get all Cvs for a given user
	@GetMapping("/user/{userId}/cv")
	public Page<CvModel> getAllCvsByUserId(@PathVariable(value = "userId") UsersDataModel userId, Pageable pageable) {
		return cvRepository.findByUserId(userId, pageable);
	}

	// Method to Get all Cvs
	@GetMapping("/cv")
	public List<CvModel> getAllcv() {
		return cvRepository.findAll();
	}

	// Method to Edit a Cv
	@PutMapping("/user/{userId}/cv/{cvId}")
	public CvModel updateUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "cvId") Long cvId,
			@Valid @RequestBody CvModel cvRequest) {

		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("Department", "id", cvRequest);
		}

		return cvRepository.findById(cvId).map(cv -> {
			cv.setCvLink(cvRequest.getCvLink());
			cv.setStatus(cvRequest.getStatus());
			return cvRepository.save(cv);
		}).orElseThrow(() -> new ResourceNotFoundException("CV", "id", cvRequest));
	}

	// Method to remove a user
	@DeleteMapping("/user/{userId}/cv/{cvId}")
	public ResponseEntity<?> deleteCv(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "cvId") Long cvId) {

		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException("Department", "id", userId);
		}

		return cvRepository.findById(cvId).map(cv -> {
			cvRepository.delete(cv);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("UserId", userId.toString(), null));
	}

}
