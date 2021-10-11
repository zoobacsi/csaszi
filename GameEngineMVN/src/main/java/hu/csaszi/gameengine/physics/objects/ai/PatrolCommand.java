package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;

public class PatrolCommand extends BasicCommand {

	private boolean moveRight = true;
	private float waitingTime;

	@Override
	public void doExecute(float delta) {

		if (waitingTime > 0) {
			waitingTime -= delta;
		} else {
			if(!actor.canMoveForward()) {
				moveRight = !moveRight;
				waitingTime = PropsUtil.getProperties().getEnemyTurnTime();
				actor.changeFacing();
			}
			waitingTime = 0;
			Vector2f actorVelocity = new Vector2f(Gravity.getGravity().getDirectedMovement(moveRight)).mul(delta);

			actor.setVelocity(actorVelocity.mul(actor.getSpeed()));
		}

		if(distance < 5 && actor.isAwareAbout(target)){
			actor.setCommand(new MoveCommand(actor, target));
		}

	}

	public PatrolCommand(GameObject actor, GameObject target){
		super(actor, target);
	}

}
