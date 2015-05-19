package de.rtcustomz.getraenkeautomat.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

import de.rtcustomz.getraenkeautomat.client.UserProxy;
import de.rtcustomz.getraenkeautomat.model.User;

@Service(User.class)
public interface UserRequest extends RequestContext {
	Request<Long> countUsers();
	
	Request<List<UserProxy>> findAllUsers();
	
	Request<List<UserProxy>> findUserEntries(int firstResult, int maxResults);
	
	Request<UserProxy> findUser(int id);
	
	InstanceRequest<UserProxy, Void> persist();
	
	InstanceRequest<UserProxy, Void> create();
	
	InstanceRequest<UserProxy, Void> edit();

	InstanceRequest<UserProxy, Void> remove();
}
