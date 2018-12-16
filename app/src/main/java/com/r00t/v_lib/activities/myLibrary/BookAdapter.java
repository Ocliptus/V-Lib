package com.r00t.v_lib.activities.myLibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.isbn.isbnAct;
import com.r00t.v_lib.data.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.r00t.v_lib.activities.addBook.isbn.isbnAct.getBitmapFromURL;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> bookList;
    private ItemClickListener itemClickListener;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_detail_layout, parent, false);

        return new BookAdapter.ViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.bookCover.setImageBitmap(getBitmapFromURL(book.getCover_small()));
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthors());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.i1)
        protected ImageView bookCover;
        @BindView(R.id.t1)
        protected TextView title;
        @BindView(R.id.t2)
        protected TextView author;

        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}