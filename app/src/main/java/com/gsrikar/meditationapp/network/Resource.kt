package com.gsrikar.meditationapp.network


/**
 * Resource contains the status of the Api calls, response data and error message
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                msg
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                null
            )
        }
    }
}