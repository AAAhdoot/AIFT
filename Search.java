public class Search{
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

static int COST = 1; //currently

public static void addFour(Square current, GridWorld gw, BinaryHeap heap, int counter){
int x = current.x;
int y = current.y;
System.out.println(current.x + "," + current.y);
int pg = 0;
Square left, right, up, down;

//assume there is a main heap called heap

pg = current.g_value + COST;

if(current.x!=0){
	System.out.println("Checking left");
    left = gw.grid[x-1][y];
    if(!left.isBlocked){
        if(left.search < counter){
            gw.grid[x-1][y].search = counter;
        }
        if(left.g_value > pg){
            gw.grid[x-1][y].g_value = pg;
            gw.grid[x-1][y].tree = current;
            if(left.inHeap){
                heap.remove(left);
            }
                gw.grid[x-1][y].f_value = gw.grid[x-1][y].g_value + gw.grid[x-1][y].h_value;
                heap.add(gw.grid[x-1][y]);
                gw.grid[x-1][y].inHeap = true;
            }
    }
    else{
        gw.grid[x-1][y].isClosed = true;
    }
}

if(current.x!=2){
	System.out.println("Checking right");
    right = gw.grid[x+1][y];
    if(!right.isBlocked){
        if(right.search < counter){
            gw.grid[x+1][y].search = counter;
        }
        if(right.g_value > pg){
            gw.grid[x+1][y].g_value = pg;
            gw.grid[x+1][y].tree = current;
            if(right.inHeap){
                heap.remove(right);
            }
                gw.grid[x+1][y].f_value = gw.grid[x+1][y].g_value + gw.grid[x+1][y].h_value;
                heap.add(gw.grid[x+1][y]);
                gw.grid[x+1][y].inHeap = true;
                System.out.println("Right value");
        }
        System.out.println("the current location is: " + current.x + "," + current.y);
    }
    else{
         gw.grid[x+1][y].isClosed = true;
    }
}

if(current.y!=0){
	System.out.println("Checking down");
    down = gw.grid[x][y-1];
    if(!down.isBlocked){
        if(down.search < counter){
            gw.grid[x][y-1].search = counter;
        }
        if(down.g_value > pg){
            gw.grid[x][y-1].g_value = pg;
            gw.grid[x][y-1].tree = current;
            if(down.inHeap){
                heap.remove(gw.grid[x][y-1]);
            }
                gw.grid[x][y-1].f_value = gw.grid[x][y-1].g_value + gw.grid[x][y-1].h_value;
                heap.add(gw.grid[x][y-1]);
                gw.grid[x][y-1].inHeap = true;
        }
        System.out.println("the current location is: " + current.x + "," + current.y);
    }
    else{
        gw.grid[x][y-1].isClosed = true;
    }
}


if(current.y!=2){
	System.out.println("Checking up");
    up = gw.grid[x][y+1];
    if(!up.isBlocked){
        if(up.search < counter){
            gw.grid[x][y+1].search = counter;
        }
        if(up.g_value > pg){
            gw.grid[x][y+1].g_value = pg;
            gw.grid[x][y+1].tree = current;
            if(up.inHeap){
                heap.remove(gw.grid[x][y+1]);
               }
                gw.grid[x][y+1].f_value = gw.grid[x][y+1].g_value + gw.grid[x][y+1].h_value;
                heap.add(gw.grid[x][y+1]);
                gw.grid[x][y+1].inHeap = true;
                
                System.out.println(gw.grid[x][y+1].f_value);
                System.out.println(gw.grid[x][y+1].g_value);
                System.out.println(gw.grid[x][y+1].search);
                System.out.println("the current location is: " + current.x + "," + current.y);
            
        }
    }
    else{
         gw.grid[x][y+1].isClosed = true;
    }
}
  return;
}


public static Square Astar(Square goal, GridWorld gw, BinaryHeap heap, int counter){
    Square current;
    int count = 0;
    while(gw.grid[goal.x][goal.y].g_value > (current = heap.peek()).g_value){
    	System.out.println("G value is:" + gw.grid[goal.x][goal.y].g_value);
    	System.out.println("x value is:" + current.x);
    	System.out.println("y value is:" + current.y);
    	System.out.println("printing first three");
        heap.remove(gw.grid[current.x][current.y]);
        gw.grid[current.x][current.y].isClosed = true;
        addFour(gw.grid[current.x][current.y],gw,heap,counter);
    }
    return gw.grid[current.x][current.y];
}

public static void repeatedAStar(Square start, Square goal, GridWorld gw, BinaryHeap heap){
    int counter = 0;
    Square current;
    while(!sqEquals(start,goal)){
        counter++;
        gw.grid[start.x][start.y].g_value = 0;
        gw.grid[start.x][start.y].search = counter;
        gw.grid[start.x][start.y].inHeap = true;
        gw.grid[start.x][start.y].f_value = start.g_value + start.calculate_h(goal);
        heap.add(gw.grid[start.x][start.y]);
        current = Astar(goal, gw, heap, counter);
        if(heap.isEmpty()){
            System.out.println("I cannot reach the target.");
            return;
        }
        start = current;
    }
    System.out.println("I reached the target.");
    return;
}

public static boolean sqEquals(Square a, Square b){
    return ((a.x == b.x) && (a.y == b.y));
}

	public static void main(String[] args){
		GridWorld gw = new GridWorld();
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				gw.grid[i][j] = new Square();
				gw.grid[i][j].x = i;
				gw.grid[i][j].y = j;
			}
		}
		BinaryHeap heap = new BinaryHeap();
		repeatedAStar(gw.grid[0][0], gw.grid[2][2],gw,heap);
   		return;
		//Built basic 3x3 for testing
	}


}