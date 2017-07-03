package com.github.morotsman.java_playground.immutable_list;

import java.util.function.Function;


public class Empty<T> extends List<T> {

    @Override
    public boolean isEmpty() {
        
        return true;
    }
    
    @Override
    public boolean equals(Object other) {
        return this.getClass() == other.getClass();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public List map(Function fun) {
        return this;
    }

    @Override
    public String toString() {
        return "Empty{" + '}';
    }
    
    
    
    
    
}
