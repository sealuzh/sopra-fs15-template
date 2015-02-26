package ch.uzh.ifi.seal.soprafs15.controller.beans.game;

public class GameResponseBean {

	private Long id;
	private String game;
	private String owner;
	private GameStatus status;
	private Integer numberOfMoves;	
	private Integer numberOfPlayers;
	private String nextPlayer;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	public Integer getNumberOfMoves() {
		return numberOfMoves;
	}
	public void setNumberOfMoves(Integer numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public String getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
}