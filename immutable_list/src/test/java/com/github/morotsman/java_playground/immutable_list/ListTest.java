package com.github.morotsman.java_playground.immutable_list;

import java.util.function.Function;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    
     @Test
    public void mapOnAListWithThreeElements() {
        List<Integer> test = List.of("one","two","three").map(a -> a.length());
        assertEquals(test,List.of(3,3,5));
    }   
    
    private class Fruit {}
    
    private class CitrusFruit extends Fruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }
    }
    
    private class Lemmon extends CitrusFruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }
    }
    
    private class Orange extends CitrusFruit {
        @Override
        public boolean equals(Object other) {
            System.out.println(this.getClass() + " " + other.getClass());
            return this.getClass() == other.getClass();
        }
    }
    
    private class Apple extends Fruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }   
    }
    
    private class GrannySmith extends Apple {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }   
    }
    
    @Test
    public void theListIsInvariant() {
        Apple myApple = new Apple();
        Fruit myFruit = myApple;
        
        List<Apple> apples = List.of(myApple);
        //List<Fruit> fruits1 = apples;//does not compile
        List<? extends Fruit> fruits2 = apples;
        assertEquals(fruits2,List.of(myApple));
    }
    
    @Test
    public void mapAcceptsContravariantInputToAFunctionAndCovariantOutputFromAFunction() {
        List<Orange> oranges = List.of(new Orange());
        
        Function<Orange,Apple> invariantConverter = (Orange o) -> new Apple();
        List<Apple> apples = oranges.map(invariantConverter);
        assertEquals(apples,List.of(new Apple()));
        
        Function<Fruit,Apple> contravariantInputConverter = (Fruit f) -> new Apple();
        apples = oranges.map(contravariantInputConverter);
        assertEquals(apples,List.of(new Apple()));
   
        Function<Orange,GrannySmith> covariantOutputConverter = (Orange o) -> new GrannySmith();
        apples = List.of(new Orange()).map(covariantOutputConverter);
        assertEquals(apples,List.of(new GrannySmith()));  
        
        Function<Fruit,GrannySmith> contravariantAndCovariantConverter = (Fruit f) -> new GrannySmith();
        apples = List.of(new Orange()).map(contravariantAndCovariantConverter);
        assertEquals(apples,List.of(new GrannySmith()));
    } 
    
    //test with many elements!!!
   
    
    
    
}
