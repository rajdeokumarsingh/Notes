package com.java.examples.thread;

/*
 *  1. Don't provide "setter" methods — 
 *  	methods that modify fields or objects referred to by fields.
 *  2. Make all fields final and private.
 *  3. Don't allow subclasses to override methods. 
 *  	The simplest way to do this is to declare the class as final. 
 *  	A more sophisticated approach is to make the constructor private 
 *  		and construct instances in factory methods.
 *  4. If the instance fields include references to mutable objects, 
 *  	don't allow those objects to be changed:
 *       Don't provide methods that modify the mutable objects.
 *       Don't share references to the mutable objects. 
 *       	Never store references to external, mutable objects 
 *       	passed to the constructor; if necessary, create copies, 
 *       	and store references to the copies. Similarly, create copies of 
 *       	your internal mutable objects when necessary to avoid returning 
 *       	the originals in your methods.
 *
 */
final public class T11ImmutableRGB {

	// Values must be between 0 and 255.
	final private int red;
	final private int green;
	final private int blue;
	final private String name;

	private void check(int red, int green, int blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0
				|| blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	public T11ImmutableRGB(int red, int green, int blue, String name) {
		check(red, green, blue);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.name = name;
	}

	public int getRGB() {
		return ((red << 16) | (green << 8) | blue);
	}

	public String getName() {
		return name;
	}

	public T11ImmutableRGB invert() {
		return new T11ImmutableRGB(255 - red, 255 - green, 255 - blue,
				"Inverse of " + name);
	}
}
