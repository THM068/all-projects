package extensions

class PersonExtension {

    static String getNameAndSurname(Person person) {
        return person.name + " " + person.last
    }
}
