public class AStar{
int COST = 1; //currently

public void addFour(Square current, GridWorld gw, BinaryHeap heap, int counter){
int x = current.x;
int y = current.y;
int pg = 0;
Square left, right, up, down;

//assume there is a main heap called heap

pg = current.g_value + COST;

if(current.x!=0){
    left = gw.grid[x-1][y];
    if(!left.isBlocked){
        if(left.search < counter){
            left.search = counter;
        }
        if(left.g_value > pg){
            left.g_value = pg;
            left.tree = current;
            if(left.inHeap){
                heap.remove(left);
                left.f_value = left.g_value + left.h_value;
                heap.add(left);
            }
        }
    }
    else{
        left.isClosed = true;
    }
}

if(current.x!=100){
    right = gw.grid[x+1][y];
    if(!right.isBlocked){
        if(right.search < counter){
            right.search = counter;
        }
        if(right.g_value > pg){
            right.g_value = pg;
            right.tree = current;
            if(right.inHeap){
                heap.remove(right);
                right.f_value = right.g_value + right.h_value;
                heap.add(right);
            }
        }
    }
    else{
        right.isClosed = true;
    }
}

if(current.y!=0){
    down = gw.grid[x][y-1];
    if(!down.isBlocked){
        if(down.search < counter){
            down.search = counter;
        }
        if(down.g_value > pg){
            down.g_value = pg;
            down.tree = current;
            if(down.inHeap){
                heap.remove(down);
                down.f_value = down.g_value + down.h_value;
                heap.add(down);
            }
        }
    }
    else{
        down.isClosed = true;
    }
}


if(current.x!=100){
    up = gw.grid[x][y+1];
    if(!up.isBlocked){
        if(up.search < counter){
            up.search = counter;
        }
        if(up.g_value > pg){
            up.g_value = pg;
            up.tree = current;
            if(up.inHeap){
                heap.remove(up);
                up.f_value = up.g_value + up.h_value;
                heap.add(up);
            }
        }
    }
    else{
        up.isClosed = true;
    }
}
  return;
}


public Square Astar(Square start, Square goal, GridWorld gw, BinaryHeap heap, int counter){
    Square current;
    while(goal.g_value > (current = heap.peek()).g_value){
        heap.remove(current);
        current.isClosed = true;
        addFour(start,gw,heap,counter);
    }
    return current;
}

public void repeatedAStar(Square start, Square goal, GridWorld gw, BinaryHeap heap){
    int counter = 0;
    Square current;
    while(!sqEquals(start,goal)){
        counter++;
        start.g_value = 0;
        start.search = counter;
        start.inHeap = true;
        start.f_value = start.g_value + start.calculate_h(goal);
        heap.add(start);
        current = Astar(start, goal, gw, heap, counter);
        if(heap.isEmpty()){
            System.out.println("I cannot reach the target.");
            return;
        }
        start = current;
    }
    System.out.println("I reached the target.");
    return;
}

public boolean sqEquals(Square a, Square b){
    return ((a.x == b.x) && (a.y == b.y));
}


//g(n)=g(n.parent)+cost(n.parent,n)
//cost(n1,n2)=the movement cost from n1 to n2cost(n_1, n_2) = \text{the movement cost from }n_1\text{ to }n_2cost(n​1​​,n​2​​)=the movement cost from n​1​​ to n​2












































}