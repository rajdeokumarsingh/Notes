package com.springinaction.springidol.config;

import com.springinaction.springidol.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@AnnotationDrivenConfig
public class SpringIdolConfig {

    //<start id="duke_bean" />
    @Bean
    public Performer duke() {
        return new Juggler();
    }
    //<end id="duke_bean" />

    //<start id="duke15_bean" />
    @Bean
    public Performer duke15() {
        return new Juggler(15);
    }
    //<end id="duke15_bean" />

    //<start id="poeticduke_bean" />
    @Bean
    public Performer poeticDuke() {
        return new PoeticJuggler(sonnet29());
    }
    //<end id="poeticduke_bean" />

    //<start id="sonnet29_bean" />
    @Bean
//  private Poem sonnet29() {
    public Poem sonnet29() {
        return new Sonnet29();
    }
    //<end id="sonnet29_bean" />


    //<start id="kenny_bean" />
    @Bean
    public Performer kenny() {
        Instrumentalist kenny = new Instrumentalist();
        kenny.setSong("Jingle Bells");
        kenny.setInstrument(piano());
        return kenny;
    }

    @Bean
    public Instrument piano() {
        return new Piano();
    }

    //<end id="kenny_bean" />
    public static void main(String[] args) throws PerformanceException {
        ApplicationContext context =
//                new AnnotationConfigApplicationContext(SpringIdolConfig.class);
                new AnnotationConfigApplicationContext("com.springinaction.springidol");

        Performer duke = (Performer) context.getBean("duke");
        duke.perform();

        Performer poeticDuke = (Performer) context.getBean("poeticDuke");
        poeticDuke.perform();

        Performer kenny = (Performer) context.getBean("kenny");
        kenny.perform();
    }
}
