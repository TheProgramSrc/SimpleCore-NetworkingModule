package xyz.theprogramsrc.networkingmodule.builder

import com.google.gson.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

@DisplayName("Http Requests Test")
internal class RequestTest {

    @Nested
    @DisplayName("GET Request")
    inner class GetRequest {

        @Test fun getRequest() = assertTrue(
            Request("https://httpbin.org/get")
                .get()
                .connect()
                .wasSuccessful()
        )

    }

    @Nested
    @DisplayName("POST Request")
    inner class PostRequest {

        @Test fun postRequest() = assertTrue(
            Request("https://httpbin.org/post")
                .post()
                .connect()
                .wasSuccessful()
        )

    }

    @Nested
    @DisplayName("PUT Request")
    inner class PutRequest {

        @Test fun putRequest() = assertTrue(
            Request("https://httpbin.org/put")
                .put()
                .connect()
                .wasSuccessful()
        )

    }

    @Nested
    @DisplayName("DELETE Request")
    inner class DeleteRequest {

        @Test fun deleteRequest() = assertTrue(
            Request("https://httpbin.org/delete")
                .delete()
                .connect()
                .wasSuccessful()
        )

    }

    @Nested
    @DisplayName("Headers and Body Request")
    inner class HeadersAndBodyRequest {

        @Test fun headersRequest() {
            val response = Request("https://httpbin.org/get")
                .get()
                .header("Accept", "application/json")
                .header("My-Test-Header", "My-Test-Value")
                .connect()
            val json = JsonParser.parseString(response.getResponseString()).asJsonObject
            val headers = json.getAsJsonObject("headers")
            assertEquals("application/json", headers.get("Accept").asString)
            assertEquals("My-Test-Value", headers.get("My-Test-Header").asString)
        }

        @Test fun bodyRequest() {
            val response = Request("https://httpbin.org/get")
                .get()
                .body("argument=value&argument2=value2")
                .connect()
            val json = JsonParser.parseString(response.getResponseString()).asJsonObject
            val args = json.getAsJsonObject("args")
            assertEquals("value", args.get("argument").asString)
            assertEquals("value2", args.get("argument2").asString)
        }

    }


}