Annotations have a number of uses, among them:
    1. Information for the compiler — Annotations can be used by the compiler to detect errors or suppress warnings.
    2. Compiler-time and deployment-time processing — Software tools can process annotation information to generate code, XML files, and so forth.
    3. Runtime processing — Some annotations are available to be examined at runtime.

Annotations Used by the Compiler
    @Deprecated
        the marked element is deprecated and should no longer be used
        The compiler generates a warning whenever a program uses it

    @Override 
        the element is meant to override an element declared in a superclass

    @SuppressWarnings
        tells the compiler to suppress specific warnings that it would otherwise generate.

        The Java Language Specification lists two categories: "deprecation" and "unchecked."
        The "unchecked" warning can occur when interfacing with legacy code written 
            before the advent of generics (discussed in the lesson titled "Generics").

Replace comments with annotations:

    public class Generation3List extends Generation2List {
        // Author: John Doe
        // Date: 3/17/2002
        // Current revision: 6
        // Last modified: 4/12/2004
        // By: Jane Doe
        // Reviewers: Alice, Bill, Cindy

        // class code goes here
    }

    first define the annotation type
        @interface ClassPreamble {
            String author();
            String date();
            int currentRevision() default 1;
            String lastModified() default "N/A";
            String lastModifiedBy() default "N/A";
            // Note use of array
            String[] reviewers();
        }

        @ClassPreamble (
            author = "John Doe",
            date = "3/17/2002",
            currentRevision = 6,
            lastModified = "4/12/2004",
            lastModifiedBy = "Jane Doe",
            // Note array notation
            reviewers = {"Alice", "Bob", "Cindy"}
        )
        public class Generation3List extends Generation2List {
            // class code goes here
        }


    Note: To make the information in @ClassPreamble appear in Javadoc-generated documentation, 
    you must annotate the @ClassPreamble definition itself with the @Documented annotation:
    // import this to use @Documented
    import java.lang.annotation.*;

    @Documented
    @interface ClassPreamble {
       // Annotation element definitions
    }


