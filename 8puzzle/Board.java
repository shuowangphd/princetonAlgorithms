public class Board {
    private int N;// board dimension N
    private int[][] tiles;
    private int[][] goal;
    
    public Board(int[][] blocks){           // construct a board from an N-by-N array of blocks
        N=blocks.length;                    // (where blocks[i][j] = block in row i, column j)
        tiles=new int[N][N];
        goal=new int[N][N];
        for (int i=0;i<N;i++) for (int j=0;j<N;j++) {
            tiles[i][j]=blocks[i][j];
            goal[i][j]=i*N+j+1;
        }
        goal[N-1][N-1]=0;
    }
        
    public int dimension() {return N;}     // board dimension N
            
    public int hamming(){                   // number of blocks out of place
        int cnt=0;
        for (int i=0;i<N;i++) for (int j=0;j<N;j++) if(tiles[i][j]!=goal[i][j]) cnt++;
        if (tiles[N-1][N-1]!=0) cnt--;//tile 0 does not count
        return cnt;
    }
    
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int mds=0;
        for (int i=0;i<N;i++) for (int j=0;j<N;j++) {
            int tile=tiles[i][j]-1;//i~yt;j~xt
            if (tile<0) continue;//tile 0 does not count
            int xg=tile%N;
            int yg=tile/N;
            mds=mds+Math.abs(j-xg)+Math.abs(i-yg);
        }
        return mds;
    }
    
    public boolean isGoal(){                // is this board the goal board?
      for (int i=0;i<N;i++) for (int j=0;j<N;j++) 
        if(tiles[i][j]!=goal[i][j]) return false;
      return true;
    }
    
    public Board twin(){                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
      Board nbd= new Board(tiles);
      if (nbd.tiles[0][1]*nbd.tiles[0][0]!=0){
        int swap=nbd.tiles[0][1];nbd.tiles[0][1]=nbd.tiles[0][0];nbd.tiles[0][0]=swap;}
      else{int swap=nbd.tiles[1][1];nbd.tiles[1][1]=nbd.tiles[1][0];nbd.tiles[1][0]=swap;}
      return nbd;
    }
    
    public boolean equals(Object y){        // does this board equal y?
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        for (int i=0;i<N;i++) for (int j=0;j<N;j++) 
          if(this.tiles[i][j]!=that.tiles[i][j]) return false;
        return true;
    }
    
    public Iterable<Board> neighbors(){    // all neighboring boards
      Stack<Board> boards = new Stack<Board>();
      int i0=0,j0=0;
      for (int i=0;i<N;i++) for (int j=0;j<N;j++) if (tiles[i][j]==0){i0=i;j0=j;i=N;j=N;}
      //u
      if(i0>0){ Board nbd= new Board(tiles); int swap=nbd.tiles[i0][j0];
        nbd.tiles[i0][j0]=nbd.tiles[i0-1][j0];nbd.tiles[i0-1][j0]=swap;boards.push(nbd);}
      //d
      if(i0<N-1){ Board nbd= new Board(tiles); int swap=nbd.tiles[i0][j0];
        nbd.tiles[i0][j0]=nbd.tiles[i0+1][j0];nbd.tiles[i0+1][j0]=swap;boards.push(nbd);}      
      //L
      if(j0>0){ Board nbd= new Board(tiles); int swap=nbd.tiles[i0][j0];
        nbd.tiles[i0][j0]=nbd.tiles[i0][j0-1];nbd.tiles[i0][j0-1]=swap;boards.push(nbd);}      
      //R
      if(j0<N-1){ Board nbd= new Board(tiles);int swap=nbd.tiles[i0][j0];
        nbd.tiles[i0][j0]=nbd.tiles[i0][j0+1];nbd.tiles[i0][j0+1]=swap;boards.push(nbd);}
      return boards;
    }
        
    public String toString() {             // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args){ // unit tests (not graded)
        In in = new In(args[0]);      // input file
        int N = in.readInt();
        int[][] blocks=new int[N][N];
        for (int i=0;i<N;i++) for (int j=0;j<N;j++) blocks[i][j]=in.readInt();
        Board bd= new Board(blocks);
        StdOut.println("dimension: "+bd.dimension());
        StdOut.println("hamming: "+bd.hamming());
        StdOut.println("Manhattan distances: "+bd.manhattan());
        StdOut.println("is goal: "+bd.isGoal());
        StdOut.print(bd);
        StdOut.print("twin: "+bd.twin());
        StdOut.println("twin equal: "+bd.equals(bd.twin()));
        StdOut.println("twin twin equal: "+bd.equals(bd.twin().twin()));
      for (Board it:bd.neighbors())StdOut.print(it);
    }
}
