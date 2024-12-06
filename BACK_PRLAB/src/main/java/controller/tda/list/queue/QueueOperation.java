package controller.tda.list.queue;

import controller.tda.OverFlowException;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class QueueOperation<E> extends LinkedList<E> {
    private Integer top;

    public QueueOperation(Integer top) {
        this.top = top;
    }

    public Boolean verify() {
        return getSize().intValue() <= top.intValue();
    }

    public void queue(E dato) throws Exception {
        if (verify()) {
            add(dato, getSize());
        } else {
            throw new OverFlowException("Error, cola llena");
        }
    }

    public E dequeue() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, cola vacia");
        } else {
            return removeFirst();
        }
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }
}
