Interfaces in Java
    can contain only constants, method signatures, and nested types. 

    Can extends from any number of other interfaces
        public interface GroupedInterface extends Interface1, Interface2, Interface3 
        // FIXME: class can only extend ONE other class

    A class that implements an interface must implement all the methods declared in the interface.

    There are no method bodies.

    Accessibility
        All methods declared in an interface are implicitly public, so the public modifier can be omitted.
        All constant values defined in an interface are implicitly public, static, and final. Once again, 
            these modifiers can be omitted.

        // XXX:
        interface如果没有public的modifier, 则访问权限是package级别的。不会默认为public。

Interface和Class相比(外形上)，仅仅是函数没有body?

Implementing an Interface
    public interface Relatable {
        public int isLargerThan(Relatable other);
    }

    public class RectanglePlus implements Relatable {
        // ....
        // a method required to implement // the Relatable interface
        public int isLargerThan(Relatable other) {
            // FIXME: will compile error if we do not cast other to RectanglePlus
            RectanglePlus otherRect = (RectanglePlus)other;

            if (this.getArea() < otherRect.getArea())
                return -1;
            else if (this.getArea() > otherRect.getArea())
                return 1;
            else
                return 0;               
        }
    }



