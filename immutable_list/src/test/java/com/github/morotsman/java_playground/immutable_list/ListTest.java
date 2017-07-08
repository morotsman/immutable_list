package com.github.morotsman.java_playground.immutable_list;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
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
    
    @Test
    public void mapWithArrayListOnAnEmptyList() {
        List<Integer> test = testEmptyList().mapImplementedWithArrayList(a -> a.length());
        assertEquals(test,List.of());
    }    

    @Test
    public void mapWithArrayListOnAListWithOneElement() {
        List<Integer> test = List.of("one").mapImplementedWithArrayList(a -> a.length());
        assertEquals(test,List.of(3));
    }   
    
     @Test
    public void mapWithArrayListOnAListWithThreeElements() {
        List<Integer> test = List.of("one","two","three").mapImplementedWithArrayList(a -> a.length());
        assertEquals(test,List.of(3,3,5));
    }       
    
    private class Fruit {

        @Override
        public String toString() {
            return "Fruit";
        }
    
        
    }
    
    private class CitrusFruit extends Fruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }

        @Override
        public String toString() {
            return "CitrusFruit";
        }
        
        
    }
    
    private class Lemmon extends CitrusFruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }

        @Override
        public String toString() {
            return "Lemmon";
        }
        
        
    }
    
    private class Orange extends CitrusFruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }

        @Override
        public String toString() {
            return "Orange";
        }
        
    }
    
    private class Apple extends Fruit {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }

        @Override
        public String toString() {
            return "Apple";
        }
        
        
    }
    
    private class GrannySmith extends Apple {
        @Override
        public boolean equals(Object other) {
            return this.getClass() == other.getClass();
        }

        @Override
        public String toString() {
            return "GrannySmith";
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
    public void testMapForStackOverFlow() {
        listOfSize(80000).map(i -> i*2);
    }
    
    @Test
    public void compareTime() {
        
        Stream.of(10,100,1000,5000,6000,7000,8000,9000,10000,20000,50000,100000,1000000, 2000000, 3000000, 4000000, 5000000, 6000000).forEach(numberOfElements -> {
            long startTime = System.currentTimeMillis();
            listOfSize(numberOfElements).map(i -> i*2);
            System.out.println("Map implemented with reduce using (" + numberOfElements + ") elements: " + (System.currentTimeMillis()-startTime));
        
            startTime = System.currentTimeMillis();
            listOfSize(numberOfElements).mapImplementedWithArrayList(i -> i*2);
            System.out.println("Map implemented with ArrayList using (" + numberOfElements + ") elements: " + (System.currentTimeMillis()-startTime));    
        });

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
    public void reduceZeroElements() {
        int result = List.<Integer>of().reduce(0, (Integer a,Integer b) -> a+b); 
        assertEquals(0,result);
    }
    
    @Test
    public void reduceOneElement() {
        int result = List.of(1).reduce(0, (Integer a,Integer b) -> a+b); 
        assertEquals(1,result);
    }   
    
    
    @Test
    public void reduceManyElements() {
        int result = List.of(1,2,3).reduce(0, (Integer a,Integer b) -> a + b); 
        assertEquals(6,result);
    }
    
    @Test
    public void reduceFromOneTypeToAnother() {
        int result = List.of("one","two","three").reduce(0, (Integer a,String b) -> a + b.length()); 
        assertEquals(11,result);
    }  
    
    @Test
    public void reduceIsContravariant() {
        BiFunction<Integer, Orange, Integer> addOrange = (Integer a, Orange b) -> a + 1;
        int numberOfFruits = List.<Orange>of(new Orange(), new Orange(), new Orange()).reduce(0, addOrange);
        assertEquals(3,numberOfFruits);
        
        BiFunction<Integer, Fruit, Integer> addFruit = (Integer a, Fruit b) -> a + 1;
        numberOfFruits = List.<Orange>of(new Orange(), new Orange(), new Orange()).reduce(0, addFruit);
        assertEquals(3,numberOfFruits);
    }
    
    @Test
    public void reverseOfAnEmptyList() {
        List<Integer> result = List.<Integer>of().reverse();
        assertEquals(List.of(), result);
    }
    
    @Test
    public void reverseOfListThatContainsOneElement() {
        List<Integer> result = List.<Integer>of(1).reverse();
        assertEquals(List.of(1), result);
    }   
    
    @Test
    public void reverseOfListThatContainsManyElement() {
        List<Integer> result = List.<Integer>of(1,2,3).reverse();
        assertEquals(List.of(3,2,1), result);
    } 
    
    @Test
    public void flatMapWithAListThatIsEmpty() {
        List<Integer> result = List.<Integer>of().flatMap(a -> List.of(a,a)); 
        assertEquals(List.of(),result);
    }    
    
    @Test
    public void flatMapWithAListThatContainsOneElement() {
        List<Integer> result = List.of(1).flatMap(a -> List.of(a,a)); 
        assertEquals(List.of(1,1),result);
    }     
    
    @Test
    public void flatMapWithAListThatContainsManyElements() {
        List<Integer> result = List.of(1,2,3).flatMap(a -> List.of(a,a)); 
        assertEquals(List.of(1,1,2,2,3,3),result);
    }  
    
    @Test
    public void flatMapCovaraintAndContravariant() {
        Function<Integer,List<Integer>> timesTwoIntegers = a -> List.of(a,a);
        List<Integer> result = List.of(1,2,3).flatMap(timesTwoIntegers); 
        assertEquals(List.of(1,1,2,2,3,3),result);
        
        Function<Number,List<Integer>> timesTwoNumbers = a -> List.<Integer>of(a.intValue(),a.intValue());
        result = List.of(1,2,3).flatMap(timesTwoNumbers); 
        assertEquals(List.of(1,1,2,2,3,3),result);
        
        Function<Number,List<Integer>> timesTwoNumbers2 = a -> List.<Integer>of(a.intValue(),a.intValue());
        List<Number> result2 = List.of(1,2,3).flatMap(timesTwoNumbers2); 
        assertEquals(List.of(1,1,2,2,3,3),result2);
    }    
    
    
    @Test
    public void testConcat() {
        List<Integer> result = List.of(1,2,3).concat(List.of(4,5,6));
        assertEquals(List.of(4,5,6,1,2,3),result);
        
        result = List.<Integer>of().concat(List.of(4,5,6));
        assertEquals(List.of(4,5,6),result);
        
        result = List.of(1,2,3).concat(List.of());
        assertEquals(List.of(1,2,3),result);
        
        result = List.<Integer>of().concat(List.of());
        assertEquals(List.of(),result);
        
        result = List.of(1).concat(List.of());
        assertEquals(List.of(1),result);
        
        result = List.<Integer>of().concat(List.of(1));
        assertEquals(List.of(1),result);
        
        List<Fruit> fruits = List.<Fruit>of(new Orange()).concat(List.of(new Apple())); 
    }
    

    
    @Test
    public void testFilter() {
        List<Integer> result = List.of(1,2,3).filter(a -> a >=2); 
        assertEquals(List.of(2,3),result);
        
        result = List.<Integer>of().filter(a -> a >=2); 
        assertEquals(List.of(),result);
        
        result = List.of(1).filter(a -> a >=2); 
        assertEquals(List.of(),result);
        
        result = List.of(2).filter(a -> a >=2); 
        assertEquals(List.of(2),result);
        
        Predicate<Number> fun = (Number number) -> number.intValue() == 1;
        result = List.of(1,2,1).filter(fun);
        assertEquals(List.of(1,1),result);
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
        
        result = List.<Integer>of().groupBy(Function.identity()); 
        expected = new HashMap<>();
        assertEquals(expected,result);
        
        result = List.of(1).groupBy(Function.identity()); 
        expected = new HashMap<>();
        expected.put(1,List.of(1));
        assertEquals(expected,result);
        
        Function<Object,String> fun = (Object o) -> o.toString();
        Map<Object,List<CitrusFruit>> result2 = List.<CitrusFruit>of(new Orange(),new Lemmon(), new Orange()).groupBy(fun);
        Map<Object,List<CitrusFruit>> expected2 = new HashMap<>();
        expected2.put("Orange", List.of(new Orange(), new Orange()));
        expected2.put("Lemmon", List.of(new Lemmon()));
        assertEquals(expected2,result2);
    }
    
    
    
    
}
