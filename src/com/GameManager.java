package com;

import java.lang.Math;
import java.util.*;

public class GameManager {
    private int[][] mat = new int[9][9];
    private int plate;
    private int max;
    private int level;
    private int nStep;
    private int[] target;
    ArrayList<Matrix> list = new ArrayList<Matrix>();
    GameManager (int _level) {
        level = _level;
        if (level == 1) max = 4;
        else max = 5;
        makeWallsandIce();
        nStep = 0;

        list.clear();
        target = new int[max];
        for (int i = 0; i < max; ++i) {
            target[i] = (int)(max * 4+Math.random()*(max * 2));
        }
        int counter = 0;
        do {
            int i = (int)(Math.random()*(max-1));
            if (target[i] == -1) continue;
            target[i] = -1;
            counter++;
        } while (counter < (max - 3));
        nStep = (int)(max * 2+Math.random()*(max));

        do {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    make (i, j);
                }
            }
            plate =(int)(1+Math.random()*(max-1+1));
        } while (checkPlate() == -1);
    }
    private void makeWallsandIce() {
        if (level == 1) {
            mat = new Walls().getWallsAndIce(1);
        }
        else if (level == 2) {
            mat = new Walls().getWallsAndIce(2);
        }
        else if (level == 3) {
            mat = new Walls().getWallsAndIce(3);
        }
    }
    private int random(int tar1, int tar2) {
        while (true) {
            int res = (int)(1+Math.random()*(max-1+1));
            if (res != tar1 && res != tar2) return res;
        }
    }
    private void make(int i, int j) {
        if (mat[i][j] != -1 && mat[i][j] < 6)
        {
            int tari=-1, tarj=-1;
            if (i >= 2) if (mat[i-1][j] == mat[i-2][j]) tari = mat[i-1][j];
            if (j >= 2) if (mat[i][j-1] == mat[i][j-2]) tarj = mat[i][j-1];
            mat[i][j] = random(tari, tarj);
        }
    }
    public int[][] getMat() {
        return mat;
    }
    public int getPlate() {
        return plate;
    }

    public int getStep() {
        return nStep;
    }

    public int[] getTarget() {
        return target;
    }

    public int getMax() {
        return max;
    }

    public int m(int i, int j) {
        if (i > 8 || j > 8 || i < 0 || j < 0) return -1;
        return mat[i][j];
    }

    private int[] con5 () {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                int a1 = 0;
                while (m(i, j) == m(i - a1, j) && m(i, j) > 0 && m(i, j) < 6) a1++;
                int a2 = 0;
                while (m(i, j) == m(i + a2, j) && m(i, j) > 0 && m(i, j) < 6) a2++;
                int a3 = 0;
                while (m(i, j) == m(i, j - a3) && m(i, j) > 0 && m(i, j) < 6) a3++;
                int a4 = 0;
                while (m(i, j) == m(i, j + a4) && m(i, j) > 0 && m(i, j) < 6) a4++;
                if (a1 + a2 >= 4 && a3 + a4 >= 4) {
                    int[] cross = new int[2];
                    cross[0] = i;
                    cross[1] = j;
                    return cross;
                }
            }
        }
        return null;
    }

    public int checkPlate() {
        int flag = 1;
        int p = plate;
        while (flag <= max) {
            // System.out.println(flag);
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j)
                    if (m(i, j) > 0 && m(i, j) <= max) {
                        int row = 0;
                        int _i = i, _j = j;
                        while (m(_i - 1, _j) == plate) {
                            row++;
                            _i--;
                        }
                        _i = i;
                        _j = j;
                        while (m(_i + 1, _j) == plate) {
                            row++;
                            _i++;
                        }
                        if (row >= 2) return plate;
                        int line = 0;
                        _i = i;
                        _j = j;
                        while (m(_i, _j - 1) == plate) {
                            line++;
                            _j--;
                        }
                        _i = i;
                        _j = j;
                        while (m(_i, _j + 1) == plate) {
                            line++;
                            _j++;
                        }
                        if (line >= 2) return plate;
                    }
            }
            System.out.println("这个不行");
            plate = (plate + 1) % max + 1;
            flag += 1;
        }
        plate = p;
        return -1;
    }

    public int eliminate(int x, int y) {
        boolean can = false;
        int flag = 0;
        if (x != -1 && (m(x, y) <= 0 || m(x, y) >= 6)) return 0;
        if (x != -1) {
            int t = m(x, y);
            mat[x][y] = plate;
            plate = t;
        }
        int[][] eliminateMap = new int[9][9];
        int[] cross = con5();
        if (cross != null) {
            System.out.println("con5");
            flag += 5;
            for (int _i = 0; _i < 9; ++_i)
                for (int _j = 0; _j < 9; ++_j)
                    if (Math.abs(_i - cross[0]) + Math.abs(_j - cross[1]) <= 2 && m(_i, _j) != -1) {
                        eliminateMap[_i][_j] = 11;
                    }
        }
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                int k = 0;
                while (m(i + k, j) == m(i, j) && m(i, j) != -1 && m(i, j) < 6 && m(i, j) > 0) k++;
                if (k >= 3) {
                    can = true;
                    for (int _k = 0; _k < k; ++_k) {
                        if (eliminateMap[i + _k][j] < 10) {
                            eliminateMap[i + _k][j] = 10;
                            if (m(i + _k, j + 1) >= 6) eliminateMap[i + _k][j + 1] = 10;
                            if (m(i + _k, j - 1) >= 6) eliminateMap[i + _k][j - 1] = 10;
                        }
                    }
                    if (m(i - 1, j) >= 6) {
                        eliminateMap[i - 1][j] = 10;
                    }
                    if (m(i + k, j) >= 6) {
                        eliminateMap[i + k][j] = 10;
                    }
                }
                if (k >= 5) { // beam
                    System.out.println("con41");
                    flag += 4;
                    for (int _i = 0; _i < 9; ++_i) {
                        for (int _j = 0; _j < 9; ++_j) {
                            if (m(_i, _j) == m(i, j)) {
                                eliminateMap[_i][_j] = 14;
                            }
                        }
                    }
                }
                else if (k == 4) { // 冲击波
                    System.out.println("con31");
                    flag += 3;
                    if (x != -1) {
                        for (int _j = 0; _j < 9; ++_j) {
                            if (mat[x][_j] != -1)
                                eliminateMap[x][_j] = 13;
                        }
                    }
                    else {
                        int _x = i + (int)(1+Math.random()*(4-1+1)) - 1;
                        for (int _j = 0; _j < 9; ++_j) {
                            if (mat[_x][_j] != -1)
                                eliminateMap[_x][_j] = 13;
                        }
                    }
                }
                if (can) {
                    i = 10;
                    break;
                }
            }
        }
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                int k = 0;
                while (m(i, j + k) == m(i, j) && m(i, j) != -1 && m(i, j) < 6 && m(i, j) > 0) k++;
                if (k >= 3) {
                    can = true;
                    for (int _k = 0; _k < k; ++_k) {
                        if (eliminateMap[i][j + _k] < 10) {
                            eliminateMap[i][j + _k] = 10;
                            if (m(i + 1, j + _k) >= 6) eliminateMap[i + 1][j + _k] = 10;
                            if (m(i - 1, j + _k) >= 6) eliminateMap[i - 1][j + _k] = 10;
                        }
                    }
                    if (m(i, j - 1) >= 6) {
                        eliminateMap[i][j - 1] = 10;
                    }
                    if (m(i, j + k) >= 6) {
                        eliminateMap[i][j + k] = 10;
                    }
                }
                if (k >= 5) { // beam
                    System.out.println("con42");
                    flag += 4;
                    for (int _i = 0; _i < 9; ++_i) {
                        for (int _j = 0; _j < 9; ++_j) {
                            if (m(_i, _j) == m(i, j)) {
                                eliminateMap[_i][_j] = 14;
                            }
                        }
                    }
                }
                else if (k == 4) { // 冲击波
                    System.out.println("con32");
                    flag += 3;
                    if (y != -1) {
                        for (int _i = 0; _i < 9; ++_i) {
                            if (mat[_i][y] != -1)
                                eliminateMap[_i][y] = 12;
                        }
                    }
                    else {
                        int _y = j + (int)(1+Math.random()*(4-1+1)) - 1;
                        for (int _i = 0; _i < 9; ++_i) {
                            if (mat[_i][_y] != -1)
                                eliminateMap[_i][_y] = 12;
                        }
                    }
                }
                if (can) {
                    i = 10;
                    break;
                }
            }
        }
        if (can == false && x != -1) {
            int t = m(x, y);
            mat[x][y] = plate;
            plate = t;
        }
        else if (can == true && x != -1) {
            nStep--;
            highLevel();
        }
        // eliminate
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (eliminateMap[i][j] >= 1) {
                    if (m(i, j) > 0 && m(i, j) <= 6) {
                        // update target
                        if (m(i, j) <= max) {
                            target[m(i, j) - 1] = target[m(i, j) - 1] == 0 || target[m(i, j) - 1] == -1 ? target[m(i, j) - 1] : target[m(i, j) - 1] - 1;
                        }
                        // effect
                        mat[i][j] = eliminateMap[i][j];
                    }
                }
            }
        }
        list.add(new Matrix(mat, flag, target));
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (eliminateMap[i][j] >= 1) {
                    if (m(i, j) >= 10) mat[i][j] = 0;
                    else if (m(i, j) > 6)mat[i][j]--; // ice
                }
            }
        }
        list.add(new Matrix(mat, 0, target));
        return flag;
    }

    public void drop (int i, int j) {
        if (m(i - 1, j) > 0 && m(i - 1, j) < 6) { // up is food
            mat[i][j] = m(i - 1, j);
            mat[i - 1][j] = 0;
            drop(i - 1, j);
        }
        else if (m(i - 1, j - 1) > 0 && m(i - 1, j - 1) < 6 && m(i - 1, j + 1) > 0 && m(i - 1, j + 1) < 6) { // up-left and up-right are food
            int flag = (int)(1+Math.random()*(2-1+1));
            if (flag == 1) {
                mat[i][j] = m(i - 1, j - 1);
                mat[i - 1][j - 1] = 0;
                drop(i - 1, j - 1);
            }
            else {
                mat[i][j] = m(i - 1, j + 1);
                mat[i - 1][j + 1] = 0;
                drop(i - 1, j + 1);
            }
        }
        else if (m(i - 1, j - 1) > 0 && m(i - 1, j - 1) < 6) { // up-left is food
            mat[i][j] = m(i - 1, j - 1);
            mat[i - 1][j - 1] = 0;
            drop(i - 1, j - 1);
        }
        else if (m(i - 1, j + 1) > 0 && m(i - 1, j + 1) < 6) { // up-right is food
            mat[i][j] = m(i - 1, j + 1);
            mat[i - 1][j + 1] = 0;
            drop(i - 1, j + 1);
        }
        else if (m(i - 1, j) == -1) {
            mat[i][j] = (int)(1+Math.random()*(max-1+1));
        }
    }

    public void dropMat() {
        int f = 0;
        for (int i = 0; i < 9; ++i) {
            f = 0;
            for (int j = 0; j < 9; ++j) {
                if (mat[i][j] == 0) {
                    f = 1;
                    drop(i, j);
                }
            }
            if (f == 1) list.add(new Matrix(mat, 0, target));
        }
    }

    public ArrayList<Matrix> oneClick(int x, int y) {
        list.clear();
        int flag = eliminate(x, y);
        int maxEliminate = 0;
        while (flag != 0 || maxEliminate < 100) {
            maxEliminate++;
            dropMat();
            int newFlag = eliminate(-1, -1);
            flag = newFlag;
        }
        if (checkPlate() == -1) {
            System.out.println("失败");
            nStep = 0;
        }
        dropList();
        return list;
    }

    public class Matrix {
        int [][] mat = new int[9][9];
        int flag;
        int [] target = new int[max];
        Matrix(int[][] _mat, int _flag, int[] _target) {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    mat[i][j] = _mat[i][j];
                }
            }
            flag = _flag;
            for (int i = 0; i < max; ++i) {
                target[i] = _target[i];
            }
        }
    }

    public boolean isEqual(Matrix m1, Matrix m2) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (m1.mat[i][j] != m2.mat[i][j]) return false;
            }
        }
        for (int i = 0; i < max; ++i) {
            if (m1.target[i] != m2.target[i]) return false;
        }
        if (m1.flag != m2.flag) return false;
        return true;
    }

    public void dropList() {
        Matrix m1 = list.get(list.size() - 1);
        Matrix m2 = list.get(list.size() - 2);
        while (list.size() > 5 && isEqual(m1, m2)) {
            list.remove(list.size() - 1);
            m1 = m2;
            m2 = list.get(list.size() - 2);
        }
    }

    public void highLevel() {
        if (level == 3) {
            int count = 0;
            do {
                int i = (int) (Math.random() * 9);
                int j = (int) (Math.random() * 9);
                if (m(i, j) == plate) {
                    mat[i][j] = 9 * (int)(2 * Math.random()) - 1;
                    count++;
                }
            } while (count < 3);
            list.add(new Matrix(mat, 0, target));
        }
    }

    public static void main(String[] args) {
        GameManager test = new GameManager(4);
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.print(test.getMat()[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
