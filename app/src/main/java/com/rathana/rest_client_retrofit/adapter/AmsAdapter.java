package com.rathana.rest_client_retrofit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.rathana.rest_client_retrofit.R;
import com.rathana.rest_client_retrofit.model.Article;

import java.util.List;

public class AmsAdapter extends RecyclerView.Adapter<AmsAdapter.ViewHolder> {

    List<Article> articles;
    Context mContext;

    public AmsAdapter(List<Article> articles, Context mContext) {
        this.articles = articles;
        this.mContext = mContext;
        this.callback= (AmsCallback) mContext;
    }

    public void addMoreItems(List<Article> articles) {
        int previousPos=this.articles.size();
        this.articles.addAll(articles);
        notifyItemRangeInserted(previousPos-1,this.articles.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(mContext).
                inflate(R.layout.article_item_layout,
                        viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Article article = articles.get(i);
        viewHolder.title.setText(article.getTitle());
        viewHolder.date.setText(article.getCreateDate());
        //viewHolder.date.setText(DateFormatHelper.formatDate(article.getCreatedDate()));
        viewHolder.author.setText("admin");

        //image
        if(article.getImage()==null)
            viewHolder.thumb.setImageResource(R.drawable.image);
        else
            Glide.with(mContext)
            .load(article.getImage())
            .override(450,350)
            .into(viewHolder.thumb);


        viewHolder.btnMenu.setOnClickListener(v->{
            PopupMenu menu=new PopupMenu(mContext,v);
            menu.inflate(R.menu.popup_menu);
            menu.setOnMenuItemClickListener(item->{
                switch (item.getItemId()){
                    case R.id.btnDelete:
                        callback.onDelete(article,viewHolder.getAdapterPosition());
                        return true;
                    case R.id.btnEdit:
                        callback.onEdit(article, viewHolder.getAdapterPosition());
                        return true;
                    default: return false;
                }
            });
            menu.show();
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumb,btnMenu;
        private TextView title,date,author;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumb=itemView.findViewById(R.id.thumb);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            author=itemView.findViewById(R.id.author);
            btnMenu=itemView.findViewById(R.id.btnMenu);

        }
    }


    AmsCallback callback;
    public interface AmsCallback{
        void onDelete(Article article,int pos);
        void onEdit(Article article,int pos);
    }
}
