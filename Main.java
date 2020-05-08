package dz;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final char DOT_EMPTY = '_';
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static int fieldSyzeY;
    private static int fieldSyzeX;
    private static int block;
    private static char[][] field;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static void initField() {
        fieldSyzeX = 5;
        fieldSyzeY = 5;
        block = 4;
        field = new char[fieldSyzeY][fieldSyzeX];
        for(int y = 0; y < fieldSyzeY; y++){
            for (int x = 0; x < fieldSyzeX; x++){
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    private static void printField() {
        System.out.println("______");
        for(int y = 0; y < fieldSyzeY; y++){
            for(int x = 0; x < fieldSyzeX; x++){
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

    }

    private static boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSyzeX && y >= 0 && y < fieldSyzeY;
    }

    private static boolean isEmptyCell(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }


    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты от 1 до " + fieldSyzeY);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isValidCell(x,y) || !isEmptyCell(x,y));
        field[y][x] = DOT_HUMAN;
    }

    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(fieldSyzeX);
            y = RANDOM.nextInt(fieldSyzeY);
        } while (!isEmptyCell(x,y));
        field[y][x] = DOT_AI;
    }

    private static boolean isFieldFull() {
        for(int y = 0; y < fieldSyzeY; y++){
            for(int x = 0; x < fieldSyzeX; x++){
                if (field[y][x] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    private static boolean checkWin(char c) {
        for(int a = 0; a <= fieldSyzeY - block; a++) {
            for(int b = 0; b <= fieldSyzeX - block; b++) {
                if(diagonalWin(c, a, b) || blockWin(c, a, b)) return  true;
            }
        }
        return false;
    }

    private static boolean diagonalWin(char c, int a, int b) {
        boolean diagonal1 = true, diagonal2 = true;
        for (int y = a; y < block + a; y++) {
            for (int x = b; x < block + b; x++) {
                diagonal1 &= (field[y][x] == c);
                diagonal2 &= (field[block - y][x] == c);
            }
            if (diagonal1 || diagonal2) return true;
        }
        return false;
    }

    private static boolean blockWin(char c, int a, int b) {
        boolean blockY = true;
        boolean blockX = true;
        for(int y = a; y < block + a; y++) {
            for (int x = b; x < block + b; x++) {
                blockY &= (field[y][x] == c);
                blockX &= (field[x][y] == c);
            }
            if (blockY || blockX) return true;
        }
        return false;
    }


    public static void main(String[] args) {
        initField();
        printField();
        while (true) {
            humanTurn();
            printField();
            if (checkWin(DOT_HUMAN)) {
                System.out.println("Выиграл игрок!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
            aiTurn();
            printField();
            if (checkWin(DOT_AI)) {
                System.out.println("Выиграл компьютер!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }
}


