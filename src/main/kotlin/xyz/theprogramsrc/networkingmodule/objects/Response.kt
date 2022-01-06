package xyz.theprogramsrc.networkingmodule.objects

import java.io.InputStream
import java.net.HttpURLConnection

class Response(val request: Request, val connection: HttpURLConnection) {

    var expectedStatusCode: Int = 200

    /**
     * Checks if the status code is the same as the expected status code
     * @return true if the status code is the same as the expected status code
     */
    fun wasSuccessful(): Boolean = connection.responseCode == expectedStatusCode

    /**
     * Gets the response code
     * @return the response code
     */
    fun getResponseCode(): Int = connection.responseCode

    /**
     * Gets the response message
     * @return the response message
     */
    fun getResponseMessage(): String = connection.responseMessage

    /**
     * Gets the response stream
     * @return the response stream
     */
    fun getResponseStream(): InputStream = connection.inputStream

    /**
     * Gets the response string
     * @return the response string
     */
    fun getResponseString(): String = connection.inputStream.bufferedReader().use { it.readText() }

    /**
     * Gets the response headers
     * @return the response headers
     */
    fun getResponseHeaders(): Map<String, List<String>> = connection.headerFields

    /**
     * Gets the response header
     * @return the response header
     */
    fun getResponseHeader(header: String): String? = connection.headerFields[header]?.get(0)

    /**
     * Gets the response header
     * @return the response header
     */
    fun getResponseHeader(header: String, index: Int): String? = connection.headerFields[header]?.get(index)

    /**
     * Gets the response header count
     * @return the response header count
     */
    fun getResponseHeaderCount(header: String): Int = connection.headerFields[header]?.size ?: 0

    /**
     * Gets the response header names
     * @return the response header names
     */
    fun getResponseHeaderNames(): List<String> = connection.headerFields.keys.toList()

}