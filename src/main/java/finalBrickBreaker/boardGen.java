package finalBrickBreaker;
import java.awt.*;
import java.util.Arrays;


public class boardGen {

    public int board[][];
    public int brickWidth;
    public int brickHeight;

    public boardGen (int row, int col){

        board = new int[row][col];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j<board[0].length; j++){

                //brick not touching ball
                board[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D g){
        for(int i = 0; i< board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] > 0){
                    Color myColor = new Color (148,0,211);
                    g.setColor(myColor);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    //shows separate bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }

    }
    public void setBrickValue(int value, int row, int col)
    {
        board[row][col] = value;
    }










}
