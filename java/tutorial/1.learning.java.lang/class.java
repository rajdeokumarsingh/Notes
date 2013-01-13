constructor
    If a class has no constructor:
        The compiler automatically provides a no-argument, default constructor for any class without constructors.
        This default constructor will call the no-argument constructor of the superclass.
            the compiler will complain if the superclass doesn't have a no-argument constructor

Arbitrary Number of Arguments
    public Polygon polygonFrom(Point... corners) 
        you can use "corners" like an array

Initializing Fields
public class BedAndBreakfast {

    ////////////////////////////////////////////////////////////////////////////////
    // static field
    public static int capacity = 10;

    static {
        // whatever code is needed for initialization goes here
    }

    public static varType myVar = initializeClassVariable();
    private static varType initializeClassVariable() {

        // initialization code goes here
    }

    ////////////////////////////////////////////////////////////////////////////////
    // instance field
    private boolean full = false;

    // The Java compiler copies initializer blocks into every constructor. 
    // Therefore, this approach can be used to share a block of code between multiple constructors.
    {
        // whatever code is needed for initialization goes here
    }

    // The method is final because calling non-final methods 
    // during instance initialization can cause problems.
    private varType myVar = initializeInstanceVariable();
    protected final varType initializeInstanceVariable() {
        // initialization code goes here
    }
}

// Using this with a Constructor
    public class Rectangle {
        ...
        public Rectangle() {
            this(0, 0, 0, 0);
        }
        public Rectangle(int width, int height) {
            this(0, 0, width, height);
        }
        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

            Access Levels 
Modifier        Class Package Subclass World
public            Y     Y       Y        Y
protected         Y     Y       Y        N
no modifier       Y     Y       N        N
private           Y     N       N        N

            public (no modifier) protected  private
Class          Y        Y           Y          Y
Subclass       Y        Y(*)        Y          N
Package        Y        Y           N          N
World          Y        N           N          N

    // FIXME:
    A subclass inherits all of the public and protected members of its parent, 
        no matter what package the subclass is in
    If the subclass is in the same package as its parent, 
        it also inherits the package-private members of the parent.
