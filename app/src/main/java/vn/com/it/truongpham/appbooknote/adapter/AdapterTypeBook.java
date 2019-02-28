package vn.com.it.truongpham.appbooknote.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mobile.sarproj.com.layout.SwipeLayout;
import vn.com.it.truongpham.appbooknote.R;
import vn.com.it.truongpham.appbooknote.data.TypeBook;
import vn.com.it.truongpham.appbooknote.view.IOnClick;


public class AdapterTypeBook extends RecyclerView.Adapter<AdapterTypeBook.ViewHolder> {
    Context context;
    List<TypeBook> list;
    IOnClick.IOnClickAdapter clickAdapter;

    public AdapterTypeBook(Context context, List<TypeBook> list,IOnClick.IOnClickAdapter clickAdapter) {
        this.context = context;
        this.list = list;
        this.clickAdapter=clickAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type_book, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvName.setText(list.get(i).name);
        viewHolder.drag_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAdapter.OnClickItem(list.get(i),viewHolder.swipe_layout);
            }
        });

        viewHolder.right_view_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAdapter.OnClickItemEdit(list.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView imgEdit;
        ImageView right_view_edit;
        SwipeLayout swipe_layout;
        CardView drag_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBook);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            right_view_edit=itemView.findViewById(R.id.right_view_edit);
            swipe_layout=itemView.findViewById(R.id.swipe_layout);
            drag_item=itemView.findViewById(R.id.drag_item);
        }
    }

}
