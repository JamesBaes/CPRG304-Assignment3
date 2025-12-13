package implementations;

import java.io.Serializable;

/**
 * @author Nathanael Lee
 * @version 1.0 Dec. 9, 2025
 * Class Description: 
 * The BSTreeNode that stores each individual element in the tree. 
 */

public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private E Element;
	private BSTreeNode<E> left, right, parent;
	
	public BSTreeNode(E item) {
		Element = item;
		left = null;
		right = null;
	}
	
	//gets the height of the node
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
	
	//deletes itself and the nodes connected below it
	void clear() {
		if (left != null) {
			left.clear();			
		}
		if (right != null) {
			right.clear();		
		}
		delete();
	}
	
	//deletes only itself
	void delete() {
		left = null;
		right = null;
		Element = null;
	}
	
	void setElement(E Element) {
		this.Element = Element; 
	}
	
	void setRight(BSTreeNode<E> Node) {
		left = Node; 
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
