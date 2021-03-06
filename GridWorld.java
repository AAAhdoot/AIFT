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
		populate();
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
				this.grid[i][j].h_value = Math.abs(i - targetx) + Math.abs(j - targety);
				this.grid[i][j].x = i;
				this.grid[i][j].y = j;
			}
		}
	}

	public GridWorld(GridWorld prev){
		this.grid = new Square[CAPACITY][CAPACITY];
		this.agentx = prev.agentx;
		this.agenty = prev.agenty;
		this.targetx = prev.targetx;
		this.targety = prev.targety;
		int temp;
		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j] = new Square();
				this.grid[i][j].h_value = Math.abs(i - this.targetx) + Math.abs(j - this.targety);
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
		

		for(int i=0;i<CAPACITY;i++){
			for(int j=0;j<CAPACITY;j++){
				this.grid[i][j].h_value = Math.abs(i - targetx) + Math.abs(j - targety);
			}
		}

		return;
	}


public void generate() {
        for (int i = 0; i < CAPACITY; i++) {
            // draw the north edge
            for (int j = 0; j < CAPACITY; j++) {
                System.out.print("+-");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < CAPACITY; j++) {
                
                if(j == agenty && i ==agentx ){
                    System.out.print("|A");
                }
                else if(j==targety && i == targetx){
                    System.out.print("|T");
                }

                else{

                	if(this.grid[i][j].isBlocked==true){
                    	System.out.print("|B");
                	}
                	else if(this.grid[i][j].travel==true){
                		this.grid[i][j].travel = false;
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
	
//a way to modulo this perhaps to determine the place?

}
