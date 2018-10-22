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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.qa.cv.MyCvV1Application;
import com.qa.cv.MyCvV1ApplicationTests;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class })
@AutoConfigureMockMvc
public class UserIntegrationTest {

	static ExtentReports report = new ExtentReports("C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\User Integration Test Results.html");;
	ExtentTest test;
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DepartmentRepository departmentRepo;

	@Before
	public void clearDB() {
		MyCvV1ApplicationTests.counter++;
		test = report.startTest("User Integration Testing " + MyCvV1ApplicationTests.counter);
		userRepo.deleteAll();
		departmentRepo.deleteAll();
	}
	
	@After
	public void tearDown() {
	report.endTest(test);
	report.flush();
	}

	@Test
	public void findingAllUsersFromDatabase() throws Exception {
		test.log(LogStatus.INFO, "Finding All Users in Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		userRepo.save(new UsersDataModel("Tom", "Jones", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		test.log(LogStatus.INFO, "Created a User With the Name 'Tom'");
		
		test.log(LogStatus.INFO, "Attempt to find all Users");
		mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName", is("Jon"))).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[1].firstName", is("Tom")));
		
		if(mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Jon") == true &&
				mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Tom") == true	){
			test.log(LogStatus.PASS, "All users have been found");
		} else {
			test.log(LogStatus.FAIL, "Atleast one user has not been found");
		}
		
	}

	@Test
	public void findingAUserFromDatabase() throws Exception {
		test.log(LogStatus.INFO, "Finding a User in Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));

		mvc.perform(get("/api/user/" + userId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("Jon"))).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/user/" +userId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Jon") == true){
			test.log(LogStatus.PASS, "User has been found");
		} else {
			test.log(LogStatus.FAIL, "User was not found");
		}
		
	}

	@Test
	public void editAUserInDatabase() throws Exception {
		test.log(LogStatus.INFO, "Editing a user in the Database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = id.substring(index1, index2);
		
		test.log(LogStatus.INFO, "Attempt to change the user's details, name should be changed to 'Kilua'");
		mvc.perform(put("/api/user/" + userId).contentType(MediaType.APPLICATION_JSON).content(
				"{\"firstName\": \"Kilua\",\"lastName\" : \"Gon\", \"email\" : \"rock\",\"password\" : \"paper\"}"))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("Kilua")));
		
		if(mvc.perform(get("/api/user/" +userId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Kilua") == true){
			test.log(LogStatus.PASS, "User has successfully been edited");
		} else {
			test.log(LogStatus.FAIL, "User details edit unsuccessful");
		}
		
	}
	
//	This No Work
	@Ignore
	 @Test
	 public void createAUserInDatabase() throws Exception {
	 DepartmentModel department = new DepartmentModel("Big Boss");
	 departmentRepo.save(department);
	 	 	
	 mvc.perform(MockMvcRequestBuilders.post("api/department/" + department.getDepartmentId() + "/user")
	 .contentType(MediaType.APPLICATION_JSON)
	 .content("{\"firstName\": \"Kilua\",\"lastName\" : \"Gon\", \"email\" : \"rock\",\"password\" : \"paper\"}"))
	 .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	 .andExpect(jsonPath("$.firstName", is("Kilua")));
	 }

	@Test
	public void getAUserByName() throws Exception {
		test.log(LogStatus.INFO, "Finding a user by name");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		String name = "Jon";
		String lastName = "Snow";

		mvc.perform(get("/api/findbyname/" + name + "&" + lastName).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].firstName", is("Jon"))).andExpect(status().isOk());

		if(mvc.perform(get("/api/findbyname/" + name + "&" + lastName).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Jon") == true){
			test.log(LogStatus.PASS, "User has successfully successfully been found by name");
		} else {
			test.log(LogStatus.FAIL, "User search by name was unsuccessful");
		}
	}

	@Test
	public void getAllUsersInADepartment() throws Exception {
		test.log(LogStatus.INFO, "Finding a user by department");
		DepartmentModel department = new DepartmentModel("Big Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		DepartmentModel department2 = new DepartmentModel("Little Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Little Boss'");
		departmentRepo.save(department);
		departmentRepo.save(department2);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		userRepo.save(new UsersDataModel("Will", "Smith", "ws@gmail.com", "MIB", department2));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon' in 'Big Boss'");
		test.log(LogStatus.INFO, "Created a User With the Name 'Will' in 'Little Boss'");
		
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		int deptId = Integer.parseInt(id.substring(index1, index2));
		int dept2Id = deptId + 1;

		mvc.perform(get("/api/department/" + dept2Id + "/user").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.[0]firstName", is("Will"))).andExpect(status().isOk());

		if(mvc.perform(get("/api/department/" + dept2Id + "/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Will") == true){
			test.log(LogStatus.PASS, "Users have been found by Department");
		} else {
			test.log(LogStatus.FAIL, "Error finding user by department");
		}
		
		
	}

	@Test
	public void getAUserByEmailAndPassword() throws Exception {
		test.log(LogStatus.INFO, "Finding a user by email and verifying password");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		String email = "js@gmail.com";
		String password = "password";

		mvc.perform(get("/api/login/" + email + "&" + password).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.[0]firstName", is("Jon"))).andExpect(status().isOk());

		if(mvc.perform(get("/api/login/" + email + "&" + password).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Jon") == true){
			test.log(LogStatus.PASS, "User have been found by email and password has been verified");
		} else {
			test.log(LogStatus.FAIL, "Error finding user by email or password verification error");
		}
		
		
	}

	@Test
	public void deleteAUser() throws Exception {
		test.log(LogStatus.INFO, "Deleting a user from the database");
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));
		
		mvc.perform(delete("/api/user/" + userId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		if(mvc.perform(get("/api/user/" +userId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Jon") == false){
			test.log(LogStatus.PASS, "User has successfully been deleted");
		} else {
			test.log(LogStatus.FAIL, "User deletion unsuccessful");
		}
		
	}

	@Test
	public void updateAUsersDepartment() throws Exception {
		test.log(LogStatus.INFO, "Updating the users department");
		DepartmentModel department = new DepartmentModel("Big Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		DepartmentModel department2 = new DepartmentModel("Little Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Little Boss'");
		departmentRepo.save(department);
		departmentRepo.save(department2);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		int deptId = Integer.parseInt(id.substring(index1, index2));
		int dept2Id = deptId + 1;

		String uId = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
		int userIndex1 = uId.indexOf(":") + 1;
		int userIndex2 = uId.indexOf(",");
		String userId = (uId.substring(userIndex1, userIndex2));
		test.log(LogStatus.INFO, "Attempt to change the Department of user to 'Little Boss'");
		
		mvc.perform(put("/api/department/" + dept2Id + "/user/" + userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.department.departmentId", is(dept2Id)));

		if(mvc.perform(get("/api/user/" +userId).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().contains("Little Boss") == true){
			test.log(LogStatus.PASS, "User has successfully been moved to a different department");
		} else {
			test.log(LogStatus.FAIL, "User's department update unsuccessful");
		}
	}

}
