String filePath = "/Users/tma24/Documents/last_3_months_profile_id.csv"
String filePathProfileIdFromNowTvList = "/Users/tma24/Documents/intersect.txt"

File file = new File(filePath)
List<Integer> profileIds = []
List<Integer> nowTvProfiles = []
def line, noOfLines = 0;
file.withReader { reader ->
    while ((line = reader.readLine()) != null) {
        String [] split = line.split(",")
        profileIds << split[0]
        noOfLines++
    }
}
profileIds.remove(0)
println(profileIds)

File profileIdFile = new File(filePathProfileIdFromNowTvList)
String pLine
profileIdFile.withReader { reader ->
    while ((pLine = reader.readLine()) != null) {
        nowTvProfiles << pLine
    }
}

println(nowTvProfiles)

List<Integer> commonProfiles = nowTvProfiles.intersect(profileIds)
println(commonProfiles)
