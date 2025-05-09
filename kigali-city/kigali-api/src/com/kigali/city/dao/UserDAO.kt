package com.kigali.city.com.kigali.city.dao

import arrow.core.extensions.list.monad.map
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kigali.city.com.kigali.city.domains.User
import com.mysql.cj.xdevapi.DocResult
import java.time.LocalDateTime
import java.util.*

class UserDAO (val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
) : CrudRepository<User, String> {
    val userCollectionName = "users"
    val userCollection  = db.getCollection(userCollectionName)

    override fun save(user: User): User {
        user.dateCreated =  LocalDateTime.now()
        userCollection.add(objectMapper.writeValueAsString(user)).execute()
        return findByUsername(user.username).get()
    }

    override fun findById(id: String): Optional<User> {
        val doc = userCollection.getOne(id) ?: return Optional.empty()
        return Optional.of(objectMapper.readValue(doc.toString(), User::class.java))
    }

    override fun existsById(id: String): Boolean {
        return findById(id).isPresent
    }

    fun findByUsername(username: String?): Optional<User> {
        val docResult = userCollection.find("username = :username").bind("username", username).execute()
        return if(docResult.count() > 0) {
            val doc = docResult.fetchOne()
            val user = objectMapper.readValue(doc.toString(), User::class.java)
            return Optional.ofNullable(user)
        }
        else{
           Optional.empty()
        }
    }

    override fun deleteById(id: String) {
        userCollection.removeOne(id)
    }

    override fun count(): Long {
        return userCollection.count()
    }

    override fun deleteAll() {
        userCollection.remove("true").execute()
    }

    override fun findAll(pageRequest: PageRequest): List<User> {
        val docResult = userCollection.find().limit(pageRequest.pageSize).offset(pageRequest.offset).execute()
        return getListOfUsers(docResult)
    }

    override fun findAll(): List<User>{
        val docResult = userCollection.find().execute()
        return getListOfUsers(docResult)
    }

    private fun getListOfUsers(docResult: DocResult): List<User> {
        if(docResult.count() == 0L) {
            return listOf()
        }
        val listOfDbDocuments  = docResult.fetchAll()
        return listOfDbDocuments.map {
            objectMapper.readValue(it.toString(), User::class.java)
        }
    }

}