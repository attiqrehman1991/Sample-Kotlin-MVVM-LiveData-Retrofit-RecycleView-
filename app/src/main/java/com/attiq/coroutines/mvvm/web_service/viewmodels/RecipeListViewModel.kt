package com.attiq.coroutines.mvvm.web_service.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeListViewModel : ViewModel() {

    private val _posts = MutableLiveData<String>()

//    suspend fun posts() {
//        withContext(Dispatchers.IO) {
//            try {
//                val result= network.fetchPosts().await()
//                _posts.value= result
//            } catch (err: Exception) {
//                err.printStackTrace()
//                _posts.value= null
//            }
//        }

//    }

    fun posts() {
        _posts.value= ""
    }
}