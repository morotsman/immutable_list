package com.github.morotsman.java_playground.immutable_list;

import java.util.Objects;
import java.util.function.Function;


public class Cons<T> extends List<T> {
    
    final T value;
    final List<T> tail;
    
    public Cons(final T value, final List<T> tail) {
        this.value = value;
        this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }    

    @Override
    public List map(Function fun) {
        return new Cons(fun.apply(value), tail.map(fun));
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
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
    
}
