import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

class Line {
    private final ArrayList<Line> components = new ArrayList<>();
    private final ArrayList<Node> blockedNodes = new ArrayList<>();
    ArrayList<Node> nodes;
    private Point start;
    private Point end;
    private ArrayList<Point> points = new ArrayList<>();


    public Line(Point start, Point end) throws IOException {
        this.start = start;
        this.end = end;

        pathGen(start, end);

        //generatePoints(start, end);
//
//        nodes = rayCast();
//
//        generateNodeBased();

//        this.split(this, 0);

        //nodes = rayCast();
        //generateNodeBased();
    }

    public Line(Point start, Point end, int i) {
        this.start = start;
        this.end = end;

        generatePoints(start, end);

//        nodes = rayCast();
//
//        generateNodeBased();

        //this.getNodes(i);
    }

    private static double frac(double a, double b) {
        return a / b;
    }

    private void pathGen(Point s, Point e) {

        Node start = World.findNode(s.getLocation());
        Node end = World.findNode(e.getLocation());

        Node best = null;


        for (Node n : start.getNeighbors()) {
            if (n.getLocation().equals(end.getLocation())) {
                generatePoints(new Point(48 + (s.getLocation().x * 65), 48 + (s.getLocation().y * 65)), new Point(48 + (e.x * 65), 48 + (e.y * 65)));
                return;
            } else {
                if (!n.isBlocked() && !n.isVisited() && notLocalBlocked(start, n)) {
                    if (best == null) {
                        best = n;
                    } else {
                        if (manhattanDistance(best) > manhattanDistance(n)) {
                            best = n;
                        }
                    }
                    n.setVisited(true);

                }
            }

        }
        if (best != null) {
            generatePoints(new Point(48 + (s.getLocation().x * 65), 48 + (s.getLocation().y * 65)), new Point(48 + (best.getLocation().x * 65), 48 + (best.getLocation().y * 65)));
            if (best.getLocation().equals(e.getLocation())) {
                return;
            }
            pathGen(best.getLocation(), e);
        }
    }

    private boolean notLocalBlocked(Node start, Node n) {
        if (start.getTopLeft().getLocation().equals(n.getLocation())) {
            try {
                if(n.getLeft().isBlocked() && n.getTop().isBlocked()) {
                    return false;
                }
            } catch (NullPointerException ignored){}
        }
        if (start.getTopRight().getLocation().equals(n.getLocation())) {
            try {
                if(n.getRight().isBlocked() && n.getTop().isBlocked()) {
                    return false;
                }
            } catch (NullPointerException ignored){}
        }
        if (start.getBottomRight().getLocation().equals(n.getLocation())) {
            try {
                if(n.getLeft().isBlocked() && n.getBottom().isBlocked()) {
                    return false;
                }
            } catch (NullPointerException ignored){}
        }
        if (start.getBottomRight().getLocation().equals(n.getLocation())) {
            try {
                if(n.getRight().isBlocked() && n.getBottom().isBlocked()) {
                    return false;
                }
            } catch (NullPointerException ignored){}
        }

        return true;
    }

    private void generateNodeBased() {
        // reset points to be node based
        points.clear();
        for (int i = 0; i < nodes.size() - 1; i++) {
            Point start = new Point(48 + (nodes.get(i).getLocation().x * 65), 48 + (nodes.get(i).getLocation().y * 65));
            Point end = new Point(48 + (nodes.get(i + 1).getLocation().x * 65), 48 + (nodes.get(i + 1).getLocation().y * 65));

            generatePoints(start, end);
        }
    }

