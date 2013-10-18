package com.java.examples.basic.classs;

import java.lang.reflect.Type;

public class InterfaceTest {
    
    class NewString implements java.lang.CharSequence {
        @Override
        public int length() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public char charAt(int index) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            // TODO Auto-generated method stub
            return null;
        }
    }
    
    public interface OperateCar {

        enum Direction {
            RIGHT, LEFT
        };

        // constant declarations, if any method signatures

        int turn(Direction direction, double radius, double startSpeed,
                double endSpeed);

        int changeLanes(Direction direction, double startSpeed, double endSpeed);

        int signalTurn(Direction direction, boolean signalOn);

        int getRadarFront(double distanceToCar, double speedOfCar);

        int getRadarRear(double distanceToCar, double speedOfCar);
        // more method signatures
    }

    class OperateBMW760i implements OperateCar {

        // the OperateCar method signatures, with implementation --
        // for example:
        @Override
        public int signalTurn(Direction direction, boolean signalOn) {
            // code to turn BMW's LEFT turn indicator lights on
            // code to turn BMW's LEFT turn indicator lights off
            // code to turn BMW's RIGHT turn indicator lights on
            // code to turn BMW's RIGHT turn indicator lights off
            return 0;
        }

        @Override
        public int turn(Direction direction, double radius, double startSpeed,
                double endSpeed) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int changeLanes(Direction direction, double startSpeed,
                double endSpeed) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getRadarFront(double distanceToCar, double speedOfCar) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getRadarRear(double distanceToCar, double speedOfCar) {
            // TODO Auto-generated method stub
            return 0;
        }

        // other members, as needed -- for example, helper classes not
        // visible to clients of the interface
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("OperateBMW760i, class: " + OperateBMW760i.class);
        System.out.println("OperateBMW760i, super class: "
                + OperateBMW760i.class.getSuperclass());
        System.out.println("OperateBMW760i, generic super class: "
                + OperateBMW760i.class.getGenericSuperclass());

        for (Class c : OperateBMW760i.class.getInterfaces()) {
            System.out.println("OperateBMW760i, interface: " + c);
        }
        for (Type t : OperateBMW760i.class.getGenericInterfaces()) {
            System.out.println("OperateBMW760i, generic interface: " + t);
        }
    }

}
