package com.gsrikar.meditationapp.model

import com.google.firebase.firestore.PropertyName


/**
 * Details of the Meditation
 */
data class MeditationModel(
    /**
     * Random for the meditation
     */
    @get:PropertyName("doing_right_now")
    @set:PropertyName("doing_right_now")
    var drn: Int? = null,

    /**
     * Name of the meditation
     */
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = null
)