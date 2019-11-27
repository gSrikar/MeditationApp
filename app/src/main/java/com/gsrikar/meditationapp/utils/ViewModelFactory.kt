package com.gsrikar.meditationapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.InvocationTargetException


/**
 * Provides arguments to the view model
 */
class ViewModelFactory(private val repository: Any) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(repository.javaClass).newInstance(repository)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        // Throw exception for requesting to create unknown view model
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}