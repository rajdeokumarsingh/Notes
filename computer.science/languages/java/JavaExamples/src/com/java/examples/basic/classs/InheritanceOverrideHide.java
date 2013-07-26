package com.java.examples.basic.classs;


class Animal {
    public static void testClassMethod() {
        System.out.println("The class" + " method in Animal.");
    }
    
    public void testInstanceMethod() {
        System.out.println("The instance " + " method in Animal.");
    }
}

class Cat extends Animal {
    public static void testClassMethod() {
        System.out.println("The class method" + " in Cat.");
    }
    
    @Override
    public void testInstanceMethod() {
        System.out.println("The instance method" + " in Cat.");
    }
}

class Dog extends Animal {
    public static void testClassMethod() {
        System.out.println("The class method" + " in Dog.");
    }
    
    @Override
    public void testInstanceMethod() {
        System.out.println("The instance method" + " in Dog.");
    }
}

public class InheritanceOverrideHide {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Object myCat = new Cat();
        Object myDog = new Dog();
        Object anm = new Animal();
//        Animal myAnimal = myCat;
//        Animal.testClassMethod();
//        myAnimal.testClassMethod();
//        myCat.testClassMethod();
//        myAnimal.testInstanceMethod();
        System.out.println("cat is aninaml: " + (myCat instanceof Animal));
        System.out.println("cat is cat: " + (myCat instanceof Cat));
        System.out.println("dog is aninaml: " + (myDog instanceof Animal));
        System.out.println("dog is dog: " + (myDog instanceof Dog));
        System.out.println("cat is dog: " + (myCat instanceof Dog));
        System.out.println("dog is cat: " + (myDog instanceof Cat));
        System.out.println("null is cat: " + (null instanceof Cat));
        
    }
}
