import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private final BufferedImage image;
    private final ArrayList<Node> neighbors = new ArrayList<>();
    private Point location;
    private boolean isVisited;
    private boolean blocked = false;
    private Node left = null;
    private Node right = null;
    private Node top = null;
    private Node bottom = null;
    private Node topLeft = null;
    private Node topRight = null;
    private Node bottomLeft = null;
    private Node bottomRight = null;

    public Node(BufferedImage image, Point location) {
        this.image = image;
        this.location = location;

    }

    public Node getTopLeft() {
        return topLeft;
    }

    public Node getTopRight() {
        return topRight;
    }

    public Node getBottomLeft() {
        return bottomLeft;
    }

    public Node getBottomRight() {
        return bottomRight;
    }

    public void findNeighbors() {

        try {
            neighbors.add(World.getNodes()[location.x + 1][location.y]);
            right = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x - 1][location.y]);
            left = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x][location.y + 1]);
            top = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x][location.y - 1]);
            bottom = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x - 1][location.y - 1]);
            bottomLeft = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x + 1][location.y - 1]);
            bottomRight = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x - 1][location.y + 1]);

            topLeft = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            neighbors.add(World.getNodes()[location.x + 1][location.y + 1]);
            topRight = neighbors.get(neighbors.size() - 1);
        } catch (IndexOutOfBoundsException ignored) {
        }

        neighbors.removeIf(Objects::isNull);
    }

    public ArrayList<Node> getNeighbors() {

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
