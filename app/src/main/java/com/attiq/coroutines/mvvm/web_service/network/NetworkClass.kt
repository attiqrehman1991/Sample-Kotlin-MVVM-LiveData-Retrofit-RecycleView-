package com.attiq.coroutines.mvvm.web_service.network

import android.os.Handler
import android.os.Looper
import com.attiq.coroutines.mvvm.web_service.model.PartData
import java.util.concurrent.Executors

private const val ONE_SECOND = 1_000L

private const val ERROR_RATE = 0.3

private val executor = Executors.newCachedThreadPool()

private val uiHandler = Handler(Looper.getMainLooper())

fun networkCall():NetworkClass<PartData> {
    val response = WebAccess.partsApi.partDataList().execute()

    if(response.code()==200) {
        response.
    }
}

sealed class FakeNetworkResult<T>

class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()

class FakeNetworkError<T>(val error: Throwable) : FakeNetworkResult<T>()

typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

class FakeNetworkException(message: String) : Throwable(message)

class NetworkClass<T> {
    var result: FakeNetworkResult<T>? = null

    var listener= mutableListOf<FakeNetworkListener<T>>()

    fun addOnResultListener(listener: (FakeNetworkResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }

    fun onSuccess(data: T) {
        result = FakeNetworkSuccess(data)
        sendResultToAllListeners()
    }

    fun onError(throwable: Throwable) {
        result = FakeNetworkError(throwable)
        sendResultToAllListeners()
    }

    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    private fun trySendResult(listener: FakeNetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}