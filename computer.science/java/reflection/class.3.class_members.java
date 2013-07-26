Discovering Class Members
    two categories of method:
        1. search all method/filed in a class, including private members
            getDeclaredField(), getDeclaredMethod(), getDeclaredConstructor()

        2. search no-private method/field in a class and its parent and children
            getField(), getMethod(), getConstructor()
