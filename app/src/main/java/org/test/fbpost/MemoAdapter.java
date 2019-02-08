package org.test.fbpost;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    Context context;
    ArrayList<Memo> items = new ArrayList<Memo>();

    OnItemClickListener listener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public MemoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycler_view_row, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Memo item = items.get(position);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Memo item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public Memo getItem(int position) {
        return items.get(position);
    }

    public void setItem(List<Memo> memoList) {
        clear();

        if (memoList == null && memoList.size() <= 0)
            return;

        items.addAll(memoList);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView createdDate;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            createdDate = (TextView) itemView.findViewById(R.id.create_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }

        public void setItem(Memo item) {
            title.setText(item.getTitle());
            content.setText(item.getContent());
            setDate(createdDate, item.getCreateDate());
        }

        public void setDate(TextView view, Date date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            String str = formatter.format(date);
            view.setText(str);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

    }

}