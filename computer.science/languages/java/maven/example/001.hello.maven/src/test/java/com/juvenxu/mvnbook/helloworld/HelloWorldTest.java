package com.juvenxu.mvnbook.helloworld;

// import static org.junit.Assert.assertEquals;
// import org.junit.Test;
import junit.framework.TestCase;
import static junit.framework.Assert.assertEquals;


import com.juvenxu.mvnbook.helloworld.HelloWorld;

public class HelloWorldTest extends TestCase
{
    // @Test
    public void testSayHello()
    {
        HelloWorld helloWorld = new HelloWorld();

        String result = helloWorld.sayHello();

        assertEquals( "Hello Maven", result );
    }
}
