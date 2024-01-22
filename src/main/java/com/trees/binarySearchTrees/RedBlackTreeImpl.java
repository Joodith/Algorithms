package com.trees.binarySearchTrees;

class RedBlackTree<K,V> {
    Node<K, V> root;
    int size;

    class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right, parent;
        boolean isLeftChild, black;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = parent = null;
            isLeftChild = false;
            black = false;
        }
    }

    public void add(K key, V value) {
        Node<K, V> node = new Node<K, V>(key, value);
        if (root == null) {
            root = node;
            root.black = true;
            size++;
            return;
        }
        add(root, node);
        size++;

    }

    private void add(Node<K, V> parent, Node<K, V> newNode) {
        if (((Comparable<K>) (newNode.key)).compareTo(parent.key) > 0) {
            if (parent.right == null) {
                parent.right = newNode;
                newNode.parent = parent;
                newNode.isLeftChild = false;
                return;
            }
            add(parent.right, newNode);

        } else {
            if (parent.left == null) {
                parent.left = newNode;
                newNode.parent = parent;
                newNode.isLeftChild = true;
                return;
            }
            add(parent.left, newNode);

        }
        checkColor(newNode);

    }

    private void checkColor(Node<K, V> node) {
        if (node == root) {
            node.black=true;
            return;
        }
        if (!node.black && !node.parent.black) {
            correctTree(node);
        }
        checkColor(node.parent);
    }

    private void correctTree(Node<K, V> node) {  // Performs rotation or color flip based on aunt's color
        if (node.parent.isLeftChild) {
            if (node.parent.parent != null) {
                if (node.parent.parent.right == null || node.parent.parent.right.black) {    //aunt is black node and at right of grandparent
                    rotate(node);
                    return;
                }
                node.parent.parent.right.black = true;
                node.parent.parent.black = false;
                node.parent.black = true;
            }
        } else {
            if (node.parent.parent != null) {
                if (node.parent.parent.left == null || node.parent.parent.left.black) {     //aunt is black node and at left of grandparent
                    rotate(node);
                    return;
                }
                node.parent.parent.left.black = true;
                node.parent.parent.black = false;
                node.parent.black = true;
            }
        }

    }

    private void rotate(Node<K, V> node) {
        if (node.isLeftChild) {
            if (node.parent.isLeftChild) {
                rightRotate(node.parent.parent);
                node.parent.black = true;
                node.black = false;
                if (node.parent.right != null) node.parent.right.black = false;
                return;
            }
            rightLeftRotate(node.parent.parent);
            node.black = true;
            node.left.black = false;
            node.right.black = false;
            return;
        }
        if (!node.parent.isLeftChild) {
            leftRotate(node.parent.parent);
            node.parent.black = true;
            node.black = false;
            if (node.parent.left != null) node.parent.left.black = false;
            return;
        }
        leftRightRotate(node.parent.parent);
        node.black = true;
        node.left.black = false;
        node.right.black = false;
        return;

    }

    private void rightLeftRotate(Node<K, V> node) {
        rightRotate(node.right);
        leftRotate(node);
    }

    private void leftRightRotate(Node<K, V> node) {
        leftRotate(node.left);
        rightRotate(node);
    }

    private void leftRotate(Node<K, V> node) {  //Here node is the grandparent node of the right side three nodes rotation
        Node<K, V> temp = node.right;
        node.right = temp.left;
        if (node.right != null) {
            node.right.parent = node;
            node.right.isLeftChild = false;
        }
        if (node== root) {
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (node.isLeftChild) {
                temp.isLeftChild = true;
                temp.parent.left = temp;
            } else {
                temp.isLeftChild = false;
                temp.parent.right = temp;
            }
        }
        temp.left = node;
        node.isLeftChild = true;
        node.parent = temp;
    }

    private void rightRotate(Node<K, V> node) {  //Here node is the grandparent node of the left side three nodes rotation
        Node<K, V> temp = node.left;
        node.left = temp.right;
        if (node.left != null) {
            node.left.parent = node;
            node.left.isLeftChild = true;
        }
        if (node== root) {
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (node.isLeftChild) {
                temp.isLeftChild = true;
                temp.parent.left = temp;
            } else {
                temp.isLeftChild = false;
                temp.parent.right = temp;
            }
        }
        temp.right = node;
        node.isLeftChild = false;
        node.parent = temp;
    }

    public int height() {
        if (root == null) return 0;
        return height(root) - 1;
    }

    private int height(Node<K, V> node) {
        if (node == null) return 0;
        int leftHt = height(node.left) + 1;
        int rightHt = height(node.right) + 1;
        return Math.max(leftHt, rightHt);
    }

    public int blackNodes(Node<K, V> node) throws Exception {   //returns black nodes including the null node at any given path
        if (node == null) return 1;
        int leftBlackNodes = blackNodes(node.left);
        int rightBlackNodes = blackNodes(node.right);
        if (leftBlackNodes != rightBlackNodes)
            throw new Exception("The number of black nodes from root varies along different paths!");
        if (node.black) leftBlackNodes++;
        return leftBlackNodes;

    }

    public void inorderTraversal(Node<K,V>node){
        if(node!=null){
            inorderTraversal(node.left);
            System.out.printf("Node with key : %d | value : %d | colour : %s | child : %s\n",node.key,node.value,node.black?"B":"R",node.isLeftChild?"left":"right");
            inorderTraversal(node.right);
        }

    }



}
public class RedBlackTreeImpl{
    public static void main(String[] args) throws Exception {
        RedBlackTree<Integer,Integer> redBlackTree=new RedBlackTree<>();
        int[] arrKeys = {1, 4, 6, 3, 5, 7, 8, 2, 9};
        int[] arrValues = {1, 4, 6, 3, 5, 7, 8, 2, 9};
        for(int i=0;i<arrKeys.length;i++){
            redBlackTree.add(arrKeys[i],arrValues[i]);
        }
        System.out.println("Height of the Red Black tree : "+redBlackTree.height());
        System.out.println("No.of black nodes from root along any path to leaf : "+redBlackTree.blackNodes(redBlackTree.root));
        redBlackTree.inorderTraversal(redBlackTree.root);
    }
}
