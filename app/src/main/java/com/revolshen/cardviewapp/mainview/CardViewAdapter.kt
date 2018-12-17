package com.revolshen.cardviewapp.mainview

import android.content.*
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revolshen.cardviewapp.*

import kotlinx.android.synthetic.main.card_view.view.*

class CardViewAdapter(context: Context, var notes: ArrayList<Note>): RecyclerView.Adapter<CardAdapterViewHolder>() {

    //Dostęp do bazy danych
    private val dbHelper = SQLDataBaseHelper(context)
    private val db = dbHelper.writableDatabase
    //--------------------------------------

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CardAdapterViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val card_view_row = layoutInflater.inflate(R.layout.card_view, viewGroup, false)
        return CardAdapterViewHolder(card_view_row)
    }

    override fun getItemCount(): Int {
        val cursor = db.query(
            TableInfo.TABLE_NAME,
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
        val date = holder.view.date_cardView
        /*
        val notes = ArrayList<Note>()
        val cursor = db.query(
            TableInfo.TABLE_NAME,
            null, null, null,
            null, null, TableInfo.COLUMN_NAME_TITLE + " DESC")

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
        */

        title.setText(notes[holder.adapterPosition].title)
        message.setText(notes[holder.adapterPosition].message)
        date.setText(notes[holder.adapterPosition].date)

        //Edycja konkretnej notatki
        cardView.setOnClickListener{
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("title", notes[holder.adapterPosition].title)
            intent.putExtra("message", notes[holder.adapterPosition].message)
            intent.putExtra("ID", notes[holder.adapterPosition].id.toString())
            context.startActivity(intent)
        }
        //--------------------------------------------------------

        cardView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                db.delete(
                    TableInfo.TABLE_NAME,
                    BaseColumns._ID +"=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)


            /*
                val values = ContentValues()
                val archSQLHelper = SQLArchivesBase(holder.view.context)
                val archDB = archSQLHelper.writableDatabase

                //END of work here
                if(!notes[holder.adapterPosition].title.isNullOrEmpty()){
                   values.put(ArchivesTable.COLUMN_NAME_TITLE,notes[holder.adapterPosition].title)
                }else values.put("title","")

                if (!notes[holder.adapterPosition].message.isNullOrEmpty()){
                    values.put("message",notes[holder.adapterPosition].message)
                }else values.put(ArchivesTable.COLUMN_NAME_MESSAGE,"")
                //------------------

                archDB.insertOrThrow(ArchivesTable.TABLE_NAME,null,values)

                archDB.close()
               */

                return true
            }

        })

    }

}
class CardAdapterViewHolder(val view: View): RecyclerView.ViewHolder(view)