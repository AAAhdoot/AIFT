public class Square{
	boolean isVisited;
	boolean isBlocked;
	int place;
	int g_value;
	int h_value;
	int f_value;
	Square tree;
	int search_value;
	int x;
	int y;

	public Square(int x_coordinate, int y_coordinate){
		this.isVisited = false;
		this.isBlocked = false;
		this.place = 0;
		this.g_value = Integer.min_value;
		this.h_value = 0;
		this.f_value = 0;
		this.tree = null;
		this.search_value = 0;
		this.x = x_coordinate;
		this.y = y_coordinate;
	}

	public void calculate_h(Square goal){
		this.h_value = Math.abs(this.x - goal.x) + Math.abs(this.y- goal.y);
		return;
	}
}

// x-coordinate and y-coordinate correspond to the indices in the matrix/gridworld?!?!