public class Search{
	static GridWorld gw = new GridWorld();
	static BinaryHeap heap;
  	static int COST = 1; //currently
  	static int MAXINDEX = gw.CAPACITY-1;

	public static void main(String[] args){	
    // for(int i = 0; i<DIM; i++){
  	// 	for(int j = 0; j<DIM; j++){
  	// 			gw.grid[i][j] = new Square();
  	// 			gw.grid[i][j].x = i;
  	// 			gw.grid[i][j].y = j;
  	// 	}
  	// }
    /*long timesum = 0;
    long currtime = 0;
    for(int i = 0; i<50; i++){
      gw = new GridWorld();
      gw.populate();
      System.out.println(gw.CAPACITY);
      long startTime = System.currentTimeMillis();
      repeatedAStar(gw.grid[gw.agentx][gw.agenty], gw.grid[gw.targetx][gw.targety],'s');
      long endTime = System.currentTimeMillis();
      currtime = endTime - startTime;
      timesum = timesum + currtime;
  	}*/
  		gw.generate();
  		GridWorld ngw = new GridWorld(gw.agentx,gw.agenty,gw.targetx,gw.targety);
  		System.out.println("agent= " + ngw.agentx + "," + ngw.agenty + " and target= " + ngw.targetx + "," + ngw.targety);
  		long startTime = System.currentTimeMillis();
  		repeatedAStar(ngw,ngw.grid[ngw.agentx][ngw.agenty], ngw.grid[ngw.targetx][ngw.targety],'s');
  		long endTime = System.currentTimeMillis();
  		System.out.println("That took " + (endTime - startTime) + " milliseconds");
  		return;
  		//Built basic 3x3 for testing
	}

  public static void printPath(GridWorld ngw){
    Square curr = ngw.grid[ngw.agentx][ngw.agenty];
    while(curr.hasbranch == true && !sqEquals(curr,ngw.grid[ngw.targetx][ngw.targety])){
      System.out.print("(" + curr.x + "," + curr.y + ")" + "-->");
      curr = curr.branch;
    }
    System.out.print("(" + curr.x + "," + curr.y + ")");
  }

	public static void repeatedAStar(GridWorld ngw,Square start, Square goal, char ordering){
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
			ngw.grid[start.x][start.y].inHeap = true;
			ngw.grid[start.x][start.y].f_value = start.g_value + start.calculate_h(goal);
			heap.add(ngw.grid[start.x][start.y], ordering);		
 		//temporary call for later
			Astar(ngw,goal,counter,ordering);
			if(heap.isEmpty()){
				System.out.println("A*'s grid");
				ngw.generate(); 
				System.out.println("Our grid");
				gw.generate();
				System.out.println("I cannot reach the target.");
				return;
			}
 			//temporary calls for later
 			traverseTree(goal, start);
 			curr = traverseBranch(ngw,start,goal);
 			start = curr;
 		}
 		       System.out.println("A*'s grid");
        ngw.generate(); 
        System.out.println("Our grid");
        gw.generate();
   		System.out.println("Arrived at " + start.x + "," + start.y);
   		System.out.println("I reached the target.");
      printPath(ngw);
   		return;
	}

	public static void Astar(GridWorld ngw, Square goal, int counter, char ordering){
		Square curr;
		while(!heap.isEmpty() && ngw.grid[goal.x][goal.y].g_value > (curr = heap.peek()).f_value){
			// System.out.print("Goal G is: ");
			// System.out.println(ngw.grid[goal.x][goal.y].g_value);
			// System.out.print("Curr F is: ");
			// System.out.println(ngw.grid[curr.x][curr.y].f_value);

			heap.remove(ordering); //remove from top of heap
    	ngw.grid[curr.x][curr.y].inHeap = false; //for us
     	ngw.grid[curr.x][curr.y].isClosed = true; //set closed
     		//now, we enter addFour
    	addFour(ngw,ngw.grid[curr.x][curr.y],counter,ordering);
		}
		return;
	}

	public static void addFour(GridWorld ngw, Square curr,int counter, char ordering){
		int x = curr.x;
    	int y = curr.y;
    	int pg = 0;
    	pg = curr.g_value + COST;
    	System.out.println("Starting addFour at indices " + curr.x + "," + curr.y);
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
            			heap.remove(ordering);
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
            			heap.remove(ordering);
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
            			heap.remove(ordering);
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
    	//	System.out.println("Checking right");
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
            			heap.remove(ordering);
          			}
          			ngw.grid[x][y+1].f_value = ngw.grid[x][y+1].g_value + ngw.grid[x][y+1].h_value;
        			heap.add(ngw.grid[x][y+1],ordering);
          			ngw.grid[x][y+1].inHeap = true;
        		}
    		}
    		else{
    			//System.out.println("BLOCKED =" + ngw.grid[x][y+1].isBlocked);
          	//	System.out.println("CLOSED =" + ngw.grid[x][y+1].isClosed);
    		}
    	}

    }

    public static void cleanUp(GridWorld ngw){
    	for(int i=0;i<ngw.CAPACITY;i++){
    		for(int j=0;j<ngw.CAPACITY;j++){
    			if(ngw.grid[i][j].travel == false){
    				ngw.grid[i][j].isClosed = false;
    			}
    		}
    	}
    	return;
    }

	public static Square traverseBranch(GridWorld ngw, Square start, Square goal){
		Square curr;
		curr = start;
		while(curr.hasbranch == true && curr != goal){
			if(gw.grid[curr.branch.x][curr.branch.y].isBlocked){
				ngw.grid[curr.branch.x][curr.branch.y].isBlocked = true;
				ngw.grid[curr.branch.x][curr.branch.y].hastree = false;
				ngw.grid[curr.x][curr.y].hasbranch = false;
				System.out.println("traversing interrupted because the following branch is blocked: ");
				printSq("branch",gw.grid[curr.branch.x][curr.branch.y]);
				ngw.generate();
        return ngw.grid[curr.x][curr.y];
			}
			curr = curr.branch;
		}
		System.out.println("traversing branch complete, progressed forward to indices ");
    	printSq("curr", ngw.grid[curr.x][curr.y]);
    	return ngw.grid[curr.x][curr.y];
	}

	public static void printSq(String name, Square square){
    	System.out.println(name + "=(" + square.x + "," + square.y + ")");
	}

	public static boolean sqEquals(Square a, Square b){
    	return ((a.x == b.x) && (a.y == b.y));
  	}

  	public static void traverseTree(Square end, Square start){
  		Square curr;
  		curr = end;
  		System.out.println("Returning from square ");
  		printSq("end", end);
  		System.out.println("To square ");
  		printSq("start",start);
  		System.out.println(curr.hastree);
  		while(curr.hastree==true && curr!=start){
  			curr.tree.branch = curr;
  			curr = curr.tree;
  			curr.hasbranch = true;
  		}
  		System.out.println("traversing tree complete, backtracked to indices ");
  		printSq("curr", curr);
  	}
}