import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Image extends JPanel {
    private final int width;
    private final int height;
    private final BufferedImage img;
    private final ArrayList<Point> toDraw = new ArrayList<Point>();
    protected ArrayList<Node> nodes = new ArrayList<>();

    public Image(int width, int height) {
        this.width = width;
        this.height = height;


        img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

//            Graphics2D g2d = img.createGraphics();
//            g2d.setColor(Color.RED);
//            g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
//            g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(img, 0, 0, this);
        g2d.dispose();
    }

    public void drawPx(int x, int y) {
        try {
            img.setRGB(x, y, Color.ORANGE.getRGB());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void drawPx(int x, int y, Color c) {
        try {
            img.setRGB(x, y, c.getRGB());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public void drawPx(Point p) {
        img.setRGB(p.x, p.y, Color.ORANGE.getRGB());
    }


    public void redraw() {

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
    }

    public void update() {
        redraw();
        for (Node l : nodes) {
            img.createGraphics().drawImage(l.getImage(), l.getLocation().x - (l.getImage().getWidth() / 2), l.getLocation().y - (l.getImage().getHeight() / 2), null);
        }
        for (Point p : toDraw) {
            drawPx(p.x, p.y, Color.RED);
        }
//        for (Line l : lines) {
//            if (l.checkLine(locations)) {
//                drawPx(l);
//            }
//        }
//        drawMode(this.drawMode);
        this.updateUI();
    }

    public void drawImage(BufferedImage image, int x, int y) {

        nodes.add(new Node(image, new Point(x, y)));

    }

//    public void drawLine(Point p, Point p1) {
//        img.createGraphics().drawLine(p.x, p.y, p1.x, p1.y);
//    }

    public void drawLine(Line l) {
        //img.createGraphics().drawLine(l.getStart().x,l.getStart().y, l.getEnd().x,l.getEnd().y);
        for (Point p : l.getPoints()) {
            drawPx(p.x, p.y, Color.BLACK);
            toDraw.add(p);
        }


        updateUI();
    }

    public void reset() {
        nodes.clear();
        toDraw.clear();
    }
}