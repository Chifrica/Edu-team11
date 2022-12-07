package ble.ble.elibro

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.annotation.SuppressLint
import android.content.Context
import java.io.File

class Adapter : RecyclerView.Adapter<MainViewHolder> {
    private var mContext: Context
    private var pdfFiles: List<File>
    private var mListener: OnPdfSelectListener? = null

    constructor(context: Context, pdfFiles: List<File>, listener: OnPdfSelectListener?) {
        mContext = context
        this.pdfFiles = pdfFiles
        mListener = listener
    }

    constructor(
        context: Context,
        pdfFiles: List<File>
    ) {
        mContext = context
        this.pdfFiles = pdfFiles
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.rv_items, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.mTextView.text = pdfFiles[position].name
        holder.mTextView.isSelected = true
        holder.mCardView.setOnClickListener {
            mListener!!.onPdfSelected(
                pdfFiles[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return pdfFiles.size
    }
}