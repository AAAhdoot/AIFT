public class GridWorld{
	int CAPACITY = 101;
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
		//long startTime = System.currentTimeMillis();
		populate();
		//long endTime = System.currentTimeMillis();
		//System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}
	public GridWorld(int agentx, int agenty, int targetx, int targety){
		this.grid = new Square[CAPACITY][CAPACITY];
		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j] = new Square();
			}
		}
		this.agentx = agentx;
		this.agenty = agenty;
		this.targetx = targetx;
		this.targety = targety;
		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j].isBlocked = false;
				this.grid[i][j].x = i;
				this.grid[i][j].y = j;
			}
		}
	}

	public void populate(){
		int chance = 0;
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
			agentx = (int) (Math.random()*CAPACITY);
			agenty = (int) (Math.random()*CAPACITY);
		} while (this.grid[agentx][agenty].isBlocked == true);

		
		do{
			targetx = (int) (Math.random()*CAPACITY);
			targety = (int) (Math.random()*CAPACITY);
		} while ((agentx == targetx && agenty == targety) || this.grid[targetx][targety].isBlocked == true);
		
		//System.out.println(numblocked);
		this.grid[targetx][targety].travel = true;

		return;
	}

public void generate() {
        for (int i = 0; i < CAPACITY; i++) {
            // draw the north edge
            for (int j = 0; j < CAPACITY; j++) {
                //System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
                System.out.print("+-");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < CAPACITY; j++) {
                //System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
                
                if(j == agenty && i ==agentx ){
                    System.out.print("|A");
                }
                else if(j==targety && i == targetx){
                    System.out.print("|T");
                }
                // else if(j==i){
                //     System.out.print("|*");
                // }
                else{

                	if(this.grid[i][j].isBlocked==true){
                    	System.out.print("|B");
                	}
                	else if(this.grid[i][j].travel==true){
                		System.out.print("|*");
                	}
                	else{
                		System.out.print("| ");
                	}
                }
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < CAPACITY; j++) {
            System.out.print("+-");
        }
        System.out.println("+");
    }




	public void display(){
		System.out.println();
		System.out.print("  ");
		for(int k = 0;k<CAPACITY;k++){
				System.out.print(k + " ");
			}
			System.out.println();
			System.out.print(0 + " ");
		for(int i=0;i<CAPACITY;i++){
			if(i>0){
				System.out.println();
				System.out.print(i + " ");

			}
			for(int j=0;j<CAPACITY;j++){
				if(this.grid[i][j].isBlocked){
					System.out.print("B ");
				}
				else{
					if (i == agentx && j == agenty){
						System.out.print("A ");
					}
					else if(i == targetx && j == targety){
						System.out.print("T ");
					}
					else{
						System.out.print("U ");
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}
	
//a way to modulo this perhaps to determine the place?

}
