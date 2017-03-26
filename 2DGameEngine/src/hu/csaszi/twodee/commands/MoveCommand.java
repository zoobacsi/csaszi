package hu.csaszi.twodee.commands;

import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import hu.csaszi.twodee.entity.Character;
import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.map.TileUtil;
import hu.csaszi.twodee.map.interfaces.TileObject;
import hu.csaszi.twodee.map.interfaces.TiledMap;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.util.Direction;


public class MoveCommand extends BasicCommand {

	private Character actor;
	private Character target;
	protected Path drawPath;
	
	@Override
	public void execute(int delta) {
		
		TiledMap tiledMap = ((DefaultPlay)Application.getInstance().getCurrentState()).getTiledMap();
		TileObject actorTile = tiledMap.getTilesByOrtho(actor.getCharOrtoPosX(), actor.getCharOrtoPosY()).get(0);
		TileObject targetTile = tiledMap.getTilesByOrtho(target.getCharOrtoPosX(), target.getCharOrtoPosY()).get(0);
		
		if(!actorTile.equals(targetTile)){			
			if(actor.distanceFromSq(target) <= 5 * (Math.pow(tiledMap.getTileWidth(), 2) + Math.pow(tiledMap.getTileHeight(), 2))){
//				actor.moveTowardsDirection(target);	
//				actor.moveCharacter(1, target.moveTowardsDirection(actor));
				
				if (actorTile != null && targetTile != null) {
					drawPath = ((DefaultPlay)Application.getInstance().getCurrentState()).getPathFinder()
							.findPath(actor, actorTile.getXIndex(), actorTile.getYIndex(),
									targetTile.getXIndex(), targetTile.getYIndex());
				}
				Step step1 = null;
				Step step2 = null;
				TileObject nextTile = null;

				if (drawPath != null) {

					if (drawPath.getLength() > 0) {
						step1 = drawPath.getStep(0);
						step2 = drawPath.getStep(1);
					}
					if (step1 != null && step2 != null) {

						if (actor.distanceFromSq(target) > 2500f) {
							actor.moveToTile(step2.getX(), step2.getY(), delta);
						} else {
							actor.moveCharacter(0, Direction.STAND);
						}
					} else {
						actor.moveCharacter(0, Direction.STAND);
					}
				}
			} else {
				actor.setCommand(new ScoutCommand(actor));
			}
		} else {
			actor.setCommand(new AttackCommand(actor, target));
		}
	}
	public MoveCommand(Character actor, Character target){
		this.actor = actor;
		this.target = target;
	}
}
