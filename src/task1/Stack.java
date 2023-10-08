package task1;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    private Node<T> top;
    public void push(final T data){
        top = new Node<>(data, top);
    }
    public T pop(){
        if(isEmpty())
            throw new IllegalStateException("Stack empty");
        final T data = top.getData();
        top = top.getNext();
        return data;
    }
    public boolean isEmpty()
    {
        return top==null;
    }
    @Override
    public Iterator<T> iterator() {
        return new StackIterator<>(top);
    }

}
