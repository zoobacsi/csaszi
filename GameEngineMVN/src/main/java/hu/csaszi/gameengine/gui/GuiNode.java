package hu.csaszi.gameengine.gui;

import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkRect;
import org.lwjgl.system.MemoryStack;

import java.util.Comparator;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public abstract class GuiNode implements Comparable<GuiNode>{

    private int x, y, width, height;
    private String title;

    private boolean windowBorder;
    private boolean windowMovable;
    private boolean windowScalable;
    private boolean windowMinimizable;
    private boolean windowHasTitle;

    public GuiNode setWindowNoScrollbar(boolean windowNoScrollbar) {
        this.windowNoScrollbar = windowNoScrollbar;
        return this;
    }

    private boolean windowNoScrollbar;

    public GuiNode setX(int x) {
        this.x = x;
        return this;
    }

    public GuiNode setY(int y) {
        this.y = y;
        return this;
    }

    public GuiNode setWidth(int width) {
        this.width = width;
        return this;
    }

    public GuiNode setHeight(int height) {
        this.height = height;
        return this;
    }

    public GuiNode setTitle(String title) {
        this.title = title;
        return this;
    }

    public GuiNode setWindowMovable(boolean windowMovable) {
        this.windowMovable = windowMovable;
        return this;
    }

    public GuiNode setWindowScalable(boolean windowScalable) {
        this.windowScalable = windowScalable;
        return this;
    }

    public GuiNode setWindowMinimizable(boolean windowMinimizable) {
        this.windowMinimizable = windowMinimizable;
        return this;
    }

    public GuiNode setWindowHasTitle(boolean windowHasTitle) {
        this.windowHasTitle = windowHasTitle;
        return this;
    }

    protected GuiNode setWindowBorder(boolean windowBorder) {
        this.windowBorder = windowBorder;
        return this;
    }

    private int getFlags() {

        int flags = (windowBorder ? NK_WINDOW_BORDER : 0) |
                (windowMovable ? NK_WINDOW_MOVABLE : 0) |
                (windowScalable ? NK_WINDOW_SCALABLE : 0) |
                (windowMinimizable ? NK_WINDOW_MINIMIZABLE : 0) |
                (windowHasTitle ? NK_WINDOW_TITLE : 0) |
                (windowNoScrollbar ? NK_WINDOW_NO_SCROLLBAR : 0);
        return flags;
    }

    public boolean begin(NkContext ctx, String title, NkRect rect) {
        return nk_begin(ctx, title, rect, getFlags());
    }

    protected abstract void doLayout(NkContext ctx, MemoryStack stack);

    public void layout(NkContext ctx, String title, int x, int y, int width, int height) {
        try (MemoryStack stack = stackPush()) {
            NkRect rect = NkRect.mallocStack(stack);

            rect = nk_rect(x, y, width, height, rect);

            if (begin(ctx, title, rect)) {
                doLayout(ctx, stack);
            }

            nk_end(ctx);
        }
    }

    public abstract void shutdown();

    public final void render(NkContext ctx) {
        layout(ctx, title, x, y, width, height);
        doRender(ctx);
    }

    public abstract void doRender(NkContext ctx);

    /**
     * The higher the number, the later it will be rendered.
     *
     * @return
     */
    public abstract int getNodeLevel();

    @Override
    public int compareTo(GuiNode o) {
        return Integer.compare(this.getNodeLevel(), o.getNodeLevel());
    }
}
