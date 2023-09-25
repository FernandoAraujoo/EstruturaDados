import java.util.LinkedList;
import java.util.Queue;

enum Color {
    RED, BLACK
}

class Node<T extends Comparable<T>> {
    T data;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    Color color;

    public Node(T data) {
        this.data = data;
        this.color = Color.RED; // Novos nós são sempre vermelhos por padrão
    }
}

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;
    private Node<T> nil; // Sentinel node

    public RedBlackTree() {
        nil = new Node<>(null);
        nil.color = Color.BLACK;
        root = nil;
    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = root;
        Node<T> parent = null;
    
        // Verifica se a árvore está vazia (caso especial)
        if (root == nil) {
            root = newNode;
            newNode.color = Color.BLACK; // O primeiro nó inserido deve ser preto
            return;
        }
    
        while (current != nil) {
            parent = current;
            if (data.compareTo(current.data) < 0)
                current = current.left;
            else
                current = current.right;
        }
    
        newNode.parent = parent;
    
        if (data.compareTo(parent.data) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;
        
        // Correção da árvore Red-Black após inserção
        if (newNode != root) {
            fixInsert(newNode);
        }
    }
    

    private void fixInsert(Node<T> node) {
        while (node != root && node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                Node<T> uncle = node.parent.parent.right;

                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node<T> uncle = node.parent.parent.left;

                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }

    public void levelOrderTraversal() {
        if (root == nil) {
            System.out.println("A árvore está vazia.");
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            System.out.println(current.data + " (" + current.color + ")");
            if (current.left != nil)
                queue.offer(current.left);
            if (current.right != nil)
                queue.offer(current.right);
        }
    }

    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node<T> node) {
        if (node != nil) {
            inOrderTraversal(node.left);
            System.out.println(node.data + " (" + node.color + ")");
            inOrderTraversal(node.right);
        }
    }

    public Node<T> search(T data) {
        Node<T> current = root;

        while (current != nil) {
            if (data.compareTo(current.data) == 0)
                return current;
            else if (data.compareTo(current.data) < 0)
                current = current.left;
            else
                current = current.right;
        }

        return null;
    }

    public void remove(T data) {
        Node<T> nodeToRemove = search(data);
        if (nodeToRemove == null) {
            System.out.println("O nó não foi encontrado na árvore.");
            return;
        }

        removeNode(nodeToRemove);
    }

    private void removeNode(Node<T> node) {
        // Implemente a remoção preguiçosa (lazy deletion) aqui
        // Esta parte da implementação depende dos requisitos específicos da aplicação.
    }

    private void leftRotate(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;

        if (y.left != nil)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null)
            root = y;
        else if (x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;

        if (x.right != nil)
            x.right.parent = y;

        x.parent = y.parent;

        if (y.parent == null)
            root = x;
        else if (y == y.parent.right)
            y.parent.right = x;
        else
            y.parent.left = x;

        x.right = y;
        y.parent = x;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        tree.insert(10);
        tree.insert(5);
        tree.insert(20);
        tree.insert(3);
        tree.insert(7);
        tree.insert(15);
        tree.insert(25);

        System.out.println("Passeio por nível:");
        tree.levelOrderTraversal();

        System.out.println("\nPasseio em ordem:");
        tree.inOrderTraversal();

        int searchKey = 7;
        Node<Integer> foundNode = tree.search(searchKey);
        if (foundNode != null) {
            System.out.println("\nBusca pelo valor " + searchKey + ": encontrado (" + foundNode.color + ")");
        } else {
            System.out.println("\nBusca pelo valor " + searchKey + ": não encontrado");
        }

        int removeKey = 7;
        tree.remove(removeKey);
        System.out.println("\nApós a remoção do valor " + removeKey + ":");
        tree.levelOrderTraversal();
    }
}
