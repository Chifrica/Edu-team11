package ble.ble.elibro

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.cardview.widget.CardView
import ble.ble.elibro.R

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mTextView: TextView
    var mCardView: CardView

    init {
        mTextView = itemView.findViewById(R.id.txtView)
        mCardView = itemView.findViewById(R.id.pdf_cardView)
    }
}