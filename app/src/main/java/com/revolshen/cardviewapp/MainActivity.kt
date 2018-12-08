package com.revolshen.cardviewapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        take_notes_BT.setOnClickListener{
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            startActivityForResult(intent,0)
        }
    }

    override fun onResume() {
        super.onResume()

        //Wyświetlanie elementów na ekranie głównym - Adapter oraz LayoutManager
        recyler_view.layoutManager = GridLayoutManager(applicationContext,2)
        recyler_view.adapter = CardViewAdapter(applicationContext)
        //----------------------------------------------------------------------
    }

}


