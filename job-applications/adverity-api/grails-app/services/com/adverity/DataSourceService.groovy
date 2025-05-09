package com.adverity

import grails.gorm.transactions.Transactional

@Transactional
class DataSourceService {


     List<DataSource> getDataSources(Map<String, String> request) {
        List<DataSource> sources = []
        if(request == null || request?.isEmpty()) {
            sources = DataSource.findAll()
        }
        else {
            sources = DataSource.createCriteria().list(getDatasourceByCriteria(request))
        }

        return sources
    }

    Closure  getDatasourceByCriteria(Map<String, String> request) {
        def closure = {
            and {
                request.each {
                    eq it.key, it.key == 'id' ? it.value.toLong().longValue() : it.value
                }

            }
        }
    }



}
