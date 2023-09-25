public class ma extends RedBlackTree{
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