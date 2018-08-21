/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package hu.csaszi.gameengine.gui;


        import hu.csaszi.gameengine.game.GameManager;
        import org.lwjgl.*;
        import org.lwjgl.nuklear.*;
        import org.lwjgl.system.*;

        import java.nio.*;
        import java.util.Map;

        import static org.lwjgl.nuklear.Nuklear.*;
        import static org.lwjgl.system.MemoryStack.*;

/**
 * Java port of
 * <a href="https://github.com/vurtun/nuklear/blob/master/demo/glfw_opengl3/main.c">https://github.com/vurtun/nuklear/blob/master/demo/glfw_opengl3/main.c</a>.
 */
public class DebugPanel extends GuiNode{

    private static final int EASY = 0;
    private static final int HARD = 1;

    private float fps;

    public void setFps(float fps) {
        this.fps = fps;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    private float posX;
    private float posY;

    private Map<String, String> debugMap;

    NkColorf background = NkColorf.create()
            .r(0.10f)
            .g(0.18f)
            .b(0.24f)
            .a(1.0f);

    private int op = EASY;

    private IntBuffer compression = BufferUtils.createIntBuffer(1).put(0, 20);

    public DebugPanel(int x, int y) {
        this.setWindowBorder(false)
                .setWindowHasTitle(false)
                .setWindowMinimizable(true)
                .setWindowMovable(true)
                .setWindowScalable(true)
                .setWidth(200)
                .setHeight(400)
                .setTitle("Test")
                .setX(x)
                .setY(y);
    }

    @Override
    protected void doLayout(NkContext ctx, MemoryStack stack) {
        nk_layout_row_static(ctx, 30, 80, 2);

        if (GuiManager.getGameManager().getWindow() != null) {
            fps = GuiManager.getGameManager().getWindow().getCurrentFPS();
        }


        nk_label(ctx, "FPS", NK_TEXT_ALIGN_LEFT);
        nk_label(ctx, String.valueOf(fps), NK_TEXT_RIGHT);

        nk_label(ctx, "PosX", NK_TEXT_ALIGN_LEFT);
        nk_label(ctx,  String.valueOf(posX), NK_TEXT_RIGHT);

        nk_label(ctx, "PosY", NK_TEXT_ALIGN_LEFT);
        nk_label(ctx,  String.valueOf(posY), NK_TEXT_RIGHT);

        if(debugMap != null) {

            for (Map.Entry<String, String> entry : debugMap.entrySet()) {
                nk_label(ctx, entry.getKey(), NK_TEXT_ALIGN_LEFT);
                nk_label(ctx, entry.getValue(), NK_TEXT_RIGHT);
            }
        }

    }

    public void setDebugMap(Map<String, String> debugMap) {
        this.debugMap = debugMap;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void doRender(NkContext ctx) {

    }

    @Override
    public int getNodeLevel() {
        return 0;
    }
}