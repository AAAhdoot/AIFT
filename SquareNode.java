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

	public boolean sqEquals(Square a, Square b){
      return ((a.x == b.x) && (a.y == b.y));
    }

    public SquareNode addNode(Square square, SquareNode head){
    	SquareNode newHead = new SquareNode(square);
    	newHead.next = head;
    	return newHead;
    	//return the head of the new linked list.
    }
}