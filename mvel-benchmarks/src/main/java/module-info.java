/**
 * JMH benchmark suite for MVEL3 expression evaluation.
 */
module org.mvel3.bench {

    requires org.mvel3;
    requires jmh.core;

    opens org.mvel3.benchmark to jmh.core;
    opens org.mvel3.benchmark.domain to jmh.core;
}
