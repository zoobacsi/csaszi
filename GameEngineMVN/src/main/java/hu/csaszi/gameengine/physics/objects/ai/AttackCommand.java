package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector3f;


public class AttackCommand extends BasicCommand {

	@Override
	public void doExecute(float delta) {

		Vector3f position = actor.getTransform().pos;
		Vector3f targetPos = target.getTransform().pos;
		if(position.distance(targetPos) < 1){
			actor.attack(target);
		} else {
			actor.setCommand(new MoveCommand(actor, target));
		}
	}

	public AttackCommand(GameObject actor, GameObject target) {
		super(actor, target);
	}
}
