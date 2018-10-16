package com.qa.cv.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.qa.cv.Repositories.CvRepository;

@RestController
@RequestMapping("api/")
public class CvController {
	
	@Autowired
	CvRepository cvRepository;
	
	//Method to Post a Cv
	@PostMapping("/cv")
	public CvModel createCv(@Valid @RequestBody CvModel mSDM) {
		return cvRepository.save(mSDM);
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
	
	//Method to Edit a Cv
	@PutMapping("/cv/{id}")
	public CvModel updateCv(@PathVariable(value = "id") Long cvID,
			@Valid @RequestBody CvModel cvDetails) {
		
		CvModel mSDM = cvRepository.findById(cvID).orElseThrow(() -> new ResourceNotFoundException("Cv", "id", cvID));
			
		mSDM.setCvLink(cvDetails.getCvLink());
		mSDM.setStatus(cvDetails.getStatus());
			
		CvModel updateData = cvRepository.save(mSDM);
		return updateData;
	}
	
}
