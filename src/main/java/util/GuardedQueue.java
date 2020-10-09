package util;

public class GuardedQueue<T> {

	private Node<T> firstElement;
	private Node<T> lastElement;
	private int size;

	public void put(T element){
		Node<T> node = new Node<>(element);
		if(size==0){
			this.firstElement = node;
			this.lastElement = node;
		}else{

			Node<T> tmp = this.lastElement;
			this.lastElement = node;
			tmp.nextNode = this.lastElement;
			this.lastElement.previousNode = tmp;
		}
		++size;

	}
	public T removeFirst(){
		Node<T> tmp = firstElement;
		firstElement = tmp.nextNode;
		--size;
		return tmp.value;
	}
	public T getFirst(){
		return firstElement.value;
	}


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		Node<T> node = firstElement;
		StringBuilder sb = new StringBuilder();
		while (node!= null){
			sb.append(node.value).append(", ");
			node = node.nextNode;
		}
		if(size!=0)
			sb.setLength(sb.length()-2);
		return sb.toString();
	}

	private static class Node<T>{

		T value;
		Node<T> nextNode;
		Node<T> previousNode;

		public Node(T value){
			this.value = value;
		}

	}

}
