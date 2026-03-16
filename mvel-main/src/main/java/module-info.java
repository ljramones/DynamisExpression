/**
 * MVEL3 expression language transpiler.
 */
module org.mvel3 {

    // --- compile-time dependencies ---
    requires java.compiler;
    requires org.antlr.antlr4.runtime;
    requires com.github.javaparser.core;
    requires com.github.javaparser.symbolsolver.core;
    requires org.slf4j;

    // --- exported public API packages ---
    exports org.mvel3;
    exports org.mvel3.compiler.classfile;
    exports org.mvel3.javacompiler;
    exports org.mvel3.lambdaextractor;
    exports org.mvel3.methodutils;
    exports org.mvel3.parser;
    exports org.mvel3.parser.antlr4;
    exports org.mvel3.parser.printer;
    exports org.mvel3.parser.util;
    exports org.mvel3.transpiler;
    exports org.mvel3.transpiler.context;
    exports org.mvel3.transpiler.util;
    exports org.mvel3.util;
}
