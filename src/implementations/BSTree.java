package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import utilities.BSTreeADT;
import utilities.Iterator;

/**
 * The implementation for the utility class BSTreeADT.java using the BSTreeADT.java and Iterator.java interfaces
 * using a BSTreeNode.java class to store each individual element in the tree. 
 * 
 * @param <E> the type of elements stored in this tree.
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.2 Dec. 12, 2025
 */

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	BSTreeNode<E> root;
	private int size;
	
	/**
	 * Construct for an empty BST
	 */
	public BSTree() {
		root = null;
		size = 0;
	}
	
	/**
	 * Constructor for a BST with a single element
	 * 
	 * @param element the element to be stored as the root of the BST
	 */
	public BSTree(E element) {
		size = 0;
		add(element);
	}
	
	
	/**
	 * Returns the root node of this BST.
	 * 
	 * @return the root node of this tree.
	 * @throws NullPointerException if the tree is empty.
	 */
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException {
		if (root == null) {
			throw new NullPointerException();
		}
		return root;
	}

	
	/**
	 * Returns the height of this BST.
	 * 
	 * @return the height of the tree.
	 */
	@Override
	public int getHeight() {
		if (root == null) {
			return 0;
		}
		return root.height(root);
	}

	/**
	 * Returns the number of elements currently stored in this tree
	 * 
	 * @return the number elements in the tree
	 */
	@Override
	public int size() {
		return size;
	}

	
	/**
	 * Checks if the tree is empty.
	 * 
	 * @return true if empty, false if not empty.
	 */
	@Override
	public boolean isEmpty() {
		if (root == null && size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Removes all element from this tree
	 */
	@Override
	public void clear() {
		root.clear();
		size = 0;
	}
	
	
	/**
	 * Checks for if the tree contains the specified element.
	 * 
	 * @param entry the element to search for in the tree
	 * @return true if the element is found in the tree, false if not.
	 * @throws NullPointerException if the specified entry is null.
	 * 
	 */
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

	
	/**
	 * Searches for the specified element in this tree.
	 * 
	 * @param entry the element to search for
	 * @return the node containing the specified element
	 * @throws NullPointerException if the specified entry is null.
	 */
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

	
	/**
	 * Adds a new element to this BST
	 * 
	 * @param newEntry the element to be added
	 * @return true if the element was successfully added
	 * @throws NullPointerException if the provided entry is null
	 */
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

	
	/**
	 * Removes the node containing the minimum element in this tree.
	 * 
	 * @return a new node containing the minimum element.
	 */
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
	
	
	/**
	 * Removes the node containing the maximum element in this tree.
	 * 
	 * @return a new node containing the maximum element.
	 */
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
	
	
	/**
	 * Returns an iterator that traverse the tree using inorder traversal.
	 * 
	 * @return an iterator for indorder traversal
	 */
	@Override
	public Iterator<E> inorderIterator() {
		return new Traverser("InOrder");
	}
	
	
	/**
	 * Returns an iterator that traverses the tree using preoder traversal.
	 * 
	 * @return an iterator for preorder traversal.
	 */
	@Override
	public Iterator<E> preorderIterator() {
		return new Traverser("PreOrder");
	}
	
	
	/**
	 * Returns an iterator that traverses the tree using postorder traversal.
	 * 
	 * @return an iterator for postorder traversal.
	 */
	@Override
	public Iterator<E> postorderIterator() {
		return new Traverser("PostOrder");
	}

	
	/**
	 * An inner class that implements an iterator for traversing the BST.
	 */
	private class Traverser implements Iterator<E> {
		ArrayList<E> elementList;
		int index;
		String type;
		
		
		/**
		 * Creates a new traverser for the specified traversal type.
		 * 
		 * @param type the type of traversal.
		 */
	    public Traverser(String type) {
	    	elementList = new ArrayList<>();
	    	index = 0;
	    	this.type = type;
	    	findOrder(root);
	    }

	    
	    /**
	     * Recursive helper function to collect elements from the tree in the specified traversal order.
	     * 
	     * @param node the current node being visited in the traversal
	     */
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
	    
	    /**
	     * Checks whether there are more elements to iterate over
	     * 
	     * @return true if there are more elements to iterate over
	     */
	    @Override
	    public boolean hasNext() {
	        return index < size();
	    }
	    
	    /**
	     * Returns the next element in the iteration sequence.
	     * 
	     * @return the next element in the traversal order
	     * @throws NoSuchElementException if there are no more elements to iterate.
	     */
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
