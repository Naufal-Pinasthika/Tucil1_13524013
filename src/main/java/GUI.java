import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        Label mainLabel = new Label("Queens LinkedIn Solver");
        mainLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
    

        Label sizeLabel = new Label("Upload the queens board in .txt format to begin");
        sizeLabel.setStyle("-fx-font-size: 24px;");


        FileChooser scanTxtFile = new FileChooser();
        scanTxtFile.setTitle("Open .txt file");
        scanTxtFile.setInitialDirectory(new File(System.getProperty("user.home"))); 
        scanTxtFile.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );


        VBox resultContainer = new VBox(); 
        resultContainer.setAlignment(Pos.CENTER);
        resultContainer.setPadding(new Insets(20));

        
        Button uploadButton = new Button("Choose File (.txt)");
        uploadButton.setPrefSize(150, 30);
        uploadButton.setOnAction(e -> {
            File selectedFile = scanTxtFile.showOpenDialog(stage);

            if (selectedFile != null) {

                Label resultLabel = new Label("Result: ");
                resultLabel.setStyle("-fx-font-size: 24px;");


                resultContainer.getChildren().clear();


                Input fileInput = new Input();
                fileInput.processFileFromGUI(selectedFile);
    
    
                char[][] matrix = fileInput.inputToMatrix(fileInput.savedInputString);
                int size = fileInput.size;
    
                
                char[][] bruteForceMatrix = new char[size][size];
                for(int i = 0; i< size; i++) {
                    for( int j = 0; j < size; j++) {
                        bruteForceMatrix[i][j] = matrix[i][j];
                    }
                }
    
    
                Algorithm queenBoard = new Algorithm(bruteForceMatrix, size);
                queenBoard.temp = new int[size][size];
    
                long startTime = System.currentTimeMillis();
                boolean success = queenBoard.bruteForce(bruteForceMatrix, size, queenBoard.queens);
                long endTime = System.currentTimeMillis();
    
                long executionTime = endTime - startTime; 
    
                Label statusLabel = new Label();
                Label totalExecutionLabel = new Label();
               
    
                if (success) {
                    statusLabel.setText("Waktu pencarian: " + executionTime + " ms");
                    totalExecutionLabel.setText("Banyak kasus yang ditinjau: " + queenBoard.casesEvaluated + " kasus");
    
                    statusLabel.setStyle("-fx-font-size: 16px;");
                    totalExecutionLabel.setStyle("-fx-font-size: 16px;");
    
                    boardGUI visualizer = new boardGUI();
    
                    Canvas canvas = visualizer.board(matrix, queenBoard.temp, size);
                    
                    HBox saveButtonContainer = new HBox(20);
                    saveButtonContainer.setAlignment(Pos.CENTER);


                    // save as .txt
                    Button saveAsTxt = new Button("Save as .TXT");
                    saveAsTxt.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-weight: bold;");
                    saveAsTxt.setOnAction(ev -> {
                        Save txtSaver = new Save();
                        txtSaver.saveResult(bruteForceMatrix, size, selectedFile.getName());
                        saveAsTxt.setText(".txt saved to test/");
                        saveAsTxt.setDisable(true);
                    });

                    // save as image
                    Button saveAsImage = new Button("Save as Image");
                    saveAsImage.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-font-weight: bold;");
                    saveAsImage.setOnAction(ev -> {
                        Save imgSaver = new Save();
                        imgSaver.saveToImage(matrix, queenBoard.temp, size, selectedFile.getName());
                        saveAsImage.setText("image saved to test/");
                        saveAsImage.setDisable(true);
                    });

                    saveButtonContainer.getChildren().addAll(saveAsTxt, saveAsImage);

                    VBox.setMargin(saveButtonContainer, new Insets(20, 0, 0, 0));

                    resultContainer.getChildren().addAll(resultLabel, canvas, saveButtonContainer, statusLabel, totalExecutionLabel);
    
    
    
                } else {
    
                    statusLabel.setText("Tidak menemukan sebuah solusi");
                    resultContainer.getChildren().addAll(resultLabel, statusLabel);
                }            

            }
        
        });


        VBox topContainer = new VBox(20);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(50, 0,0, 0));


        topContainer.getChildren().addAll(mainLabel, sizeLabel, uploadButton);

        BorderPane root = new BorderPane();

        root.setTop(topContainer);
        root.setCenter(resultContainer);

        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}