package com.github.morotsman.java_playground.immutable_list;

import java.util.Optional;


class Empty<T> extends List<T> {

    @Override
    public boolean isEmpty() {
        
        return true;
    }
    
    @Override
    public Optional<T> head() {
        return Optional.empty();
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
    public String toString() {
        return "Empty{" + '}';
    }

    @Override
    public List<T> tail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
    
    
    
}
