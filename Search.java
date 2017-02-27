public class Search{
	static GridWorld gw = new GridWorld();
  static GridWorld myngw = new GridWorld();
	static BinaryHeap heap;
  static SquareNode head;
  static int sfail = 0;
  static int gfail = 0;
  static int ssucc = 0;
  static int gsucc = 0;
  static int smallexpand = 0;
  static int bigexpand = 0;
  static int COST = 1; //currently
  static int MAXINDEX = gw.CAPACITY-1;
  static int rfexpand = 0;
  static int rbexpand = 0;
  static boolean backwards = false;
  static int expanded = 0;
  static boolean adaptive = false;
  static Square path;

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
      //return the head of the new linked list.
  }

public static void main(String []args){
  GridWorld temp = new GridWorld(gw.agentx,gw.agenty,gw.targetx,gw.targety);
  for(int i=0;i<5;i++){
      AdaptiveAstar(temp, temp.grid[temp.agentx][temp.agenty], temp.grid[temp.targetx][temp.targety], 'g');
      for(SquareNode ptr = head;ptr!=null;ptr=ptr.next){
          temp.grid[ptr.square.x][ptr.square.y].h_value = temp.grid[temp.agentx][temp.agenty].g_value - temp.grid[ptr.square.x][ptr.square.y].g_value;
      }
      renew(temp);
  }
  temp.generate();
  gw.generate();
  //System.out.println(rbexpand);

}

  public static void repeatedForwardAStar(GridWorld ngw,Square start, Square goal, char ordering){
    int counter = 0;
    Square curr;
    ngw.grid[start.x][start.y].travel = true;
    ngw.grid[goal.x][goal.y].travel = true;

    while(!sqEquals(start,goal)){
     // System.out.println("repeatedForwardAStar loop");
     counter++;

     ngw.grid[start.x][start.y].g_value = 0;
     ngw.grid[start.x][start.y].search = counter;
     goal.g_value = Integer.MAX_VALUE;
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
 		
    //temporary call for later
   Astar(ngw,goal,counter,ordering);

   //System.out.println(heap.size);

   if(heap.isEmpty()){
				// System.out.println("A*'s grid");
				// ngw.generate(); 
				// System.out.println("Our grid");
				// gw.generate();
    if(ordering == 'g'){
      gfail++;
    }
    if(ordering == 's'){
      sfail++;
    }
    System.out.println("I cannot reach the target.");
    System.out.println();
    return;
  }
 			//temporary calls for later
  traverseTree(ngw,goal, start);
  curr = traverseBranch(ngw,start,goal);
  start = curr;
}
printPath(ngw);
 		    // System.out.println("A*'s grid");
       //  ngw.generate(); 
       //  System.out.println("Our grid");
       //  gw.generate();
System.out.println("Arrived at " + start.x + "," + start.y);
if(ordering == 's'){
  ssucc++;
}
if(ordering == 'g'){
  gsucc++;
}
      //printPath(ngw);
System.out.println("I reached the target.");
System.out.println();
return;
}

  public static void repeatedBackwardAStar(GridWorld ngw,Square start, Square goal, char ordering){
    backwards= true;
    ngw = new GridWorld (gw.targetx,gw.targety,gw.agentx,gw.agenty);
    repeatedForwardAStar(ngw,ngw.grid[ngw.agentx][ngw.agenty],ngw.grid[ngw.targetx][ngw.targety],ordering);

}

