package hu.csaszi.twodee.gamestates;

import hu.csaszi.twodee.gui.twl.BasicTWLGameState;
import hu.csaszi.twodee.gui.twl.RootPane;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.util.PropsValues;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import de.lessvoid.nifty.NiftyInputConsumer;
import de.matthiasmann.twl.Button;

public class DefaultMenu extends BasicTWLGameState {


//	private Image playNow;
//	private Image exitGame;
//	private Image background;
//	private Image transparentBlack;

//	List<MouseOverArea> areas = new ArrayList<MouseOverArea>();

	public String title = PropsValues.TITLE;

	private Button newBtn;
	private Button exitBtn;

	@Override
	protected RootPane createRootPane() {
		
		RootPane rp = super.createRootPane();
		rp.setTheme("mainMenu");
	
		newBtn = new Button("Start New Game");
		newBtn.setTheme("button");
		newBtn.addCallback(new Runnable() {
			public void run() {
				try{
					
					Application.getInstance().getState(1).init(Application.getInstance().getContainer(), Application.getInstance());
					Application.getInstance().enterState(1);
				
				} catch(SlickException e){
					Log.error(e.getMessage(), e);
				}
			}
		});
	
		rp.add(newBtn);
		
		exitBtn = new Button("Exit Game");
		exitBtn.setTheme("button");
		exitBtn.addCallback(new Runnable() {
			public void run() {
				Application.getInstance().getContainer().exit();
			}
		});
	
		rp.add(exitBtn);
		
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		newBtn.adjustSize();
		newBtn.setPosition(100, 100);
		
		exitBtn.adjustSize();
		exitBtn.setPosition(100, 100 + newBtn.getHeight() + 100 );
	}
	
	public DefaultMenu(int state) {

		System.out.println("default menu");
	}

	@Override
	public int getID() {

		return 0;
	}


	class MouseEvent {
		private int mouseX;
		private int mouseY;
		private int mouseWheel;
		private int button;
		private boolean buttonDown;

		public MouseEvent(final int mouseX, final int mouseY,
				final boolean mouseDown, final int mouseButton) {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
			this.buttonDown = mouseDown;
			this.button = mouseButton;
			this.mouseWheel = 0;
		}

		public void processMouseEvents(
				final NiftyInputConsumer inputEventConsumer) {
			inputEventConsumer.processMouseEvent(mouseX, mouseY, mouseWheel,
					button, buttonDown);
		
		}
	}

	@Override
	public void init(final GameContainer container, final StateBasedGame game) throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}

}
