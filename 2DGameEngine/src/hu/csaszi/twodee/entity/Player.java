
package hu.csaszi.twodee.entity;

import hu.csaszi.twodee.gamestates.DefaultPlay;
import hu.csaszi.twodee.graphics.CharGraphics;
import hu.csaszi.twodee.graphics.CharacterSet;
import hu.csaszi.twodee.runtime.Application;
import hu.csaszi.twodee.runtime.MapState;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.transition.Transition;

public class Player extends NPC {

	protected long lastDeadTime;
	
	public Player(int id, MapState parent, java.awt.Image moveImg, java.awt.Image attackImg, java.awt.Image deadImg, String name, int hp, float speed, Shape shape, float ortoX, float ortoY) {
		super(id, parent, moveImg, attackImg, deadImg, name, hp, speed, shape, ortoX, ortoY);
	}

	public Player(int id, MapState parent, CharGraphics charGraphics, String name, int hp, float speed, float ortoX, float ortoY) {
	    super(id, parent, charGraphics, name, hp, speed, ortoX, ortoY);
	}
	
	public Player(int id, MapState parent, CharacterSet characterSet, int duration, Shape charShape, String name, int hp, float speed, float ortoX, float ortoY) {
	    super(id, parent, characterSet, duration, charShape, name, hp, speed, ortoX, ortoY);
	}
	
	public void update(int delta) {
		if (attack && lastAttackTime > 0 && System.currentTimeMillis() - lastAttackTime > delta*2) {
			attack = false;
			lastAttackTime = 0;
		}

		if(charAnim == fightAnim){
			if (charAnim.getFrame() == fightAnim.getFrameCount() - 1) {
				charAnim.stop();
				charAnim.restart();
				charAnim = lastAnim;
			}
		}

		if (this.getHp() == 0) {
			attack = false;
			alive = false;
			charAnim = deadAnim;
			
			lastDeadTime = System.currentTimeMillis() + 3000;
			hp = -1;
		}		
		
		if(lastDeadTime > 0 && System.currentTimeMillis() > lastDeadTime){
			
			lastDeadTime = 0;
			Application.getInstance().enterState(Application.menu);
			try {
				((DefaultPlay)Application.getInstance().getState(Application.play)).reset();
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
