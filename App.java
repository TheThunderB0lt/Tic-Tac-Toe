import java.io.IOException;
import javafx.application.Application; // For running the JavaFX application
import javafx.geometry.Insets; // 2D geometry
import javafx.geometry.Pos;
import javafx.scene.Scene; // Box
import javafx.scene.control.Alert; // Alert message/ display alert
import javafx.scene.control.Button; // Buttons
import javafx.scene.control.Label; // setting labels in the game board
import javafx.scene.layout.BorderPane; // border box
import javafx.scene.layout.GridPane; // 3X3 GRID box
import javafx.scene.layout.HBox;
import javafx.stage.Stage; // for displaying GUI window
 
public class App extends Application { // Inheritance
    private Button buttons[][] = new Button[3][3]; // for grid, Button is predefined keyword
    private Label playerXScoreLabel, playerOScoreLabel; // label is predefined keyword for labelling
    private int playerXScore = 0, playerOScore = 0;
    private boolean playerXTurn = true;

    private BorderPane createContent() { // Borderpane is predefined keyword, box
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20)); // it aligns game box in center with 20pt padding

        // Title
        Label title = new Label("Tic Tac Toe"); // Label for the game name
        title.setStyle("-fx-font-size : 32pt; -fx-font-weight: bold;"); // text styling
        root.setTop(title); // tittle set to top 
        BorderPane.setAlignment(title, Pos.CENTER); // align the game box in the center

        // Game Board
        GridPane gridPane = new GridPane(); // 3X3 frid box
        gridPane.setHgap(10); // gap for each box horizantal
        gridPane.setVgap(10); // gap for each box vertical
        gridPane.setAlignment(Pos.CENTER); 
        for (int i = 0; i < 3; i++) { // nested loop
            for(int j = 0; j < 3; j++) {
                Button button = new Button(); // Button for setting X / O
                button.setPrefSize(100, 100); // size of each box/button box
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight: bold;");
                button.setOnAction(event->buttonClicked(button)); //**imp */ for clickinf
                buttons[i][j] = button; // X then O
                gridPane.add(button, j, i); // O then x, alternate for next person
            }
        }
        root.setCenter(gridPane); // set game box in the center
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Score
        HBox scoreBoard = new HBox(20); 
        scoreBoard.setAlignment(Pos.CENTER);
        playerXScoreLabel = new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight: bold;");
        playerOScoreLabel = new Label("Player O : 0");
        playerOScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight: bold;");
        scoreBoard.getChildren().addAll(playerXScoreLabel ,playerOScoreLabel); // score counting for each person wins

        root.setBottom(scoreBoard); // set winner label at the bottom

        return root; // returing the box
    }

    // Function for button/grid click 
    private void buttonClicked(Button button) {
        if(button.getText().equals("")) { // if box is null,add X / O
            if(playerXTurn) { // based onthe playe turns
                button.setText("X");
            } else {
                button.setText("O");
            }
            playerXTurn = !playerXTurn; // if X not wins then O is the winner, vice versa
            checkWinner(); // check winner
        } 
        return;
    }

    // check winners
    private void checkWinner() {
        //row
        for (int row = 0; row < 3; row++) {
            if(buttons[row][0].getText().equals(buttons[row][1].getText())
                && buttons[row][1].getText().equals(buttons[row][2].getText())
                && !buttons[row][0].getText().isEmpty()
            ) {
                // System.out.println("Winner");
                // we will have a winner
                String winner = buttons[row][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }
        //column
        for (int col = 0; col < 3; col++) {
            if(buttons[0][col].getText().equals(buttons[1][col].getText())
                && buttons[1][col].getText().equals(buttons[2][col].getText())
                && !buttons[0][col].getText().isEmpty()
            ) {
                // System.out.println("Winner");
                // we will have a winner
                String winner = buttons[0][col].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }
        
        //diagonal
        if(buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty()
            ) {
                // System.out.println("Winner");
                // we will have a winner
                String winner = buttons[0][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
            // diagonal 
            if(buttons[2][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[0][2].getText())
                && !buttons[2][0].getText().isEmpty()
            ) {
                // System.out.println("Winner");
                // we will have a winner
                String winner = buttons[2][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }

        // tie
        boolean tie = true;
        for (Button row[] : buttons) { // iterate thru all the X/O buttons
            for(Button button : row) {
                if(button.getText().isEmpty()) { // add X/O
                    tie = false; // not a tie
                    break;
                }
            }
        }
        if(tie) { // if tie reset the board
            showTieDialog(); // it shows an alert message that no one is the winner
            resetBoard(); // reset the board, if tie
        }
    }

    private void showWinnerDialog(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("winner"); // announce winner
        alert.setContentText("Congratulations " + winner + "! You won the game"); // announce winner
        alert.setHeaderText("");
        alert.showAndWait(); // display alert message, & wait until user response / close
    }

    // Same message dialog as winner dialog
    private void showTieDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setContentText("Game Over ! it's a tie");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    // winner score updating, 
    private void updateScore(String winner) {
         if(winner.equals("X")) { // if winner is X then incr the X++
            playerXScore++;
            playerXScoreLabel.setText("Player X : " + playerXScore); // update the score
         } 
         // if winner is O then incr the O++
         else { 
            playerOScore++;
            playerOScoreLabel.setText("Player O : " + playerOScore); // update the score
         }
    }

    // Board resetting if tie, if the winner
    private void resetBoard() {
        for (Button row[] : buttons) {
            for(Button button : row) {
                button.setText(""); // set as new board
            }
        }
    }

    @Override 
    // It extracts a warning from the compiler if the annotated method doesn't override anything
   // It means the child's class method is over-writing its base class method
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent()); // 3X3 GameBoard
        stage.setTitle("Tic Tac Toe"); // set title at the top of the box
        stage.setScene(scene);
        stage.show(); // show
    }
    
    public static void main(String[] args) {
        launch(); // run the program
    }
}
