package com.java.examples.basic.classs;

class InheritanceTestSuper {
    private int test;
    
    public class NestedSuper {
        void test() {
            test = 1;
        }
    }
    
//    public InheritanceTestSuper() {
//        test = -1;
//    }
    
    public InheritanceTestSuper(int i) {
        test = -1;
    }
    
    public void go() {
    }
}

public class InheritanceTest extends InheritanceTestSuper {
    public InheritanceTest() {
//        super();
        go();
    }
    
    public class Nested {
        void test() {
            // FIXME: JUST keep this line
//            test = 1;
        }
    }
    
    public void InheritanceTest() {
//        test = 1;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
