package edu.simplex.algorithm;

import java.util.List;

/**
 * Catalin Dumitru
 * Universitatea Alexandru Ioan Cuza
 */
public class DualSimplexSolver {
    private List<Double[]> table;

    public DualSimplexSolver(List<Double[]> table) {
        this.table = table;
    }

    public int nextIteration() {
        if (table.isEmpty() || table.get(0).length == 0) {
            return -1;
        }
        Integer pivotRow = getPivotRow();
        if (pivotRow == -1) {
            return pivotRow;
        }
        Integer pivotColumn = getPivotColumn(pivotRow);
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

    private Integer getPivotRow() {
        Double max = 0d;
        Integer maxIndex = -1;

        for (Integer rowIndex = 0; rowIndex < table.size() - 1; rowIndex++) {
            Double[] row = table.get(rowIndex);
            Double lastColumnValue = row[row.length - 1];

            if (lastColumnValue < 0 && Math.abs(lastColumnValue) > Math.abs(max)) {
                max = lastColumnValue;
                maxIndex = rowIndex;
            }
        }

        return maxIndex;
    }

    private Integer getPivotRow2() {
        Integer index = 0;

        for (Double value : table.get(table.size() - 1)) {
            if (value < 0) {
                return index;
            }
            index++;
        }

        return -1;
    }

    private Integer getPivotColumn(Integer pivotRow) {
        Double min = Double.MAX_VALUE;
        Integer minIndex = -1;

        for (int columnIndex = 0; columnIndex < table.iterator().next().length - 1; columnIndex++) {
            Double value = table.get(pivotRow)[columnIndex];
            if (value < 0) {
                Double ratio = table.get(table.size() - 1)[columnIndex] / Math.abs(value);
                if (ratio < min) {
                    min = ratio;
                    minIndex = columnIndex;
                }
            }
        }
        return minIndex;
    }

    private Integer getPivotColumn2(Integer pivotRow) {
        for (int columnIndex = 0; columnIndex < table.iterator().next().length; columnIndex++) {
            Double value = table.get(pivotRow)[columnIndex];
            if (value < 0) {
                return columnIndex;
            }
        }
        return -1;
    }
}
