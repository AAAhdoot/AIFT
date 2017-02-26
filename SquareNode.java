public class SquareNode{
	SquareNode next;
	Square square;
	public SquareNode(Square square){
		this.next = null;
		this.square = square;
	}

	public void removeNode(SquareNode head, Square square){
		SquareNode curr = head;
		while(curr.next != null){
			if(sqEquals(curr.next.square,square)){
				curr.next = curr.next.next;
				return;
			}
		}
	}

	public static boolean sqEquals(Square a, Square b){
      return ((a.x == b.x) && (a.y == b.y));
    }


}