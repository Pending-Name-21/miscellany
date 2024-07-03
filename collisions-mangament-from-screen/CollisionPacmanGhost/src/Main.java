import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Pacman Collisions Game");
        Game game = new Game();
        frame.add(game);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while (true) {
            game.updateGame();
            Thread.sleep(100);
        }
    }
}
