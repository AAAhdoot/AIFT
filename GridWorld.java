public class GridWorld{
	int CAPACITY = 3;
	Square[][] grid;

	public GridWorld(){
		this.grid = new Square[CAPACITY][CAPACITY];
		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j] = new Square();
			}
		}
		long startTime = System.currentTimeMillis();
		populate();
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}

	public void populate(){
		int chance = 0;
		//int numblocked = 0;
		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j].isBlocked = false;
				this.grid[i][j].x = i;
				this.grid[i][j].y = j;
				// with 30% probability mark unblocked
				// with 70% probability mark unblocked
				chance = (int)(Math.random() * 101);
				if(chance <= 30){
					this.grid[i][j].isBlocked = true;
					//System.out.println("blocking");
					//numblocked++;
				}
				else{
					//System.out.println("not");
				}
				chance = 0;
				System.out.println("Square at indices (" + i +"," + j + ") " + "is " +  this.grid[i][j].isBlocked);
			}

		}
		//System.out.println(numblocked);

		return;
	}

	public void generate(){
		System.out.println();
		for(int i=0;i<CAPACITY;i++){
			if(i>0){
				System.out.println();
			}
			for(int j=0;j<CAPACITY;j++){
				if(this.grid[i][j].isBlocked){
					System.out.print("B");
				}
				else{
					System.out.print("U");
				}
			}
		}
		System.out.println();
	}
	
//a way to modulo this perhaps to determine the place?

}
