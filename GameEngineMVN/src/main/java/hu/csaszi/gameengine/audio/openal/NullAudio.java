package hu.csaszi.gameengine.audio.openal;

/**
 * A null implementation used to provide an object reference when sound
 * has failed.
 * 
 * @author kevin
 */
public class NullAudio implements Audio {

	public int getBufferID() {
		return 0;
	}

	public float getPosition() {
		return 0;
	}

	public boolean isPlaying() {
		return false;
	}

	public int playAsMusic(float pitch, float gain, boolean loop) {
		return 0;
	}

	public int playAsSoundEffect(float pitch, float gain, boolean loop) {
		return 0;
	}

	public int playAsSoundEffect(float pitch, float gain, boolean loop,
			float x, float y, float z) {
		return 0;
	}

	public boolean setPosition(float position) {
		return false;
	}

	public void stop() {
	}

}
