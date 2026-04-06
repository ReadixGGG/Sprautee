package org.zonarstudio.spraute_engine.script.function;

import net.minecraft.commands.CommandSourceStack;
import org.zonarstudio.spraute_engine.script.ScriptContext;

import java.util.List;

/**
 * {@code strWidth(text)} — approximate pixel width of a string using Minecraft's
 * default 8×8 font character widths. Returns an integer.
 */
public class StrWidthFunction implements ScriptFunction {

    private static final int[] CHAR_WIDTHS = new int[256];

    static {
        java.util.Arrays.fill(CHAR_WIDTHS, 6);

        CHAR_WIDTHS[' '] = 4;
        CHAR_WIDTHS['!'] = 2;
        CHAR_WIDTHS['"'] = 5;
        CHAR_WIDTHS['#'] = 6;
        CHAR_WIDTHS['$'] = 6;
        CHAR_WIDTHS['%'] = 6;
        CHAR_WIDTHS['&'] = 6;
        CHAR_WIDTHS['\''] = 3;
        CHAR_WIDTHS['('] = 5;
        CHAR_WIDTHS[')'] = 5;
        CHAR_WIDTHS['*'] = 5;
        CHAR_WIDTHS['+'] = 6;
        CHAR_WIDTHS[','] = 2;
        CHAR_WIDTHS['-'] = 6;
        CHAR_WIDTHS['.'] = 2;
        CHAR_WIDTHS['/'] = 6;
        for (int c = '0'; c <= '9'; c++) CHAR_WIDTHS[c] = 6;
        CHAR_WIDTHS[':'] = 2;
        CHAR_WIDTHS[';'] = 2;
        CHAR_WIDTHS['<'] = 5;
        CHAR_WIDTHS['='] = 6;
        CHAR_WIDTHS['>'] = 5;
        CHAR_WIDTHS['?'] = 6;
        CHAR_WIDTHS['@'] = 7;
        for (int c = 'A'; c <= 'Z'; c++) CHAR_WIDTHS[c] = 6;
        CHAR_WIDTHS['I'] = 4;
        CHAR_WIDTHS['['] = 4;
        CHAR_WIDTHS['\\'] = 6;
        CHAR_WIDTHS[']'] = 4;
        CHAR_WIDTHS['^'] = 6;
        CHAR_WIDTHS['_'] = 6;
        CHAR_WIDTHS['`'] = 3;
        for (int c = 'a'; c <= 'z'; c++) CHAR_WIDTHS[c] = 6;
        CHAR_WIDTHS['f'] = 5;
        CHAR_WIDTHS['i'] = 2;
        CHAR_WIDTHS['k'] = 5;
        CHAR_WIDTHS['l'] = 3;
        CHAR_WIDTHS['t'] = 4;
        CHAR_WIDTHS['{'] = 5;
        CHAR_WIDTHS['|'] = 2;
        CHAR_WIDTHS['}'] = 5;
        CHAR_WIDTHS['~'] = 7;
    }

    @Override
    public String getName() { return "strWidth"; }

    @Override
    public int getArgCount() { return 1; }

    @Override
    public Class<?>[] getArgTypes() { return new Class<?>[]{Object.class}; }

    @Override
    public Object execute(List<Object> args, CommandSourceStack source, ScriptContext context) {
        if (args.isEmpty()) return 0;
        String text = String.valueOf(args.get(0));
        int width = 0;
        boolean formatting = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\u00A7' || c == '§') {
                formatting = true;
                continue;
            }
            if (formatting) {
                formatting = false;
                continue;
            }
            width += (c < 256) ? CHAR_WIDTHS[c] : 6;
        }
        return width;
    }
}
