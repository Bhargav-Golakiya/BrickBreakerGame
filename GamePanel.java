import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int level = 1;
    private int totalBricks;
    private final Timer timer;
    private final int delay = 8;

    private int playerX = 310;
    private int ballX = 120, ballY = 350, ballXDir = -1, ballYDir = -2;

    private BrickGenerator brickGenerator;

    public GamePlay() {
        startLevel(level);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void startLevel(int currentLevel) {
        brickGenerator = new BrickGenerator(currentLevel, 3 + currentLevel, 7);
        totalBricks = (3 + currentLevel) * 7;
        ballX = 120;
        ballY = 350;
        ballXDir = -1;
        ballYDir = -2;
        playerX = 310;
        play = false; // Game starts when a key is pressed
    }

    @Override
    public void paint(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(1, 1, 692, 592);

    brickGenerator.draw((Graphics2D) g);

    g.setColor(Color.YELLOW);
    g.fillRect(0, 0, 692, 3);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(691, 0, 3, 592);

    g.setColor(Color.GREEN);
    g.fillRect(playerX, 550, 100, 8);

    g.setColor(Color.YELLOW);
    g.fillOval(ballX, ballY, 20, 20);

    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("Score: " + score, 560, 30);
    g.drawString("Level: " + level, 560, 50);

    // ✅ Show "Press SPACE to Start" message ONLY if the level has started and the player has not lost
    if (!play && ballY < 570) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Press SPACE to Start", 200, 300);
    }

    // ✅ Show "You Lost!" message ONLY if the ball is out of bounds
    if (ballY > 570) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("You Lost! Press ENTER to Retry", 150, 300);
    }

    g.dispose();
}

public void actionPerformed(ActionEvent e) {
    if (play) {
        // Ball and paddle collision
        if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
            ballYDir = -ballYDir;
        }

        // Brick collision detection
        A: for (int i = 0; i < brickGenerator.getBricks().length; i++) {
            for (int j = 0; j < brickGenerator.getBricks()[0].length; j++) {
                if (brickGenerator.getBricks()[i][j] > 0) { // ✅ Only check existing bricks
                    int brickX = j * (540 / 7) + 80;
                    int brickY = i * (150 / 3) + 50;
                    int brickWidth = 540 / 7;
                    int brickHeight = 150 / 3;

                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);

                    if (ballRect.intersects(brickRect)) {
                        brickGenerator.setBrickValue(0, i, j); // Remove brick
                        score += 5;
                        totalBricks--;

                        // ✅ More precise collision detection
                        int ballCenterX = ballX + 10;
                        int ballCenterY = ballY + 10;

                        boolean hitFromLeft = ballCenterX <= brickX && ballXDir > 0;
                        boolean hitFromRight = ballCenterX >= brickX + brickWidth && ballXDir < 0;
                        boolean hitFromTop = ballCenterY <= brickY && ballYDir > 0;
                        boolean hitFromBottom = ballCenterY >= brickY + brickHeight && ballYDir < 0;

                        if (hitFromTop) {
                            ballYDir = -Math.abs(ballYDir); // Reverse Y direction (hit from top)
                        } else if (hitFromBottom) {
                            ballYDir = Math.abs(ballYDir); // Reverse Y direction (hit from bottom)
                        } else if (hitFromLeft) {
                            ballXDir = -Math.abs(ballXDir); // Reverse X direction (hit from left)
                        } else if (hitFromRight) {
                            ballXDir = Math.abs(ballXDir); // Reverse X direction (hit from right)
                        }

                        break A; // Stop checking after first collision
                    }
                }
            }
        }

        // Move the ball
        ballX += ballXDir;
        ballY += ballYDir;

        // Wall collision
        if (ballX < 0 || ballX > 670) ballXDir = -ballXDir;
        if (ballY < 0) ballYDir = -ballYDir;
    }

    // Check if all bricks are cleared
    if (brickGenerator.isLevelCleared()) {
        level++;
        JOptionPane.showMessageDialog(null, "Level " + level + " Complete! Get Ready for Next Level!");
        startLevel(level);
    }

    repaint();
}




       

public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < 600) {
        playerX += 20;
        play = true; // Start game if not already playing
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 10) {
        playerX -= 20;
        play = true; // Start game if not already playing
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        play = true; // Allow the game to start when SPACE is pressed
    }

    // ✅ Restart the same level when ENTER is pressed after losing
    if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
        restartLevel();
    }
}



 
    public void restartLevel() {
    // Reset ball position and movement
    ballX = 120;
    ballY = 350;
    ballXDir = -1;
    ballYDir = -2;

    // Reset paddle position
    playerX = 310;

    // Reset the bricks (do not generate a new level, just restart)
    brickGenerator = new BrickGenerator(level, 3 + level, 7);
    totalBricks = (3 + level) * 7;

    // Start the game again
    play = true;
    repaint();
}

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
