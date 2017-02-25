public class Searchpast{
	/*
	public static void main(String[] args){
		//create 50 gridworlds

		GridWorld[] grids = new GridWorld[50];
		for(int i = 0; i < 50; i++){
			grids[i] = new GridWorld();
			// System.out.println(i);
		}

		System.out.println("Comparing Repeated Forward A* with ties");
		//run repeated forward a * where small g favored & time
		//run repeated forward a * where large g favored & time
		System.out.println("Average time to find target in mazes:");
		System.out.println("Repeated Forward A* where small G favored: ");
		System.out.println("Repeated Forward A* where large G favored: ");
		System.out.println();
		System.out.println();
		System.out.println("Forward vs Backward Repeated A*");
		//run forward a * and time
		//run backward a* and time
		System.out.println("Average time to find target in mazes:");
		System.out.println("Repeated Forward A* : ");
		System.out.println("Repeated Backward A* : ");
		System.out.println();
		System.out.println();
		System.out.println("Repeated Forward vs Adaptive.");
		//run repeated forward a*
		//run adaptive a*
		System.out.println("Average time to find target in mazes:");
		System.out.println("Repeated Forward A* : ");
		System.out.println("Adaptive A* : ");
		System.out.println();
		System.out.println();


		//GridWorld temp = new GridWorld();
		return;
	}*/
  static GridWorld gw = new GridWorld();
  static BinaryHeap heap = new BinaryHeap();
  static int COST = 1; //currently
  static int MAXINDEX = gw.CAPACITY-1;


public static void heapSetUp(GridWorld ngw){
  // heap = new BinaryHeap();
  // int count = 0;
  // for(int i=0;i<ngw.CAPACITY;i++){
  //   for(int j=0;j<ngw.CAPACITY;j++){
  //       ngw.grid[i][j].inHeap = false;
  //       if(ngw.grid[i][j].travel == false){
  //           ngw.grid[i][j].h_value = 0;
  //           ngw.grid[i][j].f_value = 0;
  //           ngw.grid[i][j].g_value = Integer.MAX_VALUE;
  //           ngw.grid[i][j].search = 0;
  //       }
  //       else{
  //         System.out.println(count++);
  //       }
  //   }
  // }
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


public static Square traverseBranch(GridWorld ngw, Square start, Square goal){
  Square curr;
  curr = start;
  while(curr.hasbranch==true && curr!=goal){
   // System.out.println("Traversing through square:");
   // printSq("curr",curr);
    if(gw.grid[curr.branch.x][curr.branch.y].isBlocked){
      ngw.grid[curr.branch.x][curr.branch.y].isBlocked = true;
      ngw.grid[curr.branch.x][curr.branch.y].hastree = false;
      ngw.grid[curr.x][curr.y].hasbranch = false;
      System.out.println("traversing interrupted because the following branch is blocked: ");
      printSq("branch",gw.grid[curr.branch.x][curr.branch.y]);
      cleanUp(ngw);
      ngw.grid[curr.x][curr.y].isClosed = true;
      ngw.grid[curr.x][curr.y].travel = false;
      if(curr.x!=ngw.agentx && curr.y != ngw.agenty){
        ngw.grid[curr.x][curr.y].tree.isClosed = false;
        ngw.grid[curr.x][curr.y].tree.travel = true;
        return ngw.grid[curr.x][curr.y].tree;
      }
      else{
        return ngw.grid[curr.x][curr.y];
      }
    }
    curr = curr.branch; 
  }
    System.out.println("traversing branch complete, progressed forward to indices ");
    printSq("curr", curr);
    return curr;
}

  public static void addFour(GridWorld ngw, Square current,int counter, char ordering){
    int x = current.x;
    int y = current.y;
    int pg = 0;
    Square  right, up, down;

//full grid and current grid

//assume there is a main heap called heap

System.out.println("Starting addFour at indices " + current.x + "," + current.y);

    pg = current.g_value + COST;

    if(current.x!=0){
  	System.out.println("Checking up");
      if(!ngw.grid[x-1][y].isClosed && !ngw.grid[x-1][y].isBlocked){
        if(ngw.grid[x-1][y].search < counter){
          ngw.grid[x-1][y].search = counter;
        }
        if(ngw.grid[x-1][y].g_value > pg){
        //  System.out.println(ngw.grid[x-1][y].g_value + "," + pg);
          ngw.grid[x-1][y].g_value = pg;
          ngw.grid[x-1][y].tree = ngw.grid[x][y];
          ngw.grid[x-1][y].hastree = true;
          if(ngw.grid[x-1][y].inHeap){
            heap.remove(ordering);
          }
          ngw.grid[x-1][y].f_value = ngw.grid[x-1][y].g_value + ngw.grid[x-1][y].h_value;
          //System.out.println("adding square to heap ");
        //  printSq("left", ngw.grid[x-1][y]);
          heap.add(ngw.grid[x-1][y],ordering);
          ngw.grid[x-1][y].inHeap = true;
        }
        // else{
        //   System.out.println("FAIL");
        //   System.out.println(ngw.grid[x-1][y].g_value + "," + pg);
        // }
      }
else{
          System.out.println("BLOCKED =" + ngw.grid[x-1][y].isBlocked);
          System.out.println("CLOSED =" + ngw.grid[x-1][y].isClosed);
    }    }

    if(current.x!=MAXINDEX){
  	System.out.println("Checking down");
      if(!ngw.grid[x+1][y].isClosed && !ngw.grid[x+1][y].isBlocked){
        if(ngw.grid[x+1][y].search < counter){
          ngw.grid[x+1][y].search = counter;
        }
        if(ngw.grid[x+1][y].g_value > pg){
         // System.out.println(ngw.grid[x+1][y].g_value + "," + pg);
          ngw.grid[x+1][y].g_value = pg;
          ngw.grid[x+1][y].tree = ngw.grid[x][y];
          ngw.grid[x+1][y].hastree = true;
          if(ngw.grid[x+1][y].inHeap){
            heap.remove(ordering);
          }
          ngw.grid[x+1][y].f_value = ngw.grid[x+1][y].g_value + ngw.grid[x+1][y].h_value;
             //     System.out.println("adding right to heap NOWWW");
       //   System.out.println("adding square to heap ");
        //  printSq("right", ngw.grid[x+1][y]);          
          heap.add(ngw.grid[x+1][y],ordering);
          ngw.grid[x+1][y].inHeap = true;
            //      System.out.println("Right value");
        }
        // else{
        //   System.out.println("FAIL");
        //   System.out.println(ngw.grid[x+1][y].g_value + "," + pg);
        // }
      }
else{
          System.out.println("BLOCKED =" + ngw.grid[x+1][y].isBlocked);
          System.out.println("CLOSED =" + ngw.grid[x+1][y].isClosed);
    }   }

   if(current.y!=0){
  	System.out.println("Checking left");
    if(!ngw.grid[x][y-1].isBlocked && !ngw.grid[x][y-1].isClosed){
      if(ngw.grid[x][y-1].search < counter){
        ngw.grid[x][y-1].search = counter;
      }
      if(ngw.grid[x][y-1].g_value > pg){
       // System.out.println(ngw.grid[x][y-1].g_value + "," + pg);
           //   System.out.println("down g value success");
        ngw.grid[x][y-1].g_value = pg;
        ngw.grid[x][y-1].tree = ngw.grid[x][y];
        ngw.grid[x][y-1].hastree = true;
        if(ngw.grid[x][y-1].inHeap){
          heap.remove(ordering);
        }
        ngw.grid[x][y-1].f_value = ngw.grid[x][y-1].g_value + ngw.grid[x][y-1].h_value;
                //  System.out.println("adding down to heap NOWWW");
     //   System.out.println("adding square to heap ");
      //  printSq("down", ngw.grid[x][y-1]);        
        heap.add(ngw.grid[x][y-1],ordering);
        ngw.grid[x][y-1].inHeap = true;
      }
      // else{
      //    System.out.println("FAIL");
      //    System.out.println(ngw.grid[x][y-1].g_value + "," + pg);
      // }
    }else{
          System.out.println("BLOCKED =" + ngw.grid[x][y-1].isBlocked);
          System.out.println("CLOSED =" + ngw.grid[x][y-1].isClosed);
    }
  }


  if(current.y!=MAXINDEX){
  	System.out.println("Checking right");
    if(!ngw.grid[x][y+1].isBlocked && !ngw.grid[x][y+1].isClosed){
      if(ngw.grid[x][y+1].search < counter){
        ngw.grid[x][y+1].search = counter;
      }
      if(ngw.grid[x][y+1].g_value > pg){
       // System.out.println(ngw.grid[x][y+1].g_value + "," + pg);
        ngw.grid[x][y+1].g_value = pg;
        ngw.grid[x][y+1].tree = ngw.grid[x][y];
        ngw.grid[x][y+1].hastree = true;
        if(ngw.grid[x][y+1].inHeap){
          heap.remove(ordering);
        }
        ngw.grid[x][y+1].f_value = ngw.grid[x][y+1].g_value + ngw.grid[x][y+1].h_value;
         // System.out.println("adding square to heap ");
       //   printSq("up", ngw.grid[x][y+1]);        
          heap.add(ngw.grid[x][y+1], ordering);
        ngw.grid[x][y+1].inHeap = true;
      }
      // else{
      //   System.out.println("FAIL");
      //   System.out.println(ngw.grid[x][y+1].g_value + "," + pg);
      // }
    }
else{
          System.out.println("BLOCKED =" + ngw.grid[x][y+1].isBlocked);
          System.out.println("CLOSED =" + ngw.grid[x][y+1].isClosed);
    }  
  }
  System.out.println();
  ngw.generate();
  return;
  }


  public static void Astar(GridWorld ngw, Square goal, int counter, char ordering){
    Square current;
    while(ngw.grid[goal.x][goal.y].g_value > (current = heap.peek()).g_value){
   //  System.out.println("currently at the indices (" +  current.x +"," + current.y + ")");
     heap.remove(ordering);
     ngw.grid[current.x][current.y].inHeap = false;
     ngw.grid[current.x][current.y].isClosed = true;
     addFour(ngw,ngw.grid[current.x][current.y],counter,ordering);
      if(heap.isEmpty()){
        // if(ngw.grid[current.x][current.y].hastree && !ngw.grid[current.x][current.y].tree.isBlocked && 
        //   !sqEquals(ngw.grid[current.x][current.y],ngw.grid[ngw.agentx][ngw.agenty])){
        //   ngw.grid[current.x][current.y].isClosed = true;
        //   ngw.grid[current.x][current.y].tree.isClosed = false;
        //   heap.add(ngw.grid[current.x][current.y].tree, ordering);
        //   continue;
        // }
        System.out.println("HEAP IS EMPTY IN ASTAR");
        return;
     }
   }

   return;
  }

  public static void repeatedAStar(GridWorld ngw,Square start, Square goal, char ordering){
    int counter = 0;
    Square current;
      System.out.println("Starting repeatedAStar at " + start.x + "," + start.y);
    while(!sqEquals(start,goal)){
      counter++;
      //heapSetUp(ngw);
    //    System.out.println("Counter = " + counter + " Currently at indices ( " + start.x + "," + start.y + ")" );
      ngw.grid[start.x][start.y].g_value = 0;
      ngw.grid[start.x][start.y].search = counter;
      ngw.grid[start.x][start.y].inHeap = true;
      ngw.grid[start.x][start.y].f_value = start.g_value + start.calculate_h(goal);
      heap.add(ngw.grid[start.x][start.y], ordering);
      ngw.generate();
      Astar(ngw,goal,counter,ordering);
      if(heap.isEmpty()){
        System.out.println("A*'s grid");
        ngw.generate();
        System.out.println("Our grid");
        gw.generate();
       System.out.println("I cannot reach the target.");
       return;
     }
      traverseTree(goal, start);
      current = traverseBranch(ngw,start,goal);
      printSq("current",current);
      if(counter>gw.CAPACITY*gw.CAPACITY){
        gw.generate();
        System.out.println("INFINITE LOOPING");
        return;
      }
     start = current;
   }
   ngw.generate();
   System.out.println("Arrived at " + start.x + "," + start.y);
   System.out.println("I reached the target.");
   return;
  }

  public static boolean sqEquals(Square a, Square b){
    return ((a.x == b.x) && (a.y == b.y));
  }

  public static void printSq(String name, Square square){
    System.out.println(name + "=(" + square.x + "," + square.y + ")");
}

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


  }