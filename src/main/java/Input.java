import java.io.File;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Input {
    public String savedInputString = "";

    public char[][] inputMatrix;
    public int size;

    public void inputStringConstructor(String input) {
        savedInputString += input;
        savedInputString += "\n";
    }

    public void processInput(String fileName) {
        try {
            File inputObj = new File("data/" + fileName);
        
            Scanner read = new Scanner(inputObj);
    
            while (read.hasNextLine()) {
                String input = read.nextLine();
                
                // since the input is always n x n, take column value
                // to create a matrix n x n afterward;

                this.size = input.length();

                inputStringConstructor(input);


            }


            read.close();
    
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();            
        }
    }

    public void processFileFromGUI(File file) {
        try{
            Scanner read = new Scanner(file);

            this.savedInputString = "";
            
            if (read.hasNextLine()) {
                String firstLine = read.nextLine();

                this.size = firstLine.length();
                inputStringConstructor(firstLine);
            }

            while (read.hasNextLine()) {
                String input = read.nextLine();
                inputStringConstructor(input);
            }
            read.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public char[][] crateMatrix () {
        this.inputMatrix = new char[size][size];
        return this.inputMatrix;
    }

    public char[][] inputToMatrix (String input) {
        String[] temp = savedInputString.split("\n");

        this.inputMatrix = crateMatrix();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                inputMatrix[i][j] = temp[i].charAt(j);
            }
        }

        return inputMatrix;
    }

    public String inputValidation () {
        //check row & col
        String temp[] = savedInputString.split("\n");
        int row = temp.length;
        int col = temp[0].trim().length();

        if (row != col){
            return "Panjang dan lebar tidak simetris";
        }

        for (int i = 0; i < row; i++) {
            if (temp[i].trim().length() != col) {
                return "Panjang dan lebar tidak simetris";
            }
        }

        //check region

        char[][] tempMatrix = inputToMatrix(savedInputString);

        Set<Character> regions = new HashSet<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                regions.add(tempMatrix[i][j]);
            }
        }

        if (regions.size() != row || regions.size() != col){
            return "Daerah tidak simetris dengan dimensi papan";
        }

        return null;


    }

}