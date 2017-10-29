package hu.csaszi.gameengine.render.core.gl.renderer;

/**
 * The default version of the renderer relies of GL calls to do everything. 
 * Unfortunately this is driver dependent and often implemented inconsistantly
 *
 */
public class DefaultLineStripRenderer implements LineStripRenderer {
	/** The access to OpenGL */
	private GL GL = Renderer.get();

	public void end() {
		GL.glEnd();
	}

	public void setAntiAlias(boolean antialias) {
		if (antialias) {
			GL.glEnable(GL.GL_LINE_SMOOTH);
		} else {
			GL.glDisable(GL.GL_LINE_SMOOTH);
		}
	}

	public void setWidth(float width) {
		GL.glLineWidth(width);
	}

	public void start() {
		GL.glBegin(GL.GL_LINE_STRIP);
	}

	public void vertex(float x, float y) {
		GL.glVertex2f(x,y);
	}

	public void color(float r, float g, float b, float a) {
		GL.glColor4f(r, g, b, a);
	}

	public void setLineCaps(boolean caps) {
	}

	public boolean applyGLLineFixes() {
		return true;
	}

}
