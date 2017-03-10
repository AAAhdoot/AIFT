//Run this class to see how smaller g values compare against larger g values.
public class AdaptiveA{
	static GridWorld gw = new GridWorld();
	static BinaryHeap heap;
	static SquareNode head;
	 static int expanded = 0;
  static boolean adaptive = false;
  static int rfexpand = 0;
  	static int COST = 1; //currently
  	static int MAXINDEX = gw.CAPACITY-1;
  	static Square path;
  	
  	public static void main (String[] args){
  		GridWorld[] ourgw = new GridWorld[1];
  		GridWorld[] ourngw = new GridWorld[1];
      adaptive = false;
  		for(int i = 0; i<ourgw.length; i++){
  			ourgw[i] = new GridWorld();
  			ourgw[i].populate();
  			ourngw[i] = new GridWorld(ourgw[i]);
  		}

  		for(int i = 0; i<ourgw.length; i++){
  			gw = ourgw[i];
  			System.out.println("repeatedForwardAStar iteration " + i);
        repeatedForwardAStar(ourngw[i],ourngw[i].grid[ourngw[i].agentx][ourngw[i].agenty], ourngw[i].grid[ourngw[i].targetx][ourngw[i].targety],'g');
        head = null;
  		}
  		for(int i = 0; i<ourngw.length; i++){
  			ourngw[i] = new GridWorld(ourgw[i]);
  		}

  		head = null;
      adaptive = true;
  		for(int i = 0; i<ourgw.length; i++){
  			gw = ourgw[i];
        System.out.println("AdaptiveAstar iteration " + i);
  			AdaptiveAstar(ourngw[i],ourngw[i].grid[ourngw[i].agentx][ourngw[i].agenty], ourngw[i].grid[ourngw[i].targetx][ourngw[i].targety],'g');
  			head = null;
  		}

  		System.out.println("Number of expansions when Repeated Forward A* Run: " + rfexpand);
  		System.out.println();
  		System.out.println("Number of expansions when Adaptive A* Run: " + expanded);

  	}

  	public static void printSq(String name, Square square){
  		System.out.println(name + "=(" + square.x + "," + square.y + ")");
  	}

  	public static boolean sqEquals(Square a, Square b){
  		return ((a.x == b.x) && (a.y == b.y));
  	}

  	public static SquareNode addNode(Square square, SquareNode head){
  		SquareNode newHead = new SquareNode(square);
  		newHead.next = head;
  		return newHead;
  	}

    public static void repeatedForwardAStar(GridWorld ngw,Square start, Square goal, char ordering){
      int counter = 0;
      Square curr;
      //ngw.grid[start.x][start.y].travel = true;
      //ngw.grid[goal.x][goal.y].travel = true;
      //gw.grid[start.x][start.y].travel = true;
      //gw.grid[goal.x][goal.y].travel = true;

      while(!sqEquals(start,ngw.grid[goal.x][goal.y])){
        counter++;
        ngw.grid[start.x][start.y].g_value = 0;
        ngw.grid[start.x][start.y].search = counter;
        ngw.grid[goal.x][goal.y].g_value = Integer.MAX_VALUE;
        heap = new BinaryHeap();
        for(int i=0;i<ngw.CAPACITY;i++){
          for(int j=0;j<ngw.CAPACITY;j++){
            ngw.grid[i][j].inHeap = false;
            ngw.grid[i][j].isClosed = false;
          }
        }
        head = null;
        ngw.grid[start.x][start.y].inHeap = true;
        ngw.grid[start.x][start.y].f_value = ngw.grid[start.x][start.y].g_value + ngw.grid[start.x][start.y].h_value;
        heap.add(ngw.grid[start.x][start.y], ordering);   
        Astar(ngw,ngw.grid[goal.x][goal.y],counter,ordering);
        if(heap.isEmpty()){
          //gw.generate();
          System.out.println("I cannot reach the target.");
          System.out.println();
          return;
        }
        traverseTree(ngw,ngw.grid[goal.x][goal.y], ngw.grid[start.x][start.y]);
        curr = traverseBranch(ngw,ngw.grid[start.x][start.y],ngw.grid[goal.x][goal.y]);
        start = curr;
      }
      printPath(ngw);
      System.out.println("Our grid");
      gw.generate();
      System.out.println("Arrived at " + start.x + "," + start.y);
      System.out.println("I reached the target.");
      System.out.println();
      return;
    }

