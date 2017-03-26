package hu.csaszi.twodee.commands;

import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.runtime.Application;


public class AttackCommand extends BasicCommand {

	private Character actor;
	private Character target;
	
	@Override
	public void execute(int delta) {

		TileObject actorTile = ((DefaultPlay)Application.getInstance().getCurrentState()).getTiledMap().getTilesByOrtho(actor.getCharOrtoPosX(), actor.getCharOrtoPosY()).get(0);
		TileObject targetTile = ((DefaultPlay)Application.getInstance().getCurrentState()).getTiledMap().getTilesByOrtho(target.getCharOrtoPosX(), target.getCharOrtoPosY()).get(0);
		if(actorTile.equals(targetTile)){			
			actor.attack();
		} else {
			actor.setCommand(new MoveCommand(actor, target));
		}
	}

	public AttackCommand(Character actor, Character target) {
		this.actor = actor;
		this.target = target;
	}
}
