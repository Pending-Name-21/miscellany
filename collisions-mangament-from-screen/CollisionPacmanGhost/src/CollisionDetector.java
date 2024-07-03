public class CollisionDetector {
    public boolean isCollision(Pacman pacman, Ghost ghost) {
        int distanceX = Math.abs(pacman.getX() - ghost.getX());
        int distanceY = Math.abs(pacman.getY() - ghost.getY());
        return distanceX < 60 && distanceY < 60;
    }
}
