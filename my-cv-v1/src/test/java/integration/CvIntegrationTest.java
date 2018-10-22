package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.qa.cv.MyCvV1Application;
import com.qa.cv.MyCvV1ApplicationTests;
import com.qa.cv.Model.CvModel;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.CvRepository;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class })
@AutoConfigureMockMvc
public class CvIntegrationTest {
	
	static ExtentReports report = new ExtentReports("C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\CV Integration Test Results.html");;
	ExtentTest test;
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private CvRepository cvRepo;

	@Before
	public void clearDB() {
		MyCvV1ApplicationTests.counter++;
		test = report.startTest("CV Integration Testing " + MyCvV1ApplicationTests.counter);
		userRepo.deleteAll();
		departmentRepo.deleteAll();
		cvRepo.deleteAll();
	}
	
	@After
	public void tearDown() {
	report.endTest(test);
	report.flush();
	}

	public void uploadACVTest() {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		
			
	}

	@Test
	public void getASpecificCvInfoTest() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		
		mvc.perform(get("/api/cv/" + cvId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.fileName", is("Test Cv"))).andExpect(status().isOk());
		
	}
	
	@Test
	public void getAllCvInfoForAUser() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Amazing Cv"));
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));
		
		mvc.perform(get("/api/user/" + userId +"/cv").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]fileName", is("Test Cv"))).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[1]fileName", is("Amazing Cv"))).andExpect(status().isOk());
		
		
	}
	
	@Test
	public void getAllCvsInfo() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Amazing Cv"));
		
		mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].fileName", is("Test Cv"))).andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[1].fileName", is("Amazing Cv"))).andExpect(status().isOk());
		
		
	}
	
	public void downloadCV() {
		
	}
	
	@Test
	public void deleteACv() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		mvc.perform(delete("/api/cv/" + cvId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
	}
	@Test
	public void findCvsByStatus() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		
		mvc.perform(get("/api/cv/status/Gray").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]status", is("Gray"))).andExpect(status().isOk());
	}
	
	@Test
	public void changeCvStatus() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		UsersDataModel user = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department); 
		userRepo.save(user);
		cvRepo.save(new CvModel(user, null, "Gray", "PDF", "Test Cv"));
		
		String id = mvc.perform(get("/api/cv").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String cvId = (id.substring(index1, index2));
		
		mvc.perform(put("/api/cv/" + cvId + "/status/Green").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status", is("Green")));
		}
	
}
