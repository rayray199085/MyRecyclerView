package com.project.stephencao.myrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyStaggeredAdapter extends RecyclerView.Adapter<MyStaggeredViewHolder> {

    private Context context;
    private List<String> mData;
    private LayoutInflater inflater;
    private List<Integer> mRandomHeight;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public MyStaggeredAdapter(Context context, List<String> mData) {
        this.context = context;
        this.mData = mData;
        inflater = LayoutInflater.from(this.context);
        mRandomHeight = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            mRandomHeight.add((int) (100 + Math.random() * 300));
        }
    }

    @NonNull
    @Override
    public MyStaggeredViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.view_recycler_item, viewGroup, false);
        MyStaggeredViewHolder myStaggeredViewHolder = new MyStaggeredViewHolder(view);
        return myStaggeredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyStaggeredViewHolder myStaggeredViewHolder, int i) {
        ViewGroup.LayoutParams layoutParams = myStaggeredViewHolder.itemView.getLayoutParams();
        layoutParams.height = mRandomHeight.get(i);
        myStaggeredViewHolder.itemView.setLayoutParams(layoutParams);
        myStaggeredViewHolder.textView.setText(mData.get(i));

        if (mOnItemClickListener != null) {
            myStaggeredViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = myStaggeredViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(myStaggeredViewHolder.itemView,layoutPosition);
                }
            });
            myStaggeredViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = myStaggeredViewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(myStaggeredViewHolder.itemView,layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

class MyStaggeredViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public MyStaggeredViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.tv_recycler_content);
    }
}
