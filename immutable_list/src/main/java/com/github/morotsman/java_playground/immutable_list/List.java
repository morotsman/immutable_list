package com.github.morotsman.java_playground.immutable_list;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public abstract class List<T> {
    
    public static <T> List<T> of(final T... items) {
        List result = new Empty();
        
        for(int i = items.length-1; i > -1; i--) {
            result = new Cons(items[i], result);
        }
                
        return result;
    }
    
    public abstract Optional<T> head();
    
    public abstract List<T> tail();
    
    public abstract boolean isEmpty();
    
    public List<T> prepend(final T value) {
        return new Cons(value,this);
    }
    
    public void forEach(Consumer<? super T> consumer) {
        List<T> current = this;
        while(!current.isEmpty()) {
            consumer.accept(current.head().get());
            current = current.tail();
        }       
    }
   
    public <U> U reduce(final U identity, final BiFunction<U, ? super T, U> fun) {
        List<T> current = this;
        U result = identity;
        while(!current.isEmpty()) {
            result = fun.apply(result,current.head().get());
            current = current.tail();
        }
        return result;
    }
    
    public List<T> reverse() {
        return reduce(List.of(), (ts,t) -> new Cons(t,ts));
    }
    
    public <R> List<R> map(final Function<? super T,? extends R> fun) {
        return reduce(List.of(), (rs,t) -> new Cons(fun.apply(t), rs)).reverse();
    }
    
    public <U> List<U> flatMap(final Function<? super T, List<? extends U>> fun) {
        final List<U> result =  reduce(List.of(), (us,t) -> us.concat(fun.apply(t)));
        return result.reverse();
    }
    
    public List<T> concat(final List<? extends T> elements) {
        return elements.reverse().reduce(this, (ts,t) -> new Cons(t,ts));
    }
    
    public List<T> filter(final Predicate<? super T> predicate) {
        return reduce(List.of(), (ts,t) -> predicate.test(t)?new Cons(t,ts):ts).reverse();
    }
    
    public <K> Map<K, List<T>> groupBy(final Function<? super T, ? extends K> fun) {
        return reduce(new HashMap<K,List<T>>(), (groups,t) -> {
            final K key = fun.apply(t);
            final List<T> ts = groups.containsKey(key)?new Cons(t,groups.get(key)):List.of(t);
            groups.put(key, ts);
            return groups;
        });
    }
    
    
}




