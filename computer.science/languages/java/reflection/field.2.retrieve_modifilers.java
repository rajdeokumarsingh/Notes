Field[] fields = c.getDeclaredFields();
for (Field f : fields) {
    System.out.println(f.toGenericString());
    System.out.println(Modifier.toString(f.getModifiers()));
}
