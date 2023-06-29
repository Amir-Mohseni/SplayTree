public class Node {
    long value;
    Node leftChild, rightChild, parent;

    public Node(long value) {
        this.value = value;
        this.leftChild = this.rightChild = this.parent = null;
    }
}