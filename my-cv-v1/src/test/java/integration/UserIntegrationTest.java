package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

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
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.DepartmentRepository;
import com.qa.cv.Repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MyCvV1Application.class})
@AutoConfigureMockMvc
public class UserIntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Before
	public void clearDB() {
		userRepo.deleteAll();
		departmentRepo.deleteAll();
	}
	
	@Test
	public void findingAllUsersFromDatabase() throws Exception{
		
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		userRepo.save(new UsersDataModel("Tom", "Jones", "js@gmail.com", "password", department));
		
		mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].firstName", is("Jon"))).andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[1].firstName", is("Tom")));
	}
	
	@Test
	public void findingAUserFromDatabase() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));
		String[] userId1 = (userId.split(" "));
		String userId2 = userId;
	
		
		mvc.perform(get("/api/user/" + userId).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.firstName", is("Jon"))).andExpect(status().isOk());
	}
	
	@Test
	public void editAUserInDatabase() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = id.substring(index1, index2);
		mvc.perform(put("/api/user/"+userId).contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\": \"Kilua\",\"lastName\" : \"Gon\", \"email\" : \"rock\",\"password\" : \"paper\"}")).andExpect(status()
						.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", is("Kilua")));
	}
	

//	@Test
//	public void createAUserInDatabase() throws Exception {
//		DepartmentModel department = new DepartmentModel("Big Boss");
//		departmentRepo.save(department);
//		
//		mvc.perform(MockMvcRequestBuilders.post("api/department/" + department.getDepartmentId() + "/user")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content("{\"firstName\": \"Kilua\",\"lastName\" : \"Gon\", \"email\" : \"rock\",\"password\" : \"paper\"}"))
//		.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//	.andExpect(jsonPath("$.firstName", is("Kilua")));
//	}
	
	@Test
	public void getAUserByName() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		String name = "Jon";
		String lastName = "Snow";
		
		mvc.perform(get("/api/findbyname/" + name + "&" + lastName).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].firstName", is("Jon"))).andExpect(status().isOk());
		
	}
	
	@Test
	public void getAllUsersInADepartment() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		DepartmentModel department2 = new DepartmentModel("Little Boss");
		departmentRepo.save(department);
		departmentRepo.save(department2);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		userRepo.save(new UsersDataModel("Will", "Smith", "ws@gmail.com", "MIB", department2));
		
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		int deptId = Integer.parseInt(id.substring(index1, index2));
		int dept2Id = deptId +1;
		
		mvc.perform(get("/api/department/" + dept2Id + "/user").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]firstName", is("Will"))).andExpect(status().isOk());
		
	}
	
	@Test
	public void getAUserByEmailAndPassword() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		String email = "js@gmail.com";
		String password = "password";
		
		mvc.perform(get("/api/login/" + email + "&" + password).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.content.[0]firstName", is("Jon"))).andExpect(status().isOk());
		
	}
	
	@Test
	public void deleteAUser() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		departmentRepo.save(department);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		
		String id = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		String userId = (id.substring(index1, index2));
		
		mvc.perform(delete("/api/user/"+userId).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateAUsersDepartment() throws Exception {
		DepartmentModel department = new DepartmentModel("Big Boss");
		DepartmentModel department2 = new DepartmentModel("Little Boss");
		departmentRepo.save(department);
		departmentRepo.save(department2);
		userRepo.save(new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department));
		
		String id = mvc.perform(get("/api/department").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int index1 = id.indexOf(":") + 1;
		int index2 = id.indexOf(",");
		int deptId = Integer.parseInt(id.substring(index1, index2));
		int dept2Id = deptId +1;

		String uId = mvc.perform(get("/api/user").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
		int userIndex1 = uId.indexOf(":") + 1;
		int userIndex2 = uId.indexOf(",");
		String userId = (uId.substring(userIndex1, userIndex2));
		
		mvc.perform(put("/api/department/"+ dept2Id + "user/" +userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status()
						.isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.departmentId", is(dept2Id)));
		
		
	}
	
	
	
	
}
	


