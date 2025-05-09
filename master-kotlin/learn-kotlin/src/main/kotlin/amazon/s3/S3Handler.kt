package amazon.s3

import com.amazonaws.AmazonServiceException
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import java.io.File

val s3 = S3Handler()
class S3Handler {

    fun upload(action: S3DataBuilder.() -> Unit) : Unit {
        val s3DataBuilder = S3DataBuilder()
        s3DataBuilder.action()
        val s3 = AmazonS3ClientBuilder.standard().withRegion(s3DataBuilder.region).build()
        try {
            val putObject = s3.putObject(s3DataBuilder.bucketName, s3DataBuilder.key, s3DataBuilder.file)
        } catch (e: AmazonServiceException) {
            System.err.println(e.getErrorMessage())
            System.exit(1)
        }
    }

    operator fun invoke(action: S3Handler.() -> Unit): S3Handler {
        this.action()
        return this
    }
 }

class S3DataBuilder(){
    var region: Regions = Regions.EU_WEST_1
    var key: String = ""
    var bucketName: String = ""
    var file: File = File("/")
}



