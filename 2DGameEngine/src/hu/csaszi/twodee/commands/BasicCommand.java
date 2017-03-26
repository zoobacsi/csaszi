package hu.csaszi.twodee.commands;

import hu.csaszi.twodee.commands.interfaces.Command;
import hu.csaszi.twodee.entity.Character;

public abstract class BasicCommand implements Command {

	protected Character owner;
	protected boolean executed;
	
	@Override
	public abstract void execute(int delta);

	@Override
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public void setOwner(Character character) {
		this.owner = character;
	}

}
