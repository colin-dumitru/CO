package edu.simplex.controller;

/**
 * Catalin Dumitru
 * Universitatea Alexandru Ioan Cuza
 */
public class SimplexRow {
    private Double[] rowValues;

    public SimplexRow(Double[] rowValues) {
        this.rowValues = rowValues;
    }

    public Double[] getRowValues() {
        return rowValues;
    }
}
