package com.example.generateinitialitinerary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_draft_itinerary.view.*

var places = ArrayList<Results>()

var arrayList = ArrayList<DraftModelClass>()

class Results{
    var results: List<Locations>? = null
}

class Locations{
    var name: String = "Name Unavailable"
    var vicinity: String = "Not Given"
    var rating : String = "No Ratings Available"
    var types : List<String>? = null
    var visited: Boolean = false
}

class DraftItinerary : Fragment() {

    private val args by navArgs<DraftItineraryArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        places.clear()
        arrayList.clear()

        for (json in args.userName){
            places.add(Gson().fromJson(json, Results::class.java))
        }

        var locationObjects = generateAllPossibleLocations(places)

        var spotcheck = ArrayList<String>()

        for (i in locationObjects){
            if (!spotcheck.contains(i.name)){
                var type = i.types?.get(0)
                type?.let {
                    i.types?.let { it1 ->
                        DraftModelClass(i.name, i.vicinity, i.rating,
                            it, false, false, it1,
                            0)
                    }
                }?.let { arrayList.add(it) }
                spotcheck.add(i.name)
            }
        }


        var locationsThatArentRestaurants = ArrayList<DraftModelClass>()

        for (location in arrayList){
            println(location.name)
            for(interest in args.selected){
                if (location.type == interest){
                    locationsThatArentRestaurants.add(location)
                }
            }
        }
        println(locationsThatArentRestaurants.size)

        locationsThatArentRestaurants.shuffle()

        arrayList.shuffle()



        var itinerary = createDraftItinerary(locationsThatArentRestaurants)

        val view = inflater.inflate(R.layout.fragment_draft_itinerary, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        view.recyclerView.adapter = DraftAdapter(this, itinerary, arrayList)

        return view
    }

