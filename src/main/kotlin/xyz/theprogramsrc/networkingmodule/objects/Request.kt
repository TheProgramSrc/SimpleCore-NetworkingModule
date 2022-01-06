package xyz.theprogramsrc.networkingmodule.objects

/**
 * Represents a request
 * @param url The url of the request
 * @param method The method of the request
 * @param headers The headers of the request
 * @param properties The properties of the request
 */
data class Request(val url: String, val method: String = "GET", val headers: Map<String, String> = mapOf(), val properties: Map<String, String> = mapOf()){

    /**
     * Connection timeout
     * @return Timeout in milliseconds
     */
    var connectTimeout: Int = 12500

    /**
     * Read timeout
     * @return Timeout in milliseconds
     */
    var readTimeout: Int = 12500

    /**
     * The body of the request
     * @return the body
     */
    var body: String? = null

    /**
     * Adds the given header to the request
     * @param key The name of the header
     * @param value The value of the header
     * @return the request with the new header
     */
    fun addHeader(key: String, value: String): Request {
        return Request(url, method, headers.plus(Pair(key, value)))
    }

    /**
     * Adds the given headers to the request
     * @param headers the headers to add
     * @return the request with the new headers
     */
    fun addHeaders(headers: Map<String, String>): Request = Request(url, method, this.headers.plus(headers))

    /**
     * Sets the body of the request
     * @param body the body of the request
     * @return the request with the new body
     */
    fun setBody(body: String): Request = run {
        this.body = body
        this
    }

    /**
     * Adds the given property to the request
     * @param key the name of the property
     * @param value the value of the property
     * @return the request with the new property
     */
    fun addProperty(key: String, value: String): Request = Request(url, method, headers, properties.plus(Pair(key, value)))

    /**
     * Adds the given properties to the request
     * @param properties the properties to add
     * @return the request with the new properties
     */
    fun addProperties(properties: Map<String, String>): Request = Request(url, method, headers, this.properties.plus(properties))
}