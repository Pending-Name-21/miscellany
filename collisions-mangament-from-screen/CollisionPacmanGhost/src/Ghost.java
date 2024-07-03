import java.awt.Image;
import javax.swing.ImageIcon;

public class Ghost {
    private int x;
    private int y;
    private int velocity;
    private Image sprite;

    public Ghost(int x, int y, int velocity, String imagePath) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.sprite = new ImageIcon(imagePath).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
    }

    public void moveToPacman(Pacman pacman) {
        if (x < pacman.getX()) {
            x += velocity;
        } else{
            x -= velocity;
        }
        if (y < pacman.getY()) {
            y += velocity;
        }else
        {
            y -= velocity;
        }
    }

    public Image getSprite() {
        return sprite;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}