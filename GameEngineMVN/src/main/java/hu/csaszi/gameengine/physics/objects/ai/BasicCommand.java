package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;
import org.joml.Vector3f;

public abstract class BasicCommand implements Command {

	protected Character owner;
	protected boolean executed;

	protected GameObject actor;
	protected GameObject target;

	protected Vector3f position;
	protected Vector3f targetPos;
	protected float distance;

	public BasicCommand(GameObject actor, GameObject target) {
		this.actor = actor;
		this.target = target;
	}

	@Override
	public void execute(float delta){
		position = actor.getTransform().pos;
		if(target != null) {
			targetPos = target.getTransform().pos;
		} else {
			targetPos = new Vector3f();
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
