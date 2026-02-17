import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class boardGUI {

    private final Color[] colors = {
        Color.web("#E74C3C"), 
        Color.web("#8E44AD"), 
        Color.web("#3498DB"), 
        Color.web("#16A085"), 
        Color.web("#F39C12"), 
        Color.web("#D35400"), 
        Color.web("#C0392B"), 
        Color.web("#27AE60"), 
        Color.web("#2980B9"), 
        Color.web("#2C3E50"), 
        Color.web("#E67E22"), 
        Color.web("#1ABC9C"), 
        Color.web("#9B59B6"),
        Color.web("#2ECC71"), 
        Color.web("#34495E"), 
        Color.web("#7F8C8D"), 
        Color.web("#C2185B"),
        Color.web("#512E5F"), 
        Color.web("#154360"), 
        Color.web("#0E6251"),
        Color.web("#784212"), 
        Color.web("#7B241C"), 
        Color.web("#1B4F72"), 
        Color.web("#641E16"), 
        Color.web("#4A235A"), 
        Color.web("#0B5345")  
    };

    public int charToColor (char[][] matrix, int i, int j) {   

        char region = matrix[i][j];
        int colorIndex = Character.toUpperCase(region) - 'A';
        colorIndex = colorIndex % 26;
        return colorIndex;
                

    }

    public Canvas board (char[][] matrix, int[][] temp, int size) {
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
        return canvas;
    }
    
}
