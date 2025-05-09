package com.kigali.city.com.kigali.city.domains.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class RubbishLocation(var location: String) {

    ON_ROAD("Rubbish on road or nature strip"),
    IN_A_PARK("Rubbish in a park"),
    SOMEONE_DUMP_RUBBISH("Seeing someone dump rubbish"),

}