package task1;

import java.util.Iterator;

public class StackIterator<T> implements Iterator<T> {
    private Node<T> current;

    public StackIterator(final Node<T> top) {
        this.current = top;
    }

    @Override
    public boolean hasNext() {
        return current != null; //&&current.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException("Next does`n exist");
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}
