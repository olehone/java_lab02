package task1;
public class Node<T> {
    private final T data;
    private Node<T> next;
    public Node(final T data, final Node<T> next){
        this.data = data;
        this.next = next;
    }
    public T getData()
    {
        return data;
    }
    public Node<T> getNext() {
        return next;
    }
}
