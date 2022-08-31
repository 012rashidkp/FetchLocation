package com.example.fetchlocation.Activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.fetchlocation.Activities.DetaiMapActivity.Companion.EXTRA_MAP_TITLE
import com.example.fetchlocation.Activities.DetaiMapActivity.Companion.EXTRA_USER_MAP
import com.example.fetchlocation.Model.Placelistarray
import com.example.fetchlocation.Model.Places

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.fetchlocation.R
import com.example.fetchlocation.databinding.ActivityCreateMapBinding
import com.example.fetchlocation.databinding.CreatePlaceBinding
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

class CreateMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateMapBinding
    private var markers:MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding!!.titlename.text=intent.getStringExtra(EXTRA_MAP_TITLE)

        Snackbar.make(binding!!.root,"Long press to add marker",Snackbar.LENGTH_INDEFINITE)
            .setAction("ok",{})
            .setActionTextColor(ContextCompat.getColor(this,android.R.color.white)).show()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding!!.savebtn.setOnClickListener {
            if (markers.isEmpty()){
                Toast.makeText(this,"there must be atleast one marker on the map",Toast.LENGTH_LONG).show()
            return@setOnClickListener
            }

   val places=markers.map { marker ->
        Placelistarray(marker.title!!,marker.snippet!!,marker.position.latitude,marker.position.longitude)

   }
   val usermap=Places(intent.getStringExtra(EXTRA_MAP_TITLE)!!,places)
            System.out.println("usermap $usermap")
            val data=Intent()
            data.putExtra(EXTRA_USER_MAP,usermap)
            setResult(Activity.RESULT_OK,data)
            finish()

        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener {markertodelete->
            markers.remove(markertodelete)
            markertodelete.remove()

        }

        mMap.setOnMapLongClickListener {lating->
            ShowAlertDialog(lating)


        }



        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun ShowAlertDialog(lating: LatLng) {
        val dialogbinding=CreatePlaceBinding.inflate(LayoutInflater.from(this))

     val dialog=  AlertDialog.Builder(this)
         .setTitle("Create a marker")
         .setMessage("hello")
         .setView(dialogbinding.root)
         .setNegativeButton("cancel",null)
         .setPositiveButton("ok",null).show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            if (dialogbinding.edtTitle.text.toString().trim().isEmpty()||dialogbinding.edtDesc.text.toString().trim().isEmpty()){
                Toast.makeText(this,"title and description cannot blank",Toast.LENGTH_LONG).show()
            return@setOnClickListener
            }

            val marker=  mMap.addMarker(MarkerOptions().position(lating).title(dialogbinding.edtTitle.text.toString()).snippet(dialogbinding.edtDesc.text.toString()))
            markers.add(marker!!)
            dialog.dismiss()
        }




    }
}