package com.example.sudoku;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Set;

public class SudokuMain extends Application {

    GridPane grid;
    Block[][] board = makeBoard(new int[][]{
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

    Node[][] nodes = new Node[board.length][board.length];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);

        grid = new GridPane();
        grid.setGridLinesVisible(true);


        //Initial Setup of Board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int temp = board[i][j].current;
                Label temp2 = new Label();
                if (temp != 0) {
                    temp2.setText(String.valueOf(temp));
                    temp2.setMinHeight(30);
                    temp2.setMinWidth(30);
                    temp2.setFont(new Font(20));
                    temp2.setAlignment(Pos.CENTER);
                    grid.add(temp2, j, i);
                    nodes[i][j] = temp2;
                }else{
                 //Add gridpane
                  GridPane poss = new GridPane();
                  Set<Integer> possibles = board[i][j].possible;
                  int len = (int)Math.sqrt(board.length);
                    for (int k = 0; k < len; k++) {
                        for (int l = 0; l < len; l++) {
                            if(possibles.contains((k*len + l)+1)){
                                poss.add(new Label(String.valueOf(k*len+l+1)),l,k);
                            }
                        }
                    }
                    poss.setAlignment(Pos.CENTER);
                    grid.add(poss,j,i);
                    nodes[i][j] = poss;
                }
            }
        }
        root.setCenter(grid);
        grid.setAlignment(Pos.CENTER);

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();


        new Thread(){

            public void run(){

                for (int i = 0; i < board.length ; i++) {
                    for (int j = 0; j < board.length; j++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        int finalI = i;
                        int finalJ = j;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                solve(finalI, finalJ);
                            }
                        });


                    }
                }
            }

        }.start();

    }

    public Block[][] makeBoard(int[][] starter){

        Block[][] sBoard = new Block[starter.length][starter[0].length];

        for (int i = 0; i < starter.length; i++) {
            for (int j = 0; j < starter[0].length; j++) {

                if(starter[i][j] != 0) {
                    sBoard[i][j] = new Block(starter[i][j]);
                }else {
                    sBoard[i][j] = new Block();
                }
            }
        }
        return sBoard;
    }

    public void solve(int i, int j){

        if(board[i][j].current == 0){return;}
        int current = board[i][j].current;

        for (int k = 0; k < board.length; k++) {
            removePossibles(i,k,current);
        }
        for (int k = 0; k < board.length; k++) {
            removePossibles(k,j,current);
        }
        int row = i/3;
        int column = j/3;

        for (int k = row*3; k < (row*3)+3; k++) {
            for (int l = column*3; l < (column*3)+3; l++) {
                removePossibles(k,l,current);
            }
        }
    }

    public void update(){

        for (int i = 0; i < nodes.length ; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                update(i,j);
            }
        }
    }
    public void update(int i, int j){

        if(nodes[i][j] instanceof Label){
            if(Integer.parseInt(((Label) nodes[i][j]).getText()) != board[i][j].current){
                ((Label) nodes[i][j]).setText(String.valueOf(board[i][j].current));
            }

        }else{
            if(nodes[i][j] instanceof GridPane){
                if(board[i][j].current == 0){

                    ObservableList<Node> possNodes = ((GridPane) nodes[i][j]).getChildren();
                    GridPane poss = (GridPane)nodes[i][j];

                    for (Node node: possNodes) {
                        int index = poss.getRowIndex(node)*3 + poss.getColumnIndex(node);
                        ((Label)node).setText("");

                        if(board[i][j].possible.contains(index+1)){
                            ((Label)node).setText(String.valueOf(index+1));
                        }
                    }

                }else{
                    //Check if there now is a current number, change to label if it is (try a method)
                    Label temp3 = new Label();
                    temp3.setText(String.valueOf(board[i][j].current));
                    temp3.setMinHeight(30);
                    temp3.setMinWidth(30);
                    temp3.setFont(new Font(20));
                    temp3.setTextFill(Color.LIMEGREEN);
                    temp3.setAlignment(Pos.CENTER);
                    replaceNode(i,j,temp3);
                }
            }
        }
    }

    public void removePossibles(int i, int j, int current){

        board[i][j].possible.remove(current);

        if(board[i][j].possible.size() == 1){
            board[i][j].current = board[i][j].possible.iterator().next();
            System.out.println("Solved: " + board[i][j].current);
            update(i,j);
            solve(i,j);
        }else{
            update(i,j);
        }
    }

    public void replaceNode(int i, int j , Node node){

        grid.getChildren().remove(nodes[i][j]);
        grid.add(node,j,i);

    }
}
