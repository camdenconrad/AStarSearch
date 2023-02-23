import java.awt.*;
import java.awt.image.BufferedImage;

public class Node {

    private final BufferedImage image;
    private Point location;
    private boolean isVisited;
    private final Node[] neighbors = new Node[8];
    private boolean blocked = false;
    private Node left = null;
    private Node right = null;
    private Node top = null;
    private Node bottom = null;

    public Node(BufferedImage image, Point location) {
        this.image = image;
        this.location = location;


    }

    public Node[] getNeighbors() {
        try {
            neighbors[0] = World.getNodes()[location.x + 1][location.y];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[1] = World.getNodes()[location.x - 1][location.y];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[2] = World.getNodes()[location.x][location.y + 1];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[3] = World.getNodes()[location.x][location.y - 1];
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            neighbors[4] = World.getNodes()[location.x - 1][location.y - 1];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[5] = World.getNodes()[location.x + 1][location.y - 1];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[6] = World.getNodes()[location.x - 1][location.y + 1];
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors[7] = World.getNodes()[location.x + 1][location.y + 1];
        } catch (IndexOutOfBoundsException ignored) {
        }


        return neighbors;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getTop() {
        return top;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public Node getBottom() {
        return bottom;
    }

    public void setBottom(Node bottom) {
        this.bottom = bottom;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
