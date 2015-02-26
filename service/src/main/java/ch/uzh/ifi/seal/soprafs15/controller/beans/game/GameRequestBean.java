package ch.uzh.ifi.seal.soprafs15.controller.beans.game;

public class GameRequestBean {

	private String name;
	private String userToken;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}