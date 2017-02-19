public class GridWorld{
	Square[][] grid;

	public GridWorld(){
		this.grid = new Square[101][101];
	}

	public void populate(){
		int chance = 0;
		for(int i=0;i<101;i++){
			for(int j=0;j<101;j++){
				this.grid[i][j].isBlocked = false;
				this.grid[i][j].x = i;
				this.grid[i][j].y = j;
				// with 30% probability mark unblocked
				// with 70% probability mark unblocked
				chance = (int)(Math.random() * 101);
				if(chance <= 30){
					this.grid[i][j].isBlocked = true;
				}
				chance = 0;
			}
		}
		return;
	}


//a way to modulo this perhaps to determine the place?

}
