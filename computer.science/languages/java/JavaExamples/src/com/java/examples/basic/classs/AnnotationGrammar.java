package com.java.examples.basic.classs;

import java.lang.annotation.Documented;

interface House {
    @Deprecated
    void open();
    void openFrontDoor();
    void openBackDoor();
}

class AnnotationsSupper {
    void superMethod() {
    }
}

@Documented
@interface ClassPreamble {
    String author();

    String date();

    int currentRevision() default 1;

    String lastModified() default "N/A";

    String lastModifiedBy() default "N/A";

    // Note use of array
    String[] reviewers();
}

@ClassPreamble(author = "Jiang Rui", date = "1/7/2012", currentRevision = 2,
        lastModified = "1/7/2013", lastModifiedBy = "Weixing Zhao",
        reviewers = {"Zhidong Hou", "Chao Liu"})
public class AnnotationGrammar extends AnnotationsSupper {
    @Override
    void superMethod() {
        System.out.println(this.getClass() + ": superMethod()");
        super.superMethod();
    }

    /**
     * @deprecated explanation of why it was deprecated
     */
    @Deprecated
    static void deprecatedMethod() {
    }

    // use a deprecated method and tell compiler not to generate a warning
    @SuppressWarnings({"unchecked", "deprecation"})
    void useDeprecatedMethod() {
        // deprecation warning
        // - suppressed
        deprecatedMethod();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AnnotationGrammar ann = new AnnotationGrammar();
        ann.superMethod();
    }

}
