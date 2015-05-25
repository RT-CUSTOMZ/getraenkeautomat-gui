package de.rtcustomz.getraenkeautomat.shared.requests;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.proxies.UserProxy;
import de.rtcustomz.getraenkeautomat.server.daos.UserDAO;

@Service(UserDAO.class)
public interface UserRequest extends RequestContext {
	Request<Long> countUsers();
	
	Request<List<UserProxy>> findAllUsers();
	
	Request<List<UserProxy>> findUserEntries(int firstResult, int maxResults);
	
	Request<UserProxy> findById(int id);
	
	Request<Void> save(UserProxy user);
	
	Request<UserProxy> createUser(String first_name, String last_name, String nickname);

	Request<Void> delete(UserProxy user);
}
