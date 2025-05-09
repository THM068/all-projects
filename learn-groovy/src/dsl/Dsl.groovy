package dsl

import java.lang.management.PlatformLoggingMXBean

class Dsl {

     static void pipeline(@DelegatesTo(value = PipeLineDsl, strategy = Closure.DELEGATE_ONLY)final Closure closure) {
         final PipeLineDsl pipeLineDsl = new PipeLineDsl()
         closure.delegate = pipeLineDsl
         closure.resolveStrategy = Closure.DELEGATE_ONLY
         closure()

    }
}

class PipeLineDsl {
    final PlaceHolder any = PlaceHolder.ANY

    void agent(final PlaceHolder any) {
        println("Running pipeline with any placeholder")
    }
    void environment(final Closure closure) {
        println("****")
    }

    void stages(final Closure closure) {

    }

    enum PlaceHolder {
        ANY
    }
}

