import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
//import Percolation;

public class PercolationStats {
    
    private double[] results;
    private int T;
    private int open = 0;
    
    public PercolationStats(int n, int trials) {
        if(n < 1 || trials < 1) {
            throw new IllegalArgumentException("n or trials must be greater than 0");
        }
        T = trials;
        results = new double[T];
        for(int i = 0; i < trials; i++) {
            open = 0;
            Percolation run = new Percolation(n);
            while(!run.percolates()) {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                if(!run.isOpen(x, y)){
                    run.open(x,y);
                    open++;
                }
            }
            results[i] = (double) open/(n*n);
        }        
    }
    
    public double mean() {
        return StdStats.mean(results);
    }
    
    public double stddev() {
        return StdStats.stddev(results);
    }
    
    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }
    
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean                    = " + ps.mean()); 
        StdOut.println("stddev                  = " + ps.stddev());
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("95% confidence interval = " + confidence);        
    }
}