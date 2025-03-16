import java.awt.*;

public class BrickGenerator {
    private int[][] bricks;
    private int brickWidth;
    private int brickHeight;

    public BrickGenerator(int level, int row, int col) {
        bricks = new int[row][col];
        generatePattern(level, row, col);
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    private void generatePattern(int level, int row, int col) {
        switch (level % 3) {
            case 1: 
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++)
                        bricks[i][j] = (i + j) % 2;
                break;
            case 2: 
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++)
                        bricks[i][j] = (j >= i && j < col - i) ? 1 : 0;
                break;
            default: 
                for (int i = 0; i < row; i++)
                    for (int j = 0; j < col; j++)
                        bricks[i][j] = 1;
                break;
        }
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g.setColor(Color.RED);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        bricks[row][col] = value;
    }

    public boolean isLevelCleared() {
        for (int[] row : bricks) {
            for (int brick : row) {
                if (brick > 0) return false;
            }
        }
        return true;
    }

    public int[][] getBricks() {
        return bricks;
    }
}
