
/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package hu.csaszi.gameengine.gui;

import org.lwjgl.nuklear.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.text.*;

import static org.lwjgl.nuklear.Nuklear.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Java port of
 * <a href="https://github.com/vurtun/nuklear/blob/master/demo/calculator.c">https://github.com/vurtun/nuklear/blob/master/demo/calculator.c</a>.
 */
public class Calculator extends GuiNode {

    private static final String NUMS = "789456123";
    private static final String OPS = "+-*/";

    private char
            prev,
            op;

    private boolean set;

    private double[]
            a = new double[1],
            b = new double[1];

    private double[] current = a;

    private final DecimalFormat format = new DecimalFormat();

    final NkPluginFilter numberFilter;

    public Calculator(int x, int y) {
        this.setWindowBorder(true)
                .setWindowMovable(true)
                .setWindowNoScrollbar(true)
                .setWidth(180)
                .setHeight(250)
                .setTitle("Calculator")
                .setX(x)
                .setY(y);

        format.setGroupingUsed(false);
        format.setDecimalSeparatorAlwaysShown(true);

        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(dfs);

        numberFilter = NkPluginFilter.create(Nuklear::nnk_filter_float);
    }

    @Override
    protected void doLayout(NkContext ctx, MemoryStack stack) {
        nk_layout_row_dynamic(ctx, 35, 1);
        {
            ByteBuffer buffer = stack.calloc(256);

            int length = memASCII(format.format(current[0]), false, buffer);

            IntBuffer len = stack.ints(length);
            nk_edit_string(ctx, NK_EDIT_SIMPLE, buffer, len, 255, numberFilter);
            try {
                current[0] = format.parse(memASCII(buffer, len.get(0))).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        nk_layout_row_dynamic(ctx, 35, 4);

        boolean solve = false;
        for (int i = 0; i < 16; ++i) {
            if (i >= 12 && i < 15) {
                if (i > 12) {
                    continue;
                }
                if (nk_button_label(ctx, "C")) {
                    a[0] = b[0] = op = 0;
                    current = a;
                    set = false;
                }
                if (nk_button_label(ctx, "0")) {
                    current[0] *= 10.0f;
                    set = false;
                }
                if (nk_button_label(ctx, "=")) {
                    solve = true;
                    prev = op;
                    op = 0;
                }
            } else if (((i + 1) % 4) != 0) {
                if (nk_button_text(ctx, Character.toString(NUMS.charAt((i / 4) * 3 + i % 4)))) {
                    current[0] = current[0] * 10.0f + (NUMS.charAt((i / 4) * 3 + i % 4) - '0');
                    set = false;
                }
            } else if (nk_button_text(ctx, Character.toString(OPS.charAt(i / 4)))) {
                if (!set) {
                    if (current != b) {
                        current = b;
                    } else {
                        prev = op;
                        solve = true;
                    }
                }
                op = OPS.charAt(i / 4);
                set = true;
            }
        }
        if (solve) {
            if (prev == '+') {
                a[0] += b[0];
            }
            if (prev == '-') {
                a[0] -= b[0];
            }
            if (prev == '*') {
                a[0] *= b[0];
            }
            if (prev == '/') {
                a[0] /= b[0];
            }
            current = a;
            if (set) {
                current = b;
            }
            b[0] = 0;
            set = false;
        }
    }

    @Override
    public void shutdown() {
        numberFilter.free();
    }

    @Override
    public void doRender(NkContext ctx) {

    }

    @Override
    public int getNodeLevel() {
        return 1;
    }
}