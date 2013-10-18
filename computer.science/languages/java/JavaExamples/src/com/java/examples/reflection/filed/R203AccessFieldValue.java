package com.java.examples.reflection.filed;

import static java.lang.System.out;

import java.lang.reflect.Field;
import java.util.Arrays;

enum Tweedle {
	DEE, DUM
}

class Book {
	long chapters = 0;
	String[] characters = { "Alice", "White Rabbit" };
	Tweedle twin = Tweedle.DEE;

	@Override
	public String toString() {
		String chs = "[ ";
		for (String s : characters) {
			chs += s + " ,";
		}
		chs += "]";

		return "chapters: " + chapters + ", characters: " + chs + ", tweedle: "
				+ twin;
	}
}

public class R203AccessFieldValue {
	/**
	 * @param args
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Book book = new Book();
        String fmt = "%6S:  %-12s = %s%n";

        out.println(book.toString());

		Class<?> c = book.getClass();
        Field chap = c.getDeclaredField("chapters");
        out.format(fmt, "before", "chapters", book.chapters);
        chap.setLong(book, 12);
        out.format(fmt, "after", "chapters", chap.getLong(book));

        Field chars = c.getDeclaredField("characters");
        out.format(fmt, "before", "characters",
                Arrays.asList(book.characters));
        String[] newChars = { "Queen", "King" };
        chars.set(book, newChars);
        out.format(fmt, "after", "characters",
                Arrays.asList(book.characters));

        Field t = c.getDeclaredField("twin");
        out.format(fmt, "before", "twin", book.twin);
        t.set(book, Tweedle.DUM);
        out.format(fmt, "after", "twin", t.get(book));
	}
}
