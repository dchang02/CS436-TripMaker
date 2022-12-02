package com.example.generateinitialitinerary

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.generateinitialitinerary.databinding.FragmentSearchScreenBinding
import kotlinx.android.synthetic.main.fragment_search_screen.view.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

class SearchScreen : Fragment() {

    var arrayList = ArrayList<ModelClass>()

    private var data = arrayOf("airport", "amusement_park", "aquarium", "art_gallery",
        "beauty_salon", "bicycle_store", "book_store", "bowling_alley",
        "campground", "casino", "cemetery", "church", "city_hall", "clothing_store",
        "courthouse", "department_store", "electronics_store", "embassy", "fire_station", "florist",
        "gym", "hindu_temple", "home_goods_store", "jewelry_store", "library",
        "local_government_office", "mosque",
        "movie_rental", "movie_theater", "museum", "night_club", "park", "pet_store",
        "rv_park", "shoe_store", "shopping_mall", "spa", "stadium",
        "store",  "synagogue", "taxi_stand", "tourist_attraction", "university", "zoo")

    var JSONResults = ArrayList<String>()

    var selected = ArrayList<String>()

    private var client = OkHttpClient()

    private lateinit var binding: FragmentSearchScreenBinding

    private fun getData(): ArrayList<ModelClass> {
        for (i in data.indices){
            arrayList.add(ModelClass(data[i], false))
        }
        return arrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        arrayList.clear()
        getData()
        val view = inflater.inflate(R.layout.fragment_search_screen, container, false)
        view.postsRecyclerView.layoutManager = LinearLayoutManager(activity)
        view.postsRecyclerView.adapter = myAdapter(this, arrayList)

        view.button.setOnClickListener {

            val geocoder = Geocoder(this.requireContext())

            var destination_name = geocoder.getFromLocationName("XXXXXXXXXXXXXXXXXXX", 1)

            if (view.enterDestination.text.toString().length > 0){
                destination_name = geocoder.getFromLocationName(view.enterDestination.text.toString(), 1)
            }

            if (destination_name.size == 0){
                displayToastMessage(true, false)
            }

            if (destination_name.size != 0){

                var longitutude = destination_name[0].longitude
                var latitude = destination_name[0].latitude

                JSONResults.clear()

                for (interest in arrayList){
                    if (interest.isSelected){
                        selected.add(interest.name)
                        run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=${interest.name}&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                    }
                }

                var count = 0
                for (i in JSONResults.indices){
                    if ("ZERO_RESULTS" in JSONResults[i]){
                        count++
                    }
                }

                if(count == JSONResults.size){
                    displayToastMessage(false, true)
                }

                if(count == JSONResults.size){
                    return@setOnClickListener
                }


                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=meal_delivery&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=bar&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=meal_takeaway&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=restaurant&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=cafe&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitutude}&radius=32186.9&type=bakery&key=AIzaSyDRO61N5SowT1M8qgnhphbTkIj346lYBQg")


                if (JSONResults.size != 0){
                    findNavController().navigate(
                        SearchScreenDirections.actionLoginFragmentToHelloFragment(JSONResults.toTypedArray(), selected.toTypedArray())
                    )
                }

            }
        }
        return view
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        val countDownLatch = CountDownLatch(1)

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    JSONResults.add(response.body!!.string())
                }
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
    }

    fun displayToastMessage(destinationIssue : Boolean, interestsIssue : Boolean) {

        if (destinationIssue){
            Toast.makeText(
                this.requireContext(),
                "Please enter a valid destination",
                Toast.LENGTH_LONG,
            ).show()
        }
        else{
            Toast.makeText(
                this.requireContext(),
                "Apologies!, there's not enough of this type of interest in your given location",
                Toast.LENGTH_LONG
            ).show()
        }

    }

}