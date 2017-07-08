package com.github.morotsman.java_playground.immutable_list;

import java.util.Objects;
import java.util.Optional;


class Cons<T> extends List<T> {
    
    private final T value;
    private final List<T> tail;
    
    public Cons(final T value, final List<T> tail) {
        this.value = value;
        this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.value);
        hash = 29 * hash + Objects.hashCode(this.tail);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cons<?> other = (Cons<?>) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.tail, other.tail)) {
            return false;
        }
        return true;
    }

    
    

    @Override
    public String toString() {
        return "Cons{" + "value=" + value + ", tail=" + tail + '}';
    } 

    @Override
    public Optional<T> head() {
        return Optional.of(value);
    }

    @Override
    public List<T> tail() {
        return tail;
    }


    
}
