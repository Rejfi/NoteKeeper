package com.revolshen.cardviewapp

import android.content.Context
import android.content.Intent
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(context: Context): RecyclerView.Adapter<CardAdapterViewHolder>() {

    //Dostęp do bazy danych
    val dbHelper = SQLDataBaseHelper(context)
    val db = dbHelper.writableDatabase
    //--------------------------------------

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CardAdapterViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val card_view_row = layoutInflater.inflate(R.layout.card_view, viewGroup, false)
        return CardAdapterViewHolder(card_view_row)
    }

    override fun getItemCount(): Int {
        val cursorLiczbaWierszy = db.query(TableInfo.TABLE_NAME,
            null, null, null, null,
            null, null, null)
        val liczbaWierszy = cursorLiczbaWierszy.count
        cursorLiczbaWierszy.close()

        return liczbaWierszy
    }

    override fun onBindViewHolder(holder: CardAdapterViewHolder, position: Int) {
        //Elementy jednej notatki --------------------------
        val cardView = holder.view.note_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        //---------------------------------------------------
        val context = holder.view.context

        //Ładowanie treści z bazy danych
        val cursor = db.query(TableInfo.TABLE_NAME,
            null, BaseColumns._ID + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)
        cursor.moveToFirst()

        if(!(cursor.getString(1).isNullOrEmpty() &&
             cursor.getString(2).isNullOrEmpty())) {
                    title.setText(cursor.getString(1))
                    message.setText(cursor.getString(2))
            }
           // else notifyItemRemoved(holder.adapterPosition)

        val row_id = cursor.getString(0)

        cursor.close()

        //Edycja konkretnej notatki
        cardView.setOnClickListener{
            val EDIT_CODE = "EDIT_CODE"
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("title", title.text.toString())
            intent.putExtra("message", message.text.toString())
            intent.putExtra("EDIT", EDIT_CODE)
            intent.putExtra("ID", row_id)
            context.startActivity(intent)
        }
        //--------------------------------------------------------



    }
}
class CardAdapterViewHolder(val view: View): RecyclerView.ViewHolder(view)