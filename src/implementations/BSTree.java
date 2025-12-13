package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import utilities.BSTreeADT;
import utilities.Iterator;

/**
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.2 Dec. 12, 2025
 * Class Description: 
 * The implementation for the utility class BSTreeADT.java using the BSTreeADT.java and Iterator.java interfaces
 * using a BSTreeNode.java class to store each individual element in the tree. 
 */

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	BSTreeNode<E> root;
	private int size;
	
	public BSTree() {
		root = null;
		size = 0;
	}
	
	public BSTree(E element) {
		size = 0;
		add(element);
	}
	
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (root == null) {
			throw new NullPointerException();
		}
		return root;
	}

	@Override
	public int getHeight() {
		if (root == null) {
			return 0;
		}
		return root.height(root);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (root == null && size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		root.clear();
		size = 0;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException {
		if (entry == null) {
			throw new NullPointerException();
		}
		
		BSTreeNode<E> CurrentNode = root;
		while (CurrentNode != null && CurrentNode.getElement() != null) {
			if (CurrentNode.getElement().compareTo(entry) < 0) {
				CurrentNode = CurrentNode.getRight();					
			}
			else if (CurrentNode.getElement().compareTo(entry) > 0) {
				CurrentNode = CurrentNode.getLeft();			
			}
			else if (CurrentNode.getElement().compareTo(entry) == 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException {
		if (entry == null) {
			throw new NullPointerException();
		}
		
		BSTreeNode<E> CurrentNode = root;
		while (CurrentNode != null) {
			if (CurrentNode.getElement().compareTo(entry) < 0) {
				CurrentNode = CurrentNode.getRight();
			}
			else if (CurrentNode.getElement().compareTo(entry) > 0) {
				CurrentNode = CurrentNode.getLeft();
			}
			else if (CurrentNode.getElement().compareTo(entry) == 0){
				return CurrentNode;
			}
		}
		return null;
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		if (newEntry == null) {
			throw new NullPointerException();
		}
		
		size++;
		BSTreeNode<E> CurrentNode = root;
		if (CurrentNode == null) {
			root = new BSTreeNode<E>(newEntry);
			return true;
		}
		
		while (CurrentNode != null) {
			if (CurrentNode.getElement().compareTo(newEntry) < 0) {
				if (CurrentNode.getRight() == null) {
					CurrentNode.setRight(newEntry);
					CurrentNode.getRight().setParent(CurrentNode);;
					return true;
				}
				else {
					CurrentNode = CurrentNode.getRight();
				}
			}
			else if (CurrentNode.getElement().compareTo(newEntry) > 0) {
				if (CurrentNode.getLeft() == null) {
					CurrentNode.setLeft(newEntry);
					CurrentNode.getLeft().setParent(CurrentNode);
					return true;
				}
				else {
					CurrentNode = CurrentNode.getLeft();
				}
			}
			else if (CurrentNode.getElement().compareTo(newEntry) == 0){
				if (CurrentNode.getRight() == null) {
					CurrentNode.setRight(newEntry);
					CurrentNode.getRight().setParent(CurrentNode);
					return true;
				}
				else {
					CurrentNode = CurrentNode.getRight();
				}
			}
		}
		return false;
	}

	@Override
	public BSTreeNode<E> removeMin() {
		if (size == 0) {
			return null;
		}
		else if (size > 0) {
			size--;			
		}
		
		BSTreeNode<E> SmallestNode = root;
		while (SmallestNode.getLeft() != null) {
			SmallestNode = SmallestNode.getLeft();				
		}
		
		if (SmallestNode.getRight() != null) {
			SmallestNode.getParent().setLeft(SmallestNode.getRight());
		}
		
		BSTreeNode<E> TempNode = new BSTreeNode<E>(SmallestNode.getElement());
		SmallestNode.delete();
		SmallestNode = null;
		return TempNode;
	}

	@Override
	public BSTreeNode<E> removeMax() {
		if (size == 0) {
			return null;
		}
		else if (size > 0) {
			size--;			
		}
		
		BSTreeNode<E> LargestNode = root;
		while (LargestNode.getRight() != null) {
			LargestNode = LargestNode.getRight();				
		}
		
		if (LargestNode.getRight() != null) {
			LargestNode.getParent().setRight(LargestNode.getLeft());
		}
		
		BSTreeNode<E> TempNode = new BSTreeNode<E>(LargestNode.getElement());
		LargestNode.delete();
		LargestNode = null;
		return TempNode;
	}
	
	@Override
	public Iterator<E> inorderIterator() {
		return new Traverser("InOrder");
	}
	
	@Override
	public Iterator<E> preorderIterator() {
		return new Traverser("PreOrder");
	}

	@Override
	public Iterator<E> postorderIterator() {
		return new Traverser("PostOrder");
	}

	private class Traverser implements Iterator<E> {
		ArrayList<E> elementList;
		int index;
		String type;

	    public Traverser(String type) {
	    	elementList = new ArrayList<>();
	    	index = 0;
	    	this.type = type;
	    	findOrder(root);
	    }

	    private void findOrder(BSTreeNode<E> node) {
	        if (node == null) return;
	        
	        if (type == "InOrder") {
	        	findOrder(node.getLeft());
	        	elementList.add(node.getElement());
	        	findOrder(node.getRight());
	        }
	        
	        else if (type == "PreOrder") {
		        elementList.add(node.getElement());
		        findOrder(node.getLeft());
		        findOrder(node.getRight());
	        }
	        
	        else if (type == "PostOrder") {
	        	findOrder(node.getLeft());
		        findOrder(node.getRight());
		        elementList.add(node.getElement());
	        }
	    }
	    
	    @Override
	    public boolean hasNext() {
	        return index < size();
	    }

	    @Override
	    public E next() throws NoSuchElementException {
	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }
	        E next = elementList.get(index);
	        index++;
	        return next;
	    }
	}
}
