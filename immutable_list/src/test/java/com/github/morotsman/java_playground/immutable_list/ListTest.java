/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.morotsman.java_playground.immutable_list;

import java.util.stream.Stream;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author niklasleopold
 */
public class ListTest {
    
    public ListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createEmptyList() {
        List<String> test = List.of();
        assertEquals(test,new Empty());
    }
    
    @Test
    public void createListOfOneString() {
        List<String> test = List.of("one");
        assertEquals(test,new Cons("one", new Empty()));
    }    
    
    @Test
    public void createListOfManyStrings() {
        List<String> test = List.of("one", "two", "three");
        assertEquals(test,new Cons("one", new Cons("two", new Cons("three", new Empty()))));
    } 
    
    @Test
    public void createListOfManyIntegers() {
        List<Integer> test = List.of(1, 2, 3);
        assertEquals(test,new Cons(1, new Cons(2, new Cons(3, new Empty()))));
    }      
    
    @Test
    public void createListOfManyObjects() {
        List<Object> test = List.of(1, "two", 3);
        assertEquals(test,new Cons(1, new Cons("two", new Cons(3, new Empty()))));
    }  
    
    
     private List<String> testEmptyList(String... args) {
        return List.of(args);
    }   
    
    @Test
    public void mapOnAnEmptyList() {
        List<Integer> test = testEmptyList().map(a -> a.length());
        assertEquals(test,List.of());
    }    

     @Test
    public void mapOnAListWithOneElement() {
        List<Integer> test = List.of("one").map(a -> a.length());
        assertEquals(test,List.of(3));
    }   
    
    //test with many elements!!!
    
    
    
}
