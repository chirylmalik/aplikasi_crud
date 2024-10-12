package com.example.penggajian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penggajian.R;
import com.example.penggajian.model.ModelDatabase;

import java.util.List;

public class KasirAdapter extends RecyclerView.Adapter<KasirAdapter.KasirViewHolder> {

    public List<ModelDatabase> kasirList;
    private OnItemClickListener onItemClickListener;

    public KasirAdapter(List<ModelDatabase> kasirList, OnItemClickListener onItemClickListener) {
        this.kasirList = kasirList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public KasirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item untuk RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_data, parent, false);
        return new KasirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KasirViewHolder holder, int position) {
        ModelDatabase modelDatabase = kasirList.get(position);
        // Mengikat data ke tampilan item
        holder.tvNama.setText(modelDatabase.getNama());
        holder.tvNik.setText(modelDatabase.getNIK());
        holder.tvEmail.setText(modelDatabase.getEmail());

        // Set aksi klik pada item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(modelDatabase);
            }
        });

        // Set aksi klik pada tombol hapus
        holder.ivDelete.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(modelDatabase);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kasirList.size(); // Mengembalikan jumlah item dalam daftar
    }

    public void updateData(List<ModelDatabase> newKasirList) {
        // Memperbarui data di adapter
        this.kasirList.clear();
        this.kasirList.addAll(newKasirList);
        notifyDataSetChanged(); // Memberitahu adapter bahwa data telah diperbarui
    }

    public interface OnItemClickListener {
        void onItemClick(ModelDatabase modelDatabase);
        void onDeleteClick(ModelDatabase modelDatabase);
    }

    public static class KasirViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNik, tvEmail;
        ImageView ivDelete;

        public KasirViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi komponen tampilan
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNik = itemView.findViewById(R.id.tvNik);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
