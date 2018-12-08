package com.revolshen.cardviewapp

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val dbHelper = SQLDataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val info_save_message = Toast.makeText(
            applicationContext,
            "Wiadomość zapisana, możesz wyjść", Toast.LENGTH_SHORT
        )

        if(intent.hasExtra("title")) title_details.setText(intent.getStringExtra("title"))
        if(intent.hasExtra("message")) message_details.setText(intent.getStringExtra("message"))


        //Zapisanie treści z pól do bazy danych
        save_BT_details.setOnClickListener {
            if (intent.hasExtra("ID")) {
                val title = title_details.text.toString()
                val message = message_details.text.toString()

                val values = ContentValues()
                values.put("title", title)
                values.put("message", message)

                db.update(TableInfo.TABLE_NAME,values,
                    BaseColumns._ID + "=?", arrayOf(intent.getStringExtra("ID")))

                info_save_message.show()

            } else {

                val title = title_details.text.toString()
                val message = message_details.text.toString()

                val values = ContentValues()
                values.put("title", title)
                values.put("message", message)

                db.insertOrThrow(TableInfo.TABLE_NAME, null, values)
                info_save_message.show()
            }


        }
    //--------------------------------------------------------------------

    }

}


