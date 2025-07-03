import java.io.*;
import java.util.*;

class Solution {
    private static final char WATER = 'X';
    private static final int[] ROW_DIRS = { -1, 1, 0, 0 };
    private static final int[] COL_DIRS = { 0, 0, -1, 1 };
    
    public static class Point {
        int row, col;
        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    
    public int[] solution(String[] maps) {
        int rows = maps.length;
        int cols = maps[0].length();
        boolean[][] vst = new boolean[rows][cols];
        List<Integer> landSum = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!vst[row][col] && maps[row].charAt(col) != WATER) {
                    int sum = exploreLand(row, col, maps, vst);
                    landSum.add(sum);
                }
            }
        }

        if (landSum.isEmpty()) return new int[] { -1 };
        
        return landSum.stream()
            .sorted()
            .mapToInt(Integer::intValue)
            .toArray();
    }
    
    private int exploreLand(int stRow, int stCol, String[] maps, boolean[][] vst) {
        int total = 0;
        Queue<Point> queue = new LinkedList<>();

        vst[stRow][stCol] = true;
        queue.add(new Point(stRow, stCol));

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            total += Character.getNumericValue(maps[point.row].charAt(point.col));

            for (int i = 0; i < ROW_DIRS.length; i++) {
                int newRow = point.row + ROW_DIRS[i];
                int newCol = point.col + COL_DIRS[i];

                if (isValidRange(newRow, newCol, maps) &&
                    !vst[newRow][newCol] &&
                    maps[newRow].charAt(newCol) != WATER) {

                    vst[newRow][newCol] = true;
                    queue.add(new Point(newRow, newCol));
                }
            }
        }
        return total;
    }
    
    private boolean isValidRange(int row, int col, String[] maps) {
        return row >= 0 && row < maps.length && col >= 0 && col < maps[0].length();
    }
}
