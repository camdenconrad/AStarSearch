import java.awt.*;
import java.util.ArrayList;

public class LineNodeBased {

    Point start;
    Point end;

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    ArrayList<Node> nodes;

    public ArrayList<Point> getPoints() {
        return points;
    }

    private final ArrayList<Point> points = new ArrayList<>();


    public LineNodeBased(Point start, Point end) {
        this.start = start;
        this.end = end;

        generatePoints(start, end);

        nodes = rayCast();

        for(Node n : nodes) {
            System.out.println(n.getLocation());
        }

        generateNodeBased();

        while (this.getConflicts() > 1) {
            test();
        }

        generateNodeBased();
    }

    public LineNodeBased(Point start, Point end, char mode) {
        this.start = start;
        this.end = end;

        generatePoints(start, end);

        nodes = rayCast();

        generateNodeBased();
    }

    private void generateNodeBased() {
        // reset points to be node based
        points.clear();
        for(int i = 0; i < nodes.size() - 1; i++) {
            Point start = new Point(48 + (nodes.get(i).getLocation().x * 65), 48 + (nodes.get(i).getLocation().y * 65));
            Point end = new Point(48 + (nodes.get(i + 1).getLocation().x * 65), 48 + (nodes.get(i + 1).getLocation().y * 65));

            generatePoints(start, end);
        }
    }

    private ArrayList<Node> rayCast() {
        ArrayList<Node> list = new ArrayList<>();

        for (Point p : this.points) {
            Point location = new Point((int) ((p.getX()) / 65), (int) (((p.getY()) - 32) / 65));
            if(!list.contains(World.findNode(location)))
                list.add(World.findNode(location));

        }
        return list;
    }

    public void test() {
        BestNode.clear();

        if(nodes.isEmpty()) {
            return;
        }
        Node n = nodes.get(nodes.size()/2);

            if(World.blocked(n)) {
                System.out.println("Blocked: " + n.getLocation());
                for(Node neighbor : n.getNeighbors()) {
                    if(!neighbor.isBlocked()) {
                        BestNode.add(new Heuristic(neighbor, manhattanDistance(neighbor)));
                    }
                }

                System.out.println("Best location: " + BestNode.t.n().getLocation());
                nodes.set(nodes.indexOf(n), BestNode.t.n());

                BestNode.clear();
            }

    }


    private static double frac(double a, double b) {
        return a / b;
    }

    private void generatePoints(Point start, Point end) {
        lineGen1(start, end, end.getY(), start.getY());
        lineGen1(end, start, end.getY(), start.getY());

        if (start.x == end.x) {
            if (start.y > end.y) {
                for (int y = 0; y < (start.y - end.y); y++) {
                    points.add(new Point(start.x, end.y + y));
                }
            } else {
                for (int y = 0; y < (end.y - start.y); y++) {
                    points.add(new Point(start.x, start.y + y));
                }
            }
        }

        if (start.y == end.y) {
            if (start.x > end.x) {
                for (int x = 0; x < (start.x - end.x); x++) {
                    points.add(new Point(start.x - x, end.y));
                }
            } else {
                for (int x = 0; x < (end.x - start.x); x++) {
                    points.add(new Point(start.x + x, start.y));
                }
            }
        }
    }

    private void lineGen1(Point start, Point end, double y2, double y3) {
        lineGen2(start, end, y2, y3);
        lineGen2(end, start, y3, y2);
    }

    private void lineGen2(Point start, Point end, double y2, double y3) {
        for (int x = 0; x < end.getX() - start.getX(); x++) {
            for (int y = 0; y < y2 - y3; y++) {
                points.add(new Point((int) (x + start.getX()), (int) (x * frac(end.getY() - start.getY(), end.getX() - start.getX()) + start.getY())));
            }
        }
    }

    private int manhattanDistance(Node n) {
        return (Math.abs(n.getLocation().x - start.x) + Math.abs(n.getLocation().y - start.y)) * ((new LineNodeBased(start, n.getLocation().getLocation(), 't').getConflicts() + new LineNodeBased(n.getLocation().getLocation(), end, 't').getConflicts()));
    }

    private int getConflicts() {
        int i = 0;
        for (Node n : nodes) {
            if(n.isBlocked()) {
                i++;
            }
        }
        return i;
    }
}
