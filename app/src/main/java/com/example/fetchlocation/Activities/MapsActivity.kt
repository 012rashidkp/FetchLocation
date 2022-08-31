package com.example.fetchlocation.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.fetchlocation.Model.Places

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.example.fetchlocation.R
import com.example.fetchlocation.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var places: Places

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        places=intent.getSerializableExtra("place") as Places
        binding!!.txtplace.text=places.placeName
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
     System.out.println("map ready to render: ${places.placeName}")
        // Add a marker in Sydney and move the camera
        val latingboundbuilder=LatLngBounds.builder()
        for (place in places.placelist){
            val lating=LatLng(place.latitude,place.longitude)
            latingboundbuilder.include(lating)
            System.out.println("lating : $lating")
            mMap.addMarker(MarkerOptions().position(lating).title(place.title).snippet(place.description))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latingboundbuilder.build(),1000,1000,0))
      //  mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latingboundbuilder.build(),1000,1000,0))

        //val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}