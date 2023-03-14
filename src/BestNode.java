public class BestNode {

    static Heuristic t = new Heuristic(null, 1000000);

    public static boolean add(Heuristic h) {
        if (h != null && h.n() != null) {
            if (h.Heuristic() < t.Heuristic()) {
                t = h;
                return true;
            }
        }
        return false;
    }

    public static boolean clear() {
        Heuristic t = new Heuristic(null, 1000000);
        return true;
    }
}
