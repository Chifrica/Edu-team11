package ble.ble.elibro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Context mContext;
    private List<File> pdfFiles;
    private OnPdfSelectListener mListener;

    public MainAdapter(Context context, List<File> pdfFiles, OnPdfSelectListener listener) {
        this.mContext = context;
        this.pdfFiles = pdfFiles;
        this.mListener = listener;
    }

    public MainAdapter(Context context, List<File> pdfFiles) {
        mContext = context;
        this.pdfFiles = pdfFiles;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_items, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mTextView.setText(pdfFiles.get(position).getName());
        holder.mTextView.setSelected(true);

        holder.mCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mListener.onPdfSelected(pdfFiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }
}
