package repo;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.cv.MyCvV1Application;
import com.qa.cv.Model.DepartmentModel;
import com.qa.cv.Model.UsersDataModel;
import com.qa.cv.Repositories.UserRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class})
@DataJpaTest
public class UserRepositoryTest {

	static ExtentReports report = new ExtentReports(
			"C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\User Repository Test.html");
	ExtentTest test;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepo;

	@Test
	public void retrieveByIdTest() {
		test = report.startTest("User Repository Test");
		DepartmentModel department = new DepartmentModel("Big Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		UsersDataModel userModelTest = new UsersDataModel("Jon", "Snow", "js@gmail.com", "password", department );
		test.log(LogStatus.INFO, "Created a User With the Name 'Jon'");
		entityManager.persist(department);
		entityManager.persist(userModelTest);
		entityManager.flush();
		if(userRepo.findById(userModelTest.getUserId()).isPresent() == true) {
			test.log(LogStatus.PASS, "User Repository created Successfully");
		} else {
			test.log(LogStatus.FAIL, "Failed to create User Repository");
		}
		
		assertTrue(userRepo.findById(userModelTest.getUserId()).isPresent());
	}
	
	@After
	public void tearDown() {
		report.endTest(test);
		report.flush();
	}

}
