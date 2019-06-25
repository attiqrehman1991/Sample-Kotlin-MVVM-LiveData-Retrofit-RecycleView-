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

package com.attiq.coroutines.mvvm.web_service.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.attiq.coroutines.mvvm.web_service.model.PartData
import com.attiq.coroutines.mvvm.web_service.network.PartsListApiClient

class PartsRepositories {
    companion object {
        val instance: PartsRepositories by lazy {
            PartsRepositories()
        }
    }

    private val postDataApiClient = PartsListApiClient.instance
    private var partList = MediatorLiveData<PartData>()

    init {
        initMediators()
    }

    fun initMediators() {
        val source = postDataApiClient.getPartLists()
        partList.addSource(source, object : Observer<PartData> {
            override fun onChanged(response: PartData?) {
                if (response != null)
                    partList.value = response
            }
        })

    }

    fun getParts(): LiveData<PartData> {
        return partList
    }

    suspend fun fetchPartList() {
        postDataApiClient.fetchPartListData()
    }
}