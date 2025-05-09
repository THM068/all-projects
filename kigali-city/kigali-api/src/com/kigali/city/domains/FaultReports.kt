package com.kigali.city.com.kigali.city.domains

import com.kigali.city.com.kigali.city.domains.enums.FaultStatus
import com.kigali.city.com.kigali.city.domains.enums.GraffitiLocation
import com.kigali.city.com.kigali.city.domains.enums.ReportType
import com.kigali.city.com.kigali.city.domains.enums.RubbishLocation
import java.time.LocalDateTime

class FaultReports {
    var _id: String? = null
    var details: String? = null
    var photoUrls: List<String>? = null
    var position: FaultPosition? = null
    var reportType: ReportType? = null
    var graffitiLocation: GraffitiLocation? = null
    var rubbishLocation: RubbishLocation? = null
    var status: FaultStatus? = FaultStatus.AWAITING_ASSIGNMENT

    var dateCreated: LocalDateTime? = null
    var lastUpdated: LocalDateTime? = null
    var reportedById: String? = null

}

data class FaultPosition(val longitude: Double, val latitude: Double)

