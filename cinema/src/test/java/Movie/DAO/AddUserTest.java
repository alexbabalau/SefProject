package Movie.DAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import dao.RequestDao;
import model.Manager;
import service.RequestService;

public class AddUserTest {
	
	private RequestService requestService;
	private RequestDao requestDao;

	@BeforeEach
	public void setup() {
		requestService = RequestService.getInstance();
		requestDao = RequestDao.getInstance();
	}
	
	@Test
	public void addUserTest() {
		requestService.addRequest(new Manager("alex", "alex", "alex", "alex", "alex", "cinema"));
		assertEquals(requestDao.findRequest("alex").getCinema(),"cinema");
	}
	
	@AfterEach
	public void destroy() {
		requestDao.close();
	}
}
