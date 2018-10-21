package com.qa.cv.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
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
	public CvModel getCvbyID(@PathVariable(value = "cvid") Long cvId) throws SQLException {
		Optional<CvModel> cv = cvRepository.findById(cvId);
		return cv.get();
	}
	// Method to Get a Cv
	@GetMapping("/cv/{cvid}/download")
	public void downloadCv(HttpServletResponse response, @PathVariable(value = "cvid") Long cvId) throws IOException, SQLException {
		Optional<CvModel> cv = cvRepository.findById(cvId);
		CvModel cvModel = cv.get();
		response.setContentType(cvModel.getFileType());
		IOUtils.copy(cvModel.getCvLink().getBinaryStream(), response.getOutputStream());
		response.flushBuffer();
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
	
	// Method to Get all Cvs with a given status
	@GetMapping("/cv/status/{status}")
	public Page<CvModel> getAllcvByStatus(@PathVariable(value = "status") String status, Pageable pageable){
		
		return cvRepository.findByStatus(status, pageable);
		
	}

	// Method to set status/flag of cv 
	@PutMapping("/cv/{cvId}/status/{status}")
	public CvModel updateCv(@PathVariable(value = "cvId") Long cvId
			, @PathVariable(value = "status") String status) throws SerialException, SQLException {
		CvModel cvModel = cvRepository.findById(cvId).get();
		cvModel.setStatus(status);
		SerialBlob b = new SerialBlob(cvModel.getCvLink().getBytes(1l, (int) cvModel.getCvLink().length()));
		cvModel.setCvLink(b);
		return cvRepository.save(cvModel);
	}


	

	// Method to remove a user
	@DeleteMapping("/cv/{cvId}")
	public ResponseEntity<?> deleteCv(@PathVariable(value = "cvId") Long cvId) {
		return cvRepository.findById(cvId).map(cv -> {
			cvRepository.delete(cv);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("CV", "id", null));
	}

}
