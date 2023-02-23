import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

class Line {
    private final ArrayList<Line> components = new ArrayList<>();
    private Point start;
    private Point end;
    private ArrayList<Point> points = new ArrayList<>();


    public Line(Point start, Point end) throws IOException {
        this.start = start;
        this.end = end;

        generatePoints(start, end);

        this.getNodes(0);
    }

    public Line(Point start, Point end, int i) throws IOException {
        this.start = start;
        this.end = end;

        generatePoints(start, end);

        this.getNodes(i);
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

    public void getNodes(int i) throws IOException {

        ArrayList<Point> localPoints = getPoints();

        for (Point p : localPoints) {
            Point location = new Point((int) ((p.getX()) / 65), (int) (((p.getY()) - 32) / 65));

            // if node is blocked we must move around it
            if (!World.notBlocked(location)) {

                Node best = getBest(World.findNode(location));
//                if(best == null) {
//                    continue;
//                }
                System.out.println(best.getLocation());
                if(best.isBlocked()) {
                    System.out.println("What the fuck");
                }


                Point pathBreak = new Point(48 + (location.getLocation().x * 65), 48 + (location.getLocation().y * 65));
                Point bestPx = new Point(48 + (best.getLocation().x * 65), 48 + (best.getLocation().y * 65));

                Main.getGraphics().drawImage(Main.getPathbreak(), pathBreak.x, pathBreak.y);
                Main.getGraphics().update();

                Line partitionOne = new Line(start, bestPx, i++);
                Line partitionTwo = new Line(bestPx, end, i++);
                System.out.println("Depth: " +  i);

                partitionOne.getNodes(i);
                partitionTwo.getNodes(i);

                points = new ArrayList<>();
                points.addAll(partitionOne.getPoints());
                points.addAll(partitionTwo.getPoints());
                break;
            }


//            if (!points.contains(location)) {
//                points.add(location);
//            }
        }

    }

    private Node getBest(Node node) {
        int bestH = 1000000;

        Node bestNode = null;

        Point bestPx = new Point(48 + (node.getLocation().x * 65), 48 + (node.getLocation().y * 65));
//        Main.getGraphics().drawImage(Main.getChecked(), bestPx.x, bestPx.y);
//        Main.getGraphics().update();

        for (Node n : node.getNeighbors()) {
            if(bestNode == null) {
                bestNode = n;
            }


            try {
                int localHeuristic = manhattanDistance(n);
                System.out.println(localHeuristic);

                if (!n.isVisited()) {
                    Node curr = n;
                    curr.setVisited(true);

                    if (n.isBlocked()) {
                        System.out.println("Running");
                        curr = getBest(n);
                    }

                    if (localHeuristic > bestH) {
                        bestH = localHeuristic;
                        bestNode = curr;
                    }


                }
            } catch(NullPointerException ignored){}
        }
//        for (Node n : node.getNeighbors()) {
//            try {
//                n.setVisited(false);
//            } catch(NullPointerException ignored){}
//        }
        assert bestNode != null;
        if(bestNode.isBlocked()) {
//            for (Node n : node.getNeighbors()) {
//            try {
//                n.setVisited(false);
//            } catch(NullPointerException ignored){}
            bestNode = getBest(node.getLeft());
        }
//        }

        return bestNode;


    }

    private int manhattanDistance(Node n) {
        return Math.abs(n.getLocation().x - end.x) + Math.abs(n.getLocation().y - end.y);
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
