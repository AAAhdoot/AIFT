public class Search{
	static GridWorld gw = new GridWorld();
	static BinaryHeap heap;
  static SquareNode head;
  	static int COST = 1; //currently
  	static int MAXINDEX = gw.CAPACITY-1;

  public static void printSq(String name, Square square){
      //System.out.println(name + "=(" + square.x + "," + square.y + ")");
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

	public static void main(String[] args){	
    // for(int i = 0; i<DIM; i++){
  	// 	for(int j = 0; j<DIM; j++){
  	// 			gw.grid[i][j] = new Square();
  	// 			gw.grid[i][j].x = i;
  	// 			gw.grid[i][j].y = j;
  	// 	}
  	// }
 
    long currtime = 0;
    long timesum = 0;

    GridWorld[] ourgw = new GridWorld[3];
    GridWorld[] ourngw = new GridWorld[3];
    for(int i = 0; i<ourgw.length; i++){
      ourgw[i] = new GridWorld();
      ourgw[i].populate();
      ourngw[i] = new GridWorld(ourgw[i].agentx,ourgw[i].agenty,ourgw[i].targetx,ourgw[i].targety);
    }


   

    for(int i = 0; i<ourgw.length; i++){
      long startTime = System.currentTimeMillis();
      gw = ourgw[i];
      System.out.println("LOOKING AT GRID NUMBER " + i);
      System.out.println("Starting at " + ourngw[i].agentx + "," + ourngw[i].agenty);
      System.out.println("Aiming for " + ourngw[i].targetx + "," + ourngw[i].targety);
      //gw.generate();
      repeatedForwardAStar(ourngw[i],ourngw[i].grid[ourngw[i].agentx][ourngw[i].agenty], ourngw[i].grid[ourngw[i].targetx][ourngw[i].targety],'s');
      long endTime = System.currentTimeMillis();
      currtime = endTime - startTime;
      System.out.println(currtime);
      timesum = timesum + currtime;
    }
    //System.out.println(timesum/50.0);
    System.out.println("That took " + (timesum/50.0) + " milliseconds on average for smaller g");

    for(int i = 0; i<ourngw.length; i++){
      ourngw[i] = new GridWorld(ourgw[i].agentx,ourgw[i].agenty,ourgw[i].targetx,ourgw[i].targety);
    }

     timesum = 0;
    currtime = 0;

    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();

        //Calculate for larger g
    for(int i = 0; i<ourgw.length; i++){
      long startTime = System.currentTimeMillis();
      gw = ourgw[i];
      System.out.println("LOOKING AT GRID NUMBER " + i);
      System.out.println("Starting at " + ourngw[i].agentx + "," + ourngw[i].agenty);
      System.out.println("Aiming for " + ourngw[i].targetx + "," + ourngw[i].targety);
      //gw.generate();
      System.out.println();
      System.out.println();
      repeatedForwardAStar(ourngw[i],ourngw[i].grid[ourngw[i].agentx][ourngw[i].agenty], ourngw[i].grid[ourngw[i].targetx][ourngw[i].targety],'g');
      long endTime = System.currentTimeMillis();
      currtime = endTime - startTime;
      System.out.println(currtime);
      timesum = timesum + currtime;
    }
    //System.out.println(timesum/50.0);
    System.out.println("That took " + (timesum/3.0) + " milliseconds on average for bigger g");
    //System.out.println("That took " + (timesum2/50.0) + "milliseconds on average for smaller g");
 

    

      return;
  		//gw.generate();

      //temporary, for setting up error case for us
    /*
      for(int i=0;i<5;i++){
        for(int j=0;j<5;j++){
          //initially set all unblocked
          gw.grid[i][j].isBlocked = false;
          gw.grid[i][j].travel = false;
          gw.grid[i][j].x = i;
          gw.grid[i][j].y = j;
        }
      }

      gw.agentx = 1;
      gw.agenty = 0;
      gw.targetx = 4;
      gw.targety = 0;

      gw.grid[4][0].travel = true;

      gw.grid[0][4].isBlocked = true;
      gw.grid[1][2].isBlocked = true;
      gw.grid[2][2].isBlocked = true;
      gw.grid[3][0].isBlocked = true;
      gw.grid[3][1].isBlocked = true;
      gw.grid[3][2].isBlocked = true;
      gw.grid[4][4].isBlocked = true;
      //gw.grid[4][0].isBlocked = true;
      */
//GridWorld ngw = new GridWorld(gw.agentx,gw.agenty,gw.targetx,gw.targety);

      //System.out.println("A*'s grid");
        //ngw.generate(); 
        //System.out.println("Our grid");
        //gw.generate();

  		/*System.out.println("agent= " + ngw.agentx + "," + ngw.agenty + " and target= " + ngw.targetx + "," + ngw.targety);
  		long startTime = System.currentTimeMillis();
  		repeatedAStar(ngw,ngw.grid[ngw.agentx][ngw.agenty], ngw.grid[ngw.targetx][ngw.targety],'g');
  		long endTime = System.currentTimeMillis();
  		System.out.println("That took " + (endTime - startTime) + " milliseconds");
  		return;*/
  		//Built basic 3x3 for testing
	}

	public static void repeatedForwardAStar(GridWorld ngw,Square start, Square goal, char ordering){
		int counter = 0;
		Square curr;
		while(!sqEquals(start,goal)){
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
			ngw.grid[start.x][start.y].f_value = start.g_value + start.calculate_h(goal);
			heap.add(ngw.grid[start.x][start.y], ordering);		
 		//temporary call for later
			Astar(ngw,goal,counter,ordering);
			if(heap.isEmpty()){
				//System.out.println("A*'s grid");
				//ngw.generate(); 
				//System.out.println("Our grid");
				//gw.generate();
				System.out.println("I cannot reach the target.");
        System.out.println();
				return;
			}
 			//temporary calls for later
 			traverseTree(goal, start);
 			curr = traverseBranch(ngw,start,goal);
 			start = curr;
 		}
        printPath(ngw);
 		    //System.out.println("A*'s grid");
        //ngw.generate(); 
        //System.out.println("Our grid");
        //gw.generate();
   		System.out.println("Arrived at " + start.x + "," + start.y);
      //printPath(ngw);
   		System.out.println("I reached the target.");
      System.out.println();
   		return;
	}

public static void repeatedBackwardAStar(GridWorld ngw,Square start, Square goal, char ordering){
    int counter = 0;
    Square curr;
    while(!sqEquals(goal,start)){
      counter++;
      ngw.grid[goal.x][goal.y].g_value = 0;
      ngw.grid[goal.x][goal.y].search = counter;
      start.g_value = Integer.MAX_VALUE;
      heap = new BinaryHeap();
      for(int i=0;i<ngw.CAPACITY;i++){
          for(int j=0;j<ngw.CAPACITY;j++){
            ngw.grid[i][j].inHeap = false;
            ngw.grid[i][j].isClosed = false;
          }
        }
      ngw.grid[goal.x][goal.y].inHeap = true;
      ngw.grid[goal.x][goal.y].f_value = goal.g_value + start.calculate_h(start);
      heap.add(ngw.grid[goal.x][goal.y], ordering);   
    //temporary call for later
      Astar(ngw,start,counter,ordering);
      if(heap.isEmpty()){
        System.out.println("A*'s grid");
        ngw.generate(); 
        System.out.println("Our grid");
        gw.generate();
        System.out.println("I cannot reach the target.");
        return;
      }
      //temporary calls for later
      traverseTree(start, goal);
      curr = traverseBranch(ngw,goal,start);
      goal = curr;
    }
        printPath(ngw);
        System.out.println("A*'s grid");
        ngw.generate(); 
        System.out.println("Our grid");
        gw.generate();
      System.out.println("Arrived at " + goal.x + "," + goal.y);
      printPath(ngw);
      System.out.println("I reached the target.");
      return;
  }

	public static void Astar(GridWorld ngw, Square goal, int counter, char ordering){
		Square curr;
      // System.out.println(gw.grid[3][2].isBlocked);
      // System.out.println(gw.grid[4][2].isBlocked);
      // System.out.println(gw.grid[4][3].isBlocked);

      // System.out.println(ngw.grid[3][2].isBlocked);
      // System.out.println(ngw.grid[4][2].isBlocked);
      // System.out.println(ngw.grid[4][3].isBlocked);
		while(!heap.isEmpty() && ngw.grid[goal.x][goal.y].g_value > (curr = heap.peek()).f_value){
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
      //System.out.println("ENTERING ADDFOUR FOR INDICES " + curr.x + "," + curr.y);
    	addFour(ngw,ngw.grid[curr.x][curr.y],counter,ordering);
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
		  int x = curr.x;
    	int y = curr.y;
    	int pg = 0;
    	pg = curr.g_value + COST;
    	//System.out.println("Starting addFour at indices " + curr.x + "," + curr.y);
    	//ngw.generate();
    	if(curr.x != 0){
    		//System.out.println("Checking up");
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
    			//System.out.println("BLOCKED =" + ngw.grid[x-1][y].isBlocked);
          //		System.out.println("CLOSED =" + ngw.grid[x-1][y].isClosed);
    		}

    	}

    	if(curr.x != MAXINDEX){
    		//System.out.println("Checking down");
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
    			//System.out.println("BLOCKED =" + ngw.grid[x+1][y].isBlocked);
          //		System.out.println("CLOSED =" + ngw.grid[x+1][y].isClosed);
    		}

    	}

    	if(curr.y != 0){
    		//System.out.println("Checking left");
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
    		//	System.out.println("BLOCKED =" + ngw.grid[x][y-1].isBlocked);
          //		System.out.println("CLOSED =" + ngw.grid[x][y-1].isClosed);
    		}

    	}

    	if(curr.y != MAXINDEX){
    		//System.out.println("Checking right");
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
          		  //System.out.println("BLOCKED =" + ngw.grid[x][y+1].isBlocked);
          //System.out.println("CLOSED =" + ngw.grid[x][y+1].isClosed);
    		}
    	}

    }

    public static void traverseTree(Square end, Square start){
      Square curr;
      curr = end;
      //System.out.println("Returning from square ");
      printSq("end", end);
      //System.out.println("To square ");
      printSq("start",start);
      //System.out.println(curr.hastree);
      while(curr.hastree==true && curr!=start){
        curr.tree.branch = curr;
        curr = curr.tree;
        curr.hasbranch = true;
      }
      //System.out.println("traversing tree complete, backtracked to indices ");
      printSq("curr", curr);
    }

	public static Square traverseBranch(GridWorld ngw, Square start, Square goal){
     // System.out.println(gw.grid[3][2].isBlocked);
     //  System.out.println(gw.grid[4][2].isBlocked);
     //  System.out.println(gw.grid[4][3].isBlocked);

     //  System.out.println(ngw.grid[3][2].isBlocked);
     //  System.out.println(ngw.grid[4][2].isBlocked);
     //  System.out.println(ngw.grid[4][3].isBlocked);
		Square curr;
		curr = start;
    //printPath(ngw);
		while(curr.hasbranch == true && curr != goal){
			if(gw.grid[curr.branch.x][curr.branch.y].isBlocked){
        //System.out.println(gw.grid[curr.branch.x][curr.branch.y].isBlocked);
        //System.out.println("Setting indices to blocked at: " + curr.branch.x + "," + curr.branch.y);
				ngw.grid[curr.branch.x][curr.branch.y].isBlocked = true;
				ngw.grid[curr.branch.x][curr.branch.y].hastree = false;
				ngw.grid[curr.x][curr.y].hasbranch = false;
				//System.out.println("traversing interrupted because the following branch is blocked: ");
				printSq("branch",gw.grid[curr.branch.x][curr.branch.y]);
        //printPath(ngw);
				//ngw.generate();
        // return ngw.grid[curr.x][curr.y];
        return ngw.grid[ngw.agentx][ngw.agenty];
			}
			curr = curr.branch;
		}
		//System.out.println("traversing branch complete, progressed forward to indices ");
    	printSq("curr", ngw.grid[curr.x][curr.y]);
    	return ngw.grid[curr.x][curr.y];
	}

  public static void printPath(GridWorld ngw){
    Square curr = ngw.grid[ngw.agentx][ngw.agenty];
    int count =0;
    while(curr.hasbranch == true && !sqEquals(curr,ngw.grid[ngw.targetx][ngw.targety])){
      //System.out.print("(" + curr.x + "," + curr.y + ")" + "-->");
      curr.travel = true;
      //gw.grid[curr.x][curr.y].travel = true;
      curr = curr.branch;
      count++;
    }
    ngw.generate();
    System.out.print("(" + curr.x + "," + curr.y + "): ");
    System.out.println(count + " moves to get from agent to target");
  }

}