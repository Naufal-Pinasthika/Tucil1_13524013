public class Algorithm {
        public int queens;
        public char[][] matrix;
        public int[][] temp;
        public long casesEvaluated = 0;

        public Algorithm (char[][] matrix, int size) {
            this.queens = size;
            this.matrix = matrix;
            this.temp = new int[size][size];
        }

        // brute force by placing every queen horizontally
        // and increment to move down 1 queen at a time

        public void initializeTemp(int size) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    this.temp[i][j] = 0;
                }
            }
        }

        public void printProcessQueen(int size, char[][] matrix, int[][] temp) {
            // setQueen(temp, matrix, size);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (temp[i][j] == 1) {
                        System.out.print("# ");
                    } else {
                        System.out.print(matrix[i][j] + " ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
        public void printFinalQueen(int size, char[][] matrix, int[][] temp) {
            setQueen(temp, matrix, size);
            System.out.println("Final Result: \n");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }           
        }        

        // public boolean iterateQueen(int size, int[][] temp, char[][] matrix) {
        //     int visualCounter = 0;
        //     boolean flag = false;
        //     double totalDouble = Math.pow(size, size);
        //     long total = (long) totalDouble;

        //     this.casesEvaluated = 0;

        //     for (int i = 0; i < total; i++) {
        //         this.casesEvaluated++;

        //         initializeTemp(size);

        //         int digit = i;
        //         for (int j = 0; j < size; j++) {
        //             int column = digit % size;

        //             temp[j][column] = 1;

        //             digit /= size;
        //         }
        //         visualCounter++;
        //         if (visualCounter == 1000) {
        //             printProcessQueen(size, matrix, temp);
        //             visualCounter = 0;
        //         }

        //         flag = checkQueen(temp, matrix, size);
        //         if (flag) {
        //             printFinalQueen(size, matrix, temp);
        //             break;
                    
        //         }
        //     }
        //     if (flag == true) {
        //         return true;
        //     } else {
        //         return false;
        //     }
        // }

        public boolean processBruteForceQueen(int size, int[][] temp, char[][] matrix) {
            initializeTemp(size);
            this.casesEvaluated = 0;


            boolean flag = false;

            flag = bruteForceQueen(0, 0, size, size, temp);
            if (flag) {
                printFinalQueen(size, matrix, temp);
                return true;
                
            } else {
                return false;
            }

        }

        public boolean bruteForceQueen(int queen, int currIdx, int totalQueen,  int size, int[][] temp) {
            if (queen == totalQueen) {
                this.casesEvaluated++;
                if (checkQueen(this.temp,this.matrix, size)) {
                    return true;
                }
                return false;
            }

            int totalSize = size*size;

            for (int i = currIdx; i < totalSize; i++) {
                int row =  i / size;
                int col = i % size;

                this.temp[row][col] = 1;
                if (bruteForceQueen(queen+1, i+1, totalQueen, size, temp)){
                   return true;
                }

                this.temp[row][col] = 0;

            }
            
            return false;

        }


        public boolean checkQueen(int[][] temp, char[][] matrix, int size) {

            // edge case: n = 1 is always right for [0,0]
            // another thing to note: n == 2 && n == 3 is mathematically impossible
            if (size == 1) {
                return true; 
            }

            int counter = 0;

            // check horizontal
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++){
                    if (temp[i][j] == 1) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    return false;
                }
                counter = 0;
            }

            // check vertical
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++){
                    if (temp[j][i] == 1) {
                        counter++;
                    }
                }
                if (counter > 1) {
                    return false;
                }
                counter = 0;
            }

            // check surrounding
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (temp[i][j] == 1) {
                        // case: corner
                        if (i == 0 && j == 0) {
                            if (temp[i][j+1] == 1 || temp[i+1][j+1] == 1 || temp[i+1][j] == 1) {
                                return false;
                            }
                        } else if (i == 0 && j == size - 1) {
                            if (temp[i][j-1] == 1 || temp[i+1][j-1] == 1 || temp[i+1][j] == 1) {
                                return false;
                            }                        
                        } else if (i == size - 1 && j == size - 1) {
                            if (temp[i][j-1] == 1 || temp[i-1][j-1] == 1 || temp[i-1][j] == 1) {
                                return false;
                            }                          
                        } else if (i == size - 1 && j == 0) {
                            if (temp[i-1][j] == 1 || temp[i-1][j+1] == 1 || temp[i][j+1] == 1) {
                                return false;
                            }                           
                        }

                        // case: edge (without corners)
                        if (i == 0 && j > 0 && j < size-1) {
                            if (temp[i][j+1] == 1 || temp[i+1][j+1] == 1 || temp[i+1][j] == 1 || temp[i+1][j-1] == 1 || temp[i][j-1] == 1) {
                                return false;
                            }
                        } else if (i == size - 1 && j > 0 && j < size-1) {
                            if (temp[i][j-1] == 1 || temp[i-1][j-1] == 1 || temp[i-1][j] == 1 || temp[i-1][j+1] == 1 || temp[i][j+1] == 1) {
                                return false;
                            }
                        } else if (j == size - 1 && i > 0 && i < size-1) {
                            if (temp[i-1][j] == 1 || temp[i-1][j-1] == 1 || temp[i][j-1] == 1 || temp[i+1][j-1] == 1 || temp[i+1][j] == 1) {
                                return false;
                            }                        
                        } else if (j == 0 && i > 0 && i < size-1) {
                            if (temp[i-1][j] == 1 || temp[i-1][j+1] == 1 || temp[i][j+1] == 1 || temp[i+1][j+1] == 1 || temp[i+1][j] == 1) {
                                return false;
                            }                        
                        }
                        // case: any place (not in edge or corner)
                        if (i > 0 && i < size - 1 && j > 0 && j < size - 1) {

                            if (temp[i][j-1] == 1 || temp[i+1][j-1] == 1 || temp[i+1][j] == 1 || temp[i+1][j+1] == 1
                                    || temp[i][j+1] == 1 || temp [i-1][j+1] == 1 || temp[i-1][j] == 1 || temp[i-1][j-1] == 1) {
                                
                                return false;
                            }
                                    
                        }

                        

                    }
                }
            }

            // check block
            char[] arrCheck = new char[size];
            int arrCount = 0;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (temp[i][j] == 1) {
                        arrCheck[arrCount] = matrix[i][j];
                        arrCount++;
                    }
                }
            }

            for (int i = 0; i < arrCheck.length; i++) {
                for (int j = i + 1; j < arrCheck.length; j++) {
                    if (arrCheck[i] == arrCheck[j]) {
                        return false;
                    }
                }
            }

            

            return true;
        }   

        public void setQueen(int[][] temp, char[][] matrix, int size) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (temp[i][j] == 1) {
                        matrix[i][j] = '#';
                    }
                }
            }
        }
        
        public boolean bruteForce (char[][] matrix, int size, int queens) {
            // initialize queen to be put 
            boolean flag = processBruteForceQueen(size, temp, matrix);
            
            return flag;
        }
    }
