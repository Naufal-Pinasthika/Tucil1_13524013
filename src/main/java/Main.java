import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Pilih file .txt: ");
        String filename = scanner.nextLine();


        Input fileInput = new Input();
        fileInput.processInput(filename);

        char[][] matrix = fileInput.inputToMatrix(fileInput.savedInputString);
        int size = fileInput.size;

        Algorithm queenBoard = new Algorithm(matrix, size);

        queenBoard.temp = new int[size][size];

        System.out.println("Memulai pencarian algoritma brute force\n");

        long startTime = System.currentTimeMillis();

        boolean success = queenBoard.bruteForce(matrix, size, queenBoard.queens);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;        

        System.out.println();
        System.out.println("Waktu pencarian: " + executionTime + " ms");
        System.out.print("Banyak kasus yang ditinjau: " + queenBoard.casesEvaluated + " kasus");

        if (success) {
            System.out.println("\nApakah Anda ingin menyimpan solusi? (Ya/Tidak):");
            String saveOption = scanner.nextLine();
    
            if (saveOption.equals("Ya")) {
                Save filSave = new Save();
                filSave.saveResult(matrix, size, filename);
            } else if (saveOption.equals("Tidak")) {
                System.out.println("Mengakhiri program");
            }
            scanner.close();
        } else {
            System.out.println("Tidak menemukan solusi dari permainan ini");
        }
    }
}