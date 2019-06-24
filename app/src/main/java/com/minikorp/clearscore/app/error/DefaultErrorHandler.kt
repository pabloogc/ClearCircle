package com.minikorp.clearscore.app.error

/**
 * Dummy error handler implementation, does not handle exceptions.
 */
open class DefaultErrorHandler : ErrorHandler {
    override fun handle(ex: Throwable?): Boolean {
        return false //Nothing to do
    }

    override fun getMessage(ex: Throwable?): String? {
        return ex?.message ?: "Unexpected error"
    }
}