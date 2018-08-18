package hu.csaszi.gameengine.physics.objects.ai;

public interface Command {
	public void execute(float delta);
	public boolean isExecuted();
	public void setOwner(Character character);
}
