public class Square{
	boolean isClosed;
	boolean isBlocked;
	boolean inHeap;
	int place;
	int g_value;
	int h_value;
	int f_value;
	Square tree;
	int search;
	int x;
	int y;

	public Square(int x_coordinate, int y_coordinate){
		this.isClosed = false;
		this.isBlocked = false;
		this.inHeap = false;
		this.place = 0;
		this.g_value = Integer.MIN_VALUE;
		this.h_value = 0;
		this.f_value = 0;
		this.tree = null;
		this.search= 0;
		this.x = x_coordinate;
		this.y = y_coordinate;
	}

	public int calculate_h(Square goal){
		this.h_value = Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
		return this.h_value;
	}
}

// x-coordinate and y-coordinate correspond to the indices in the matrix/gridworld?!?!