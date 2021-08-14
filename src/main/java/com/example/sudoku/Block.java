package com.example.sudoku;

import java.util.HashSet;
import java.util.Set;

public class Block {

    int current;
    Set<Integer> possible;


    public Block(int current) {

        this.current = current;
        possible = new HashSet<>();

    }

    public Block() {

        current = 0;
        possible = new HashSet<>();
        for(int i = 1; i < 10; i++) {
            possible.add(i);
        }

    }


}