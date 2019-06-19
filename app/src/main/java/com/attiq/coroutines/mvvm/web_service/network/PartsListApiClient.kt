/*
*   MIT License
*
*   Copyright (c) 2019 Attiq ur Rehman
*
*   Permission is hereby granted, free of charge, to any person obtaining a copy
*   of this software and associated documentation files (the "Software"), to deal
*   in the Software without restriction, including without limitation the rights
*   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*   copies of the Software, and to permit persons to whom the Software is
*   furnished to do so, subject to the following conditions:
*
*   The above copyright notice and this permission notice shall be included in all
*   copies or substantial portions of the Software.
*
*   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
*   SOFTWARE.
*/

package com.attiq.coroutines.mvvm.web_service.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.attiq.coroutines.mvvm.web_service.model.PartData
import com.google.gson.Gson
import java.io.IOException

class PartsListApiClient {

    companion object {
        val instance: PartsListApiClient by lazy {
            PartsListApiClient()
        }
    }

    private var partLists = MutableLiveData<PartData>()
    private var retrievePLRunnable: RetrievePDRunnable? = null

    fun getPartLists(): LiveData<PartData> {
        return partLists
    }

    fun fetchPartListData() {
//        if (retrievePLRunnable != null)
//            retrievePLRunnable = null
//        retrievePLRunnable = RetrievePDRunnable(partLists);
//        val handler = AppExecutors.instance.networkIO().submit(retrievePLRunnable)
//        AppExecutors.instance.networkIO().schedule({
//            handler.cancel(true)
//        }, 5000, TimeUnit.MILLISECONDS)

        try {
            val response = WebAccess.partsApi.partDataList().execute()
            Log.d("response", "Response" + Gson().toJson(response.body()))
            if (response.code() == 200) {
                val list = response.body() as PartData
                partLists.postValue(list)
            } else
                partLists.postValue(null)
        } catch (err: IOException) {
            err.printStackTrace()
            Log.d("msg", err.toString())
            partLists.postValue(null)
        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("msg", err.toString())
            partLists.postValue(null)
        }
    }
}