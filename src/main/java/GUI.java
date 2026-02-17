import java.io.File;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

    private Stage primaryStage;
    private VBox resultContainer;
    private Label sizeLabel;
    private FileChooser scanTxtFile;
    private boardGUI visualizerHelper = new boardGUI(); 

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        Label mainLabel = new Label("Queens LinkedIn Solver");
        mainLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        sizeLabel = new Label("Upload the queens board in .txt format to begin\n                      Choose input method:");
        sizeLabel.setStyle("-fx-font-size: 24px;");

        scanTxtFile = new FileChooser();
        scanTxtFile.setTitle("Open .txt file");
        scanTxtFile.setInitialDirectory(new File(System.getProperty("user.home")));
        scanTxtFile.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        resultContainer = new VBox();
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.setPadding(new Insets(20));

        // upload .txt as input
        Button uploadButton = new Button("Upload File (.txt)");
        uploadButton.setPrefSize(150, 40);

        //manual input
        Button manualButton = new Button("Manual Input");
        manualButton.setPrefSize(150, 40);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(uploadButton, manualButton);


        // file .txt input
        uploadButton.setOnAction(e -> handleFileUpload());

        // manual input
        manualButton.setOnAction(e -> showSizeSelector());

        VBox topContainer = new VBox(20);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(50, 0, 0, 0));
        topContainer.getChildren().addAll(mainLabel, sizeLabel, buttonBox);

        BorderPane root = new BorderPane();
        root.setTop(topContainer);
        root.setCenter(resultContainer);

        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stage.setFullScreen(true);
            }
        });

        stage.show();
    }


    private void handleFileUpload() {
        boolean isFullScreen = primaryStage.isFullScreen();
        File selectedFile = scanTxtFile.showOpenDialog(primaryStage);

        if (isFullScreen) {
            Platform.runLater(() -> primaryStage.setFullScreen(true));
        }

        if (selectedFile != null) {
            resultContainer.getChildren().clear();

            Input fileInput = new Input();
            fileInput.processFileFromGUI(selectedFile);

            String validation = fileInput.inputValidation();
            if (validation != null){
                Label errorLabel = new Label(validation);
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");
                resultContainer.getChildren().add(errorLabel);
                return;
            }

            char[][] matrix = fileInput.inputToMatrix(fileInput.savedInputString);
            startSolver(matrix, fileInput.size, selectedFile.getName());
        }
    }


    private void showSizeSelector() {
        resultContainer.getChildren().clear();
        sizeLabel.setText("Select Board Size:");

        // set max to 26 yo match the total of alphabet
        Spinner<Integer> sizeSpinner = new Spinner<>(1, 26, 5);
        sizeSpinner.setStyle("-fx-font-size: 18px;");
        sizeSpinner.setPrefWidth(100);

        Button nextButton = new Button("Create Board");
        
        nextButton.setOnAction(e -> {
            int size = sizeSpinner.getValue();
            showManualGrid(size);
        });

        VBox selectorBox = new VBox(20, sizeSpinner, nextButton);
        selectorBox.setAlignment(Pos.CENTER);
        resultContainer.getChildren().add(selectorBox);
    }

    private void showManualGrid(int size) {
        resultContainer.getChildren().clear();
        sizeLabel.setText("Enter Regions (A-Z):");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        TextField[][] inputs = new TextField[size][size];

        // create size * size grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TextField tf = new TextField();
                tf.setPrefSize(40, 40);
                tf.setAlignment(Pos.CENTER);
                tf.setStyle("-fx-font-weight: bold; -fx-border-color: gray;");
                
                // force the input to capital letter & 1 char so that its consistent with .txt input
                tf.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.length() > 1){
                        return null;
                    }
                    return change;
                }));
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal.isEmpty()) {
                        String upper = newVal.toUpperCase();
                        tf.setText(upper);
                    } else {
                        tf.setStyle("-fx-control-inner-background: white; -fx-border-color: gray;");
                    }
                });



                inputs[i][j] = tf;
                grid.add(tf, j, i);
            }
        }

        Button solveButton = new Button("Solve Puzzle");
        solveButton.setPadding(new Insets(10, 30, 10, 30));

        Label errorMsg = new Label("");
        errorMsg.setStyle("-fx-text-fill: red;");

        solveButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            try {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        String txt = inputs[i][j].getText();
                        if (txt == null || txt.isEmpty() || !txt.matches("[A-Z]")) {
                            throw new Exception("Please fill all cells with A-Z");
                        }
                        sb.append(txt);
                    }
                    if (i < size - 1) sb.append("\n");
                }

                Input manualInput = new Input();
                manualInput.size = size;
                manualInput.savedInputString = sb.toString();
                
                String valid = manualInput.inputValidation();
                if (valid != null) {
                    errorMsg.setText(valid);
                    return;
                }

                char[][] matrix = manualInput.inputToMatrix(manualInput.savedInputString);
                startSolver(matrix, size, "ManualInput.txt");

            } catch (Exception ex) {
                errorMsg.setText("Error: " + ex.getMessage());
            }
        });

        VBox container = new VBox(20, grid, errorMsg, solveButton);
        container.setAlignment(Pos.CENTER);
        resultContainer.getChildren().add(container);
    }

    private void startSolver(char[][] matrix, int size, String fileName) {
        resultContainer.getChildren().clear();
        sizeLabel.setText("");

        // Prepare Brute Force Matrix
        char[][] bruteForceMatrix = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, bruteForceMatrix[i], 0, size);
        }

        Label resultLabel = new Label("Result: ");
        resultLabel.setStyle("-fx-font-size: 24px;");
        Label statusLabel = new Label("Searching...");
        Label totalExecutionLabel = new Label("");

        Canvas initialCanvas = visualizerHelper.board(matrix, new int[size][size], size);

        VBox.setMargin(resultLabel, new Insets(0, 0, 20, 0));


        VBox.setMargin(statusLabel, new Insets(20, 0, 5, 0)); 

        VBox.setMargin(totalExecutionLabel, new Insets(0, 0, 20, 0));

        resultContainer.getChildren().addAll(resultLabel, initialCanvas, statusLabel, totalExecutionLabel);

        Algorithm queenBoard = new Algorithm(bruteForceMatrix, size);

        Timeline timelineVisualizer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            if (queenBoard.isRunning) {
                if(resultContainer.getChildren().size() > 1) {
                    resultContainer.getChildren().remove(1);
                    Canvas snapshot = visualizerHelper.board(matrix, queenBoard.temp, size);
                    resultContainer.getChildren().add(1, snapshot);
                }
            }
        }));
        timelineVisualizer.setCycleCount(Timeline.INDEFINITE);
        timelineVisualizer.play();

        Thread workerThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            boolean success = queenBoard.bruteForce(bruteForceMatrix, size, size);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            Platform.runLater(() -> {
                timelineVisualizer.stop();
                
                resultContainer.getChildren().remove(1);
                Canvas finalCanvas = visualizerHelper.board(matrix, queenBoard.temp, size);
                resultContainer.getChildren().add(1, finalCanvas);

                if (success) {
                    resultLabel.setText("Result: Solved");
                    statusLabel.setText("Waktu pencarian: " + executionTime + " ms");
                    totalExecutionLabel.setText("Banyak kasus yang ditinjau: " + queenBoard.casesEvaluated + " kasus");
                    addSaveButtons(matrix, queenBoard.temp, bruteForceMatrix, size, fileName);
                } else {
                    resultLabel.setText("Result: No Solution Found");
                    statusLabel.setText("Waktu pencarian: " + executionTime + " ms");
                    totalExecutionLabel.setText("Banyak kasus yang ditinjau: " + queenBoard.casesEvaluated + " kasus");
                }
            });
        });

        workerThread.setDaemon(true);
        workerThread.start();
    }

    private void addSaveButtons(char[][] matrix, int[][] temp, char[][] resultMatrix, int size, String fileName) {
        HBox saveButtonContainer = new HBox(20);
        saveButtonContainer.setAlignment(Pos.CENTER);

        VBox.setMargin(saveButtonContainer, new Insets(10, 0, 0, 0));
        Button saveAsTxt = new Button("Save as .TXT");
        saveAsTxt.setOnAction(ev -> {
            Save txtSaver = new Save();
            txtSaver.saveResult(resultMatrix, size, fileName);
            saveAsTxt.setText("Saved");
            saveAsTxt.setDisable(true);
        });

        Button saveAsImage = new Button("Save as Image");
        saveAsImage.setOnAction(ev -> {
            Save imgSaver = new Save();
            imgSaver.saveToImage(matrix, temp, size, fileName);
            saveAsImage.setText("Saved");
            saveAsImage.setDisable(true);
        });

        saveButtonContainer.getChildren().addAll(saveAsTxt, saveAsImage);
        resultContainer.getChildren().add(saveButtonContainer);
    }

    public static void main(String[] args) {
        launch();
    }
}