    // The trip will be 10 hours long
    // Different types of locations will have weights representing the amount of
    // time a visit to there will consume
    // (ex: aquarium visits are longer than beauty store)
    // different types of destinations shouldn't be too early on in the day
    // (ex: casino, bar, nightclub)
    // Because of the weights and the max amount of time we can tell where we are in the day
    fun createDraftItinerary(arrayList: ArrayList<DraftModelClass>) : ArrayList<DraftModelClass>{
        var draftItineraryArray = ArrayList<DraftModelClass>()
        var hours = 0

        var lunch = false
        var dinner = false
        var edgeCase: Boolean
        var ridx = 0;
        var casinoOrNightclubSelected = ArrayList<DraftModelClass>()
        while (hours < 10){
            edgeCase = false

            if (hours >= 3 && !lunch){
                draftItineraryArray.add(findlunchSpot()[0])
                lunch = true
                hours++
            }

            if (hours >= 7 && !dinner){
                draftItineraryArray.add(findDinnerSpot()[0])
                dinner = true
                hours++
            }
            if (hours >= 9 && casinoOrNightclubSelected.size != 0){
                draftItineraryArray.add(arrayList[ridx])
            }

            // 1 hour Destinations
            if (arrayList[ridx].type == "airport" ||
                arrayList[ridx].type == "beauty_salon" ||
                arrayList[ridx].type == "bicycle_store" || arrayList[ridx].type == "book_store" ||
                arrayList[ridx].type == "casino" ||
                arrayList[ridx].type == "clothing_store" ||
                arrayList[ridx].type == "department_store" || arrayList[ridx].type == "home_goods_store" ||
                arrayList[ridx].type == "department_store" || arrayList[ridx].type == "jewelry_store" ||
                arrayList[ridx].type == "shoe_store" ||  arrayList[ridx].type == "store" ||
                arrayList[ridx].type == "electronics_store" ||  arrayList[ridx].type == "florist" ||
                arrayList[ridx].type == "night_club" || arrayList[ridx].type == "pet_store")
            {
                if (!arrayList[ridx].visited && arrayList[ridx].type != "casino" && arrayList[ridx].type != "night_club"){
                    arrayList[ridx].time = 1
                    draftItineraryArray.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    if ((ridx + 1) != arrayList.size){
                        ridx++
                    }
                    edgeCase = true
                    hours++
                }
                if (!arrayList[ridx].visited && (arrayList[ridx].type == "casino" || arrayList[ridx].type == "night_club")){
                    arrayList[ridx].time = 1
                    casinoOrNightclubSelected.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    edgeCase = true
                }

            }

            // 2 hour destinations
            if (arrayList[ridx].type == "bowling_alley" || arrayList[ridx].type == "cemetery" ||
                arrayList[ridx].type == "city_hall" || arrayList[ridx].type == "courthouse" ||
                arrayList[ridx].type == "embassy" || arrayList[ridx].type == "fire_station" ||
                arrayList[ridx].type == "local_government_office" || arrayList[ridx].type == "tourist_attraction" ||
                arrayList[ridx].type == "university" || arrayList[ridx].type == "church" ||
                arrayList[ridx].type == "hindu_temple" || arrayList[ridx].type == "mosque" ||
                arrayList[ridx].type == "synagogue" || arrayList[ridx].type == "gym" ||
                arrayList[ridx].type == "library" || arrayList[ridx].type == "movie_rental" ||
                arrayList[ridx].type == "movie_theater"
            ){
                if (!arrayList[ridx].visited){
                    arrayList[ridx].time = 2
                    draftItineraryArray.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    if ((ridx + 1) != arrayList.size){
                        ridx++
                    }
                    edgeCase = true
                    hours+=2
                }
            }

            // 3 hour destinations
            if (arrayList[ridx].type == "art_gallery" || arrayList[ridx].type == "museum" ||
                arrayList[ridx].type == "park" || arrayList[ridx].type == "shopping_mall" ||
                arrayList[ridx].type == "spa" || arrayList[ridx].type == "stadium"){
                if (!arrayList[ridx].visited){
                    arrayList[ridx].time = 3
                    draftItineraryArray.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    if ((ridx + 1) != arrayList.size){
                        ridx++
                    }
                    edgeCase = true
                    hours+=3
                }
            }

            // 4 hour destinations
            if (arrayList[ridx].type == "amusement_park" || arrayList[ridx].type == "aquarium" ||
                arrayList[ridx].type == "zoo"){
                if (!arrayList[ridx].visited){
                    arrayList[ridx].time = 4
                    draftItineraryArray.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    if ((ridx + 1) != arrayList.size){
                        ridx++
                    }
                    edgeCase = true
                    hours+=4
                }
            }

            //10 hour (fullday)
            if (arrayList[ridx].type == "campground" || arrayList[ridx].type == "rv_park"){
                if (!arrayList[ridx].visited){
                    arrayList[ridx].time = 10
                    draftItineraryArray.add(arrayList[ridx])
                    arrayList[ridx].visited = true
                    if ((ridx + 1) != arrayList.size){
                        ridx++
                    }
                    edgeCase = true
                    hours+=10
                }
            }

            if ((ridx + 1) == arrayList.size || !edgeCase){
                hours+=1
            }
        }

        return draftItineraryArray
    }

    fun findlunchSpot() : ArrayList<DraftModelClass>{
        var restaurant = ArrayList<DraftModelClass>()

        for (destination in arrayList){
            if (destination.type == "meal_delivery" || destination.type == "meal_takeaway" ||
                destination.type == "restaurant" || destination.type == "bar" ||
                destination.type == "cafe" || destination.type == "bakery"){
                if (!destination.visited){
                    destination.time = 1
                    restaurant.add(destination)
                    destination.visited = true
                    return restaurant
                }
            }
        }
        return restaurant
    }

    fun findDinnerSpot() : ArrayList<DraftModelClass>{
        var restaurant = ArrayList<DraftModelClass>()

        for (destination in arrayList){
            if (destination.type == "meal_delivery" ||
                destination.type == "restaurant" || destination.type == "bar"){
                if (!destination.visited){
                    destination.time = 1
                    restaurant.add(destination)
                    destination.visited = true
                    return restaurant
                }
            }
        }
        return restaurant
    }


    fun generateAllPossibleLocations(places: ArrayList<Results>) : ArrayList<Locations> {
        var returnMe = ArrayList<Locations>()

        //Get the results
        var decodePlaces = ArrayList<List<Locations>>()
        for (i in places.indices)
            places[i].results?.let { decodePlaces.add(it) }

        for(i in decodePlaces.indices)
            for (j in decodePlaces[i].indices){
                var location = Locations()
                location.name = decodePlaces[i][j].name
                location.vicinity = decodePlaces[i][j].vicinity
                location.rating = decodePlaces[i][j].rating
                location.types = decodePlaces[i][j].types
                returnMe.add(location)
            }
        return returnMe

    }

}