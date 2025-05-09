class AuthCred {
    String username
    String password
}

println "Hello"

File file = new File("/Users/tma24/keepass.tx.csv")
File m25File = new File("/Users/tma24/basic_auth_names")
List<AuthCred> authNames_m25 = new ArrayList<>()
Map coreMap = [:]


m25File.readLines().each {
   def arr =  it.split(":")

   String username = arr[0]
   String password = arr[1].trim()

   authNames_m25.add(new AuthCred(username: username, password: password))

}

file.readLines().each {
    if(it.contains("authorisation_") && it.contains("Root/int-nft.ce.eu-west-1-aws.npottdc.sky/int-nft/cybertron-int-nft-shared")) {
        def arr = it.split(",")
        String username = arr[1].replaceAll("\"","")
        String password = arr[3].replaceAll("\"","")

        coreMap.put(username, password)
//        println "${username},${password}"

    }
}

authNames_m25.each { authName ->
    String password = coreMap[authName.username]
    if(password != null &&  password != authName.password) {
        println "${authName.username} '${authName.password}' '${password}'"
    }
}





