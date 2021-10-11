package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector3f;

public abstract class BasicCommand implements Command {

	protected Character owner;
	protected boolean executed;

	protected GameObject actor;
	protected GameObject target;

	protected Vector3f position = new Vector3f();
	protected Vector3f targetPos = new Vector3f();
	protected float distance;

	public BasicCommand(GameObject actor, GameObject target) {
		this.actor = actor;
		this.target = target;
	}

	@Override
	public void execute(float delta){
		position.set(actor.getTransform().pos);
		if(target != null) {
			targetPos.set(target.getTransform().pos);
		} else {
			targetPos.set(0);
		}
		distance = position.distance(targetPos);

		doExecute(delta);
	}

	public abstract void doExecute(float delta);

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public void setOwner(Character character) {
		this.owner = character;
	}

}
