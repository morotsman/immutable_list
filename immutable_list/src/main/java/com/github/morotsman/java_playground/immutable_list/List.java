package com.github.morotsman.java_playground.immutable_list;

import java.util.function.Function;


public abstract class List<T> {
    
    public static <T> List<T> of(T... items) {
        
        List result = new Empty();
        
        for(int i = items.length-1; i > -1; i--) {
            result = new Cons(items[i], result);
        }
                
        return result;
    }
    
    public abstract boolean isEmpty();
    
    public abstract <R> List<R> map(Function<T,R> fun);
    
    
}




