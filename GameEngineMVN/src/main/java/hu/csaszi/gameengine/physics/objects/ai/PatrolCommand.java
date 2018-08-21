package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;

public class PatrolCommand extends BasicCommand {

	private boolean moveRight = true;
	private boolean wait;
	private float waitingTime;

	@Override
	public void doExecute(float delta) {

		if(!actor.canMoveForward()) {
			moveRight = !moveRight;
			waitingTime = PropsUtil.getProperties().getEnemyTurnTime();
			actor.changeFacing();
		}

		if (waitingTime > 0) {
			waitingTime -= delta;
		} else {
			waitingTime = 0;
			actor.setVelocity(moveRight ? actor.getSpeed() * delta : -actor.getSpeed() * delta, 0);
		}

		if(distance < 5 && actor.isAwareAbout(target)){
			actor.setCommand(new MoveCommand(actor, target));
		}

	}

	public PatrolCommand(GameObject actor, GameObject target){
		super(actor, target);
	}

}
