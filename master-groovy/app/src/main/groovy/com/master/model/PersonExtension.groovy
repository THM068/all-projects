package com.master.model

class PersonExtension {

    static String getNameAndLast(Person person) {
        return "${person.name} ${person.last}"
    }
}
