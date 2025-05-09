String filePath = "/Users/tma24/Documents/last_3_months_profile_id.csv"
String filePathProfileIdFromNowTvList = "/Users/tma24/Documents/intersect.txt"

File file = new File(filePath)
List<String> profileIds = []
List<String> nowTvProfiles = []
def line, noOfLines = 0;
file.withReader { reader ->
    while ((line = reader.readLine()) != null) {
        String [] split = line.split(",")
        profileIds << split[0]
        noOfLines++
    }
}
profileIds.remove(0)
File profileIdFile = new File(filePathProfileIdFromNowTvList)
String pLine
profileIdFile.withReader { reader ->
    while ((pLine = reader.readLine()) != null) {
        nowTvProfiles << pLine.replace("\"", "")
    }
}
List<Integer> commonProfiles = nowTvProfiles.intersect(profileIds)
println("profileIds:          ${profileIds}")
println("nowTvProfiles:       ${nowTvProfiles}")
println("commonProfiles:      ${commonProfiles}")
println("profileIds size:     ${profileIds.size()}")
println("nowTvProfiles size:  ${nowTvProfiles.size()}")
println("commonProfiles size: ${commonProfiles.size()}")


