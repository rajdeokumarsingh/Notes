package com.java.examples.basic.classs;

enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}

public class EnumGrammarBasic {
    Day day;

    public EnumGrammarBasic(Day day) {
        this.day = day;
    }

    public void tellItLikeItIs() {
        switch (day) {
        case MONDAY:
            System.out.println("Mondays are bad.");
            break;

        case FRIDAY:
            System.out.println("Fridays are better.");
            break;

        case SATURDAY:
        case SUNDAY:
            System.out.println("Weekends are best.");
            break;

        default:
            System.out.println("Midweek days are so-so.");
            break;
        }
        System.out.println("day: " + day);
    }
    
    public static void dumpAllDay() {
        System.out.println("dumpAllDay");
        
        for(Day d: Day.values()) {
            System.out.println("day: " + d);
        }
        System.out.format("%n%n");
    }

    public static void main(String[] args) {
        EnumGrammarBasic.dumpAllDay();
        
        EnumGrammarBasic firstDay = new EnumGrammarBasic(Day.MONDAY);
        firstDay.tellItLikeItIs();
        EnumGrammarBasic thirdDay = new EnumGrammarBasic(Day.WEDNESDAY);
        thirdDay.tellItLikeItIs();
        EnumGrammarBasic fifthDay = new EnumGrammarBasic(Day.FRIDAY);
        fifthDay.tellItLikeItIs();
        EnumGrammarBasic sixthDay = new EnumGrammarBasic(Day.SATURDAY);
        sixthDay.tellItLikeItIs();
        EnumGrammarBasic seventhDay = new EnumGrammarBasic(Day.SUNDAY);
        seventhDay.tellItLikeItIs();
    }
}
