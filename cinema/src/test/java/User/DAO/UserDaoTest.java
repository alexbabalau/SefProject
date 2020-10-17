package User.DAO;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.jupiter.api.*;

import dao.UserDao;
import model.Regular;
import model.User;
import service.UserService;
import javax.sql.DataSource;

public class UserDaoTest {
	private UserService userService;
	private UserDao userDao;
	
	private DataSource dataSource;

	@BeforeEach
	public void setup() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/cinema_tracker");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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