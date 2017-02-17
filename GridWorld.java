import Square.java;

public class GridWorld{
	Square[][] grid;

	public GridWorld(){
		this.grid = new Square[101][101];
	}

	public void populate(){
		for(int i=0;i<101;i++){
			for(int j=0;j<101;j++){
				this.grid[i][j].x_coordinate = i;
				this.grid[i][j].y_coordinate = j;
			}
		}
		return;
	}


//a way to modulo this perhaps to determine the place?

}
