package hu.csaszi.gameengine.physics.objects.ai;

import hu.csaszi.gameengine.physics.objects.GameObject;

public class ScoutCommand extends BasicCommand {

	private int seconds;

	@Override
	public void doExecute(float delta) {

		if(seconds < 60) {
			seconds++;
		} else {
			seconds = 0;
			actor.changeFacing();
		}

		if(distance < 5 && actor.isAwareAbout(target)){
			actor.setCommand(new MoveCommand(actor, target));
		}
			
	}
	
	public ScoutCommand(GameObject actor, GameObject target){
		super(actor, target);
	}

}
