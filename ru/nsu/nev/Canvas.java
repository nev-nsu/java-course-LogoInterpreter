package ru.nsu.nev;

import ru.nsu.nev.program.LogicalError;

public class Canvas {


    public static final char freeSpace = ' ';

    private static char[][] map = null;
    private static int width = 402;
    private static int height = 26;
    private static final int maxWidth = width - 2;
    private static int minWidth = 1;
    private static final int  maxHeight = height - 2;
    private static int minHeight = 1;


    public static int getWidth (){return width-2;}
    public static int getHeight (){return height-2;}

    static {
        map = new char[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                map[i][j] = freeSpace;
    }

    public static void setMinSize (int x, int y){
        minWidth = x;
        minHeight = y;
    }

    public static void setSize (int nwidth, int nheight) throws LogicalError {
        if (nwidth > maxWidth || nheight > maxHeight
         || nwidth < minWidth || nheight < minHeight)
            throw new LogicalError("invalid field size");
        // clean the border
        for (int i = 0; i < height; i++) {
            map[i][0] = freeSpace;
            map[i][width-1] = freeSpace;
        }
        for (int i = 0; i < width; i++) {
            map[0][i] = freeSpace;
            map[height - 1][i] = freeSpace;
        }
        // clean other
        if (nwidth + 2 < width)
            for (int i = nwidth+1; i < width; i++)
                for (int j = 0; j < height; j++)
                    map[j][i] = freeSpace;
        if (nheight + 2 < height)
            for (int i = nheight+1; i < height; i++)
                for (int j = 0; j < width; j++)
                    map[i][j] = freeSpace;
        height = nheight + 2;
        width = nwidth + 2;
        // draw a border
        for (int i = 0; i < height; i++) {
            map[i][0] = '|';
            map[i][width - 1] = '|';
        }
        for (int i = 0; i < width; i++) {
            map[0][i] = '-';
            map[height - 1][i] = '-';
        }
    }

    public static void drawPoint (int y, int x, char ch) throws LogicalError {
        if (x < 0 || y < 0 || x >= height - 2 || y >= width - 2)
            throw new LogicalError("overstepping");
        map[x+1][y+1] = ch;
    }

    public static void show(){
        for(int i = 0; i < height; i++)
            System.out.println (String.valueOf(map[i]));
    }

}
