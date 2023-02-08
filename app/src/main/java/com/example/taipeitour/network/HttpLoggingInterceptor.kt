package com.example.taipeitour.network

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.charset.UnsupportedCharsetException
import java.util.*
import java.util.concurrent.TimeUnit

class HttpLoggingInterceptor : Interceptor {
    private val logger = Logger.DEFAULT
    private var level = Level.NONE

    enum class Level {
        NONE,
        HEADERS,
        BODY
    }

    interface Logger {
        fun log(message: String)

        companion object {
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Log.w("RDClient", "logger >> \nmessage")
                }
            }
        }
    }

    fun setLevel(level: Level): HttpLoggingInterceptor {
        this.level = level
        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = level
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS
        val requestBody = request.body
        val hasRequestBody = requestBody != null
        val connection = chain.connection()
        val protocol = connection?.protocol() ?: Protocol.HTTP_1_1
        var requestStartMessage = "--> " + request.method + ' ' + request.url + ' ' + protocol
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        logger.log(requestStartMessage)

        if (logHeaders) {
            if (hasRequestBody) {
                if (requestBody!!.contentType() != null) {
                    logger.log("Content-Type: " + requestBody.contentType())
                }
                if (requestBody.contentLength() != -1L) {
                    logger.log("Content-Length: " + requestBody.contentLength())
                }
            }
            val headers = request.headers
            var i = 0
            val count = headers.size

            while(i<count) {
                val name = headers.name(i)
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    logger.log(name + ": " + headers.value(i))
                }
                i++
            }

            if (!logBody || !hasRequestBody) {
                logger.log("--> END " + request.method)
            } else if (bodyEncoded(request.headers)) {
                logger.log("--> END " + request.method + " (encoded body omitted)")
            } else {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                logger.log("")
                if (isPlaintext(buffer)) {
                    logger.log(
                        buffer.readString(
                            Objects.requireNonNull(
                                charset
                            )
                        )
                    )
                    logger.log("--> END " + request.method + " (" + requestBody.contentLength() + "-byte body)")
                } else {
                    logger.log("--> END " + request.method + " (binary " + requestBody.contentLength() + "-byte body omitted)")
                }
            }
        }
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs =
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body
        val contentLength =
            responseBody?.contentLength()
        val bodySize =
            if (contentLength != -1L) "contentLength-byte" else "unknown-length"
        logger.log(
            "<-- " + response.code + ' ' + response.message + ' ' + response.request.url + " (" + tookMs + "ms" + (if (!logHeaders) ", " +
                    bodySize + " body" else "") + ')'
        )
        if (logHeaders) {
            val headers = response.headers
            var i = 0
            val count = headers.size
            while (i < count) {
                logger.log(headers.name(i) + ": " + headers.value(i))
                i++
            }
            if (bodyEncoded(response.headers)) {
                logger.log("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody!!.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = try {
                        contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        logger.log("")
                        logger.log("Couldn't decode the response body; charset is likely malformed.")
                        logger.log("<-- END HTTP")
                        return response
                    }
                }
                if (!isPlaintext(buffer)) {
                    logger.log("")
                    logger.log("<-- END HTTP (binary " + buffer.size + "-byte body omitted)")
                    return response
                }
                if (contentLength != 0L) {
                    logger.log("")
                    logger.log(
                        buffer.clone().readString(
                            Objects.requireNonNull(charset)
                        )
                    )
                }
                logger.log("<-- END HTTP (" + buffer.size + "-byte body)")
            }
        }
        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {
        private val UTF8 = StandardCharsets.UTF_8
        fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    if (Character.isISOControl(prefix.readUtf8CodePoint())) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false
            }
        }
    }
}