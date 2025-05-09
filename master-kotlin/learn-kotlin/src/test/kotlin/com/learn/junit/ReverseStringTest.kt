package com.learn.junit

import org.junit.jupiter.api.Test
import streams.StreamsTest
import java.io.FileInputStream
import java.io.FileOutputStream

class ReverseStringTest {
    private val BUFFER = 4096
    private val FILE_PATH = "/Users/tma24/private_projects/master-kotlin/learn-kotlin/src/image.jpeg"
    private val DEST_FILE_PATH = "/Users/tma24/private_projects/master-kotlin/learn-kotlin/src/image_out.jpeg"

    @Test
    fun `copy image`(){
        val buffer = ByteArray(BUFFER)
        var readPart = -1
        FileInputStream(FILE_PATH).use {  ins ->
            FileOutputStream(DEST_FILE_PATH).use { fos ->
                do {
                    readPart = ins.read(buffer)
                    println(readPart)
                    if(readPart != -1) {
                        fos.write(buffer, 0, readPart)
                    }
                }
                while( readPart != -1)
            }
        }
    }

}

