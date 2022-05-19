
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;
import java.util.Random;


public class brickBreaker extends JPanel implements ActionListener, KeyListener {


    private boolean play = false;
    private int score = 0;
    private int totalbricks = 36;

    //timer acts as ball speed
    private Timer timer;
    private int delay = 10;

    //paddle position
    private int playerX = 310;

    //ball position
    private int ballposX ;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2; //change values to change ball velocity
    private int randWidth;
    private  boardGen board;

    private void randomStart() {
        Random startRand = new Random();
        randWidth = startRand.nextInt(100, 650);
    }
    public brickBreaker(){
        board = new boardGen(4,9);
        addKeyListener(this); //key detection
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start(); //starts game cycle
        randomStart();
        ballposX = randWidth;
    }



    public void paint(Graphics g) {

        //background colour
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);



        //drawing map
        board.draw((Graphics2D) g);

        //border
        Color myColor2 = new Color (189, 181, 213);
        g.setColor(myColor2);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(681, 0, 3, 592);

        //Paddle
        g.setColor(Color.white);
        g.fillRect(playerX, 550, 100, 8);

        //Ball
        g.setColor(Color.white);
        g.fillOval(ballposX, ballposY, 20, 20);

        // when win
        if(totalbricks <= 0)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("You Won", 260,300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        // when lose
        if(ballposY > 570)
        {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Score: "+score, 190,300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        g.dispose();
    }
    //key detection overrides current
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) //for restarting game after lose
        {
            if(!play)
            {
                play = true;
                ballposX = randWidth;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalbricks = 36;
                board = new boardGen(4, 9);

                repaint();
            }
        }

    }
    public void moveLeft() {
        play = true;
        playerX -=15;
    }

    public void moveRight() {
        play = true;
        playerX += 15;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //intersections for player and ball
        timer.start();
        if(play) {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
            {
                ballYdir = -ballYdir;

            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 100, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 110, 8)))
            {
                ballYdir = -ballYdir;
            }

            // check map collision with ball
            A: for(int i = 0; i<board.board.length; i++)
            {
                for(int j =0; j<board.board[0].length; j++)
                {
                    if(board.board[i][j] > 0)
                    {
                        int brickX = j * board.brickWidth + 80;
                        int brickY = i * board.brickHeight + 50;
                        int brickWidth = board.brickWidth;
                        int brickHeight = board.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect))
                        {
                            board.setBrickValue(0, i, j);
                            score+=5;
                            totalbricks--;

                            // when ball hit right or left of brick
                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
                            {
                                ballXdir = -ballXdir;
                            }
                            // when ball hits top or bottom of brick
                            else
                            {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if(ballposX < 0)
            {
                ballXdir = -ballXdir;
            }
            if(ballposY < 0)
            {
                ballYdir = -ballYdir;
            }
            if(ballposX > 670)
            {
                ballXdir = -ballXdir;
            }

            repaint();
        }



    }






}
