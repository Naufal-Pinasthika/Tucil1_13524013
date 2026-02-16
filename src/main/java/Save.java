import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.embed.swing.SwingFXUtils;

public class Save {
        private final Color[] colors = {
        Color.web("#FFCC80"), 
        Color.web("#E6EE9C"), 
        Color.web("#ADD8E6"), 
        Color.web("#CE93D8"), 
        Color.web("#FFB7B2"), 
        Color.web("#F0E68C"), 
        Color.web("#B5EAD7"), 
        Color.web("#C7CEEA"), 
        Color.web("#FF9AA2"), 
        Color.web("#FFE082"), 
        Color.web("#A5D6A7"), 
        Color.web("#90CAF9"), 
        Color.web("#D8BFD8"), 
        Color.web("#F8BBD0"),         
        Color.web("#FF6961"),
        Color.web("#FFAB91"), 
        Color.web("#FFF59D"), 
        Color.web("#C5E1A5"), 
        Color.web("#81D4FA"), 
        Color.web("#B39DDB"), 
        Color.web("#F48FB1"), 
        Color.web("#FFDAC1"), 
        Color.web("#E2F0CB"), 
        Color.web("#B3E5FC"), 
        Color.web("#E1BEE7"), 
        Color.web("#80CBC4")  
    };

    public void saveResult(char[][] matrix, int size, String filename) {

        // remove .txt extension (will be added again)
        int trimmedFileNameIndex = filename.lastIndexOf(".");
        String trimmedFileName = filename.substring(0, trimmedFileNameIndex);


        String resultFileName = "test/" + trimmedFileName + "_solved.txt";

        try{
            FileWriter fileWrite = new FileWriter(resultFileName);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    fileWrite.write(matrix[i][j] + " ");
                }
                fileWrite.write("\n"); 
            }                  
            fileWrite.close();      

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int charToColor (char[][] matrix, int i, int j) {   

        char region = matrix[i][j];
        int colorIndex = Character.toUpperCase(region) - 'A';
        colorIndex = colorIndex % 26;
        return colorIndex;
                

    }

    public void saveToImage(char[][] matrix, int[][] temp, int size, String fileName) {
        int cellSize = 100;
        int cellWidth = size * cellSize;
        int cellHeight = size * cellSize;

        Canvas canvas = new Canvas(cellWidth, cellHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = j * cellSize;
                double y = i * cellSize;
                int colorIndex = charToColor(matrix, i, j);


                // color region
                gc.setFill(colors[colorIndex]);
                gc.fillRect(x, y, cellSize, cellSize);

                //border
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                gc.strokeRect(x, y, cellSize, cellSize);


                // queen
                if (temp[i][j] == 1) {
                    gc.setFill(Color.GOLD);
                    gc.setFont(Font.font("Segoe UI Emoji", FontWeight.BOLD, cellSize * 0.6));
                    gc.setTextAlign(TextAlignment.CENTER);
                    
                    gc.fillText("♛", x + (cellSize / 2.0), y + (cellSize / 1.4));
                    
                    gc.setEffect(null);
                }

            }
        }
        try {
            WritableImage writableImage = new WritableImage(cellWidth, cellHeight);
            canvas.snapshot(null, writableImage);

            int trimmedFileNameIndex = fileName.lastIndexOf(".");
            String trimmedFileName = fileName.substring(0, trimmedFileNameIndex);
            
            File outputFile = new File("test/" + trimmedFileName + "_solved.png");
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", outputFile);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
}
