package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import main.domain.Report;
import main.domain.ReportItem;
import main.domain.Word;
import main.service.FileService;
import main.service.ImportService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private ListView<Word> listView;

    private FileService<String, Word> fileService;

    @FXML
    void initialize() {
        try {
            fileService = new FileService<>("src/test.txt", 100, 900);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBinarySearchButtonClicked(ActionEvent event) throws IOException {
        Dialog<String> addWordDialog = new Dialog<>();
        addWordDialog.setTitle("Binary search");
        addWordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField wordTextField = new TextField();
        TextField resultField = new TextField();
        TextField blockTransferCount = new TextField();
        TextField blocksCount = new TextField();
        blockTransferCount.setDisable(true);
        resultField.setDisable(true);
        blocksCount.setDisable(true);
        blocksCount.setText(String.valueOf(fileService.getActualBlocksCount()));
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            int index = -1;
            try {
                index = fileService.blockBinarySearch(wordTextField.getText());
                blockTransferCount.setText(fileService.getBlockTransferCount().toString());
                fileService.setBlockTransferCount(0);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            resultField.setText(String.valueOf(index != -1));
        });

        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTextField, 1, 0);
        grid.add(new Label("Result:"), 0, 1);
        grid.add(resultField, 1, 1);
        grid.add(new Label("Block transfers count:"), 0, 2);
        grid.add(blockTransferCount, 1, 2);
        grid.add(new Label("Blocks count:"), 0, 3);
        grid.add(blocksCount, 1, 3);
        grid.add(searchButton, 0, 4);

        addWordDialog.getDialogPane().setContent(grid);
        addWordDialog.showAndWait();
    }

    @FXML
    void onImportButtonClicked(ActionEvent event) {
        ImportService.loadDictionary(fileService.getBlockFile(), 7);
        listView.getItems().addAll(ImportService.getWords());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Import was successfully completed!!!");
        alert.showAndWait();
    }

    @FXML
    void onCompareButtonClicked(ActionEvent event) {
        Dialog<String> addWordDialog = new Dialog<>();
        addWordDialog.setTitle("Binary search");
        addWordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea keysTextArea = new TextArea();
        TextArea b = new TextArea();
        TextArea i = new TextArea();

        Report binarySearchReport = new Report();
        Report interpolationSearchReport = new Report();

        List<Word> words = ImportService.getWords();
        List<String> keys = words.stream().map(Word::getKey).collect(Collectors.toList());
        keys.forEach(it -> {
            keysTextArea.appendText(it + "\n");
            try {
                fileService.blockBinarySearch(it);
                binarySearchReport.addItem(new ReportItem(it, fileService.getBlockTransferCount()));
                binarySearchReport.addCount(fileService.getBlockTransferCount());
                fileService.setBlockTransferCount(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileService.blockInterpolationSearch(it);
                interpolationSearchReport.addItem(new ReportItem(it, fileService.getBlockTransferCount()));
                interpolationSearchReport.addCount(fileService.getBlockTransferCount());
                fileService.setBlockTransferCount(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        keysTextArea.appendText("Keys count: " + keys.size());
        b.setText(binarySearchReport.toString());
        i.setText(interpolationSearchReport.toString());

        grid.add(new Label("Keys:"), 0, 0);
        grid.add(keysTextArea, 1, 0);
        grid.add(new Label("Binary search result:"), 0, 1);
        grid.add(b, 1, 1);
        grid.add(new Label("Interpolation search result:"), 0, 2);
        grid.add(i, 1, 2);
        addWordDialog.getDialogPane().setContent(grid);
        addWordDialog.showAndWait();
    }

    @FXML
    void onInterpolationSearchButtonClicked(ActionEvent event) throws IOException {
        Dialog<String> addWordDialog = new Dialog<>();
        addWordDialog.setTitle("Interpolation search");
        addWordDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField wordTextField = new TextField();
        TextField resultField = new TextField();
        TextField blockTransferCount = new TextField();
        TextField blocksCount = new TextField();
        blockTransferCount.setDisable(true);
        resultField.setDisable(true);
        blocksCount.setDisable(true);
        blocksCount.setText(String.valueOf(fileService.getActualBlocksCount()));
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            int index = -1;
            try {
                index = fileService.blockInterpolationSearch(wordTextField.getText());
                blockTransferCount.setText(fileService.getBlockTransferCount().toString());
                fileService.setBlockTransferCount(0);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            resultField.setText(String.valueOf(index != -1));
        });

        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTextField, 1, 0);
        grid.add(new Label("Result:"), 0, 1);
        grid.add(resultField, 1, 1);
        grid.add(new Label("Block transfers count:"), 0, 2);
        grid.add(blockTransferCount, 1, 2);
        grid.add(new Label("Blocks count:"), 0, 3);
        grid.add(blocksCount, 1, 3);
        grid.add(searchButton, 0, 4);

        addWordDialog.getDialogPane().setContent(grid);
        addWordDialog.showAndWait();
    }

}
