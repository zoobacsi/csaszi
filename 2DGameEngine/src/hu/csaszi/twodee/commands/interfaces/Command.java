package hu.csaszi.twodee.commands.interfaces;

import hu.csaszi.twodee.entity.Character;

public interface Command {
	public void execute(int delta);
	public boolean isExecuted();
	public void setOwner(Character character);
}
