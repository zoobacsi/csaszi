package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector3f;


public class AttackCommand extends BasicCommand {

	private float attackInterval = 0;

	@Override
	public void doExecute(float delta) {

		if(attackInterval > 0) {
			attackInterval -= delta;
		}
		if(distance < 2.4f){
			if(attackInterval <= 0) {
				attackInterval = actor.getAttackInterval();
				actor.attack(target);
				actor.setVelocity(0, 0);
			}
		} else {
			attackInterval = 0;
			actor.setCommand(new MoveCommand(actor, target));
		}
	}

	public AttackCommand(GameObject actor, GameObject target) {
		super(actor, target);
	}
}
