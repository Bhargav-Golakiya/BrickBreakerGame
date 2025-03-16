import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login_page loginPage = new login_page();
            loginPage.setVisible(true);
        });
    }

    public static void startGame() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Brick Breaker");
            GamePlay gamePlay = new GamePlay();
            frame.setBounds(10, 10, 700, 600);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(gamePlay);
            frame.setVisible(true);
        });
    }
}
