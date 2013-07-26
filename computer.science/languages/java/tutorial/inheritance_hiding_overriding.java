Overriding and Hiding Methods
    
    Instance Methods
        An instance method in subclass OVERRIDEs same method in its superclass

        use the @Override annotation that instructs the compiler that you intend to override a method in the superclass.
            If the compiler detects that the method does not 
                exist in one of the superclasses, it will generate an error. 

    Class Methods
        An class method in subclass HIDEs same method in its superclass
        // FIXME: Hide和Override的区别
        如果子类有一个方法hide了基类的方法A，
        基类的引用指向一个子类对象，然后用这个引用去调用方法A, 那么被调用的将是基类的方法。


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

public static void main(String[] args) {
    Cat myCat = new Cat();
    Animal myAnimal = myCat;
    Animal.testClassMethod();       // Hide, 基类方法被调用
    myAnimal.testClassMethod();     // Hide, 基类方法被调用
    myCat.testClassMethod();        // 子类方法被调用
    myAnimal.testInstanceMethod();  // Override, 子类方法被调用
}

// 输出如下：
The class method in Animal.
The class method in Animal.
The instance method in Cat.

Modifiers
    The access specifier for an overriding method can allow more, 
        but not less, access than the overridden method. 

    For example, a protected instance method in the superclass 
        can be made public, but not private, in the subclass.



