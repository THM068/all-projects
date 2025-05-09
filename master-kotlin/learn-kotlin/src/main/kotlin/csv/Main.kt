package csv

import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader


class Main {
    val path = "/Users/tma24/Downloads/PIxSyyrIKFORrCXfMYqZBI.csv"
    val bufferedReader = BufferedReader(FileReader(path))
}


@Throws(Exception::class)
fun readAll(reader: Reader): List<Array<String?>?>? {
    val csvReader = CSVReader(reader)
    var list: List<Array<String?>?> = ArrayList()
    list = csvReader.readAll()
    reader.close()
    csvReader.close()
    return list
}