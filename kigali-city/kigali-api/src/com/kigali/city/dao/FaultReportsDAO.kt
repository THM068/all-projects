package com.kigali.city.com.kigali.city.dao

import arrow.core.extensions.list.monad.map
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kigali.city.com.kigali.city.domains.FaultReports
import com.mysql.cj.xdevapi.DocResult
import java.time.LocalDateTime
import java.util.*

class FaultReportsDAO(val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule()).disable(WRITE_DATES_AS_TIMESTAMPS)):CrudRepository<FaultReports, String> {
    val faultReportsCollectionName = "fault_reports"
    val faultReportsCollection  = db.getCollection(faultReportsCollectionName)

    override fun save(faultReport: FaultReports): FaultReports {
        faultReport.dateCreated = LocalDateTime.now()
        faultReport.lastUpdated = LocalDateTime.now()
        val idList = faultReportsCollection.add(objectMapper.writeValueAsString(faultReport)).execute().generatedIds

        return findById(idList.first()).get()
    }

    override fun findById(id: String): Optional<FaultReports> {
        val doc = faultReportsCollection.getOne(id) ?: return Optional.empty()
        return Optional.of(objectMapper.readValue(doc.toString(), FaultReports::class.java))
    }

    override fun existsById(id: String): Boolean {
        return findById(id).isPresent
    }

    override fun deleteById(id: String) {
        faultReportsCollection.removeOne(id)    }

    override fun count(): Long {
        return faultReportsCollection.count()
    }

    override fun deleteAll() {
        faultReportsCollection.remove("true").execute()
    }

    override fun findAll(): List<FaultReports> {
        val docResult = faultReportsCollection.find().execute()
        return getListOfFaults(docResult)
    }

    override fun findAll(pageRequest: PageRequest): List<FaultReports> {
        val docResult = faultReportsCollection.find().limit(pageRequest.pageSize).offset(pageRequest.offset).execute()
        return getListOfFaults(docResult)
    }

    private fun getListOfFaults(docResult: DocResult): List<FaultReports> {
        if(docResult.count() == 0L) {
            return listOf()
        }
        val listOfDbDocuments  = docResult.fetchAll()
        return listOfDbDocuments.map {
            objectMapper.readValue(it.toString(), FaultReports::class.java)
        }
    }
}