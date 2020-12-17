package com.example.com.Objects;

import java.util.Comparator;

public class ComparatorScore implements Comparator<Winner> {
    @Override
    public int compare(Winner a, Winner b) {
        return b.getScore() - a.getScore();
    }
}