public static void Astar(GridWorld ngw, Square goal, int counter, char ordering){
  Square curr;

  while(!heap.isEmpty() && ngw.grid[goal.x][goal.y].g_value > (curr = heap.peek()).f_value){
    System.out.println("AStar loop");

			heap.remove(ordering); //remove from top of heap

    	ngw.grid[curr.x][curr.y].inHeap = false; //for us
      head = addNode(ngw.grid[curr.x][curr.y], head);
     	ngw.grid[curr.x][curr.y].isClosed = true; //set closed
     		//now, we enter addFour
       addFour(ngw,ngw.grid[curr.x][curr.y],counter,ordering);
     }
     if(!heap.isEmpty()){

      heap.remove(ordering);
     }
     else{
     // System.out.println("heap is empty in Astar");
     }
     return;
   }

   public static void addFour(GridWorld ngw, Square curr,int counter, char ordering){
      // System.out.println(gw.grid[3][2].isBlocked);
      //   System.out.println(gw.grid[4][2].isBlocked);
      //   System.out.println(gw.grid[4][3].isBlocked);

      //   System.out.println(ngw.grid[3][2].isBlocked);
      //   System.out.println(ngw.grid[4][2].isBlocked);
      //   System.out.println(ngw.grid[4][3].isBlocked);

    if(ordering == 's'){
      smallexpand++;
    }
    if(ordering == 'g'){
      bigexpand++;
    }

    if(adaptive == true){
      expanded++;
    }

    if(backwards == false){
      rfexpand++;
    }
    if(backwards == true){
      rbexpand++;
    }

    int x = curr.x;
    int y = curr.y;
    int pg = 0;
    pg = curr.g_value + COST;
      	//System.out.println("Starting addFour at indices " + curr.x + "," + curr.y);
      	//ngw.generate();
    if(curr.x != 0){
      		System.out.println("Checking up");
      if(!ngw.grid[x-1][y].isClosed && !ngw.grid[x-1][y].isBlocked){
       if(ngw.grid[x-1][y].search < counter){
        ngw.grid[x-1][y].g_value = Integer.MAX_VALUE;
        ngw.grid[x-1][y].search = counter;
      }
      if(ngw.grid[x-1][y].g_value > pg){
        System.out.println("g_val success");
       ngw.grid[x-1][y].g_value = pg;
       ngw.grid[x-1][y].tree = ngw.grid[x][y];
       ngw.grid[x-1][y].hastree = true;
       if(ngw.grid[x-1][y].inHeap){
              			//System.out.println("IN ADDFOUR UP, ABOUT TO REMOVE INDICES " + (x-1) + "," + y);
        heap.findRemove(ngw.grid[x-1][y],ordering);
                    //System.out.println("IN ADDFOUR UP, JUST REMOVED INDICES " + (x-1) + "," + y);
      }
      ngw.grid[x-1][y].f_value = ngw.grid[x-1][y].g_value + ngw.grid[x-1][y].h_value;
      heap.add(ngw.grid[x-1][y],ordering);
      ngw.grid[x-1][y].inHeap = true;
    }
  }
  else{
      			System.out.println("BLOCKED =" + ngw.grid[x-1][y].isBlocked);
            		System.out.println("CLOSED =" + ngw.grid[x-1][y].isClosed);
  }

}

if(curr.x != MAXINDEX){
      		System.out.println("Checking down");
  if(!ngw.grid[x+1][y].isClosed && !ngw.grid[x+1][y].isBlocked){
   if(ngw.grid[x+1][y].search < counter){
    ngw.grid[x+1][y].g_value = Integer.MAX_VALUE;
    ngw.grid[x+1][y].search = counter;
  }
  if(ngw.grid[x+1][y].g_value > pg){
    System.out.println("g_val success");
   ngw.grid[x+1][y].g_value = pg;
   ngw.grid[x+1][y].tree = ngw.grid[x][y];
   ngw.grid[x+1][y].hastree = true;
   if(ngw.grid[x+1][y].inHeap){
                    //System.out.println("IN ADDFOUR DOWN, ABOUT TO REMOVE INDICES " + (x+1) + "," + y);
     heap.findRemove(ngw.grid[x+1][y],ordering);
                    //System.out.println("IN ADDFOUR DOWN, JUST REMOVED INDICES " + (x+1) + "," + y);
   }
   ngw.grid[x+1][y].f_value = ngw.grid[x+1][y].g_value + ngw.grid[x+1][y].h_value;
   heap.add(ngw.grid[x+1][y],ordering);
   ngw.grid[x+1][y].inHeap = true;
 }
}
else{
      			System.out.println("BLOCKED =" + ngw.grid[x+1][y].isBlocked);
            		System.out.println("CLOSED =" + ngw.grid[x+1][y].isClosed);
}

}

if(curr.y != 0){
      		System.out.println("Checking left");
  if(!ngw.grid[x][y-1].isClosed && !ngw.grid[x][y-1].isBlocked){
   if(ngw.grid[x][y-1].search < counter){
    ngw.grid[x][y-1].g_value = Integer.MAX_VALUE;
    ngw.grid[x][y-1].search = counter;
  }
  if(ngw.grid[x][y-1].g_value > pg){
    System.out.println("g_val success");
   ngw.grid[x][y-1].g_value = pg;
   ngw.grid[x][y-1].tree = ngw.grid[x][y];
   ngw.grid[x][y-1].hastree = true;
   if(ngw.grid[x][y-1].inHeap){
            			//System.out.println("IN ADDFOUR LEFT, ABOUT TO REMOVE INDICES " + x + "," + (y-1));
    heap.findRemove(ngw.grid[x][y-1],ordering);
                  //System.out.println("IN ADDFOUR LEFT, JUST REMOVED INDICES " + x + "," + (y-1));
  }
  ngw.grid[x][y-1].f_value = ngw.grid[x][y-1].g_value + ngw.grid[x][y-1].h_value;
  heap.add(ngw.grid[x][y-1],ordering);
  ngw.grid[x][y-1].inHeap = true;
}
}
else{
    			System.out.println("BLOCKED =" + ngw.grid[x][y-1].isBlocked);
          		System.out.println("CLOSED =" + ngw.grid[x][y-1].isClosed);
}

}

if(curr.y != MAXINDEX){
    		System.out.println("Checking right");
  if(!ngw.grid[x][y+1].isClosed && !ngw.grid[x][y+1].isBlocked){
   if(ngw.grid[x][y+1].search < counter){
    ngw.grid[x][y+1].g_value = Integer.MAX_VALUE;
    ngw.grid[x][y+1].search = counter;
  }
  if(ngw.grid[x][y+1].g_value > pg){
    System.out.println("g_val success");
   ngw.grid[x][y+1].g_value = pg;
   ngw.grid[x][y+1].tree = ngw.grid[x][y];
   ngw.grid[x][y+1].hastree = true;
   if(ngw.grid[x][y+1].inHeap){
                  //System.out.println("IN ADDFOUR RIGHT, ABOUT TO REMOVE INDICES " + x + "," + (y+1));
     heap.findRemove(ngw.grid[x][y+1],ordering);
                  //System.out.println("IN ADDFOUR RIGHT, JUST REMOVED INDICES " + x + "," + (y+1));

   }
   ngw.grid[x][y+1].f_value = ngw.grid[x][y+1].g_value + ngw.grid[x][y+1].h_value;
   heap.add(ngw.grid[x][y+1],ordering);
   ngw.grid[x][y+1].inHeap = true;
 }
}
else{
            // System.out.println(gw.grid[3][2].isBlocked);
            // System.out.println(gw.grid[4][2].isBlocked);
            // System.out.println(gw.grid[4][3].isBlocked);

            // System.out.println(ngw.grid[3][2].isBlocked);
            // System.out.println(ngw.grid[4][2].isBlocked);
            // System.out.println(ngw.grid[4][3].isBlocked);
            //System.out.println (x + "," + (y+1));
          		  System.out.println("BLOCKED =" + ngw.grid[x][y+1].isBlocked);
          System.out.println("CLOSED =" + ngw.grid[x][y+1].isClosed);
}
}

}

