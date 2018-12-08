package com.revolshen.cardviewapp

import android.app.Activity
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.ImageFormat
import android.media.Image
import android.media.ImageReader
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.BaseColumns
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_view.*
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()

        //Wyświetlanie elementów na ekranie głównym - Adapter oraz LayoutManager
        recyler_view.layoutManager = GridLayoutManager(applicationContext,2)
        recyler_view.adapter = CardViewAdapter(applicationContext)
        //----------------------------------------------------------------------

    }

    //Odpalanie aktywności edycji notatek
    fun onClickTakeNote(v: View){
        val intent = Intent(v.context, DetailsActivity::class.java)
        v.context.startActivity(intent)
    }
    //------------------------------------------------------------
}


