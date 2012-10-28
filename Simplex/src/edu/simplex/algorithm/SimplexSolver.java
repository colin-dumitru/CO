package edu.simplex.algorithm;

import java.util.List;

/**
 * Catalin Dumitru
 * Universitatea Alexandru Ioan Cuza
 */
public class SimplexSolver {
    private List<Double[]> table;

    public SimplexSolver(List<Double[]> table) {
        this.table = table;
    }

    public int nextIteration() {
        if (table.isEmpty() || table.get(0).length == 0) {
            return -1;
        }
        Integer pivotColumn = getPivotColumn2();
        if (pivotColumn == -1) {
            return pivotColumn;
        }
        Integer pivotRow = getPivotRow(pivotColumn);
        Double pivotValue = table.get(pivotRow)[pivotColumn];

        for (int l = 0; l < table.get(0).length; l++) {
            table.get(pivotRow)[l] = table.get(pivotRow)[l] / pivotValue;
        }
        for (int k = 0; k < table.size(); k++) {
            if (k != pivotRow && table.get(k)[pivotColumn] != 0) {
                for (int l = 0; l < table.get(0).length; l++) {
                    if (l != pivotColumn) {
                        table.get(k)[l] = table.get(k)[l] - table.get(k)[pivotColumn] * table.get(pivotRow)[l];
                    }
                }
            }
        }
        for (int k = 0; k < table.size(); k++) {
            if (k != pivotRow && table.get(k)[pivotColumn] != 0) {
                table.get(k)[pivotColumn] = 0D;
            }
        }
        return pivotColumn;
    }

    private Integer getPivotColumn() {
        Double max = 0d;
        Integer index = 0;
        Integer maxIndex = -1;

        for (Double value : table.get(table.size() - 1)) {
            if (value < 0 && Math.abs(value) > Math.abs(max)) {
                max = value;
                maxIndex = index;
            }
            index++;
        }

        return maxIndex;
    }

    private Integer getPivotColumn2() {
        Integer index = 0;

        for (Double value : table.get(table.size() - 1)) {
            if (value < 0) {
                return index;
            }
            index++;
        }

        return -1;
    }

    private Integer getPivotRow(Integer pivotColumn) {
        Double min = Double.MAX_VALUE;
        Integer minIndex = -1;

        for (int index = 0; index < table.size() - 1; index++) {
            Double value = table.get(index)[pivotColumn];
            if (value > 0) {
                Double ratio = table.get(index)[table.get(index).length - 1] / value;
                if (ratio < min) {
                    min = ratio;
                    minIndex = index;
                }
            }
        }
        return minIndex;
    }
}
