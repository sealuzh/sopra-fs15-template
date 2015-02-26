package ch.uzh.ifi.seal.soprafs15.controller;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs15.model.Move;
import ch.uzh.ifi.seal.soprafs15.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.uzh.ifi.seal.soprafs15.GameConstants;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GameMoveRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GameMoveResponseBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GamePlayerRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GamePlayerResponseBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GameRequestBean;
import ch.uzh.ifi.seal.soprafs15.controller.beans.game.GameResponseBean;
import ch.uzh.ifi.seal.soprafs15.model.Game;
import ch.uzh.ifi.seal.soprafs15.model.repositories.GameRepository;
import ch.uzh.ifi.seal.soprafs15.model.repositories.UserRepository;

@RestController
public class GameServiceController extends GenericService {

	Logger logger = LoggerFactory.getLogger(GameServiceController.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private GameRepository gameRepo;

	private final String CONTEXT = "/game";

	/*
	 *	Context: /game
	 */
	
	@RequestMapping(value = CONTEXT)
	@ResponseStatus(HttpStatus.OK)
	public List<GameResponseBean> listGames() {
		logger.debug("listGames");
		List<GameResponseBean> result = new ArrayList<>();
		
		GameResponseBean tmpGameResponseBean;
		for(Game game : gameRepo.findAll()) {
			tmpGameResponseBean = new GameResponseBean();
			
			tmpGameResponseBean.setId(game.getId());
			tmpGameResponseBean.setGame(game.getName());
			tmpGameResponseBean.setOwner(game.getOwner());
			tmpGameResponseBean.setStatus(game.getStatus());
			tmpGameResponseBean.setNumberOfMoves(game.getMoves().size());
			tmpGameResponseBean.setNumberOfPlayers(game.getPlayers().size());
			tmpGameResponseBean.setNextPlayer(game.getNextPlayer().getUsername());
			
			result.add(tmpGameResponseBean);
		}
		
		return result;
	}
	
	@RequestMapping(value = CONTEXT, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String addGame(@RequestBody GameRequestBean gameRequestBean) {
		logger.debug("addGame: " + gameRequestBean);

		User owner = userRepo.findByToken(gameRequestBean.getUserToken());
		
		if(owner != null) {
			Game game = new Game();
			
			game.setName(gameRequestBean.getName());
			game.setOwner(owner.getUsername());
			//TODO Mapping into Game

			game = gameRepo.save(game);
	
			return CONTEXT + "/" + game.getId();
		}
			
		return null;
	}
	
	/*
	 *	Context: /game/{game-id} 
	 */
	@RequestMapping(value = CONTEXT + "/{gameId}")
	@ResponseStatus(HttpStatus.OK)
	public GameResponseBean getGame(@PathVariable Long gameId) {
		logger.debug("getGame: " + gameId);
		
		Game game = gameRepo.findOne(gameId);
		
		GameResponseBean gameResponseBean = new GameResponseBean();
		
		gameResponseBean.setId(game.getId());
		gameResponseBean.setGame(game.getName());
		gameResponseBean.setOwner(game.getOwner());
		gameResponseBean.setStatus(game.getStatus());
		gameResponseBean.setNumberOfMoves(game.getMoves().size());
		gameResponseBean.setNumberOfPlayers(game.getPlayers().size());
		gameResponseBean.setNextPlayer(game.getNextPlayer().getUsername());
		
		return gameResponseBean;
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/start", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void startGame(@PathVariable Long gameId, @RequestBody String userToken) {
		logger.debug("startGame: " + gameId);
		
		Game game = gameRepo.findOne(gameId);
		User owner = userRepo.findByToken(userToken);
		
		if(owner != null && game != null
			&& game.getOwner().equals(owner.getUsername())) {
			//TODO: Start game
		}
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/stop", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void stopGame(@PathVariable Long gameId, @RequestBody String userToken) {
		logger.debug("startGame: " + gameId);
		Game game = gameRepo.findOne(gameId);
		User owner = userRepo.findByToken(userToken);
		
		if(owner != null && game != null
			&& game.getOwner().equals(owner.getUsername())) {
			//TODO: Stop game
		}
	}
	
	/*
	 *	Context: /game/{game-id}/move
	 */
	
	@RequestMapping(value = CONTEXT + "/{gameId}/move")
	@ResponseStatus(HttpStatus.OK)
	public List<GameMoveResponseBean> listMoves(@PathVariable Long gameId) {
		logger.debug("listMoves");
		List<GameMoveResponseBean> result = new ArrayList<>();
		
//		Game game = gameRepo.findOne(gameId);
		
//		GameMoveResponseBean tmpGameMoveResponseBean;
//		for(Move move : game.getMoves()) {
//			tmpGameMoveResponseBean = new GameMoveResponseBean();
//			
//			TODO: Mapping into GameMoveResponseBean
//			
//			result.add(tmpGameMoveResponseBean);
//		}
		
		return result;
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/move", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addMove(@RequestBody GameMoveRequestBean gameMoveRequestBean) {
		logger.debug("addMove: " + gameMoveRequestBean);
		//TODO Mapping into Move + execution of move
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/move/{moveId}")
	@ResponseStatus(HttpStatus.OK)
	public GameMoveResponseBean getMove(@PathVariable Long gameId, @PathVariable Integer moveId) {
		logger.debug("getMove: " + gameId);
		
//		Move move = gameRepo.findOne(gameId).getMoves().get(moveId);
		
		GameMoveResponseBean gameMoveResponseBean = new GameMoveResponseBean();
		
//		TODO Mapping into GameMoveResponseBean
		
		return gameMoveResponseBean;
	}
	
	/*
	 *	Context: /game/{game-id}/player
	 */
	@RequestMapping(value = CONTEXT + "/{gameId}/player")
	@ResponseStatus(HttpStatus.OK)
	public List<GamePlayerResponseBean> listPlayers(@PathVariable Long gameId) {
		logger.debug("listPlayers");
		List<GamePlayerResponseBean> result = new ArrayList<>();
		
		Game game = gameRepo.findOne(gameId);
		
		GamePlayerResponseBean tmpGamePlayerResponseBean;
		for(User player : game.getPlayers()) {
			tmpGamePlayerResponseBean = new GamePlayerResponseBean();
			
			tmpGamePlayerResponseBean.setUserId(player.getId());
			tmpGamePlayerResponseBean.setNumberOfMoves(getNumberOfMoves(player, game.getId()));
			
			result.add(tmpGamePlayerResponseBean);
		}
		
		return result;
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/player", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String addPlayer(@PathVariable Long gameId, @RequestBody GamePlayerRequestBean gamePlayerRequestBean) {
		logger.debug("addPlayer: " + gamePlayerRequestBean);
		
		Game game = gameRepo.findOne(gameId);
		User player = userRepo.findByToken(gamePlayerRequestBean.getUserToken());
		
		if(game != null && player != null
			&& game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
			game.getPlayers().add(player);
			logger.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
			return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size() - 1);
		} else {
			logger.error("Error adding player with token: " + gamePlayerRequestBean.getUserToken());
		}
		return null;
	}
	
	@RequestMapping(value = CONTEXT + "/{gameId}/player/{playerId}")
	@ResponseStatus(HttpStatus.OK)
	public GamePlayerResponseBean getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {
		logger.debug("getPlayer: " + gameId);
		
		Game game = gameRepo.findOne(gameId);
		User player = game.getPlayers().get(playerId);
		
		GamePlayerResponseBean gamePlayerResponseBean = new GamePlayerResponseBean();
		gamePlayerResponseBean.setUserId(player.getId());
		gamePlayerResponseBean.setNumberOfMoves(getNumberOfMoves(player, game.getId()));
		
		return gamePlayerResponseBean;
	}
	
	private Integer getNumberOfMoves(User player, Long gameId) {
		Integer numberOfMovesInGame = 0;
		for(Move move : player.getMoves()) {
			if(move.getGame().getId().equals(gameId)) {
				numberOfMovesInGame++;
			}
		}
		return numberOfMovesInGame;
	}
}
