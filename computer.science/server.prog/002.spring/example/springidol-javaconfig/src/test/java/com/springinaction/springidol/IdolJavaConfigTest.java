package com.springinaction.springidol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:com/springinaction/springidol/springidol-java-config.xml"})
public class IdolJavaConfigTest {

//  @Autowired
//  ApplicationContext context;

    @Autowired
    Poem poem;

    @Autowired
    @Qualifier("duke15")
    Performer duke15;

    @Autowired
    @Qualifier("duke15")
    Performer duke;

    @Test
    public void testPoem() throws Exception {
        assertNotNull(poem);
        assertTrue(poem instanceof Sonnet29);
    }

    @Test
    public void testPerformer() throws Exception {
        assertNotNull(duke15);
        assertTrue(duke15 instanceof Juggler);

        Juggler juggler = (Juggler) duke15;
        assertEquals(15, juggler.getBeanBags());

        assertSame(duke, duke15);
    }
}
