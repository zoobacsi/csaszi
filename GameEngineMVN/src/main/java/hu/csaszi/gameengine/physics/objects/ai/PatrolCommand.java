package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector2f;

public class PatrolCommand extends BasicCommand {

	boolean moveRight = true;

	@Override
	public void doExecute(float delta) {

		actor.setVelocity(new Vector2f(moveRight ? actor.getSpeed() * delta : -actor.getSpeed() * delta, 0));

		if(!actor.canMoveForward()) {
			moveRight = !moveRight;
		}

		if(distance < 5 && actor.isAwareAbout(target)){
			actor.setCommand(new MoveCommand(actor, target));
		}

	}

	public PatrolCommand(GameObject actor, GameObject target){
		super(actor, target);
	}

}
