package ble.ble.elibro.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ble.ble.elibro.R;

public class MainViewHolder extends RecyclerView.ViewHolder{

    public TextView mTextView;
    public CardView mCardView;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.txtView);
        mCardView = itemView.findViewById(R.id.pdf_cardView);
    }
}
