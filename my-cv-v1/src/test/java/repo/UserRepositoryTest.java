package repo;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.cv.MyCvV1Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MyCvV1Application.class})
@DataJpaTest
public class UserRepositoryTest {

}
