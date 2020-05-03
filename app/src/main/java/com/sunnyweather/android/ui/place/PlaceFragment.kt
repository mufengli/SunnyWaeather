package com.sunnyweather.android.ui.place

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment :Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager=LinearLayoutManager(context)
        val adapter=PlaceAdapter(viewModel.placeList)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=layoutManager
        searchPlaceEdit.addTextChangedListener{ editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlace(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this, Observer {
            result ->
            val place=result.getOrNull()
            if(place!=null){
                recyclerView.visibility=View.VISIBLE
                bgImageView.visibility=View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}