    public static void AdaptiveAstar(GridWorld ngw,Square start, Square goal, char ordering){
  adaptive = true;
    int counter = 0;
    int acounter = 0;
    Square curr;
    SquareNode ptr;
      //ngw.grid[start.x][start.y].travel = true;
      //ngw.grid[goal.x][goal.y].travel = true;
      //gw.grid[start.x][start.y].travel = true;
      //gw.grid[goal.x][goal.y].travel = true;

      while(!sqEquals(start,ngw.grid[goal.x][goal.y])){
        printSq("curr",start);
        counter++;
        ngw.grid[start.x][start.y].g_value = 0;
        ngw.grid[start.x][start.y].search = counter;
        ngw.grid[goal.x][goal.y].g_value = Integer.MAX_VALUE;
        heap = new BinaryHeap();
        for(int i=0;i<ngw.CAPACITY;i++){
          for(int j=0;j<ngw.CAPACITY;j++){
            ngw.grid[i][j].inHeap = false;
            ngw.grid[i][j].isClosed = false;
          }
        }
        head = null;
        ngw.grid[start.x][start.y].inHeap = true;
        ngw.grid[start.x][start.y].f_value = ngw.grid[start.x][start.y].g_value + ngw.grid[start.x][start.y].h_value;
        heap.add(ngw.grid[start.x][start.y], ordering);   
        Astar(ngw,ngw.grid[goal.x][goal.y],counter,ordering);
        if(heap.isEmpty()){
          gw.generate();
          System.out.println("I cannot reach the target.");
          System.out.println();
          return;
        }
        traverseTree(ngw,ngw.grid[goal.x][goal.y], ngw.grid[start.x][start.y]);
        curr = traverseBranch(ngw,ngw.grid[start.x][start.y],ngw.grid[goal.x][goal.y]);
        for(ptr = head;ptr!=null;ptr=ptr.next){
          printSq("curr in update",ngw.grid[ptr.square.x][ptr.square.y]);
          System.out.println("Before: " + ngw.grid[ptr.square.x][ptr.square.y].h_value);
          //System.out.println("Goal's g-value is: " + ngw.grid[goal.x][goal.y].g_value);
          //System.out.println("Current's g-value is: " + ngw.grid[ptr.square.x][ptr.square.y].g_value);

          ngw.grid[ptr.square.x][ptr.square.y].h_value = ngw.grid[goal.x][goal.y].g_value - ngw.grid[ptr.square.x][ptr.square.y].g_value;
          System.out.println("After: " + ngw.grid[ptr.square.x][ptr.square.y].h_value);
          System.out.println();
        }
        System.out.println("Number of expansions in this Astar: " + (expanded - acounter));
        acounter = expanded;
        ngw.generate();
        gw.generate();
        start = curr;
      }
      printPath(ngw);
      System.out.println("Our grid");
      gw.generate();
      System.out.println("Arrived at " + start.x + "," + start.y);
      System.out.println("I reached the target.");
      System.out.println();
      return;
   }

  	public static void Astar(GridWorld ngw, Square goal, int counter, char ordering){
  		Square curr;
  		while(!heap.isEmpty() && ngw.grid[goal.x][goal.y].g_value > (curr = heap.peek()).f_value){
  			heap.remove(ordering); 
  			ngw.grid[curr.x][curr.y].inHeap = false;
  			head = addNode(ngw.grid[curr.x][curr.y], head);
  			ngw.grid[curr.x][curr.y].isClosed = true;
  			addFour(ngw,ngw.grid[curr.x][curr.y],counter,ordering);
  		}
  		if(!heap.isEmpty()){
  			heap.remove(ordering);
  		}
  		return;
  	}

