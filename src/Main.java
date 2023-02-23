import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final JFrame frame = new JFrame();
    public static Point justClicked = null;
    static BufferedImage startImg;
    private static BufferedImage pathbreak;
    private static BufferedImage checked;
    private static Image graphics;
    private static BufferedImage square;
    private static BufferedImage blocked;
    private static BufferedImage endImg;
    private static Point start;
    private static Point end;
    private static int clickCount = 0;

    public static BufferedImage getPathbreak() {
        return pathbreak;
    }

    public static BufferedImage getChecked() {
        return checked;
    }

    public static Image getGraphics() {
        return graphics;
    }

    public static void main(String[] args) throws IOException {
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setTitle("A Star Search");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1024, screenSize.height);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setResizable(false);

        graphics = new Image(1024, 1008);
        frame.add(graphics);

        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //square size = 64 * 64
        square = ImageIO.read(new File("Images/square.png"));
        blocked = ImageIO.read(new File("Images/squareBlocked.png"));
        startImg = ImageIO.read(new File("Images/startFlag.png"));
        endImg = ImageIO.read(new File("Images/endFlag.png"));
        checked = ImageIO.read(new File("Images/check-mark.png"));
        pathbreak = ImageIO.read(new File("Images/refresh.png"));

        frame.setIconImage(startImg);

        World world = new World();

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!e.isShiftDown()) {
                    Point location = new Point((((e.getX()) - 32) / 65), ((e.getY()) - 32) / 65);
                    justClicked = new Point(48 + (location.x * 65), 48 + (location.y * 65));

                    System.err.println(location);

                    if (location.x < 15 && location.y < 15) {
                        if (World.notBlocked(location)) {
                            clickCount++;
                            if (clickCount == 1) {
                                graphics.drawImage(startImg, justClicked.x, justClicked.y);
                                start = justClicked;
                                graphics.update();
                            } else {
                                if (clickCount == 2) {
                                    graphics.drawImage(endImg, justClicked.x, justClicked.y);
                                    end = justClicked;

                                    graphics.update();
                                    try {
                                        graphics.drawLine(new Line(start, end));
                                        graphics.update();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                }
                            }
                        }
                    }
//                } else {
//                    Point location = new Point((((e.getX()) - 32) / 65), ((e.getY()) - 32) / 65);
//                    World.getNodes()[location.x][location.y].setBlocked(true);
//                    justClicked = new Point(48 + (location.x * 65), 48 + (location.y * 65));
//                    graphics.drawImage(blocked, 48 + (justClicked.x * 65), 48 + (justClicked.y * 65));
//                    graphics.update();
//
//
                }


            }

        });

        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {

                    World world = new World();
                    graphics.reset();

                    for (int i = 0; i < 15; i++) {
                        for (int j = 0; j < 15; j++) {
                            graphics.drawImage(square, 48 + (i * 65), 48 + (j * 65));
                        }
                    }
                    graphics.update();

                    for (Point p : world.getBlocked()) {
                        graphics.drawImage(blocked, 48 + (p.x * 65), 48 + (p.y * 65));
                    }
                    graphics.update();
                    clickCount = 0;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                graphics.drawImage(square, 48 + (i * 65), 48 + (j * 65));
            }
        }
        graphics.update();

        for (Point p : world.getBlocked()) {
            graphics.drawImage(blocked, 48 + (p.x * 65), 48 + (p.y * 65));
        }
        graphics.update();


    }

}