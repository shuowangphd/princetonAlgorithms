public class Solver implements Comparable<Solver>{
    private Board c;//current
    private Solver b;// backward
    private int p;//priority;
    private Solver ss;//solved Solver
    private int sol=0;
    private int move=0;
    public int compareTo(Solver that) {
        if      (this.p < that.p) return -1;
        else if (this.p > that.p) return +1;
        else                      return  0;
    }
  
    public Solver(Board initial){           // find a solution to the initial board (using the A* algorithm)
        c=initial;b=null; p = initial.manhattan();
    }
    private Solver(Board cu,Solver ba,int pr,int mv){           // find a solution to the initial board (using the A* algorithm)
        c=cu;b=ba; p = pr;move=mv;
    }
    
    private void solve(){
        Board c2=c.twin();
        Solver s= new Solver(c);
        Solver s2= new Solver(c2);
        int move=1;
        MinPQ<Solver> pq = new MinPQ<Solver>();
        MinPQ<Solver> pq2 = new MinPQ<Solver>();
        Board cur=c;
        Board cur2=c2;
        while (!cur.isGoal() && !cur2.isGoal()){
            for (Board b:cur.neighbors()) {
                if(b.equals(cur)) continue;
                int pr = b.manhattan()+s.move; 
                Solver ts=new Solver(b,s,pr,s.move+1);
                pq.insert(ts);
            }
            s = pq.delMin();
            cur = s.c;
            //twin
            for (Board b2:cur2.neighbors()) {
                if(b2.equals(cur2)) continue;
                int pr2 = b2.manhattan()+s2.move; 
                Solver ts2=new Solver(b2,s2,pr2,s2.move+1);
                pq2.insert(ts2);
            }
            s2 = pq2.delMin();
            cur2 = s2.c;
            //StdOut.println("move:"+(move-1));
            //StdOut.print(cur);
        }
        if (cur.isGoal()){sol=1;ss=s;}
        else sol=2;
    }
    
    public boolean isSolvable(){            // is the initial board solvable?
           if (sol ==0) solve();
           if (sol==1) return true;
           else return false;
    }
      
    public int moves(){ 
        if (sol==0) solve();
        if (sol==2) return -1;
        return ss.move;}     // min number of moves to solve initial board; -1 if unsolvable
      
    public Iterable<Board> solution(){      // sequence of boards in a shortest solution; null if unsolvable
        if (sol==0) solve();
      if (sol==2) return null;
      Solver st=ss;
      Stack<Board> sb= new Stack<Board>();
      for(int i=0;i<ss.p;i++){
          sb.push(st.c);
          st=st.b;
      }
      return sb;
    }
      
    public static void main(String[] args){ // solve a slider puzzle (given below)
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
    // solve the puzzle
    Solver solver = new Solver(initial);
    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}// run Solver puzzle03.txt
