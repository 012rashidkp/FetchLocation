package com.example.fetchlocation.Activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchlocation.Adapter.PlaceAdapter
import com.example.fetchlocation.ItemClickListener
import com.example.fetchlocation.Model.Placelistarray
import com.example.fetchlocation.Model.Places
import com.example.fetchlocation.databinding.ActivityDetailMapsBinding
import com.example.fetchlocation.databinding.CreateMapLayoutBinding
import com.example.fetchlocation.databinding.CreatePlaceBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail_maps.*

class DetaiMapActivity : AppCompatActivity() {
    private val REQUEST_CODE=203
    companion object{
        val EXTRA_MAP_TITLE="EXTRA_MAP_TITLE"
        val EXTRA_USER_MAP="EXTRA_USER_MAP"
    }
   lateinit var usermaps:MutableList<Places>
   lateinit var madapter:PlaceAdapter

    private var binding:ActivityDetailMapsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailMapsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

      usermaps= generateSampleData().toMutableList()
  madapter=PlaceAdapter(this, usermaps,object : ItemClickListener {
      override fun itemclick(position: Int) {
          System.out.println("position $position")
          val intent=Intent(this@DetaiMapActivity,MapsActivity::class.java)
          intent.putExtra("place",usermaps[position])
          System.out.println("place ${usermaps[position]}")
          startActivity(intent)
      }

  })
        binding!!.placelist.adapter=madapter
        binding!!.placelist.layoutManager=LinearLayoutManager(this)
        madapter.notifyDataSetChanged()

binding!!.createbtn.setOnClickListener {
ShowAlertDialog()
}

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      if (requestCode==REQUEST_CODE && resultCode==Activity.RESULT_OK){

          val usermap=data?.getSerializableExtra(EXTRA_USER_MAP) as Places
          System.out.println("resultdata $usermap")
          usermaps.add(usermap)
          madapter.notifyItemInserted(usermaps.size-1)



      }

        super.onActivityResult(requestCode, resultCode, data)

    }
    private fun ShowAlertDialog() {
        val dialogbinding =CreateMapLayoutBinding.inflate(LayoutInflater.from(this))

        val dialog = AlertDialog.Builder(this)
            .setTitle("Map Title")
            .setMessage("hello")
            .setView(dialogbinding.root)
            .setNegativeButton("cancel", null)
            .setPositiveButton("ok", null).show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            if (dialogbinding.edtmaptitle.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "title  cannot blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent=Intent(this@DetaiMapActivity,CreateMapActivity::class.java)
            intent.putExtra(EXTRA_MAP_TITLE,dialogbinding.edtmaptitle.text.toString())
            startActivityForResult(intent,REQUEST_CODE)

            dialog.dismiss()
        }
    }

        private fun generateSampleData():List<Places> {

        return listOf(
            Places(
                "Memories from University",
                listOf(
                    Placelistarray("Branner Hall", "Best dorm at Stanford", 37.426, -122.163),
                    Placelistarray("Gates CS building", "Many long nights in this basement", 37.430, -122.173),
                    Placelistarray("Pinkberry", "First date with my wife", 37.444, -122.170)
                )
            ),
            Places("January vacation planning!",
                listOf(
                    Placelistarray("Tokyo", "Overnight layover", 35.67, 139.65),
                    Placelistarray("Ranchi", "Family visit + wedding!", 23.34, 85.31),
                    Placelistarray("Singapore", "Inspired by \"Crazy Rich Asians\"", 1.35, 103.82)
                )
            ),
            Places("Singapore travel itinerary",
                listOf(
                    Placelistarray("Gardens by the Bay", "Amazing urban nature park", 1.282, 103.864),
                    Placelistarray("Jurong Bird Park", "Family-friendly park with many varieties of birds", 1.319, 103.706),
                    Placelistarray("Sentosa", "Island resort with panoramic views", 1.249, 103.830),
                    Placelistarray("Botanic Gardens", "One of the world's greatest tropical gardens", 1.3138, 103.8159)
                )
            ),
            Places("My favorite places in the Midwest",
                listOf(
                    Placelistarray("Chicago", "Urban center of the midwest, the \"Windy City\"", 41.878, -87.630),
                    Placelistarray("Rochester, Michigan", "The best of Detroit suburbia", 42.681, -83.134),
                    Placelistarray("Mackinaw City", "The entrance into the Upper Peninsula", 45.777, -84.727),
                    Placelistarray("Michigan State University", "Home to the Spartans", 42.701, -84.482),
                    Placelistarray("University of Michigan", "Home to the Wolverines", 42.278, -83.738)
                )
            ),
            Places("Restaurants to try",
                listOf(
                    Placelistarray("Champ's Diner", "Retro diner in Brooklyn", 40.709, -73.941),
                    Placelistarray("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625),
                    Placelistarray("Shizen", "Elegant sushi in San Francisco", 37.768, -122.422),
                    Placelistarray("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739),
                    Placelistarray("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635)
                )
            )
        )

    }
}