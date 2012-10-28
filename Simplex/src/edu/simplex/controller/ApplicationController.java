package edu.simplex.controller;

import edu.simplex.algorithm.SimplexSolver;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Catalin Dumitru
 * Universitatea Alexandru Ioan Cuza
 */
public class ApplicationController implements Initializable {
    @FXML
    private Button loadButton;
    @FXML
    private Button nextButton;
    @FXML
    private TableView simplexTableView;

    private List<Double[]> table;
    private List<TableColumn> tableColumns;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindControls();
    }

    private void bindControls() {
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadTable();
            }
        });
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextIteration();
            }
        });
    }

    private void nextIteration() {
        int pivotColumn = new SimplexSolver(table).nextIteration();
        highlightPivotColumn(pivotColumn);
        resetRows();
    }

    private void highlightPivotColumn(int pivotColumn) {
        for(int i = 0; i < tableColumns.size(); i++) {
            if(i == pivotColumn) {
                tableColumns.get(i).setStyle("-fx-background-color:green;");
            } else {
                tableColumns.get(i).setStyle("");
            }
        }
    }

    private void loadTable() {
        FileChooser fileChooser = new FileChooser();
        File chosenFile = fileChooser.showOpenDialog(null);

        if (chosenFile != null) {
            try {
                loadTableFromFile(chosenFile);
                bindTableData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindTableData() {
        resetColumns();
        resetRows();
    }

    private void resetRows() {
        List<SimplexRow> tableRows = createRows();
        simplexTableView.setItems(FXCollections.observableArrayList(tableRows));
    }

    private void resetColumns() {
        tableColumns = createColumns();
        simplexTableView.getColumns().addAll(tableColumns);
    }

    private List<SimplexRow> createRows() {
        List<SimplexRow> rows = new ArrayList<>();
        for (Double[] values : table) {
            rows.add(new SimplexRow(values));
        }
        return rows;
    }

    private List<TableColumn> createColumns() {
        if(table.isEmpty()) {
            return new ArrayList<>();
        }
        final List<TableColumn> columns = new ArrayList<>();

        for(int i = 0; i < table.get(0).length; i++) {
            TableColumn<SimplexRow, Double> tableColumn = new TableColumn<>(String.format("X%s", i + 1));
            tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SimplexRow, Double>, ObservableValue<Double>>() {
                @Override
                public ObservableValue<Double> call(TableColumn.CellDataFeatures<SimplexRow, Double> data) {
                    Integer tableColumnsIndex = columns.indexOf(data.getTableColumn());
                    return new ReadOnlyObjectWrapper<>(data.getValue().getRowValues()[tableColumnsIndex]);
                }
            });
            columns.add(tableColumn);
        }
        return columns;
    }

    private void loadTableFromFile(File chosenFile) throws FileNotFoundException {
        try (FileReader fileReader = new FileReader(chosenFile);
             BufferedReader reader = new BufferedReader(fileReader)) {
            processFile(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile(BufferedReader reader) throws IOException {
        String line = null;
        initTable();

        while ((line = reader.readLine()) != null) {
            String[] params = line.split("\\s");
            addRow(params);
        }
    }

    private void addRow(String[] params) {
        Double[] values = new Double[params.length];
        for(int i = 0; i < params.length; i++) {
            values[i] = Double.parseDouble(params[i]);
        }
        table.add(values);
    }

    private void initTable() {
        table = new ArrayList<>();
    }
}
