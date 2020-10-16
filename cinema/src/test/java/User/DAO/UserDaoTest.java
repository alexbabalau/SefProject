package User.DAO;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.jupiter.api.*;

import dao.UserDao;
import model.Regular;
import model.User;
import service.UserService;
import javax.sql.DataSource;

public class UserDaoTest {
	private UserService userService;
	private UserDao userDao;
	
	@Resource(name="jdbc/cinema_tracker")
	private DataSource dataSource;

	@BeforeEach
	public void setup() {
		userService = UserService.getInstance(dataSource);
		userDao = UserDao.getInstance(dataSource);
	}
	
	@Test
	public void addUserTest() {
		User tempUser = new Regular("daria", "daria", "daria", "daria@yahoo.com", "07x3");
		
		userService.addUser(tempUser, false);
		
		User fromDBUser = userDao.getUser("daria");
		
		assertEquals(tempUser, fromDBUser);
	}
	
}