package com.kigali.city.com.kigali.city.dao

import java.util.*

data class PageRequest(val  offset: Long, val pageSize: Long )
interface CrudRepository<T, ID>  {

    fun  save(var1: T): T

    fun findById(var1: ID): Optional<T>

    fun existsById(var1: ID): Boolean

    fun deleteById(id: ID)

    fun count(): Long

    fun deleteAll()

    fun findAll(): List<T>

    fun findAll(pageRequest: PageRequest): List<T>


}