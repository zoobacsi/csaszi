package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector2f;

public class MoveCommand extends BasicCommand {

	@Override
	public void doExecute(float delta) {

		if(distance >= 2.4f){
			if(actor.isAwareAbout(target)){
				if (actor.canMoveForward()) {
					Vector2f actorVelocity = new Vector2f(Gravity.getGravity().getDirectedMovement(actor, target)).mul(delta);

					actor.setVelocity(actorVelocity.mul(actor.getSpeed()));
				} else {
					actor.setVelocity(0, 0);
				}

			} else {
				actor.setCommand(new PatrolCommand(actor, target));
			}
		} else {
			actor.setCommand(new AttackCommand(actor, target));
		}
	}
	public MoveCommand(GameObject actor, GameObject target){
		super(actor, target);
	}
}
