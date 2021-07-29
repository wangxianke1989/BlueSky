package com.bluesky.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluesky.android.R
import com.bluesky.android.databinding.FragmentPlaceBinding
import com.bluesky.android.ui.weather.WeatherActivity

class PlaceFragment: Fragment() {
    private  var  _binding:FragmentPlaceBinding?=null
    private val binding get() = _binding

    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter:PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceBinding.inflate(inflater,container,false)
        val view = binding?.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(viewModel.isPlaceSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context,WeatherActivity::class.java).apply{
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        binding?.recyclerView?.adapter = adapter

        binding?.searchPlaceEdit?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                val content = editable.toString()
                if (content.isNotEmpty()){
                    viewModel.searchPlaces(content)
                }else{
                    binding?.recyclerView?.visibility = View.GONE
                    binding?.bgImageView?.visibility = View.VISIBLE
                    viewModel.placeList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer{ result->
            val places = result.getOrNull()
            if(places != null){
                binding?.recyclerView?.visibility = View.VISIBLE
                binding?.bgImageView?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}