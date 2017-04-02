package logo;

import logo.program.LogicalError;


/**
 * Pseudo graphical interface to display program output as rectangle field of chars
 */
public class Canvas {

    /**
     * A symbol to draw a free cell
     */
    public static final char freeSpace = ' ';

    private static int width = 402;
    private static final int maxWidth = width - 2;
    private static int height = 26;
    private static final int maxHeight = height - 2;
    private static final char[][] map = new char[height][width];
    private static int minWidth = 1;
    private static int minHeight = 1;

    static {
        cleanField();
    }

    public static void cleanField() {
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

    /**
     * Set low limits
     *
     * @param x x &gt;= 0
     * @param y y &gt;= 0
     * @throws LogicalError if input is invalid
     */
    public static void setMinSize(int x, int y) throws LogicalError {
        if (x < 0 || y < 0)
            throw new LogicalError("invalid limits");
        minWidth = x;
        minHeight = y;
    }

    private static void drawRectangle (int x1, int y1, int x2, int y2, char ch){
        for (int j = y1; j <= y2; j++)
            for (int i = x1; i <= x2; i++)
                map[j][i] = ch;
    }

    /**
     * @param nwidth  minWidth &lt;= nwidth &lt;= 400
     * @param nheight minHeight &lt;= nheight &lt;= 24
     * @throws LogicalError if input is invalid
     */
    public static void setFieldSize(int nwidth, int nheight) throws LogicalError {
        if (nwidth > maxWidth || nheight > maxHeight
                || nwidth < minWidth || nheight < minHeight)
            throw new LogicalError("invalid field size");
        // cleanField the border
        drawRectangle(0, 0, 0, height - 1, freeSpace);
        drawRectangle(width - 1, 0, width - 1, height - 1, freeSpace);
        drawRectangle(0, 0, width - 1, 0, freeSpace);
        drawRectangle(0, height - 1, width - 1, height - 1, freeSpace);
        // cleanField other
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

    /**
     * @param x 0 &lt;= x &lt; width
     * @param y 0 &lt;= y &lt; height
     * @throws LogicalError if input is invalid
     */
    public static void drawPoint(int x, int y, char ch) throws LogicalError {
        if (x < 0 || y < 0 || y >= height - 2 || x >= width - 2)
            throw new LogicalError("overstepping");
        map[y + 1][x + 1] = ch;
    }

    /**
     * Print content in the default console output
     */
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
