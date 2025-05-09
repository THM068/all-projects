package keepass

println "Hello"

def gaugeBasicAuthNamesFiles = new File("/Users/tma24/users.txt")
def keepassDatabaseCSV = new File("/Users/tma24/test.csv")
def uniqueBasicAuthNames = new File("/Users/tma24/uniqueNames.txt")
String linesOfInterest = "skyglobal-int-nft"

assert gaugeBasicAuthNamesFiles.exists()
Set<String> uniqueNames = new HashSet<String>()
gaugeBasicAuthNamesFiles.readLines().each {
    uniqueNames.add(it.split("\t").first())
}
List<String> uniqueNamesList = uniqueNames.asList().sort { a,b -> a <=> b }
uniqueNamesList.forEach {userNamePrefix ->

    keepassDatabaseCSV.readLines().each { line ->
        if(line.contains(linesOfInterest) && line.contains("BASIC_AUTH_${userNamePrefix}")) {
             def array = line.split(",")
             String username = array[1].replace("\"","")
             String title = array[2].replace("\"","")
             String password = array[3].replace("\"","")
             println("${username},${title},${password}")
        }
    }

}