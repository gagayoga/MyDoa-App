package com.example.mydoaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {

    private SurahInterface mListener;
    private List<Surah> doaList;

    public SurahAdapter() {
        doaList = new ArrayList<>();
    }

    public void setOnItemClickListener(SurahInterface listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public SurahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahAdapter.ViewHolder holder, int position) {
        Surah doa = doaList.get(position);
        holder.arti.setText(doa.getArti());
        holder.urut.setText(doa.getUrut());
        holder.nama.setText(doa.getNama());
    }

    @Override
    public int getItemCount() {
        return doaList.size();
    }

    public void setDoaList(List<Surah> doaList) {
        this.doaList = doaList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView arti,nama,urut;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            arti = itemView.findViewById(R.id.arti);
            urut = itemView.findViewById(R.id.urut);
            nama = itemView.findViewById(R.id.nama);

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
