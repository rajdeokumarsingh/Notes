Inheritance
    superclass (also a base class or a parent class)

    subclass (also a derived class, extended class, or child class)
        inherits all the members (fields, methods, and nested classes) from its superclass

        Constructors are not members, so they are not inherited by subclasses, 
        but the constructor of the superclass can be invoked from the subclass

every class is implicitly a subclass of Object

What You Can Do in a Subclass
    Hiding
        You can declare a field in the subclass with the same name as the one in the superclass, 
        thus hiding it 
        // FIXME: (not recommended).

        You can write a new static method in the subclass that has the same signature 
            as the one in the superclass, thus hiding it.
            // FIXME: why not call it overriding?

    Overriding(just for method)
        You can write a new instance method in the subclass that has the same signature 
            as the one in the superclass, thus overriding it.

Private Members in a Superclass
    A subclass can not access the private members of its parent class directly.

    a public or protected nested class inherited by a subclass 
        has indirect access to all of the private members of the superclass.
        // XXX: HOW?

Casting Objects
    // To make sure no runtime exception thrown
    if (obj instanceof MountainBike) {
        MountainBike myBike = (MountainBike)obj;
    }

Subclass Constructors
    Invocation of a superclass constructor must be the first line in the subclass constructor.

    public MountainBike(int startHeight, 
            int startCadence,
            int startSpeed,
            int startGear) {
        super(startCadence, startSpeed, startGear); // FIXME: must be the first line in the constructor
        seatHeight = startHeight;
    }   


    If a constructor does not explicitly invoke a superclass constructor, 
        the Java compiler automatically inserts a call to the no-argument constructor of the superclass. 

    If the super class does not have a no-argument constructor, (却有一个其他的constructor)
        you will get a compile-time error. 
