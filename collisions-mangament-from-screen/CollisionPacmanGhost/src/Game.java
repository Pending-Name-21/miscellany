import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {
    private Pacman pacman;
    private Ghost ghost;
    private CollisionDetector collision;

    public Game() {
        this.pacman = new Pacman(0, 0, 1, "resources/pacman.png");
        this.ghost = new Ghost(200, 200, 2, "resources/ghost.png");
        this.collision = new CollisionDetector();
    }

    @Override
    protected void paintComponent(Graphics graphs) {
        super.paintComponent(graphs);
        graphs.drawImage(pacman.getSprite(), pacman.getX(), pacman.getY(), this);
        graphs.drawImage(ghost.getSprite(), ghost.getX(), ghost.getY(), this);
    }

    public void updateGame() {
        pacman.moveTo(1, 0);
        ghost.moveToPacman(pacman);
        if (collision.isCollision(pacman, ghost)) {
            JOptionPane.showMessageDialog(this, "U dead, shouldve called ghostbusters man", "Game over", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Collision detected");
            System.exit(0);
        }
        repaint();
    }
}
