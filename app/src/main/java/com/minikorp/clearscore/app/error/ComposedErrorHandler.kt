package com.minikorp.clearscore.app.error

/**
 * Error handler that delegates to other two, left one takes priority
 */
data class ComposedErrorHandler(val left: ErrorHandler, val right: ErrorHandler) : ErrorHandler {

    override fun handle(ex: Throwable?): Boolean {
        return left.handle(ex) || right.handle(ex)
    }

    override fun getMessage(ex: Throwable?): String? {
        return left.getMessage(ex) ?: right.getMessage(ex)
    }
}