public static void traverseTree(GridWorld ngw, Square end, Square start){
  Square curr;
  curr = end;
      //System.out.println("Returning from square ");
      //printSq("end", end);
      //System.out.println("To square ");
      //printSq("start",start);
      //System.out.println(curr.hastree);
  while(curr.hastree==true && curr!=start){
    ngw.grid[curr.x][curr.y].tree.branch = ngw.grid[curr.x][curr.y];
    //System.out.println("Going through trees from square " + curr.x + "," + curr.y + " to square "+ curr.tree.x + "," + curr.tree.y);
    curr = curr.tree;
    ngw.grid[curr.x][curr.y].hasbranch = true;
  }
      //System.out.println("traversing tree complete, backtracked to indices ");
      //printSq("curr", curr);
}

public static Square traverseBranch(GridWorld ngw, Square start, Square goal){
     // System.out.println(gw.grid[3][2].isBlocked);
     //  System.out.println(gw.grid[4][2].isBlocked);
     //  System.out.println(gw.grid[4][3].isBlocked);

     //  System.out.println(ngw.grid[3][2].isBlocked);
     //  System.out.println(ngw.grid[4][2].isBlocked);
     //  System.out.println(ngw.grid[4][3].isBlocked);
  //sqhead = start;
  Square curr = start;
    //printPath(ngw);
  while(curr.hasbranch == true && curr != goal){
   if(gw.grid[curr.branch.x][curr.branch.y].isBlocked){
        //System.out.println(gw.grid[curr.branch.x][curr.branch.y].isBlocked);
        //System.out.println("Setting indices to blocked at: " + curr.branch.x + "," + curr.branch.y);
    ngw.grid[curr.branch.x][curr.branch.y].isBlocked = true;
    ngw.grid[curr.branch.x][curr.branch.y].hastree = false;
    ngw.grid[curr.x][curr.y].hasbranch = false;
				//System.out.println("traversing interrupted because the following branch is blocked: ");
				//printSq("branch",gw.grid[curr.branch.x][curr.branch.y]);
        //printPath(ngw);
				//ngw.generate();
        // return ngw.grid[curr.x][curr.y];
    return ngw.grid[curr.x][curr.y];
  }
  //ngw.grid[curr.x][curr.y].travel = true;
  //sqhead.branch = ngw.grid[curr.branch.x][curr.branch.y];
  //System.out.println("Going through branches from square " + curr.x + "," + curr.y + " to square "+ curr.branch.x + "," + curr.branch.y);
  curr = curr.branch;
}
		//System.out.println("traversing branch complete, progressed forward to indices ");
    	//printSq("curr", ngw.grid[curr.x][curr.y]);
return ngw.grid[curr.x][curr.y];
}

