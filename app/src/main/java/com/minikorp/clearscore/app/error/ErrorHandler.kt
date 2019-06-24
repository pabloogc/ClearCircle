package com.minikorp.clearscore.app.error

/**
 * Basic interface for UI error handling.
 *
 * Base class should provide basic error handling, screens
 * can implement specific screen-only management and compose
 * it if needed using [plus] function.
 */
interface ErrorHandler {

    /**
     * Handle error side effects (analytics, logout user...)
     *
     * @return true if the event was handled.
     */
    fun handle(ex: Throwable?): Boolean

    /**
     * Map exception to localized error.
     */
    fun getMessage(ex: Throwable?): String?

    operator fun plus(other: ErrorHandler): ErrorHandler {
        return ComposedErrorHandler(this, other)
    }
}