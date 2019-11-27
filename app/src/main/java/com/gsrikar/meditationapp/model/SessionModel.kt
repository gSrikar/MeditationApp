package com.gsrikar.meditationapp.model

import com.google.firebase.firestore.PropertyName


/**
 * Details of the Session
 */
data class SessionModel(
    /**
     * Image link
     */
    @get:PropertyName("imageLink")
    @set:PropertyName("imageLink")
    var imageLink: String? = null,

    /**
     * Audio link
     */
    @get:PropertyName("link")
    @set:PropertyName("link")
    var link: String? = null
)