package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.Gravity;
import hu.csaszi.gameengine.physics.objects.GameObject;
import hu.csaszi.gameengine.util.PropsUtil;
import org.joml.Vector2f;

public class BulletMoveCommand extends BasicCommand {

	private Vector2f targetDirection = new Vector2f();

	@Override
	public void doExecute(float delta) {

		if (actor.canMoveForward()) {
			actor.setVelocity(new Vector2f(this.targetDirection).mul(delta).mul(PropsUtil.getProperties().getBulletSpeed()));
		} else {
			System.out.println("FASZOM!");
			actor.setVelocity(0, 0);
			actor.destroy();
		}
	}
	public BulletMoveCommand(GameObject actor, Vector2f targetDirection){
		super(actor, null);

		this.targetDirection.set(targetDirection);
	}
}
