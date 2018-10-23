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
import com.qa.cv.Repositories.DepartmentRepository;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class})
@DataJpaTest
public class DepartmentRepositoryTest {
	
	static ExtentReports report = new ExtentReports(
			"C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\Department Repository Test.html");
	ExtentTest test;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DepartmentRepository departmentRepo;

	@Test
	public void retrieveByIdTest() {
		test = report.startTest("Department Repository Test");
		DepartmentModel departmentModelTest = new DepartmentModel("Big Boss");
		test.log(LogStatus.INFO, "Created a Department With the Name 'Big Boss'");
		entityManager.persist(departmentModelTest);
		entityManager.flush();
		if(departmentRepo.findById(departmentModelTest.getDepartmentId()).isPresent() == true) {
			test.log(LogStatus.PASS, "Department Repository created Successfully");
		} else {
			test.log(LogStatus.FAIL, "Failed to create Department Repository");
		}
		assertTrue(departmentRepo.findById(departmentModelTest.getDepartmentId()).isPresent());
	}
	
	@After
	public void tearDown() {
		report.endTest(test);
		report.flush();
	}
}
