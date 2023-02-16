import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Serhan Asad
 * @version 1.0
 * @userid sasad3
 * @GTID 903731776
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data is null");
        }
        size = 0;
        for (T tmp : data) {
            add(tmp);
        }
    }
    

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data to be added
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Helper method for add(T data)
     *
     * @param data data to be added
     * @param node root of a tree
     */
    private void addHelper(T data, BSTNode<T> node) {
        if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, node.getRight());
            }
        } else if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, node.getLeft());
            }
        }
    }



    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        BSTNode<T> parent = null;
        BSTNode<T> current = root;
        while (current != null) {
            int cmp = data.compareTo(current.getData());
            if (cmp == 0) {
                // Found the node to be deleted
                if (current.getLeft() == null && current.getRight() == null) {
                    // Case 1: current is a leaf node
                    if (parent == null) {
                        // current is the root node
                        root = null;
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                    size--;
                    return current.getData();
                } else if (current.getLeft() == null) {
                    // Case 2: current has only a right child
                    if (parent == null) {
                        // current is the root node
                        root = current.getRight();
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(current.getRight());
                    } else {
                        parent.setRight(current.getRight());
                    }
                    size--;
                    return current.getData();
                } else if (current.getRight() == null) {
                    // Case 2: current has only a left child
                    if (parent == null) {
                        // current is the root node
                        root = current.getLeft();
                    } else if (parent.getLeft() == current) {
                        parent.setLeft(current.getLeft());
                    } else {
                        parent.setRight(current.getLeft());
                    }
                    size--;
                    return current.getData();
                } else {
                    // Case 3: current has two children
                    BSTNode<T> successorParent = current;
                    BSTNode<T> successor = current.getRight();
                    while (successor.getLeft() != null) {
                        successorParent = successor;
                        successor = successor.getLeft();
                    }
                    current.setData(successor.getData());
                    if (successorParent.getLeft() == successor) {
                        successorParent.setLeft(successor.getRight());
                    } else {
                        successorParent.setRight(successor.getRight());
                    }
                    size--;
                    return data;
                }
            } else if (cmp < 0) {
                parent = current;
                current = current.getLeft();
            } else {
                parent = current;
                current = current.getRight();
            }
        }
        throw new java.util.NoSuchElementException("Data is not in the tree.");
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot run get on null data.");
        }
        return getHelper(data, root);
    }

    /**
     * recursive helper method for get
     * @param data data to get
     * @param node node to check 
     * @return data of BST that equals the data passed in
     */
    private T getHelper(T data, BSTNode<T> node) {
        if (node == null) {
            throw new java.util.NoSuchElementException("data not found");
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return getHelper(data, node.getLeft());
            } else if (data.compareTo(node.getData()) > 0) {
                return getHelper(data, node.getRight());
            }
        }
        return node.getData();
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if in the tree, false if not
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }

        return containsHelper(root, data);
    }

    /**
     *
     * @param node refers to the current node
     * @param data the data we search for in the tree
     * @return true if data is in the tree, false otherwise
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else {
            if (data.equals(node.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) > 0) {
                boolean value = containsHelper(node.getRight(), data);
                return value;
            } else {
                boolean value = containsHelper(node.getLeft(), data);
                return value;
            }
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        return preOrderHelper(root, list);
    }

    /**
     * recursive helper method for pre order traversal
     * @param node node to examine for data
     * @param list list to add data to
     * @return list containing data in the BST
     */
    private List<T> preOrderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            list.add(node.getData());
            preOrderHelper(node.getLeft(), list);
            preOrderHelper(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        return inOrderHelper(root, list);
    }

    /**
     * recursive helper method for in order traversal
     * @param node node to examine for data
     * @param list list to add data to
     * @return list containing data in the BST
     */
    private List<T> inOrderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            inOrderHelper(node.getLeft(), list);
            list.add(node.getData());
            inOrderHelper(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        return postOrderHelper(root, list);
    }

    /**
     * recursive helper method for post order traversal
     * @param node node to examine for data
     * @param list list to add data to
     * @return list containing data in the BST
     */
    private List<T> postOrderHelper(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            postOrderHelper(node.getLeft(), list);
            postOrderHelper(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> data = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> tmp = queue.poll();
            data.add(tmp.getData());
            if (tmp.getLeft() != null) {
                queue.add(tmp.getLeft());
            }
            if (tmp.getRight() != null) {
                queue.add(tmp.getRight());
            }
        }
        return data;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height()
     *
     * @param node the leaf to start finding height
     * @return height the node
     */
    private int heightHelper(BSTNode<T> node) {
        return node == null ? -1
            : Math.max(heightHelper(node.getLeft()),
                heightHelper(node.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k must be between 0 and the size of the BST.");
        }
        List<T> kLargestList = new ArrayList<>();
        kLargestHelper(root, k, kLargestList);
        return kLargestList;
    }
    /**
     * Helper method for kLargest
     *
     * @param node the root node of the BST or a subtree of the BST
     * @param k the number of largest elements to find
     * @param kLargestList a list that stores the k largest elements in descending order
     */
    private void kLargestHelper(BSTNode<T> node, int k, List<T> kLargestList) {
        if (node != null && kLargestList.size() < k) {
            kLargestHelper(node.getRight(), k, kLargestList);
            if (kLargestList.size() < k) {
                kLargestList.add(0, node.getData());
            }
            if (kLargestList.size() < k) {
                kLargestHelper(node.getLeft(), k, kLargestList);
            }
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
