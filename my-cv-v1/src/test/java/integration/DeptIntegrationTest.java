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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.qa.cv.MyCvV1Application;
import com.qa.cv.MyCvV1ApplicationTests;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Repositories.DepartmentRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyCvV1Application.class})
@AutoConfigureMockMvc
public class DeptIntegrationTest {

	static ExtentReports report = new ExtentReports("C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\Department Integration Test Results.html");;
	ExtentTest test;
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Before
	public void clearDB() {
		MyCvV1ApplicationTests.counter++;
		departmentRepo.deleteAll();
	}
	
	@After
	public void tearDown() {
	report.endTest(test);
	report.flush();
	}
	
	@Test
	public void findingADepartmentFromDatabase() throws Exception 
	{
		test = report.startTest("Finding A Department From Database Test");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		departmentRepo.save(new DepartmentModel("Big Boss"));
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String deptId = id.substring(index1, index2);
		
		if (mvc.perform(get("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Big Boss") == true) {
			test.log(LogStatus.PASS, "Department Has been found");
		} else {
			test.log(LogStatus.FAIL, "Big Boss Department not found");
		}
		
		mvc.perform(get("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.role", is("Big Boss")));
	}
	
	@Test
	public void addDepartmentToDatabase() throws Exception{
		test = report.startTest( "Adding a Department To Database Test");
		
		test.log(LogStatus.INFO, "Attempt to add the Department 'Big Boss'");
		mvc.perform(MockMvcRequestBuilders.post("/api/department")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"role\" : \"Big Boss\"}"))
		.andExpect(status()
				.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.role", is("Big Boss")));
		
		if(mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Big Boss") == true){
			test.log(LogStatus.PASS, "Department 'Big Boss' Has been found");
		} else {
			test.log(LogStatus.FAIL, "Big Boss Department not found");
		}
		
	}
	
	@Test
	public void editADepartmentInTheDatabase() throws Exception{
		test = report.startTest(  "Editing A Department In The Database");
		departmentRepo.save(new DepartmentModel("Big Boss"));
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String deptId = id.substring(index1, index2);
		test.log(LogStatus.INFO, "Attempt to change the Department name to 'Little Boss'");
		mvc.perform(put("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON)
		.content("{\"role\" : \"Little Boss\"}")).andExpect(status()
				.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.role", is("Little Boss")));
		
		if(mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Little Boss") == true){
			test.log(LogStatus.PASS, "Department name has successfully been changed to 'Little Boss'");
		} else {
			test.log(LogStatus.FAIL, "Department name change unsuccessful");
		}
		
	}
	
	@Test
	public void deleteADepartmentFromTheDatabase() throws Exception{
		test = report.startTest( "Deleting A Department From The Database");
		departmentRepo.save(new DepartmentModel("Big Boss"));
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String deptId = id.substring(index1, index2);
		test.log(LogStatus.INFO, "Attempt to delete the Department");
		mvc.perform(delete("/api/department/"+deptId).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		if(mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Little Boss") == false){
			test.log(LogStatus.PASS, "Department has been deleted");
		} else {
			test.log(LogStatus.FAIL, "Department deletion unsuccessful");
		}
	}
	
	@Test
	public void findingAllDepartmentsFromDatabase() throws Exception 	{
		test = report.startTest( "Finding All Departments From Database");
		departmentRepo.save( new DepartmentModel("Big Boss"));
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		mvc.perform(MockMvcRequestBuilders.post("/api/department")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"role\" : \"Little Boss\"}"));
		test.log(LogStatus.INFO, "Created a Department With the Name 'Little Boss'");
	
		test.log(LogStatus.INFO, "Attempt to find all Department");
		mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].role", is("Big Boss"))).andExpect(status().isOk()).andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[1].role", is("Little Boss")));
		
		if(mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Big Boss") == true &&
				mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Little Boss") == true	){
			test.log(LogStatus.PASS, "All Departments have been found");
		} else {
			test.log(LogStatus.FAIL, "Atleast one department has not been found");
		}
	}

}
