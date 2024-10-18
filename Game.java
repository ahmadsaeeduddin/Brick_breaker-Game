
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {

    private boolean play = true;
    private int score = 0;
    private int totalBricks = 21;
    private final Timer timer;
    private final int delay = 7;
    private int playerX = 320;
    private int ballX = 350;
    private int ballY = 500;
    private int ballDX = -2;
    private int ballDY = -2;
    private final JFrame frame;
    private final MapGenerator map;

    public Game(JFrame f) {
        this.frame = f;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        map = new MapGenerator(3, 7);

        Random random = new Random();
        int randomNumber = random.nextInt(2);

        if(randomNumber == 0){
            ballDX = -3;
            ballDY = -3;
        }
        if(randomNumber == 1){
            ballDX = 3;
            ballDY = -3;
        }


        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // brick
        map.draw((Graphics2D) g);

        //score
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 570, 35);

        //if ball falls through
        if (ballY > 570) {
            play = false;
            timer.stop();
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 270, 300);
            g.drawString("Your Score: " + score, 270, 350);

        }

        if (totalBricks <= 0) {
            timer.stop();
            g.setColor(Color.yellow);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("You Won : " + score, 233, 300);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Press Enter to Restart!", 155, 350);
        }

        // border
        g.setColor(Color.green);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(691, 0, 3, 592);

        // the paddle
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.green);
        g.fillOval(ballX, ballY, 20, 20);

        g.dispose();
    }

    @Override // No need for it
    public void keyTyped(KeyEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e
    ) {
        // System.out.println("Key Pressed HAHA" + playerX);
        // if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        //     // playerX += 20;
        //     if (playerX > 600) {
        //         playerX = 10;
        //     } else {
        //         moveRight();
        //     }
        // }
        // if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        //     // playerX -= 20;
        //     if (playerX < 0) {
        //         playerX = 600;
        //     } else {
        //         moveLeft();
        //     }
        // }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballX = 120;
                ballY = 350;
                ballDX = -4;
                ballDY = -4;
                score = 0;
                totalBricks = 21;
                for (int i = 0; i < map.map.length; i++) {
                    for (int j = 0; j < map.map[0].length; j++) {
                        map.map[i][j] = 1;
                    }
                }
                timer.start();
                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override // No need for it
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDY = -ballDY;
            }
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickx = j * map.brickwidth + 80;
                        int brickY = i * map.brickheight + 50;
                        Rectangle brickRect = new Rectangle(brickx, brickY, map.brickwidth, map.brickheight);
                        if (new Rectangle(ballX, ballY, 20, 20).intersects(brickRect)) {
                            map.setbrickValue(i, j);
                            if (map.map[i][j] == 0) {
                                totalBricks--;
                                score += 5;
                            }

                            if (ballX + 19 <= brickRect.x || ballX + 19 >= brickRect.x + brickRect.width) {
                                ballDX = -ballDX;
                            } else {
                                ballDY = -ballDY;
                            }

                            if (totalBricks == 0) {
                                play = false;
                                timer.stop();
                                //JOptionPanel.showMessageDialog(frame, "You Win, Score: " + score);
                            }
                            break A;
                        }
                    }
                }
            }
            ballX += ballDX;
            ballY += ballDY;
            if (ballX <= 0) {
                ballDX = -ballDX;
            }
            if (ballY <= 0) {
                ballDY = -ballDY;
            }
            if (ballX >= 665) {
                ballDX = -ballDX;
            }
            if (ballX <= 620 && ballX >= 8) {
                playerX = ballX;
            }
        }
        repaint();
    }

}
