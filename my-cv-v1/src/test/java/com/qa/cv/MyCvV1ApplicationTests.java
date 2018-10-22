package com.qa.cv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.relevantcodes.extentreports.ExtentReports;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCvV1ApplicationTests {

	@Test
	public void contextLoads() {
	}

public static ExtentReports report = new ExtentReports("C:\\Users\\Admin\\Desktop\\ANOTHER ONE\\APITestResults.html");
	
	public static int counter = 0;
}
