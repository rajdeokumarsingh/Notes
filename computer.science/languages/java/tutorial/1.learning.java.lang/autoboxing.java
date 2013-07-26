The Java compiler applies autoboxing when a primitive value is:
    1. Assigned to a variable of the corresponding wrapper class.
        Character ch = 'a';
        
    2. Passed as a parameter to a method that expects an object of the corresponding wrapper class.
        List<Integer> li = new ArrayList<Integer>();
        for (int i = 1; i < 50; i += 2)
            li.add(new Integer.valueOf(i));

The Java compiler applies unboxing when an object of a wrapper class is:
    1. Passed as a parameter to a method that expects a value of the corresponding primitive type.
        public static int sumEven(List<Integer> li) {
            int sum = 0;
            for (Integer i : li)
                if (i.intValue() % 2 == 0)
                    sum += i.intValue();
            return sum;
        }

    2. Assigned to a variable of the corresponding primitive type.



