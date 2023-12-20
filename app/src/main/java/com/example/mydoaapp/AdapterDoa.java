package com.example.mydoaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterDoa extends RecyclerView.Adapter<AdapterDoa.ViewHolder> {

    private Interface mListener;
    private List<ModelDoa> doaList;

    public AdapterDoa() {
        doaList = new ArrayList<>();
    }

    public void setOnItemClickListener(Interface listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AdapterDoa.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doaitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoa.ViewHolder holder, int position) {
        ModelDoa doa = doaList.get(position);
        holder.idTextView.setText(doa.getId());
        holder.doaTextView.setText(doa.getDoa());
    }

    @Override
    public int getItemCount() {
      return   doaList.size();
    }

    public void setDoaList(List<ModelDoa> doaList) {
        this.doaList = doaList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTextView;
        TextView doaTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.iddoa);
            doaTextView = itemView.findViewById(R.id.juduldoa);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (mListener != null) {
                    mListener.onItemClick(doaList.get(position));
                }
            }
        }
    }
}
