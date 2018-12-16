package com.r00t.v_lib.activities.myLibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
   private List<Book> bookList;
   private ItemClickListener itemClickListener;

    public BookAdapter(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_detail_layout,parent,false);

        return new BookAdapter.ViewHolder(itemView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        holder.tvSector.setText(bookList);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public interface ItemClickListener{
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemClickListener{
        //☺☺☺☺☺☺☺☺
        protected TextView tvSector;
        protected ImageView ivCover_mylib;
        private ItemClickListener itemClickListener;
        public ViewHolder(View itemView, ItemClickListener itemClickListener)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view, int position) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }

}