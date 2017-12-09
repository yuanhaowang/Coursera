import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UF_nobottom;
    private int open_spots = 0;
    private boolean[][] grid;
    private int size;    
    private int top;
    private int bottom;
    
    public Percolation(int n) {
        if(n < 1) {
            throw new IllegalArgumentException("out of range");
        }
        UF = new WeightedQuickUnionUF(n*n + 2);
        UF_nobottom = new WeightedQuickUnionUF(n*n+1);
        top = n*n;
        bottom = n*n + 1;
        size = n;
        grid = new boolean[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }        
    }
    
    private int index(int row, int col) {
        return ((row - 1) * size + col - 1);
    }
    
    private boolean check(int row, int col) {
        return (row < 1) ? false : (col < 1) ? false :(row > size) ? false : (col > size) ? false : true;
    }
    
    public void open(int row, int col) {
        if(!check(row, col)) {
            throw new IllegalArgumentException("out of range");
        }
        if(!isOpen(row, col)) {
            grid[row-1][col-1] = true;
            open_spots++;
            int cur_index = index(row, col);
            if(row == 1) {
                UF.union(top, cur_index);
                UF_nobottom.union(top, cur_index);
            }
            if(row == size) {
                UF.union(bottom, cur_index);
            }
            if(row - 1 > 0 && isOpen(row - 1, col)) {
                UF.union(cur_index, index(row-1, col));
                UF_nobottom.union(cur_index, index(row-1, col));
            }
            if(row + 1 <= size && isOpen(row + 1, col)) {
                UF.union(cur_index, index(row+1, col));
                UF_nobottom.union(cur_index, index(row+1, col));
            }
            if(col + 1 <= size && isOpen(row, col+1)) {
                UF.union(cur_index, index(row, col + 1));
                UF_nobottom.union(cur_index, index(row, col+1));
            }
            //Right
            if(col - 1 > 0 && isOpen(row, col-1)) {
                UF.union(cur_index, index(row, col - 1));
                UF_nobottom.union(cur_index, index(row, col-1));
            }
        }        
    }
    public boolean isOpen(int row, int col) {
        if(check(row, col)) {
            return grid[row-1][col-1];
        } else{
            throw new IllegalArgumentException("out of range");
        }
    }
    
    public boolean isFull(int row, int col) {
        if(check(row, col)) {
            return UF_nobottom.connected(top, index(row, col));
        } else {
            throw new IllegalArgumentException("out of range");
        }
    }
    
    public int numberOfOpenSites() {
        return open_spots;
    }
    
    public boolean percolates() {
        return UF.connected(top, bottom);
    }
}