public static void printPath(GridWorld ngw){
  Square curr = ngw.grid[ngw.agentx][ngw.agenty];
  int count =0;

  while(curr.hasbranch == true && !sqEquals(curr,ngw.grid[ngw.targetx][ngw.targety])){
      System.out.print("(" + curr.x + "," + curr.y + ")" + "-->");
    ngw.grid[curr.x][curr.y].travel = true;
    gw.grid[curr.x][curr.y].travel = true;
    curr = curr.branch;
    count++;
  }
  //ngw.generate();
  System.out.print("(" + curr.x + "," + curr.y + "): ");
  System.out.println(count + " moves to get from agent to target");
}



public static void renew(GridWorld ngw){
      for(int i=0;i<ngw.CAPACITY;i++){
      for(int j=0;j<ngw.CAPACITY;j++){
    ngw.grid[i][j].isClosed = false;
    //ngw.grid[i][j].isBlocked = false;
    ngw.grid[i][j].inHeap = false;
    ngw.grid[i][j].g_value = Integer.MAX_VALUE;
    //this.h_value = 0;
    ngw.grid[i][j].f_value = 0;
    ngw.grid[i][j].tree = null;
    ngw.grid[i][j].branch = null;
    ngw.grid[i][j].search= 0;
    //this.x = -1;
    //this.y = -1;
    ngw.grid[i][j].hastree = false;
    ngw.grid[i][j].hasbranch = false;
    ngw.grid[i][j].travel = false;
      }
   }
}



public static void AdaptiveAstar(GridWorld ngw,Square start, Square goal, char ordering){
  adaptive = true;
  Square curr;
  head =null;


     ngw.grid[start.x][start.y].g_value = 0;
     //ngw.grid[start.x][start.y].search = counter;
     ngw.grid[goal.x][goal.y].g_value = Integer.MAX_VALUE;
     heap = new BinaryHeap();
     for(int i=0;i<ngw.CAPACITY;i++){
      for(int j=0;j<ngw.CAPACITY;j++){
       ngw.grid[i][j].inHeap = false;
       ngw.grid[i][j].isClosed = false;
     }
   }
   expanded=0;
   ngw.grid[start.x][start.y].inHeap = true;
   ngw.grid[start.x][start.y].f_value = ngw.grid[start.x][start.y].g_value + ngw.grid[start.x][start.y].h_value;
   heap.add(ngw.grid[start.x][start.y], ordering);  

  while(!heap.isEmpty() && ngw.grid[goal.x][goal.y].g_value > (curr = heap.peek()).f_value){
   // System.out.println("AStar loop");
      // System.out.print("Goal G is: ");
      // System.out.println(ngw.grid[goal.x][goal.y].g_value);
      // System.out.print("Curr F is: ");
      // System.out.println(ngw.grid[curr.x][curr.y].f_value);
      //System.out.println("ABOUT TO REMOVE INDICES " + curr.x + "," + curr.y);
      heap.remove(ordering); //remove from top of heap
      //System.out.println("JUST REMOVED INDICES " + curr.x + "," + curr.y);
      ngw.grid[curr.x][curr.y].inHeap = false; //for us
      head = addNode(ngw.grid[curr.x][curr.y], head);
      ngw.grid[curr.x][curr.y].isClosed = true; //set closed
        //now, we enter addFour
     // System.out.println("ENTERING ADDFOUR FOR INDICES " + curr.x + "," + curr.y);
       adaptiveaddFour(ngw,ngw.grid[curr.x][curr.y],ordering);
     }
     if(!heap.isEmpty()){
        traverseTree(ngw,goal, start);
        traverseBranch(ngw,start,goal);
        printPath(ngw);
        System.out.println(expanded);
     }
     else{
     // System.out.println("FAIL");
      System.out.println(expanded);
     }
     return;
   }



