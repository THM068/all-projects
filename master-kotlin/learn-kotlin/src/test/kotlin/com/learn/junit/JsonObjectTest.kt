package com.learn.junit;
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.JsonPath.read
import org.json.JSONObject
import org.junit.jupiter.api.Test
import net.minidev.json.JSONArray

public class JsonObjectTest {

    @Test
    fun `check if service is in freezeList`() {
        val jsonBody = JSONObject(json)
        val externalDataObject = jsonBody.getJSONObject("externaldata")
         assert(externalDataObject.toString().contains("NOWTV-DE"))
        println(jsonBody::class.java)

        "".groupBy {  }
    }

    @Test
    fun `test me`() {
        println(j)
        val jsonArray: JSONArray = read(j, "$..json")
        println(jsonArray)
        println(jsonArray.toJSONString().contains("{\"freeze_service\":[\"NOWTV-DE\"]}"));
        //println(jsonArray.toJSONString().contains("{\\\"externaldata\\\":{\\\"freeze_service\\\":[\\\"NOWTV-GB\\\"]}"));
    }

    val j = "{\"meta\":{\"rev\":\"3-1682f8cdb93700000000000002000000\",\"flags\":33554432,\"expiration\":0,\"id\":\"PROFILE_99536276\"},\"xattrs\":{},\"json\":\"{\\\"externaldata\\\":{\\\"freeze_service\\\":[\\\"NOWTV-GB\\\"]},\\\"modifieddate\\\":\\\"2021-05-27T16:19:38.203Z\\\",\\\"type\\\":\\\"user\\\",\\\"id\\\":{\\\"profileid\\\":\\\"99536276\\\",\\\"trackingid\\\":\\\"XcBNQZFe5RvkEJgO90VuB7Ic6odC3Q/9gfM+15lco+s=\\\",\\\"uuid\\\":\\\"58538348-29fe-4f95-bca0-c55641285017\\\",\\\"aliases\\\":{\\\"email\\\":{\\\"alias\\\":\\\"gauge535522.1622132377643@oogway.oog\\\",\\\"verified\\\":false,\\\"registrationdate\\\":\\\"2021-05-27T16:19:37.972Z\\\"}}},\\\"authentication\\\":{\\\"passwordrequireschange\\\":false},\\\"details\\\":{\\\"username\\\":\\\"GAUGE535522.1622132377643\\\",\\\"title\\\":\\\"Mr\\\",\\\"firstname\\\":\\\"Lavelle\\\",\\\"lastname\\\":\\\"Romaguera\\\",\\\"contact\\\":{\\\"email\\\":\\\"gauge535522.1622132377643ce@oogway.oog\\\"},\\\"usertype\\\":\\\"guest\\\",\\\"display\\\":{}},\\\"marketing\\\":{\\\"targetedoptin\\\":true,\\\"skyoptin\\\":true,\\\"targetedoptindate\\\":\\\"2021-05-27T16:19:37.972Z\\\"},\\\"services\\\":{\\\"nowtv\\\":{\\\"servicename\\\":\\\"nowtv\\\",\\\"termsandconditionsaccepted\\\":\\\"2000-01-01T00:00:00.000Z\\\",\\\"globalaccess\\\":false}},\\\"registration\\\":{\\\"date\\\":\\\"2021-05-27T16:19:37.972Z\\\",\\\"termsandconditionsaccepted\\\":true},\\\"_version\\\":3}\"}"

    val json = """
        {
          "json" : {
            "externaldata": {
            "freeze_service": [
              "NOWTV-DE"
            ]
          }
          },
          
        }
    """.trimIndent()

}
