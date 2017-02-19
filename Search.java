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

	public static void main(String[] args){
		Square[][] temp = new Square[3][3];
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				temp[i][j] = new Square();
				temp[i][j].x = i;
				temp[i][j].y = j;
			}
		}
		//Built basic 3x3 for testing
	}



}