package com.revolshen.cardviewapp.mainview

import android.content.*
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolshen.cardviewapp.*

import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(context: Context, var notes: ArrayList<Note>, val db: SQLiteDatabase): RecyclerView.Adapter<CardAdapterViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CardAdapterViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val card_view_row = layoutInflater.inflate(R.layout.card_view, viewGroup, false)
        return CardAdapterViewHolder(card_view_row)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: CardAdapterViewHolder, position: Int) {
        //Elementy jednej notatki----
        val cardView = holder.view.note_cardView
        val title = holder.view.title_cardView
        val message = holder.view.message_cardView
        val context = holder.view.context
        val date = holder.view.date_cardView
        //-----------------------------


        //Pokazywanie danych notatki-------
        title.setText(notes[holder.adapterPosition].title)
        message.setText(notes[holder.adapterPosition].message)
        date.setText(notes[holder.adapterPosition].date)
        //------------------------


        //Edycja konkretnej notatki---------
        cardView.setOnClickListener{
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("title", notes[holder.adapterPosition].title)
            intent.putExtra("message", notes[holder.adapterPosition].message)
            intent.putExtra("ID", notes[holder.adapterPosition].id.toString())
            context.startActivity(intent)
        }
        //-------------------------------------------------------

       //Usuwanie notatek-------------------
        cardView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                db.delete(
                    TableInfo.TABLE_NAME,
                    BaseColumns._ID +"=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)

                return true
            }
        })
     //------------------------------------------------------

    }

}
class CardAdapterViewHolder(val view: View): RecyclerView.ViewHolder(view)