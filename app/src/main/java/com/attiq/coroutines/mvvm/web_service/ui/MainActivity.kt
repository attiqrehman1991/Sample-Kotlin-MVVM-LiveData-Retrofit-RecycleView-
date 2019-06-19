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

package com.attiq.coroutines.mvvm.web_service.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.attiq.coroutines.mvvm.web_service.R
import com.attiq.coroutines.mvvm.web_service.adapter.PartAdapter
import com.attiq.coroutines.mvvm.web_service.model.Part
import com.attiq.coroutines.mvvm.web_service.model.PartData
import com.attiq.coroutines.mvvm.web_service.viewmodels.PostListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PartAdapter
    private lateinit var viewModel: PostListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders
            .of(this)
            .get(PostListViewModel::class.java)

        initRecycleView()
        subscribeObservers()
        viewModel.fetchPosts()
    }

    private fun initRecycleView() {
        adapter = PartAdapter(mutableListOf(), { partItem: Part -> partItemClicked(partItem) })
        rv_parts.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.posts().observe(this, object : Observer<PartData> {
            override fun onChanged(partData: PartData?) {
                adapter.addAll(partData!!.parts)
            }
        })
    }

    //    private fun loadPartsAndUpdateList() {
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val webResponse = WebAccess.partsApi.getPartsAsync().await()
//
//                if (webResponse.isSuccessful) {
//                    val partList: List<PartData>? = webResponse.body()
//                    Log.d(tag, partList?.toString())
//                    adapter.partItemList = partList ?: listOf()
//                    adapter.notifyDataSetChanged()
//                } else {
//                    Log.e(tag, "Error ${webResponse.code()}")
//                    Toast.makeText(this@MainActivity, "Error ${webResponse.code()}", Toast.LENGTH_LONG).show()
//                }
//            } catch (e: IOException) {
//                Log.e(tag, "Exception " + e.printStackTrace())
//                Toast.makeText(this@MainActivity, "Exception ${e.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    private fun addPart(partItem: PartData) {
//        GlobalScope.launch(Dispatchers.Main) {
//            val webResponse = WebAccess.partsApi.addPartAsync(partItem).await()
//            Log.d(tag, "Add success: ${webResponse.isSuccessful}")
//            loadPartsAndUpdateList()
//        }
//    }
//
//
    private fun partItemClicked(partItem: Part) {
        Toast.makeText(this, "Clicked: ${partItem.itemName}", Toast.LENGTH_LONG).show()

//        // Launch second activity, pass part ID as string parameter
//        val showDetailActivityIntent = Intent(this, PartDetailActivity::class.java)
//        //showDetailActivityIntent.putExtra(Intent.EXTRA_TEXT, partItem.id.toString())
//        showDetailActivityIntent.putExtra("ItemId", partItem.id)
//        showDetailActivityIntent.putExtra("ItemName", partItem.itemName)
//        startActivity(showDetailActivityIntent)
    }


}
