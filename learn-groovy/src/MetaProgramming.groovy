import groovy.transform.ToString

import java.util.regex.Pattern
@ToString
class SomeGroovyClass {

    def invokeMethod(String name, Object args) {
        return "called invokeMethod $name $args"
    }

    def test() {
        return 'method exists'
    }
}

def someGroovyClass = new SomeGroovyClass()

assert someGroovyClass.test() == 'method exists'
assert someGroovyClass.someMethod() == 'called invokeMethod someMethod []'

class POGO {
    String property

    void setProperty(String name, Object value) {
        this.@"$name" = "override"
    }
}

def pogo = new POGO()
pogo.property = 'a'

assert pogo.property == 'override'

class Book {
    String title
}
Book.metaClass.static.create << {->  new Book(title: "Hello world") }

def c = { it * 3 }
println( c(9))
assert  Book.create().title == "Hello world"

Pattern pattern = ~ $/(\s|^)AppleWebKit\/[\d\.]+\s+\(.+\)\s+Version\/(1[0-9]|[2-9][0-9]|\d{3,})(\.|$|\s)/$
( "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15" =~ pattern).find()

println( ( "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15" =~ pattern).class )



assert pattern.matcher("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15").find()

def m1 = [a: 3, b: 4]
def m2 = [c: 5, d: 4,*:m1]
println m2
//(0<..<5).each { print it + " " }