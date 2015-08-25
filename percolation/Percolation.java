public class Percolation {
    private boolean [] box;
    private int totalN;
    private int sz;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf1;
    public Percolation(int n){     // create N-by-N grid, with all sites blocked
            if(n<1) throw new java.lang.IllegalArgumentException();
     totalN=n*n+2;//+topNode+BottomNode
     sz=n;
     box=new boolean [totalN];//true means open
     uf = new WeightedQuickUnionUF(totalN);
     uf1 = new WeightedQuickUnionUF(totalN-1);
  }
  
  public void open(int i, int j){// open site (row i, column j) if it is not open already
      box[index(i,j)]=true;
      //Up
      if (i==1) {uf.union(index(i,j),(totalN-2));
      uf1.union(index(i,j),(totalN-2));}
      else if (isOpen(i-1,j)) {uf.union(index(i,j),index(i-1,j));
          uf1.union(index(i,j),index(i-1,j));}
      //D
      if (i==sz) uf.union(index(i,j),(totalN-1));  
      else if (isOpen(i+1,j)) {uf.union(index(i,j),index(i+1,j));
          uf1.union(index(i,j),index(i+1,j));}      
      //L
      if (j!=1 && isOpen(i,j-1)) {uf.union(index(i,j),index(i,j-1));
          uf1.union(index(i,j),index(i,j-1));}  
      //R
      if (j!=sz && isOpen(i,j+1)) {uf.union(index(i,j),index(i,j+1));
          uf1.union(index(i,j),index(i,j+1));}  
  }
  
  private int index(int i, int j){//convert 2d index(i,j) to 1d index
    if(i<1 ||j<1||i>sz||j>sz) throw new java.lang.IndexOutOfBoundsException();
      return (i-1)*sz+(j-1);
  }
  
  public boolean isOpen(int i, int j){// is site (row i, column j) open?
    return box[index(i,j)];
  }

  public boolean isFull(int i, int j){// is site (row i, column j) full?
    return uf1.connected(index(i,j),(totalN-2));
  }
  
  public boolean percolates(){// does the system percolate?
    return uf.connected((totalN-2),(totalN-1));
  }

     public static void main(String[] args){   // test client (optional)
     int sz1=5;
     Percolation pc=new Percolation(sz1);
     System.out.println("pc.box.length:"+pc.box.length);
     System.out.println("pc.box[0]:"+pc.box[0]);
     pc.open(1,2);
     System.out.println("pc.box[7]:"+pc.box[7]+pc.isOpen(1,2));
   }
}