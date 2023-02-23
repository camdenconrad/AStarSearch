import java.awt.*;

public class World {

    private static final int BLOCKED_SIZE = (int) ((15 * 15) * 0.15);
    static Node[][] nodes = new Node[15][15];
    static Point[] blocked = new Point[BLOCKED_SIZE];
    Node primary = new Node(null, new Point(0, 0));

    public World() {

        // generate blocked nodes

        for (int i = 0; i < BLOCKED_SIZE; i++) {
            blocked[i] = new Point(new java.util.Random().nextInt(15), new java.util.Random().nextInt(15));
            check(blocked, i);
        }


        nodes[0][0] = primary;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                try {
                    if (!World.notBlocked(nodes[i][j].getLocation())) {
                        nodes[i][j].setBlocked(true);
                    }
                    nodes[i][j + 1] = new Node(null, new Point(i, j + 1));
                    nodes[i][j].setRight(nodes[i][j + 1]);
                    if (i < 14) {
                        nodes[i][j].setBottom(nodes[i + 1][j]);
                    }
                    if (i > 0) {
                        nodes[i][j].setTop(nodes[i - 1][j]);
                    }
                    if (j > 0) {
                        nodes[i][j].setLeft(nodes[i][j - 1]);
                    }
                } catch (IndexOutOfBoundsException | NullPointerException ignored) {
                }
                if (nodes[i][j].isBlocked()) {
                    System.out.print("Z ");
                } else {
                    System.out.print("x ");
                }
            }
            try {
                System.out.println();
                if (!World.notBlocked(nodes[i][0].getLocation())) {
                    nodes[i][0].setBlocked(true);
                }
                nodes[i + 1][0] = new Node(null, new Point(i + 1, 0));
                nodes[i][0].setBottom(nodes[i + 1][0]);
                if (i > 0) {
                    nodes[i][0].setTop(nodes[i - 1][0]);
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
        }


//
//        for (int i = 0; i < 14; i++) {
//            for (int j = 0; j < 14; j++) {
//                boolean add = true;
//                for (int k = 0; k < BLOCKED_SIZE; k++) {
//                    if (new Point(i,j).equals(blocked[k])) {
//                        add = false;
//
//                    }
//                }
//
//                if (add) {
//                    n = new Node(null, )
//                }
//            }
//        }
    }

    public static Node[][] getNodes() {
        return nodes;
    }

    public static Node findNode(Point location) {
        return nodes[location.x][location.y];
    }

    private static void check(Point[] randoms, int i) {
        for (int j = 0; j < i; j++) {
            while (randoms[j].equals(randoms[i])) {
                randoms[i] = new Point(new java.util.Random().nextInt(15), new java.util.Random().nextInt(15));
            }
        }
        for (int j = 0; j < i; j++) {
            if (randoms[j].equals(randoms[i])) {
                check(randoms, i);

            }
        }
    }

    public static boolean notBlocked(Point location) {
        for (Point p : blocked) {
            if (p.equals(location)) {
                return false;
            }
        }

        return true;
    }

    public Point[] getBlocked() {
        return blocked;
    }
}
