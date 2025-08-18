import java.util.Random;
import java.util.Scanner;

public class MagicSquareGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (args.length != 1) {
            System.out.println("Please enter an odd number (bigger than 1).");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(args[0]);
            if (n % 2 == 0 || n < 3) {
                System.out.println("Please enter an odd number that is bigger than 1.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Input invalid. Number has to be odd and above 1.");
            return;
        }

        int[][] magicSquare = generateMagicSquare(n);
        shuffleSquare(magicSquare, n);
        int moveCount = 0;

        while (true) {
            displaySquare(magicSquare, n);
            System.out.println("Enter your move in format row, collumn direction e.g., 1 1 D): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (!input.matches("^\\d+\\s+\\d+\\s*[udlrUDLR]$")) {
                System.out.println("Invalid format! Enter row, column, and direction (U, D, L, R). Example: '2 2 D'");
                continue;
            }

            String[] parts = input.split("\\s+");
            int row = Integer.parseInt(parts[0]) - 1;
            int col = Integer.parseInt(parts[1]) - 1;
            char direction = parts[2].charAt(0);

            if (makeMove(magicSquare, row, col, direction, n)) {
                moveCount++;
            } else {
                System.out.println("Invalid move! Please try again!");
            }

            if (isMagicSquare(magicSquare, n)) {
                displaySquare(magicSquare, n);
                System.out.println("You WON!!! Your ammount of moves was " + moveCount + " moves! ");
                break;
            }
        }
        scanner.close();
    }

    private static int[][] generateMagicSquare(int n) {
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
        return magicSquare;
    }

    private static void shuffleSquare(int[][] square, int n) {
        Random random = new Random();
        for (int i = 0; i < n * 2; i++) {
            int row = random.nextInt(n);
            int col = random.nextInt(n);
            char[] directions = {'u', 'd', 'l', 'r'};
            char direction = directions[random.nextInt(4)];
            makeMove(square, row, col, direction, n);
        }
    }

    private static boolean makeMove(int[][] square, int row, int col, char direction, int n) {
        int newRow = row, newCol = col;

        switch (direction) {
            case 'u': newRow = row - 1; break;
            case 'd': newRow = row + 1; break;
            case 'l': newCol = col - 1; break;
            case 'r': newCol = col + 1; break;
            default: return false;
        }

        if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
            int temp = square[row][col];
            square[row][col] = square[newRow][newCol];
            square[newRow][newCol] = temp;
            return true;
        }
        return false;
    }

    private static boolean isMagicSquare(int[][] matrix, int n) {
        int magicSum = n * (n * n + 1) / 2;

        for (int i = 0; i < n; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            if (rowSum != magicSum || colSum != magicSum) return false;
        }

        int mainDiagSum = 0, secDiagSum = 0;
        for (int i = 0; i < n; i++) {
            mainDiagSum += matrix[i][i];
            secDiagSum += matrix[i][n - i - 1];
        }
        return mainDiagSum == magicSum && secDiagSum == magicSum;
    }

    private static void displaySquare(int[][] square, int n) {
        for (int[] rowArray : square) {
            for (int value : rowArray) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
    }
}
