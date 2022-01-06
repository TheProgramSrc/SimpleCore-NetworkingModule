package xyz.theprogramsrc.networkingmodule.builder

import xyz.theprogramsrc.networkingmodule.objects.Request
import xyz.theprogramsrc.networkingmodule.objects.Response
import java.net.HttpURLConnection
import java.net.URL

object ConnectionBuilder {

    /**
     * Connects to the given [URL]
     * @param url The [URL] to connect to
     * @return The [Response] of the connection
     */
    fun connect(url: String): Response = connect(Request(url))

    /**
     * Connects to the given [URL] using the given request method
     * @param url The [Request] to connect to
     * @param method The request method to use
     * @return The [Response] of the connection
     */
    fun connect(url: String, method: String): Response = connect(Request(url, method))

    /**
     * Connects to the given [URL] using the given request method and headers
     * @param url The [Request] to connect to
     * @param method The request method to use
     * @param properties The properties to use in the query
     * @return The [Response] of the connection
     */
    fun connect(url: String, method: String, properties: Map<String, String>): Response = connect(Request(url, method, properties=properties))

    /**
     * Connects to the given [URL] using the given request method and headers
     * @param url The [Request] to connect to
     * @param method The request method to use
     * @param properties The properties to use in the query
     * @param headers The headers to use in the query
     * @return The [Response] of the connection
     */
    fun connect(url: String, method: String, properties: Map<String, String>, headers: Map<String, String>): Response = connect(Request(url, method, headers, properties))

    /**
     * Connects to the given [URL] using the given request method and headers
     * @param request The [Request] to connect to
     * @return The [Response] of the connection
     */
    fun connect(request: Request): Response {
        // Generate the url and connection
        val url = URL(request.url)
        val connection = url.openConnection() as HttpURLConnection

        // Apply the method
        if(connection.requestMethod != request.method){
            connection.requestMethod = request.method
        }

        // Apply User Agent
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")

        // Apply the headers
        val headers = request.headers
        for (header in headers) {
            connection.setRequestProperty(header.key, header.value)
        }

        // Apply the body or properties if any
        val body = if(request.properties.isNotEmpty()) {
            val props = request.properties
            props.entries.joinToString("&") { "${it.key}=${it.value}" }
        } else {
            request.body
        } ?: ""
        connection.doInput = true
        connection.doOutput = true
        connection.outputStream.write(body.toByteArray())

        // Apply timeouts
        connection.connectTimeout = request.connectTimeout
        connection.readTimeout = request.readTimeout

        // Connect
        connection.connect()

        return Response(request, connection)
    }
}