package de.rtcustomz.getraenkeautomat.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.server.ModelServiceLocator;
import de.rtcustomz.getraenkeautomat.server.UserDAO;

@Service(value = UserDAO.class, locator = ModelServiceLocator.class)
public interface UserRequest extends RequestContext {
	Request<Long> countUsers();
	
	Request<List<UserProxy>> findAllUsers();
	
	Request<List<UserProxy>> findUserEntries(int firstResult, int maxResults);
	
	Request<UserProxy> findUser(int id);
	
	Request<Void> save(UserProxy User);
	
	Request<UserProxy> createUser(String id, String type);

	Request<Void> delete(UserProxy User);
}
