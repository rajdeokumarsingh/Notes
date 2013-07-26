Class c = book.getClass();
Field fchar = c.getDeclaredField("characters");
String[] chars = (String[]) fchar.get(book);
for(String s : chars) {
    out.println(s);
}

String[] newChars = { "Queen", "King" };
fchar.setAccessible(true); // if fchar is a private member
fchar.set(book, newChars);

        

