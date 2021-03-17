package common;

public class Value<T> {

    private T object;

    public Value() {
        this.object = null;
    }

    public Value(final T object) {
        this.object = object;
    }

    public T get() {
        return object;
    }

    public void set(final T object) {
        this.object = object;
    }

    public boolean isPresent() {
        return object != null;
    }

    public boolean isEmpty() {
        return object == null;
    }
}