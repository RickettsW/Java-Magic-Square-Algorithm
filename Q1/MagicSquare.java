public class MagicSquare {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter an odd number bigger than 1 pretty please.");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error, this number is not odd and bigger than 1.");
            return;
        }

        if (n % 2 == 0 || n <= 1) {
            System.out.println("Please enter an odd number greater than 1.");
            return;
        }

        int[][] magicSquare = new int[n][n];

        int row = 0, col = n / 2; 

        for (int num = 1; num <= n * n; num++) {
            magicSquare[row][col] = num;

            int newRow = (row - 1 + n) % n; 
            int newCol = (col + 1) % n; 

            if (magicSquare[newRow][newCol] != 0) {
                row = (row + 1) % n; 
            } else {
                row = newRow;
                col = newCol;
            }
        }

        for (int[] rowArray : magicSquare) {
            for (int value : rowArray) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
    }
}
