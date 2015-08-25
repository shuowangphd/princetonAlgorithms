public class PercolationStats {
    private int n;
    private int t;
    private double[] pv;
    private int cnt;
    private double mu;
    private double sigma;
    
    public PercolationStats(int N, int T){     // perform T independent experiments on an N-by-N grid
       n=N;
       t=T;
       pv=new double [t];
       for (int k=0;k<t;k++){
           Percolation perc = new Percolation(n);
           cnt=0;
           int i=0;
           int j=0;
        //Repeat the following until the system percolates:
           while(!perc.percolates()){
        //Choose a site (row i, column j) uniformly at random among all blocked sites.
               do {
               i=StdRandom.uniform(1,n+1);
               j=StdRandom.uniform(1,n+1);} while(perc.isOpen(i,j));
        //Open the site (row i, column j). 
        cnt++;
        perc.open(i,j);
           }
        // The fraction of sites that are opened provides the percolation threshold. 
           pv[k]=cnt*1./n/n;
       }
    }
    public double mean() {                     // sample mean of percolation threshold
        double mm=0;
        for (int i=0;i<pv.length;i++) 
            mm+=pv[i];
        return mm/pv.length;
    }
    public double stddev()  {                  // sample standard deviation of percolation threshold
       double sigmaSq=0;
       for (int i=0;i<pv.length;i++) 
            sigmaSq+=(pv[i]-mu)*(pv[i]-mu);
        return Math.sqrt(sigmaSq/(t-1));
    }
    public double confidenceLo()  {            // low  endpoint of 95% confidence interval
       return mu-1.96*sigma/Math.sqrt(t);}
    public double confidenceHi(){              // high endpoint of 95% confidence interval
       return mu+1.96*sigma/Math.sqrt(t);}
    
    public static void main(String[] args){    // test client
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats ps= new PercolationStats(n,t);
       ps.mu=ps.mean();
       ps.sigma=ps.stddev();
       System.out.println("mu:"+ps.mu);
       System.out.println("stddev:"+ps.sigma);
       System.out.println("confidenceLoHi:"+ps.confidenceLo()+" "+ps.confidenceHi());
}
}