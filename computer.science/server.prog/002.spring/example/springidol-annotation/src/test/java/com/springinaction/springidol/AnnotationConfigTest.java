package com.springinaction.springidol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/springinaction/springidol/spring-idol.xml")
public class AnnotationConfigTest {

    @Autowired
    @Qualifier("eddie")
    private Instrumentalist eddie;

    @Autowired
    @Qualifier("guitar")
    private Instrument guitar;

//    @Autowired
//    ApplicationContext context;

    @Test
    public void shouldWireWithAutowire() {
        // assertNotNull(context);

        assertNotNull(eddie.getInstrument());
        assertEquals(guitar, eddie.getInstrument());
    }
}
