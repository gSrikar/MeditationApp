package com.gsrikar.meditationapp.network


/**
 * Status of Api Response calls
 */
enum class Status {
    // Api call is a success
    SUCCESS,
    // Api call failed
    ERROR,
    // Api call is loading or started
    LOADING
}