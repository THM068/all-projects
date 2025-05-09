package com.kigali.city.com.kigali.city.domains.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class GraffitiLocation(val location: String) {

    COUNCIL_PROPERTY("On Council Property"),
    PRIVATE_PROPERTY("On Private Property")
}