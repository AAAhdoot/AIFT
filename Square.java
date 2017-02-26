public class Square{
	boolean isClosed;
	boolean isBlocked;
	boolean inHeap;
	int g_value;
	int h_value;
	int f_value;
	Square tree;
	boolean hastree;
	Square branch;
	Boolean hasbranch;
	int search;
	boolean travel;
	int x;
	int y;

	public Square(){
		this.isClosed = false;
		this.isBlocked = false;
		this.inHeap = false;
		this.g_value = Integer.MAX_VALUE;
		this.h_value = 0;
		this.f_value = 0;
		this.tree = null;
		this.branch = null;
		this.search= 0;
		this.x = -1;
		this.y = -1;
		this.hastree = false;
		this.hasbranch = false;
		this.travel = false;
	}

	public int calculate_h(Square goal){
		this.h_value = Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
		return this.h_value;
	}

	public void setIsBlocked(){

	}
}

// x-coordinate and y-coordinate correspond to the indices in the matrix/gridworld?!?!