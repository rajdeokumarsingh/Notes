package com.java.examples.file;

import java.io.*;

public class SerializableStudent implements Serializable {
    private long id;
    private String name;
    private int age;
    private String department;

    // transient filed could not be serialized
    private transient String password;

    public SerializableStudent(long id, String name, int age, String department, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SerializableStudent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializableStudent student = new SerializableStudent(
                123, "test stu", 25, "CS", "my password");
        FileOutputStream fos = new FileOutputStream("/tmp/ss.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(student);
        oos.close();

        FileInputStream fis = new FileInputStream("/tmp/ss.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        SerializableStudent studentRestore = (SerializableStudent) ois.readObject();
        // we will see the field "password" is null
        System.out.println("restore student: " + studentRestore.toString());
    }
}
