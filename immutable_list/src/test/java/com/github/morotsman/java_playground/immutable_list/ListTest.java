package com.github.morotsman.java_playground.immutable_list;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
    
    private List<Integer> listOfSize(int size) {
        Integer[] elements = new Integer[size];
        for(int i = 0; i < size; i++) {
            elements[i] = new Integer(i);
        }
        return List.of(elements);
    }
    
    @Test
    public void testForStackOverFlow() {
        listOfSize(80000).map(i -> i*2);
    }
    
    
    @Test
    public void addOranges() {
        List<Orange> oranges = List.of(new Orange());
        List<Orange> moreOranges = oranges.prepend(new Orange());
        assertEquals(oranges,List.of(new Orange()));
        assertEquals(moreOranges,List.of(new Orange(), new Orange()));
    }
    
    
    @Test
    public void addLemmonToCitrus() {
        List<CitrusFruit> citrusFruits = List.of(new Orange());
        List<CitrusFruit> moreCitrusFruits = citrusFruits.prepend(new Lemmon());
        assertEquals(citrusFruits,List.of(new Orange()));
        assertEquals(moreCitrusFruits,List.of(new Lemmon(), new Orange()));
    }       
   
    
    @Test
    public void testReduce() {
        int result = List.of(1,2,3).reduce(0, (a,b) -> a+b); 
        assertEquals(6,result);
    }
    
    @Test
    public void testConcat() {
        List<Integer> result = List.of(1,2,3).concat(List.of(4,5,6));
        assertEquals(List.of(4,5,6,1,2,3),result);
    }
    
    @Test
    public void testFlatMap() {
        List<Integer> result = List.of(1,2,3).flatMap(a -> List.of(a,a)); 
        assertEquals(List.of(1,1,2,2,3,3),result);
    }
    
    @Test
    public void testFilter() {
        List<Integer> result = List.of(1,2,3).filter(a -> a >=2); 
        assertEquals(List.of(2,3),result);
    }
    
    @Test
    public void testGroupBy() {
        Map<Integer,List<Integer>> result = List.of(1,2,3,4,3,2).groupBy(a -> a); 
        Map<Integer,List<Integer>> expected = new HashMap<>();
        expected.put(1,List.of(1));
        expected.put(2,List.of(2,2));
        expected.put(3,List.of(3,3));
        expected.put(4,List.of(4));
        assertEquals(expected,result);
    }
    
    
    
}
