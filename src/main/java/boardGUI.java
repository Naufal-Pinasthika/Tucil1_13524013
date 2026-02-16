import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class boardGUI {

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
