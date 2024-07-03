import java.awt.Image;
import javax.swing.ImageIcon;

public class Pacman {
    private int y;
    private int x;
    private int velocity;
    private Image sprite;

    public Pacman(int x, int y, int velocity, String imagePath) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.sprite = new ImageIcon(imagePath).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
    }

    public void moveTo(int x, int y) {
        this.x += x * velocity;
        this.y += y * velocity;
    }

    public int getX() {
        return x;
    }

    public Image getSprite() {
        return sprite;
    }

    public int getY() {
        return y;
    }
}
