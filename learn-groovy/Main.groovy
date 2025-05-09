import groovy.json.JsonParser
import groovy.json.JsonSlurper

println("Hello world")
Map map = [ name: "Thomas", surname: "Ncube", isBlack: true]
class Person {
    String name
    String surname
    Boolean isBlack
    Boolean isBald
}

Person p = new Person(map)


println(p.properties)

JsonSlurper slurper = new JsonSlurper()