  	public static void addFour(GridWorld ngw, Square curr,int counter, char ordering){
  	   
       if(adaptive == false){
        rfexpand++;
       }
       if(adaptive == true){
        expanded++;
       }
       
  		int x = curr.x;
  		int y = curr.y;
  		int pg = 0;
  		pg = curr.g_value + COST;

  		if(curr.x != 0){
  			if(!ngw.grid[x-1][y].isClosed && !ngw.grid[x-1][y].isBlocked){
  				if(ngw.grid[x-1][y].search < counter){
  					ngw.grid[x-1][y].g_value = Integer.MAX_VALUE;
  					ngw.grid[x-1][y].search = counter;
  				}
  				if(ngw.grid[x-1][y].g_value > pg){
  					ngw.grid[x-1][y].g_value = pg;
  					ngw.grid[x-1][y].tree = ngw.grid[x][y];
  					ngw.grid[x-1][y].hastree = true;
  					if(ngw.grid[x-1][y].inHeap){
  						heap.findRemove(ngw.grid[x-1][y],ordering);
  					}
  					ngw.grid[x-1][y].f_value = ngw.grid[x-1][y].g_value + ngw.grid[x-1][y].h_value;
  					heap.add(ngw.grid[x-1][y],ordering);
  					ngw.grid[x-1][y].inHeap = true;
  				}
  			}

  		}
  		if(curr.x != MAXINDEX){
  			if(!ngw.grid[x+1][y].isClosed && !ngw.grid[x+1][y].isBlocked){
  				if(ngw.grid[x+1][y].search < counter){
  					ngw.grid[x+1][y].g_value = Integer.MAX_VALUE;
  					ngw.grid[x+1][y].search = counter;
  				}
  				if(ngw.grid[x+1][y].g_value > pg){
  					ngw.grid[x+1][y].g_value = pg;
  					ngw.grid[x+1][y].tree = ngw.grid[x][y];
  					ngw.grid[x+1][y].hastree = true;
  					if(ngw.grid[x+1][y].inHeap){
  						heap.findRemove(ngw.grid[x+1][y],ordering);
  					}
  					ngw.grid[x+1][y].f_value = ngw.grid[x+1][y].g_value + ngw.grid[x+1][y].h_value;
  					heap.add(ngw.grid[x+1][y],ordering);
  					ngw.grid[x+1][y].inHeap = true;
  				}
  			}

  		}
  		if(curr.y != 0){
  			if(!ngw.grid[x][y-1].isClosed && !ngw.grid[x][y-1].isBlocked){
  				if(ngw.grid[x][y-1].search < counter){
  					ngw.grid[x][y-1].g_value = Integer.MAX_VALUE;
  					ngw.grid[x][y-1].search = counter;
  				}
  				if(ngw.grid[x][y-1].g_value > pg){
  					ngw.grid[x][y-1].g_value = pg;
  					ngw.grid[x][y-1].tree = ngw.grid[x][y];
  					ngw.grid[x][y-1].hastree = true;
  					if(ngw.grid[x][y-1].inHeap){
  						heap.findRemove(ngw.grid[x][y-1],ordering);
  					}
  					ngw.grid[x][y-1].f_value = ngw.grid[x][y-1].g_value + ngw.grid[x][y-1].h_value;
  					heap.add(ngw.grid[x][y-1],ordering);
  					ngw.grid[x][y-1].inHeap = true;
  				}
  			}

  		}
  		if(curr.y != MAXINDEX){
  			if(!ngw.grid[x][y+1].isClosed && !ngw.grid[x][y+1].isBlocked){
  				if(ngw.grid[x][y+1].search < counter){
  					ngw.grid[x][y+1].g_value = Integer.MAX_VALUE;
  					ngw.grid[x][y+1].search = counter;
  				}
  				if(ngw.grid[x][y+1].g_value > pg){
  					ngw.grid[x][y+1].g_value = pg;
  					ngw.grid[x][y+1].tree = ngw.grid[x][y];
  					ngw.grid[x][y+1].hastree = true;
  					if(ngw.grid[x][y+1].inHeap){
  						heap.findRemove(ngw.grid[x][y+1],ordering);
  					}
  					ngw.grid[x][y+1].f_value = ngw.grid[x][y+1].g_value + ngw.grid[x][y+1].h_value;
  					heap.add(ngw.grid[x][y+1],ordering);
  					ngw.grid[x][y+1].inHeap = true;
  				}
  			}

  		}
  	}

