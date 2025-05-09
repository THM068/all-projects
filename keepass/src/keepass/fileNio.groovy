package keepass

import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.Paths

Path path = Paths.get("/Users/tma24/backup.txt")
println path.toAbsolutePath()

boolean doesPathExist = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})
assert doesPathExist

Files.readAllLines(path).each {println it}