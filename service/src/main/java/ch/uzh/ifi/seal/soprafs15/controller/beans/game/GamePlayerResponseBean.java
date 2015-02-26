package ch.uzh.ifi.seal.soprafs15.controller.beans.game;

public class GamePlayerResponseBean {

	private Long userId;
	private Integer numberOfMoves;
	

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getNumberOfMoves() {
		return numberOfMoves;
	}
	public void setNumberOfMoves(Integer numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
}