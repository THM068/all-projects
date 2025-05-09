package com.kigali.city.com.kigali.city.domains.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class FaultStatus(val text: String) {
    AWAITING_ASSIGNMENT("Awaiting Assignment"), ASSIGNED("Assigned"), COMPLETED("Completed")
}