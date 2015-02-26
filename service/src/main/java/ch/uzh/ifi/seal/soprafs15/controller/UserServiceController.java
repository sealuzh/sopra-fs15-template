package ch.uzh.ifi.seal.soprafs15.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserLoginLogoutRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserResponseBean;
import ch.uzh.ifi.seal.soprafs15.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.uzh.ifi.seal.soprafs15.controller.beans.JsonUriWrapper;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserLoginLogoutResponseBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.user.UserStatus;
import ch.uzh.ifi.seal.soprafs15.model.Game;
import ch.uzh.ifi.seal.soprafs15.model.repositories.UserRepository;

@RestController
@RequestMapping(UserServiceController.CONTEXT)
public class UserServiceController extends GenericService {

	Logger logger = LoggerFactory.getLogger(UserServiceController.class);
	
	static final String CONTEXT = "/user"; 

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponseBean> listUsers() {
		logger.debug("listUsers");
		List<UserResponseBean> result = new ArrayList<>();
		
		UserResponseBean tmpUserResponseBean;
		for(User user : userRepo.findAll()) {
			tmpUserResponseBean = new UserResponseBean();
			
			tmpUserResponseBean.setId(user.getId());
			tmpUserResponseBean.setName(user.getName());
			tmpUserResponseBean.setUsername(user.getUsername());
			
			List<String>games = new ArrayList<>();
			for(Game game : user.getGames()) {
				games.add(game.getName());
			}
			tmpUserResponseBean.setGames(games);
			
			result.add(tmpUserResponseBean);
		}
				
		return result;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public JsonUriWrapper addUser(@RequestBody UserRequestBean userRequestBean) {
		logger.debug("addUser: " + userRequestBean);

		User user = new User();
		user.setName(userRequestBean.getName());
		user.setUsername(userRequestBean.getUsername());
		user.setStatus(UserStatus.OFFLINE);
		user.setToken(UUID.randomUUID().toString());
		user = userRepo.save(user);
		
		return getJsonUrl(CONTEXT + "/" + user.getId());
	}

	
	@RequestMapping(method=RequestMethod.GET, value = "{userId}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseBean getUser(@PathVariable Long userId) {
		logger.debug("getUser: " + userId);
		
		User user = userRepo.findOne(userId);
		
		UserResponseBean userResponseBean = new UserResponseBean();
		if(user != null) {
			userResponseBean.setId(user.getId());
			userResponseBean.setName(user.getName());
			userResponseBean.setUsername(user.getUsername());
			
			List<String>games = new ArrayList<>();
			for(Game game : user.getGames()) {
				games.add(game.getName());
			}
			userResponseBean.setGames(games);
		}
		
		return userResponseBean;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "{userId}/login")
	@ResponseStatus(HttpStatus.OK)
	public UserLoginLogoutResponseBean login(@PathVariable Long userId) {
		logger.debug("login: " + userId);
		
		User user = userRepo.findOne(userId);
		
		if(user != null) {
			user.setToken(UUID.randomUUID().toString());
			user.setStatus(UserStatus.ONLINE);
			user = userRepo.save(user);
			
			UserLoginLogoutResponseBean userLoginLogoutResponseBean = new UserLoginLogoutResponseBean();
			userLoginLogoutResponseBean.setUserToken(user.getToken());
			
			return userLoginLogoutResponseBean;
		}
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "{userId}/logout")
	@ResponseStatus(HttpStatus.OK)
	public void logout(@PathVariable Long userId, @RequestBody UserLoginLogoutRequestBean userLoginLogoutRequestBean) {
		logger.debug("getUser: " + userId);
		
		User user = userRepo.findOne(userId);
		
		if(user != null && user.getToken().equals(userLoginLogoutRequestBean.getToken())) {
			user.setStatus(UserStatus.OFFLINE);
			userRepo.save(user);
		}
	}
}
