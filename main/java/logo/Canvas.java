package logo;

import logo.program.LogicalError;

public class Canvas {


    public static final char freeSpace = ' ';

    private static int width = 402;
    private static final int maxWidth = width - 2;
    private static int height = 26;
    private static final int maxHeight = height - 2;
    private static final char[][] map = new char[height][width];
    private static int minWidth = 1;
    private static int minHeight = 1;

    static {
        clean();
    }

    public static void clean() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                map[i][j] = freeSpace;
    }

    public static int getWidth() {
        return width - 2;
    }

    public static int getHeight() {
        return height - 2;
    }

    public static void setMinSize(int x, int y) {
        minWidth = x;
        minHeight = y;
    }

    private static void drawRectangle (int x1, int y1, int x2, int y2, char ch){
        for (int j = y1; j <= y2; j++)
            for (int i = x1; i <= x2; i++)
                map[j][i] = ch;
    }

    public static void setSize(int nwidth, int nheight) throws LogicalError {
        if (nwidth > maxWidth || nheight > maxHeight
                || nwidth < minWidth || nheight < minHeight)
            throw new LogicalError("invalid field size");
        // clean the border
        drawRectangle(0, 0, 0, height - 1, freeSpace);
        drawRectangle(width - 1, 0, width - 1, height - 1, freeSpace);
        drawRectangle(0, 0, width - 1, 0, freeSpace);
        drawRectangle(0, height - 1, width - 1, height - 1, freeSpace);
        // clean other
        if (nwidth + 2 < width)
            drawRectangle(nwidth+1, 0, width-1, height-1, freeSpace);
        if (nheight + 2 < height)
            drawRectangle(0, nheight+1, width-1, height-1, freeSpace);
        height = nheight + 2;
        width = nwidth + 2;
        // draw a border
        drawRectangle(0, 0, 0, height - 1, '|');
        drawRectangle(width - 1, 0, width - 1, height - 1, '|');
        drawRectangle(0, 0, width - 1, 0, '-');
        drawRectangle(0, height - 1, width - 1, height - 1, '-');
    }

    public static void drawPoint(int x, int y, char ch) throws LogicalError {
        if (x < 0 || y < 0 || y >= height - 2 || x >= width - 2)
            throw new LogicalError("overstepping");
        map[y + 1][x + 1] = ch;
    }

    public static void show() {
        for (int i = 0; i < height; i++)
            System.out.println(String.valueOf(map[i]));
    }

    public static String[] getField() {
        String[] res = new String[height];
        for (int i = 0; i < height; i++)
            res[i] = String.valueOf(map[i]);
        return res;
    }

}
