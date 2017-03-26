package hu.csaszi.twodee.commands;

import org.newdawn.slick.util.Log;

import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.map.interfaces.TiledMap;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.util.Direction;

public class ScoutCommand extends BasicCommand {

	protected Character actor;
	
	@Override
	public void execute(int delta) {

		DefaultPlay gameState = ((DefaultPlay)Application.getInstance().getCurrentState());
		TiledMap tiledMap = gameState.getTiledMap();
		
		Character player = gameState.getPlayer();
		//Log.info("distanceMin: " + 5 * (Math.pow(tiledMap.getTileWidth(), 2) + Math.pow(tiledMap.getTileHeight(), 2)));
		
		if(actor.distanceFromSq(player) > 5 * (Math.pow(tiledMap.getTileWidth(), 2) + Math.pow(tiledMap.getTileHeight(), 2))){
			//actor.moveCharacter(5, Direction.getById(Application.getRandom().nextInt(8)));
			actor.moveCharacter(0, Direction.STAND);
		} else {
			actor.setCommand(new MoveCommand(actor, player));
		}
			
	}
	
	public ScoutCommand(Character actor){
		this.actor = actor;
	}

}
