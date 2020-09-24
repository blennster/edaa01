package queue_singlelinkedlist;
import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**	
	 * Inserts the specified element into this queue, if possible
	 * post:	The specified element is added to the rear of this queue
	 * @param	e the element to insert
	 * @return	true if it was possible to add the element 
	 * 			to this queue, else false
	 */
	public boolean offer(E e) {
		QueueNode<E> node = new QueueNode<>(e);

		if (this.size == 0)
		    node.next = node;
		else {
			node.next = this.last.next;
			this.last.next = node;
		}

		this.last = node;
		this.size++;
		return true;
	}
	
	/**	
	 * Returns the number of elements in this queue
	 * @return the number of elements in this queue
	 */
	public int size() {		
		return size;
	}
	
	/**	
	 * Retrieves, but does not remove, the head of this queue, 
	 * returning null if this queue is empty
	 * @return 	the head element of this queue, or null 
	 * 			if this queue is empty
	 */
	public E peek() {
		if (this.size == 0) return null;
		else return this.last.next.element;
	}

	/**	
	 * Retrieves and removes the head of this queue, 
	 * or null if this queue is empty.
	 * post:	the head of the queue is removed if it was not empty
	 * @return 	the head of this queue, or null if the queue is empty 
	 */
	public E poll() {
		if (size == 0) return null;
		else {
			QueueNode<E> first = this.last.next;
			this.last.next = first.next;
			this.size--;
			return first.element;
		}
	}
	
	/**	
	 * Returns an iterator over the elements in this queue
	 * @return an iterator over the elements in this queue
	 */	
	public Iterator<E> iterator() {
		if (size == 0) return new QueueIterator(null);
		else return new QueueIterator(this.last);
	}

	/*** Appends the specified queue to this queue
	 * post: all elements from the specified queue are appended
	 * to this queue. The specified queue (q) is empty after the call.
	 * @param q the queue to append
	 * @throws IllegalArgumentException if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) {
		if (this == q) throw new IllegalArgumentException();

		// Ifall båda är tomma så gör inget.
		if (this.size == 0 && q.size == 0) return;
		// Ifall denna är tom, flytta bara över.
		else if (this.size == 0) {
			this.size = q.size;
			this.last = q.last;
		}
		// Ifall andra är tom så gör inget.
		else if (q.size == 0) return;
		// Ifall båda har element, koppla ihop början av den andra på slutet av denna.
		else {
			QueueNode<E> first = this.last.next; // Början av denna kö
			this.last.next = q.last.next; // sätt början av nästa kö i slutet av denna
			this.last = q.last; // ändra slutet till slutet av andra kön
			this.last.next = first; // länka cirkulärt igen
			this.size += q.size;
		}

		q.last = null; // töm andra kön
		q.size = 0;
	}
	
	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

	private class QueueIterator implements Iterator<E> {
	    private QueueNode<E> pos;
	    boolean hasTraversed = false; // Flagga används för att inkludera första och sista element i kön

	    private QueueIterator(QueueNode<E> pos) {
	    	this.pos = pos;
		}

		@Override
		public boolean hasNext() {
			return pos != null && !(pos == last && hasTraversed);
		}

		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			hasTraversed = true;

			this.pos = pos.next;
	    	return pos.element;
		}
	}

}
