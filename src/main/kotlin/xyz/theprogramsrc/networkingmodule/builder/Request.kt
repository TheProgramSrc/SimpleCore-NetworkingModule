package xyz.theprogramsrc.networkingmodule.builder

import xyz.theprogramsrc.networkingmodule.objects.Response
import java.net.HttpURLConnection
import java.net.URL

/**
 * This class is used to build a request
 * @param url The url of the request
 */
class Request(val url: String){

    /**
     * The method of the request.
     * Defaults to GET
     */
    var method: String = "GET"

    /**
     * The headers of the request.
     * Defaults to map with User-Agent
     */
    var headers: MutableMap<String, String> = mutableMapOf(
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36"
    )

    /**
     * The body of the request. (Set to empty string to ignore).
     * Defaults to empty string
     */
    var body: String = ""

    /**
     * The timeout of the request (Set to -1 to disable).
     * Defaults to -1
     */
    var timeout: Int = -1

    /**
     * True if the request should use the cache, false otherwise.
     * Defaults to true
     */
    var useCache: Boolean = true

    /**
     * Sets the request method to GET
     * @return this
     */
    fun get() = this.apply { method = "GET" }

    /**
     * Sets the request method to POST
     * @return this
     */
    fun post() = this.apply { method = "POST" }

    /**
     * Sets the request method to PUT
     * @return this
     */
    fun put() = this.apply { method = "PUT" }

    /**
     * Sets the request method to DELETE
     * @return this
     */
    fun delete() = this.apply { method = "DELETE" }

    /**
     * Sets the body of the request
     * @param body The body of the request
     * @return this
     */
    fun body(body: String) = this.apply { this.body = body }

    /**
     * Sets the timeout of the request
     * @param timeout The timeout of the request. (Set to -1 to disable)
     * @return this
     */
    fun timeout(timeout: Int) = this.apply { this.timeout = timeout }

    /**
     * Sets the header of the request. If the header already exists, it will be overwritten
     * @param key The key of the header
     * @param value The value of the header
     * @return this
     */
    fun header(key: String, value: String) = this.apply { headers[key] = value }

    /**
     * Sets if the request should use the cache
     * @param useCache True if the request should use the cache, false otherwise
     * @return this
     */
    fun useCache(useCache: Boolean) = this.apply { this.useCache = useCache }

    /**
     * Sends the request and returns a response
     * @return the [Response]
     */
    fun connect(): Response {
        val url = URL(
            if(this.method == "GET" && this.body.isNotBlank()) {
                "${this.url}?${this.body}"
            } else {
                this.url
            }
        )
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = this.method
        connection.doOutput = this.method == "POST" || this.method == "PUT" || (this.body.isNotBlank() && this.method != "GET")
        connection.doInput = true
        connection.useCaches = this.useCache
        if(this.timeout != -1) connection.connectTimeout = this.timeout
        if(this.timeout != -1) connection.readTimeout = this.timeout
        this.headers.forEach { connection.setRequestProperty(it.key, it.value) }
        if(this.body.isNotEmpty() && this.method != "GET") connection.outputStream.write(this.body.toByteArray())
        return Response(connection)
    }
}