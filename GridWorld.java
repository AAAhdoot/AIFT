public class GridWorld{
	int CAPACITY = 3;
	Square[][] grid;
	int agentx;
	int agenty;
	int targetx;
	int targety;

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
		boolean agentPlaced = false;
		int numUnblocked = 0; //stores number of unblocked cells, need at least two
		
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
					numUnblocked++;
				}

				if(i == (CAPACITY - 1) && j == (CAPACITY - 1) && numUnblocked < 2){
					i = 0;
					j = 0;
					System.out.println("Reprinting...");
					continue;
				}
				chance = 0;
				//System.out.println("Square at indices (" + i +"," + j + ") " + "is " +  this.grid[i][j].isBlocked);
			}

		}

		do{
			agentx = (int) (Math.random()*3);
			agenty = (int) (Math.random()*3);
		} while (this.grid[agentx][agenty].isBlocked == true);

		
		do{
			targetx = (int) (Math.random()*3);
			targety = (int) (Math.random()*3);
		} while ((agentx == targetx && agenty == targety) || this.grid[targetx][targety].isBlocked == true);
		
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
					if (i == agentx && j == agenty){
						System.out.print("A");
					}
					else if(i == targetx && j == targety){
						System.out.print("T");
					}
					else{
						System.out.print("U");
					}
				}
			}
		}
		System.out.println();
	}
	
//a way to modulo this perhaps to determine the place?

}
