//I added the necessary libraries here
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FourInARowGame extends Canvas implements MouseListener{
    //Showing the game status thanks to SituationMassage
    private String  situationMassage = "Player 1 can be start";
    //set the coordinates of the game board here. For example, if we set starX to zero, it will look like this.
    //also determine the number of row columns and the diameter of the circle on the game board.
    private final int StartX = 50, StartY = 50,circleDiameter = 50, numRow = 6, numCol = 7;
    private int[][] board = new int[numRow +2] [numCol +2 ];

    //this variable determines the next player
    private int playerTurn =1;
    //This variable checks whether the game is over or not
    private boolean gameOver;
    //controls the number of circles in the game
    private int numberOfPieces = 0;

    //Here ı add mouse listenr for mouse events and also determine the window size.
    public FourInARowGame(){
        addMouseListener(this);
        setSize(new Dimension(450, 450));
    }

    public void paint(Graphics g){
        displayGame(g); //The displayGame method draws fill circles on the game board.
        showNextPlayer(g); //Method to show the next player.
        drawGameBoard(g); //The  drawGmaeBoard method draws the game board.
    }

//Thanks to the for loop, we can access every row and column. Also, the value in each cell is checked.
//If the value is 1, a fill red circle is drawn in that cell. If it is 2, the same thing is done in blue.
//Using startX, starY and circleDimater, the circleDiameter are placed in the appropriate place.
    public void displayGame(Graphics g) {
        for (int i = 1; i<=numRow; i++)
            for(int j = 1; j <=numCol; j++)
                if(board[i][j]==1){
                    g.setColor(Color.red);
                    g.fillOval(StartX + (j -1)* circleDiameter, StartY + (i -1)* circleDiameter , circleDiameter,circleDiameter);
                }else if (board[i][j]==2){
                    g.setColor(Color.blue);
                    g.fillOval(StartX + (j -1)* circleDiameter, StartY + (i -1)* circleDiameter , circleDiameter,circleDiameter);
    }
}
//The purpose of this method is to show text at the top of the game window show who the next player is and the game status.
    public void showNextPlayer(Graphics g){
        //This sets the font, boldness and size of your text
        g.setFont(new Font(Font.DIALOG, Font.BOLD,24));
        if (playerTurn ==1)
        g.setColor(Color.BLACK);
        else g.setColor(Color.BLACK);
        //settings where text will be shown
        g.drawString( situationMassage, 100 , 40);
    }

    //This method draws the game board, that is, it creates the empty circles we see.
    public void drawGameBoard(Graphics g){
        Graphics2D x = (Graphics2D) g;

      
        for ( int i=0; i < numRow; i++){
            for (int j = 0; j < numCol; j++) {
                int a = StartX + j * circleDiameter + circleDiameter / 2;
                int b = StartY + i * circleDiameter + circleDiameter/2;

                x.drawOval(a-circleDiameter/2, b-circleDiameter/2, circleDiameter, circleDiameter);
            }
        }
    }
    //it's checks the coordinates are on the game board.
    public boolean isInPage(int x, int y){
        return (x> StartX && x < StartX + numCol * circleDiameter && y> StartY && y<StartY + numRow * circleDiameter);
    }
    //it's checks the rows and columns are empty.
    public boolean isEmptye(int row, int col) {
        return board[row][col] == 0;
    

    }
    //  game status is updated here. Updates the situationMassage message showing which player's turn it is.
    public void changeplayer(){
        if (playerTurn == 1) {
             situationMassage = "Next Turn: Player 2";
            playerTurn = 2;

        }else{
             situationMassage = "Next Turn: Player 1";
            playerTurn = 1;
        }
    }

    //Winning status is checked here. Here horizontal, vertical, diagonal etc. 4-way matches are checked. 
    //Finally, if one of the conditions is provide points increases by 1.

    private boolean checkWin(int row, int col) {
        int points = 0;

        if (board[row - 1][col] == playerTurn && board[row - 2][col] == playerTurn && board[row - 3][col] == playerTurn) points++;
        if (board[row + 1][col] == playerTurn && board[row + 2][col] == playerTurn && board[row + 3][col] == playerTurn) points++;
        if (board[row][col - 1] == playerTurn && board[row][col - 2] == playerTurn && board[row][col - 3] == playerTurn) points++;
        if (board[row][col + 1] == playerTurn && board[row][col + 2] == playerTurn && board[row][col + 3] == playerTurn) points++;
        if (board[row - 1][col - 1] == playerTurn && board[row - 2][col - 2] == playerTurn && board[row - 3][col - 3] == playerTurn) points++;
        if (board[row + 1][col + 1] == playerTurn && board[row + 2][col + 2] == playerTurn && board[row + 3][col + 3] == playerTurn) points++;
        if (board[row - 1][col + 1] == playerTurn && board[row - 2][col + 2] == playerTurn && board[row - 3][col + 3] == playerTurn) points++;
        if (board[row + 1][col - 1] == playerTurn && board[row + 2][col - 2] == playerTurn && board[row + 3][col - 3] == playerTurn) points++;
        if (board[row - 1][col] == playerTurn && board[row + 1][col] == playerTurn && board[row + 2][col] == playerTurn) points++;
        if (board[row][col - 1] == playerTurn && board[row][col + 1] == playerTurn && board[row][col + 2] == playerTurn) points++;
        if (board[row - 1][col + 1] == playerTurn && board[row + 1][col - 1] == playerTurn && board[row + 2][col - 2] == playerTurn) points++;
        if (board[row - 1][col - 1] == playerTurn && board[row + 1][col + 1] == playerTurn && board[row + 2][col + 2] == playerTurn) points++;

        return points > 0;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        //If the game is not over and the clicked place is on the game board, the first empty place in the column is placed here.
        if (!gameOver) {
            if(isInPage(evt.getX(), evt.getY())){
                int col = (evt.getX()-StartX )/ circleDiameter+1;
                int row = numRow;

                while (row >= 1 && !isEmptye(row, col)) {
                    row--;
                }

                if (row >=1) {
                    board[row][col] = playerTurn;
                    numberOfPieces++;
//-----------------------------------------------------------------------
//Winning check is done here. The first one to match wins.
                    if (checkWin(row, col)) {
                        if (playerTurn == 1) {
                             situationMassage = "GAme OVER! Red Win!";
                        }else {
                            situationMassage = "Game Over! Blue Win!";
                        }
                        gameOver = true;
                    }
//-----------------------------------------------------------------------
//If no one can make a match and the game board is full, there will be a tie.                   
                    if (numberOfPieces == 42 && !gameOver) {
                        situationMassage = "Tie!";
                        gameOver = true;
                    } else if(!gameOver){
                        changeplayer();
                    }
                }
            }
                repaint();
            }
        }
        //There are mouseEvent events here. It's about our mouse click.
        @Override
        public void mouseEntered(MouseEvent arg0) {
        }
    
        @Override
        public void mouseExited(MouseEvent arg0) {
        }
    
        @Override
        public void mousePressed(MouseEvent arg0) {
        }
    
        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

        public static void main(String[] args) {
            JFrame frame1 = new JFrame("For in Row Game");
            JPanel panel1 = new JPanel();
            frame1.add(panel1);

        //I create two buttons there. When the game first starts, we will see these two buttons. 
        //If we want to play against the computer, we must press the computerButton. 
        //If we want to play between two players, the other button must be pressed. 
        //But there is no game against the computer in this code.
            JButton computerButton = new JButton("Player vs Computer");
            JButton playersButton = new JButton("Player vs Player");
            // ActionListener that will appear when the "Player  vs Player" button is pressed
            playersButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //In this part, the screen is being edited, thanks to this we will see the game screen 
                    FourInARowGame playersGame = new FourInARowGame();
                    frame1.getContentPane().removeAll(); //remove JFrame
                    frame1.add(playersGame);
                    //JFrame will be updated and redrawn
                    frame1.revalidate();
                    frame1.repaint();
                }
            });

            panel1.add(playersButton);
            panel1.add(computerButton);
            //This sets the game window. We provide size and visibility. We can turn it on and off.
            frame1.setSize(450,450);
            frame1.setLocationRelativeTo(null);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setVisible(true);

        }


    }
    

