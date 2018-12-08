package com.revolshen.cardviewapp

import android.app.ActionBar
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
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
        val cursor = db.query(TableInfo.TABLE_NAME,
            null, null, null,
            null, null,null)
        val rowCount = cursor.count
        cursor.close()

        return rowCount
    }

    override fun onBindViewHolder(holder: CardAdapterViewHolder, position: Int) {
        //Elementy jednej notatki --------------------------
        val cardView = holder.view.note_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        val context = holder.view.context

        val notes = ArrayList<Note>()
        val cursor = db.query(TableInfo.TABLE_NAME,
            null, null, null,
            null, null,TableInfo.COLUMN_NAME_TITLE + " DESC")

        if(cursor.count > 0){
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                notes.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        title.setText(notes[holder.adapterPosition].title)
        message.setText(notes[holder.adapterPosition].message)

        //Edycja konkretnej notatki
        cardView.setOnClickListener{
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("title", notes[holder.adapterPosition].title)
            intent.putExtra("message", notes[holder.adapterPosition].message)
            intent.putExtra("ID", notes[holder.adapterPosition].id.toString())
            context.startActivity(intent)
        }
        //--------------------------------------------------------

        cardView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {

                db.delete(TableInfo.TABLE_NAME,
                    BaseColumns._ID +"=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))
                notifyItemRemoved(holder.adapterPosition)
               return true
            }

        })

    }

}
class CardAdapterViewHolder(val view: View): RecyclerView.ViewHolder(view)