package com.r00t.v_lib.activities.myLibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> bookList;
    private View.OnClickListener onClickListener;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_detail_layout, parent, false);
        return new BookAdapter.ViewHolder(itemView); //, itemClickListener
    }
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthors());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.i1)
        protected ImageView bookCover;
        @BindView(R.id.t1)
        protected TextView title;
        @BindView(R.id.t2)
        protected TextView author;

        public ViewHolder(View itemView)  {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}