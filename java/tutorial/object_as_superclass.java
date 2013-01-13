Object as a Superclass
    protected Object clone() throws CloneNotSupportedException
        Creates and returns a copy of this object.

    public boolean equals(Object obj)
        Indicates whether some other object is "equal to" this one.

    protected void finalize() throws Throwable
        Called by the garbage collector on an object when garbage
        collection determines that there are no more references to the object

    public final Class getClass()
        Returns the runtime class of an object.

    public int hashCode()
        Returns a hash code value for the object.

    public String toString()
        Returns a string representation of the object.

The clone() Method
    implements the Cloneable interface

The finalize() Method
    callback method, finalize()
    may be invoked on an object when it becomes garbage

    you can override finalize() to do cleanup, such as freeing resources

The getClass() Method
    You cannot override getClass.

The equals() Method

The hashCode() Method
    The value returned by hashCode() is the object''s hash code, 
        which is the object''s memory address in hexadecimal.

    By definition, if two objects are equal, their hash code must also be equal. 
        Therefore, if you override the equals() method, 
        you must also override the hashCode() method as well.




