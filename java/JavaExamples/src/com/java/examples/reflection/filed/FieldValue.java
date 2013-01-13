package com.java.examples.reflection.filed;

import static java.lang.System.out;

import java.lang.reflect.Field;

enum Tweedle {
	DEE, DUM
}

class Book {
	long chapters = 0;
	String[] characters = { "Alice", "White Rabbit" };
	private Tweedle twin = Tweedle.DEE;

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

public class FieldValue {
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
		// TODO Auto-generated method stub
		Book book = new Book();
		out.println(book.toString());

		Class<?> c = book.getClass();
		Field fchpt = c.getDeclaredField("chapters");
		Field fchar = c.getDeclaredField("characters");
		Field ftwin = c.getDeclaredField("twin");

		fchpt.setLong(book, 12);

		String[] chars = (String[]) fchar.get(book);
		for(String s : chars) {
			out.println(s);
		}
		String[] newChars = { "Queen", "King" };
		fchar.set(book, newChars);

		ftwin.setAccessible(true);
		ftwin.set(book, Tweedle.DUM);
		
		out.println(book.toString());
	}
}
