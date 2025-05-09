package com.kigali.city.com.kigali.city.dao

import com.mysql.cj.xdevapi.ClientFactory
import com.mysql.cj.xdevapi.Collection
import com.mysql.cj.xdevapi.Schema
import com.mysql.cj.xdevapi.Session
import com.mysql.cj.xdevapi.SessionFactory


object db {
    val DB_SCHEMA="reportit_kigali"
    val DB_URL = "mysqlx://localhost:33060/${DB_SCHEMA}?user=root&password="
    fun getSession(): Session {
        val cf = ClientFactory()
        val cli = cf.getClient(DB_URL, """{"pooling":{"enabled":true, "maxSize":8,"maxIdleTime":30000, "queueTimeout":10000} }""");
        val session = cli.getSession()
        return session
    }

    fun getSchema(): Schema {
        return getSession().getSchema(DB_SCHEMA)
    }

    fun getCollection( collection: String): Collection {
        return getSchema().createCollection(collection, true)
    }
}