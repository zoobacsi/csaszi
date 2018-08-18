package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector2f;
import org.joml.Vector3f;


public class MoveCommand extends BasicCommand {

	@Override
	public void doExecute(float delta) {

		Vector3f position = actor.getTransform().pos;
		Vector3f targetPos = target.getTransform().pos;
		float distance = position.distance(targetPos);

		if(distance >= 1){
			if(actor.isAwareAbout(target)){
				boolean right = position.sub(targetPos).x < 0;
				actor.setVelocity(new Vector2f(right ? actor.getSpeed()* delta * 2 : -actor.getSpeed() * delta * 2, 0));
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
