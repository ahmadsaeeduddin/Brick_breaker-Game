
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapGenerator {

    public int map[][];
    public int brickwidth;
    public int brickheight;
    private BufferedImage brickImage;
    private BufferedImage brickImage2;
    private BufferedImage brickImage3;

    public MapGenerator(int r, int c) {
        map = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                map[i][j] = 1;
            }
        }
        brickwidth = 540 / c;
        brickheight = 150 / r;
        try {
            brickImage = ImageIO.read(getClass().getResource("first.png"));
            brickImage2 = ImageIO.read(getClass().getResource("second.png"));
            brickImage3 = ImageIO.read(getClass().getResource("third.png"));
        } catch (IOException e) {
            //e.printStackTrace();
            System.exit(1); // Exit if image can't be loaded
        }

    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    // g.setColor(Color.blue);
                    // g.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                    g.drawImage(brickImage, j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight, null);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                }
                if (map[i][j] == 2) {
                    g.drawImage(brickImage2, j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight, null);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                }
                if (map[i][j] == 3) {
                    g.drawImage(brickImage3, j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight, null);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
                }

            }
        }
    }

    public void setbrickValue(int r, int c) {
        map[r][c] += 1;
        if (map[r][c] > 3) {
            map[r][c] = 0;
        }
    }
}