  	public static void traverseTree(GridWorld ngw, Square end, Square start){
  		Square curr;
  		curr = end;
      // if(adaptive){
      //   if(curr.hastree == true){
      //     System.out.println("C");
      //   }
      //   if(!sqEquals(curr,ngw.grid[start.x][start.y])){
      //     System.out.println("B");
      //   }
      // }
  		while(curr.hastree==true && !sqEquals(curr,ngw.grid[start.x][start.y])){
  			ngw.grid[curr.x][curr.y].tree.branch = ngw.grid[curr.x][curr.y];
        curr = curr.tree;
  			ngw.grid[curr.x][curr.y].hasbranch = true;
  		}
  	}

  	public static Square traverseBranch(GridWorld ngw, Square start, Square goal){
  		Square curr = ngw.grid[start.x][start.y];
  		// if(adaptive){
    //     if(curr.hasbranch == true){
    //       System.out.println("D");
    //     }
    //     if(!sqEquals(curr,ngw.grid[goal.x][goal.y])){
    //       System.out.println("E");
    //     }
    //   }
      while(curr.hasbranch == true && !sqEquals(curr,ngw.grid[goal.x][goal.y])){
        if(gw.grid[curr.branch.x][curr.branch.y].isBlocked){
  				ngw.grid[curr.branch.x][curr.branch.y].isBlocked = true;
  				ngw.grid[curr.branch.x][curr.branch.y].hastree = false;
  				ngw.grid[curr.x][curr.y].hasbranch = false;
  				return ngw.grid[curr.x][curr.y];
  			}
  			curr = curr.branch;
  		}
  		return ngw.grid[curr.x][curr.y];
  	}

  	public static void printPath(GridWorld ngw){
  		Square curr = ngw.grid[ngw.agentx][ngw.agenty];
  		int count = 0;

      // if(adaptive){
      //   if(!curr.hasbranch == true){
      //     System.out.println("A");
      //   }
      //   if(sqEquals(curr,ngw.grid[ngw.targetx][ngw.targety])){
      //     System.out.println("B");
      //   }
      // }

  		while(curr.hasbranch == true && !sqEquals(curr,ngw.grid[ngw.targetx][ngw.targety])){
  			System.out.print("(" + curr.x + "," + curr.y + ")" + "-->");
  			gw.grid[curr.x][curr.y].travel = true;
  			curr = curr.branch;
  			count++;
  		}
  		System.out.print("(" + curr.x + "," + curr.y + "): ");
  		System.out.println(count + " moves to get from agent to target");
  	}


public static void renew(GridWorld ngw){
   //    for(int i=0;i<ngw.CAPACITY;i++){
   //    for(int j=0;j<ngw.CAPACITY;j++){
   //  ngw.grid[i][j].isClosed = false;
   //  //ngw.grid[i][j].isBlocked = false;
   //  ngw.grid[i][j].inHeap = false;
   //  ngw.grid[i][j].g_value = Integer.MAX_VALUE;
   //  //this.h_value = 0;
   //  ngw.grid[i][j].f_value = 0;
   //  ngw.grid[i][j].tree = null;
   //  ngw.grid[i][j].branch = null;
   //  ngw.grid[i][j].search= 0;
   //  //this.x = -1;
   //  //this.y = -1;
   //  ngw.grid[i][j].hastree = false;
   //  ngw.grid[i][j].hasbranch = false;
   //  ngw.grid[i][j].travel = false;
   //    }
   // }
}






  }