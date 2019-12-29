package com;

public class Walls {
    int[][] mat = new int[9][9];
    Walls() {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                mat[i][j] = 0;
            }
        }
    }
    public int[][] getWallsAndIce(int level) {
        if (level == 1) {
            int label = (int)(Math.random()*3 + 1);
            makeWallsAndIce(label);
        }
        else if (level == 2) {
            int label = (int)(Math.random()*3 + 4);
            makeWallsAndIce(label);
        }
        else if (level == 3) {
            makeWallsAndIce(7);
        }
        return mat;
    }
    public void makeWallsAndIce(int label) {
        if (label == 1) { // 心形
            for (int i = 0; i < 9; ++i) {
                mat[i][0] = -1;
                mat[i][8] = -1;
                mat[0][i] = -1;
                mat[8][i] = -1;
            }
            mat[1][1] = -1; mat[1][4] = -1; mat[1][7] = -1;
            mat[5][1] = -1; mat[5][7] = -1;
            mat[6][1] = -1; mat[6][2] = -1; mat[6][6] = -1; mat[6][7] = -1;
            mat[7][1] = -1; mat[7][2] = -1; mat[7][3] = -1; mat[7][5] = -1; mat[7][6] = -1; mat[7][7] = -1;
        }
        else if (label == 2) { // 十字
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (i == 0 || i == 1 || i == 7 || i == 8) {
                        if (j == 0 || j == 1 || j == 2 || j == 6 || j == 7 || j == 8) {
                            mat[i][j] = -1;
                        }
                    }
                    if (i == 3 || i == 4 || i == 5) {
                        mat[i][3] = 6;
                        mat[i][4] = 6;
                        mat[i][5] = 6;
                    }
                }
            }
            mat[4][4] = -1; mat[4][1] = 6; mat[4][7] = 6; mat[1][4] = 6; mat[7][4] = 6;
        }
        else if (label == 3) {
            for (int i = 3; i < 6; ++i) {
                for (int j = 3; j < 6; ++j) {
                    mat[i][j] = -1;
                }
            }
            for (int i = 3; i < 6; ++i) {
                for (int j = 0; j < 3; ++j) {
                    mat[j][i] = 6;
                }
            }
            for (int i = 3; i < 6; ++i) {
                for (int j = 6; j < 9; ++j) {
                    mat[j][i] = 6;
                }
            }
        }
        else if (label == 4) { // 平行四边形
            mat[0][6] = -1; mat[0][7] = -1; mat[0][8] = -1;
            mat[1][7] = -1; mat[1][8] = -1;
            mat[2][8] = -1;
            mat[6][0] = -1; mat[7][0] = -1; mat[8][0] = -1;
            mat[7][1] = -1; mat[8][1] = -1;
            mat[8][2] = -1;
            for (int i = 0; i < 4; ++i) {
                mat[3][i] = 7;
                mat[i][3] = 7;
            }
            for (int i = 5; i < 9; ++i) {
                mat[5][i] = 7;
                mat[i][5] = 7;
            }
        }
        else if (label == 5) {  // 十字
            for (int i = 3; i < 6; ++i) {
                for (int j = 3; j < 6; ++j) {
                    mat[i][j] = -1;
                }
            }
            for (int i = 3; i < 6; ++i) {
                for (int j = 0; j < 3; ++j) {
                    mat[i][j] = 6;
                }
            }
            for (int i = 3; i < 6; ++i) {
                for (int j = 6; j < 9; ++j) {
                    mat[i][j] = 6;
                }
            }
        }
        else if (label == 6) { // 厚冰块
            for (int i = 0; i < 9; ++i) {
                mat[0][i] = -1;
                mat[8][i] = 7;
            }
            for (int i = 0; i < 8; ++i) {
                if (mat[i][4] != -1) mat[i][4] = 7;
            }
        }
    }
}
