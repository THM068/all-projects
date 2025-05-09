import groovy.json.JsonSlurper

class FeatureFlag {
    String name
    String value
}
List<FeatureFlag> m25FeatureFlags = new ArrayList<>()
Map<String,String> coreFeatureFlagMap = [:]
Map<String,String> m25FeatureFlagMap = [:]

File m25Config = new File("/Users/tma24/Downloads/cybertron-variables.json")
File coreConfig = new File("/Users/tma24/tmp/stable-int-config.txt")

def json = new JsonSlurper().parseText(m25Config.text)

def result = GQ {
    from p in json.results.Variables
    where p.Name.contains("FEATURE_") && (p.Scope.Environment?.contains("Environments-142"))
    select p.Name, p.Value, p.Scope.Environment

}.toList().each {
//    println "${it[0]}, ${it[1]}, ${it[2]}"
    m25FeatureFlagMap.put(it[0], it[1])
    m25FeatureFlags << new FeatureFlag(name: it[0], value: it[1])
}

coreConfig.readLines().each {
    if(it.trim().startsWith("FEATURE_")) {
        String []arr = it.split(":")
        String name = arr[0].trim()
        String value = arr[1].trim().replaceAll("\"","")

        coreFeatureFlagMap.put(name, value)

        if(m25FeatureFlagMap.containsKey(name) && !(m25FeatureFlagMap.get(name).equals(value)) ) {
            println "${name} , ${m25FeatureFlagMap.get(name)},  [ ${name}, ${value} ]"
        }
        else {
            println " Missing: ${name} - ${value}"
        }
//        def results = m25FeatureFlags.find { m25 ->
//          m25.name == name && !m25.value.equals(value)
//        }

//        if(results) {
//            println "${results.name} , ${results.value},  [ ${name}, ${value} ]"
//        }

//        if (results == null) {
//            println "Does not exist ${results?.name} , ${results?.value},  [ '${name}', '${value}' ]\""
//        }
    }
}

println "---------------------------Print flags that are in m25 but not Coreplatform------------------------------------------------------"

m25FeatureFlags.each { m25Flag ->
    if(!coreFeatureFlagMap.containsKey(m25Flag.name)) {
        println m25Flag.name
    }
}