public static void adaptiveaddFour(GridWorld ngw, Square curr, char ordering){
      // System.out.println(gw.grid[3][2].isBlocked);
      //   System.out.println(gw.grid[4][2].isBlocked);
      //   System.out.println(gw.grid[4][3].isBlocked);

      //   System.out.println(ngw.grid[3][2].isBlocked);
      //   System.out.println(ngw.grid[4][2].isBlocked);
      //   System.out.println(ngw.grid[4][3].isBlocked);

    if(ordering == 's'){
      smallexpand++;
    }
    if(ordering == 'g'){
      bigexpand++;
    }

    if(adaptive == true){
      expanded++;
    }

    if(backwards == false){
      rfexpand++;
    }
    if(backwards == true){
      rbexpand++;
    }

    int x = curr.x;
    int y = curr.y;
    int pg = 0;
    pg = curr.g_value + COST;
        //System.out.println("Starting addFour at indices " + curr.x + "," + curr.y);
        //ngw.generate();
    if(curr.x != 0){
      //    System.out.println("Checking up");
      if(!ngw.grid[x-1][y].isClosed && !gw.grid[x-1][y].isBlocked){
      //  if(ngw.grid[x-1][y].search < counter){
      //   ngw.grid[x-1][y].g_value = Integer.MAX_VALUE;
      //   ngw.grid[x-1][y].search = counter;
      // }
      if(ngw.grid[x-1][y].g_value > pg){
      //  System.out.println("g_val success");
       ngw.grid[x-1][y].g_value = pg;
       ngw.grid[x-1][y].tree = ngw.grid[x][y];
       ngw.grid[x-1][y].hastree = true;
       if(ngw.grid[x-1][y].inHeap){
                    //System.out.println("IN ADDFOUR UP, ABOUT TO REMOVE INDICES " + (x-1) + "," + y);
        heap.findRemove(ngw.grid[x-1][y],ordering);
                    //System.out.println("IN ADDFOUR UP, JUST REMOVED INDICES " + (x-1) + "," + y);
      }
      ngw.grid[x-1][y].f_value = ngw.grid[x-1][y].g_value + ngw.grid[x-1][y].h_value;
      heap.add(ngw.grid[x-1][y],ordering);
      ngw.grid[x-1][y].inHeap = true;
    }
  }
  else{
        //    System.out.println("BLOCKED =" + ngw.grid[x-1][y].isBlocked);
         //       System.out.println("CLOSED =" + ngw.grid[x-1][y].isClosed);
  }

}

if(curr.x != MAXINDEX){
     //     System.out.println("Checking down");
  if(!ngw.grid[x+1][y].isClosed && !gw.grid[x+1][y].isBlocked){
  //  if(ngw.grid[x+1][y].search < counter){
  //   ngw.grid[x+1][y].g_value = Integer.MAX_VALUE;
  //   ngw.grid[x+1][y].search = counter;
  // }
  if(ngw.grid[x+1][y].g_value > pg){
  //  System.out.println("g_val success");
   ngw.grid[x+1][y].g_value = pg;
   ngw.grid[x+1][y].tree = ngw.grid[x][y];
   ngw.grid[x+1][y].hastree = true;
   if(ngw.grid[x+1][y].inHeap){
                    //System.out.println("IN ADDFOUR DOWN, ABOUT TO REMOVE INDICES " + (x+1) + "," + y);
     heap.findRemove(ngw.grid[x+1][y],ordering);
                    //System.out.println("IN ADDFOUR DOWN, JUST REMOVED INDICES " + (x+1) + "," + y);
   }
   ngw.grid[x+1][y].f_value = ngw.grid[x+1][y].g_value + ngw.grid[x+1][y].h_value;
   heap.add(ngw.grid[x+1][y],ordering);
   ngw.grid[x+1][y].inHeap = true;
 }
}
else{
         //   System.out.println("BLOCKED =" + ngw.grid[x+1][y].isBlocked);
         //       System.out.println("CLOSED =" + ngw.grid[x+1][y].isClosed);
}

}

if(curr.y != 0){
     //     System.out.println("Checking left");
  if(!ngw.grid[x][y-1].isClosed && !gw.grid[x][y-1].isBlocked){
  //  if(ngw.grid[x][y-1].search < counter){
  //   ngw.grid[x][y-1].g_value = Integer.MAX_VALUE;
  //   ngw.grid[x][y-1].search = counter;
  // }
  if(ngw.grid[x][y-1].g_value > pg){
 //   System.out.println("g_val success");
   ngw.grid[x][y-1].g_value = pg;
   ngw.grid[x][y-1].tree = ngw.grid[x][y];
   ngw.grid[x][y-1].hastree = true;
   if(ngw.grid[x][y-1].inHeap){
                  //System.out.println("IN ADDFOUR LEFT, ABOUT TO REMOVE INDICES " + x + "," + (y-1));
    heap.findRemove(ngw.grid[x][y-1],ordering);
                  //System.out.println("IN ADDFOUR LEFT, JUST REMOVED INDICES " + x + "," + (y-1));
  }
  ngw.grid[x][y-1].f_value = ngw.grid[x][y-1].g_value + ngw.grid[x][y-1].h_value;
  heap.add(ngw.grid[x][y-1],ordering);
  ngw.grid[x][y-1].inHeap = true;
}
}
else{
         // System.out.println("BLOCKED =" + ngw.grid[x][y-1].isBlocked);
            //  System.out.println("CLOSED =" + ngw.grid[x][y-1].isClosed);
}

}

if(curr.y != MAXINDEX){
    //    System.out.println("Checking right");
  if(!ngw.grid[x][y+1].isClosed && !gw.grid[x][y+1].isBlocked){
  //  if(ngw.grid[x][y+1].search < counter){
  //   ngw.grid[x][y+1].g_value = Integer.MAX_VALUE;
  //   ngw.grid[x][y+1].search = counter;
  // }
  if(ngw.grid[x][y+1].g_value > pg){
    //System.out.println("g_val success");
   ngw.grid[x][y+1].g_value = pg;
   ngw.grid[x][y+1].tree = ngw.grid[x][y];
   ngw.grid[x][y+1].hastree = true;
   if(ngw.grid[x][y+1].inHeap){
                  //System.out.println("IN ADDFOUR RIGHT, ABOUT TO REMOVE INDICES " + x + "," + (y+1));
     heap.findRemove(ngw.grid[x][y+1],ordering);
                  //System.out.println("IN ADDFOUR RIGHT, JUST REMOVED INDICES " + x + "," + (y+1));

   }
   ngw.grid[x][y+1].f_value = ngw.grid[x][y+1].g_value + ngw.grid[x][y+1].h_value;
   heap.add(ngw.grid[x][y+1],ordering);
   ngw.grid[x][y+1].inHeap = true;
 }
}
else{
            // System.out.println(gw.grid[3][2].isBlocked);
            // System.out.println(gw.grid[4][2].isBlocked);
            // System.out.println(gw.grid[4][3].isBlocked);

            // System.out.println(ngw.grid[3][2].isBlocked);
            // System.out.println(ngw.grid[4][2].isBlocked);
            // System.out.println(ngw.grid[4][3].isBlocked);
            //System.out.println (x + "," + (y+1));
          //      System.out.println("BLOCKED =" + ngw.grid[x][y+1].isBlocked);
        //  System.out.println("CLOSED =" + ngw.grid[x][y+1].isClosed);
}
}

}

































}