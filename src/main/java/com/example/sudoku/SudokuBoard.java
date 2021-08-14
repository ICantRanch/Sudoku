package com.example.sudoku;

public class SudokuBoard {

    Block[][] sBoard;

    public SudokuBoard(int[][] startingBoard){

        sBoard = new Block[startingBoard.length][startingBoard[0].length];


        for (int i = 0; i < startingBoard.length; i++) {
            for (int j = 0; j < startingBoard[0].length; j++) {

                if(startingBoard[i][j] != 0) {
                    sBoard[i][j] = new Block(startingBoard[i][j]);
                }else {
                    sBoard[i][j] = new Block();
                }
            }
        }
    }

    public int getCurrent(int i, int j){
        return sBoard[i][j].current;
    }

    public Block getBlock(int i, int j){
        return sBoard[i][j];
    }



    public void print() {

        String out = "";

        for (int i = 0; i < sBoard.length; i++) {
            for (int j = 0; j < sBoard[i].length; j++) {
                if(sBoard[i][j].current != 0) {
                    out += sBoard[i][j].current;
                }else {
                    out += "_";
                }
            }
            System.out.println(out);
            out = "";
        }
    }
}
