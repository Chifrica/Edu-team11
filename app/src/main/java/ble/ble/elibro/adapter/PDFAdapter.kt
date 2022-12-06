package ble.ble.elibro.adapter

import androidx.recyclerview.widget.RecyclerView
import ble.ble.elibro.model.MainViewHolder
import ble.ble.elibro.service.OnPdfSelectListener
import android.view.ViewGroup
import android.view.LayoutInflater
import ble.ble.elibro.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import java.io.File

class PDFAdapter(private var mContext: Context, private var pdfFiles: List<File>, var mListener: OnPdfSelectListener) : RecyclerView.Adapter<MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.rv_items, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        Log.i("PDFAdapter", "onBindViewHolder: position")

        holder.mTextView.text = pdfFiles[position].name
        holder.mTextView.isSelected = true
        holder.mCardView.setOnClickListener {
            mListener!!.onPdfSelected(
                pdfFiles[position]
            )
        }
    }

    override fun getItemCount(): Int = pdfFiles.size
}