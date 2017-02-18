import Square.java;
import GridWorld.java;
import BinaryHeap.java;

public class AStar{
int COST = 1; //currently

public Square[] A*(Square start, Square goal, GridWorld gw, BinaryHeap heap){

start.inHeap = true;
heap.add(start);
start.g_value = 0;
start.f_value = start.g_value + start.calculate_h(goal);


while(!heap.isEmpty){
	Square current = heap.peek();
	if(current.x_coordinate = goal.x_coordinate && current.y_coordinate = goal.y_coordinate){
		////return some function that puts it all together into an array
	}
	heap.remove(current);
	current.isClosed = true;


}


}


public void addFour(Square current, GridWorld gw, BinaryHeap heap){
int x = current.x_coordinate;
int y = current.y_coordinate;
int pg = 0;

//assume there is a main heap called heap

if(current.x_coordinate!=0){
    if(!gw.grid[x-1][y].isClosed){
    	pg = current.g_value + COST;
    	if(!gw.grid[x-1][y].inHeap){
        	heap.add(gw.grid[x-1][y]);
        }
    }
    
}
if(current.x_coordinate!=100){
    if(!gw.grid[x+1][y].isClosed){
        heap.add(gw.grid[x+1][y]);
    }
}
if(current.y_coordinate!=0){
    if(!gw.grid[x][y-1].isClosed){
        heap.add(gw.grid[x][y-1]);
    }
}
if(current.x_coordinate!=100){
    if(!gw.grid[x][y+1].isClosed){
        heap.add(gw.grid[x][y+1]);
    }
}
  return;
}







//g(n)=g(n.parent)+cost(n.parent,n)
//cost(n1,n2)=the movement cost from n1 to n2cost(n_1, n_2) = \text{the movement cost from }n_1\text{ to }n_2cost(n​1​​,n​2​​)=the movement cost from n​1​​ to n​2












































}