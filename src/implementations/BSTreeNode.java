package implementations;

import java.io.Serializable;

/**
 * The BSTreeNode that store each individual element in the tree.
 * 
 * Each node contains an element of generic type E and maintains references
 * to its left child, right child, and parent node.
 * 
 * @param <E> the type of element stored in this node
 * @author Nathanael Lee, James Baes, Tony Do, Eian Verastigue
 * @version 1.0 Dec. 9, 2025
 */
public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private E Element;
	private BSTreeNode<E> left, right, parent;
	
	
	/**
	 * Constructor for the BSTreeNode
	 * 
	 * @param item the element to be stored in this node
	 */
	public BSTreeNode(E item) {
		Element = item;
		left = null;
		right = null;
	}
	
	/**
	 * Gets the height of the node
	 * 
	 * @param node the provided BSTreeNode
	 * @return the height
	 */
	int height(BSTreeNode<E> node) {
        if (node == null) {
            return 0; 
        } 
        else {
            int left = height(node.left);
            int right = height(node.right);
            return Math.max(left, right) + 1; //+1 to include the root 
        }
    }
	
	/**
	 * Deletes itself and the nodes connected below it
	 */
	void clear() {
		if (left != null) {
			left.clear();			
		}
		if (right != null) {
			right.clear();		
		}
		delete();
	}
	
	/**
	 * Sets all attributes of the BSTreeNode to null;
	 */
	void delete() {
		left = null;
		right = null;
		Element = null;
	}
	
	// getters and setters
	void setElement(E Element) {
		this.Element = Element; 
	}
	
	void setRight(BSTreeNode<E> Node) {
		right = Node; 
	}
	
	void setRight(E Element) {
		right = new BSTreeNode<E>(Element);
	}
	
	void setLeft(BSTreeNode<E> Node) {
		left = Node; 
	}
	
	void setLeft(E Element) {
		left = new BSTreeNode<E>(Element);
	}
	
	public void setParent(BSTreeNode<E> parent) {
		this.parent = parent;
	}
	
	public E getElement() {
		return Element; 
	}
	
	BSTreeNode<E> getRight() {
		return right; 
	}
	
	BSTreeNode<E> getLeft() {
		return left; 
	}

	public BSTreeNode<E> getParent() {
		return parent;
	}


	

}