    private ArrayList<Node> rayCast() {
        ArrayList<Node> list = new ArrayList<>();

        for (Point p : this.points) {
            Point location = new Point((int) ((p.getX()) / 65), (int) (((p.getY()) - 32) / 65));
            if (!list.contains(World.findNode(location)))
                list.add(World.findNode(location));

        }
        return list;
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

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Line> getComponents() {
        return components;
    }

    public Line split(Line l, int i) {
        getConflicts();
        //while (getConflicts() > 0) {
        if (l.blockedNodes.isEmpty()) {
            return l;
        }

        Node node = l.blockedNodes.get(l.blockedNodes.size() / 2);
        System.out.println(node.getLocation());

        Node best = getBest(node).n();
        BestNode.clear();
//
//            System.err.println("Best location: " + best.getLocation());
//
        Point pathBreak = new Point(48 + (node.getLocation().x * 65), 48 + (node.getLocation().y * 65));
        Point bestPx = new Point(48 + (best.getLocation().x * 65), 48 + (best.getLocation().y * 65));

        Main.getGraphics().drawImage(Main.getPathbreak(), pathBreak.x, pathBreak.y);
        Main.getGraphics().drawImage(Main.getChecked(), bestPx.x, bestPx.y);
        Main.getGraphics().update();
//
        Line partitionOne = new Line(start, bestPx, 0);
        Line partitionTwo = new Line(bestPx, end, 0);
        l.blockedNodes.clear();

        points.clear();
        points.addAll(partitionOne.getPoints());
        points.addAll(partitionTwo.getPoints());


//        partitionOne = split(partitionOne, 0);
//
//        partitionTwo = split(partitionTwo, 0);


        components.add(partitionOne);
        components.add(partitionTwo);

        for (Node n : blockedNodes) {
            System.out.println("Blocked Location: " + n.getLocation());

        }


        //}

        return this;
    }

    private Heuristic getBest(Node node) {
        // find the best node nearby - the lowest heuristic

        for (Node n : node.getNeighbors()) {
//            Point pathBreak = new Point(48 + (n.getLocation().x * 65), 48 + (n.getLocation().y * 65));
//
//            Main.getGraphics().drawImage(Main.getChecked(), pathBreak.x, pathBreak.y);
//            Main.getGraphics().update();
            System.err.println("Point: " + n.getLocation());
            System.out.println("Manhattan Distance: " + manhattanDistance(n));

            if (!n.isVisited()) {

                n.setVisited(true);

                if (n.isBlocked()) {
                    BestNode.add(getBest(n));
                    continue;
                }
                BestNode.add(new Heuristic(n, manhattanDistance(n)));

            }


        }

        System.out.println(BestNode.t);
        Main.getGraphics().drawImage(Main.getFlag(), 48 + (BestNode.t.n().getLocation().x * 65), 48 + (BestNode.t.n().getLocation().y * 65));
        Main.getGraphics().update();
        return BestNode.t;
    }

    /**
     * @param n Node to be calculated
     * @return Heuristic value
     * Calculates manhattan distance * conflicts
     */
    private int manhattanDistance(Node n) {
        return (Math.abs(n.getLocation().x - end.x) + Math.abs(n.getLocation().y - end.y));// * ((new Line(start, n.getLocation().getLocation(), 't').getConflicts() + new Line(n.getLocation().getLocation(), end, 't').getConflicts()));
    }

    private double distance(Node n) {
        return 0;
    }

    private int getConflicts() {
        blockedNodes.clear();
        for (Point p : this.getPoints()) {
            Point location = new Point((int) ((p.getX()) / 65), (int) (((p.getY()) - 32) / 65));

            //if node is blocked we must move around it
            if (!World.notBlocked(location)) {
                if (!blockedNodes.contains(World.findNode(location))) {
                    blockedNodes.add(World.findNode(location));
                }
            }
        }

        return blockedNodes.size();
    }

    private int getConflicts(Line l) {
        l.blockedNodes.clear();
        for (Point p : l.getPoints()) {
            Point location = new Point((int) ((p.getX()) / 65), (int) (((p.getY()) - 32) / 65));

            //if node is blocked we must move around it
            if (!World.notBlocked(location)) {
                if (!l.blockedNodes.contains(World.findNode(location))) {
                    l.blockedNodes.add(World.findNode(location));
                }
            }
        }

        return l.blockedNodes.size();
    }

    /*
    try {

                bestPx = new Point(48 + (n.getLocation().x * 65), 48 + (n.getLocation().y * 65));
                Main.getGraphics().drawImage(Main.getChecked(), bestPx.x, bestPx.y);
                Main.getGraphics().update();


                if (!n.isBlocked() && !n.isVisited()) {
                    int thisH = Math.abs(n.getLocation().x - end.x) + Math.abs(n.getLocation().y - end.y);
                    System.out.println("Possible H: " + thisH);

                    if (thisH < bestH) {
                        bestH = thisH;
                        bestNode = n;
                        System.out.println("New H: " + bestH);
                    }
                }

                n.setVisited(true);


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
     */

}
