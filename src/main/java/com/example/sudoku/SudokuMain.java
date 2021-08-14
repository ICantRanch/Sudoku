package com.example.sudoku;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Set;

public class SudokuMain extends Application {

    SudokuBoard board = new SudokuBoard(new int[][]{
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    });


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        for (int i = 0; i < board.sBoard.length; i++) {
            for (int j = 0; j < board.sBoard[0].length; j++) {
                int temp = board.getCurrent(i, j);
                Label temp2 = new Label();
                if (temp != 0) {
                    temp2.setText(String.valueOf(temp));
                    temp2.setMinHeight(30);
                    temp2.setMinWidth(30);
                    temp2.setFont(new Font(20));
                    temp2.setAlignment(Pos.CENTER);
                    grid.add(temp2, i, j);
                }else{
                 //Add gridpane
                  GridPane poss = new GridPane();
                  Set<Integer> possibles = board.getBlock(i,j).possible;
                  int len = (int)Math.sqrt(board.sBoard.length);
                    for (int k = 0; k < len; k++) {
                        for (int l = 0; l < len; l++) {
                            if(possibles.contains((int)(k*len + l)+1)){
                                poss.add(new Label(String.valueOf(k*len+l+1)),l,k);
                            }
                        }
                    }
                    poss.setAlignment(Pos.CENTER);
                    grid.add(poss,i,j);
                }
            }
        }

        root.setCenter(grid);
        grid.setAlignment(Pos.CENTER);

        //stage.setMinHeight(root.getHeight());
        //.setMinWidth(root.getWidth());
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }
}
