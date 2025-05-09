package com.kigali.city.com.kigali.city.domains.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ReportType(var fault: String) {

    POTHOLES("Potholes or damaged Road"),
    RUBBISH("Rubbish"),
    GRAFFITI("Graffiti"),
    UNSIGHTLY_PREMISE("Unsightly Premise"),
    OVERGROWN_VEGETATION( "Overgrown Vegetation"),
    MISSING_STREET_SIGN("Missing Street Sign"),
    MISSING_STREET_LIGHT("Missing Street Light"),
    OTHER("Other"),

}