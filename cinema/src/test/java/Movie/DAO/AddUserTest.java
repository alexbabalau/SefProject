package Movie.DAO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.RequestDao;
import model.Manager;
import service.RequestService;

public class AddUserTest {
	
	@Test
	public void addUserTest() {
		RequestService requestService = RequestService.getInstance();
		RequestDao requestDao = RequestDao.getInstance();
		requestService.addRequest(new Manager("alex", "alex", "alex", "alex", "alex", "cinema"));
		assertEquals(requestDao.findRequest("alex").getCinema(),"alex");
	}
	
}
