package com.springinaction.springidol;

import static java.lang.System.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:com/springinaction/springidol/spring-idol.xml" })
public class IdolTest {

    @BeforeClass
    public static void setup() {
        setProperty("STANS_SONG", "Total Eclipse of the Heart");
    }

    @Autowired
    ApplicationContext context;

    @Test
    public void carlShouldBeWiredWithKennysSong() {
        Instrumentalist kenny = (Instrumentalist) context.getBean("kenny");
        Instrumentalist carl = (Instrumentalist) context.getBean("carl");
        assertEquals(kenny.getSong(), carl.getSong());
    }

    @Test
    public void stanShouldBeWiredWithSongFromSystemProperties() {
        Instrumentalist stan = (Instrumentalist) context.getBean("stan");
        assertEquals("Total Eclipse of the Heart", stan.getSong());
    }

    @Test
    public void rickyShouldBeWiredWithSongFromSongBook() {
        Instrumentalist ricky = (Instrumentalist) context.getBean("ricky");
        assertEquals("That Old Black Magic", ricky.getSong());
    }

    @Test
    public void testFactory() throws Exception {
        Stage myStage = (Stage) context.getBean("myStage");
        assertNotNull(myStage);
    }

    @Test
    public void testPrototype() throws Exception {
        Sonnet29 sonnet1 = (Sonnet29) context.getBean("moreSonnet");
        Sonnet29 sonnet2 = (Sonnet29) context.getBean("moreSonnet");
        assertNotSame(sonnet1, sonnet2);
    }

    @Test
    public void testOneManBand() throws Exception {
        OneManBand oneManBand = (OneManBand) context.getBean("oneManBand");

        Collection<Instrument> instruments = oneManBand.getInstruments();
        assertEquals(instruments.size(), 1);
        assertTrue(instruments.contains(context.getBean("saxophone")));
    }
}
