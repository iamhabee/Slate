package com.habee.slate.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.habee.slate.R;
import com.habee.slate.model.SlateModels;
import com.habee.slate.util.Utility;

import java.util.ArrayList;

// Recycler Adapter class
public class SlateRecyclerAdapter extends RecyclerView.Adapter<SlateRecyclerAdapter.ViewHolder> {

    private static final String TAG = "SlateRecyclerAdapter";
    private ArrayList<SlateModels> mSlate = new ArrayList<>();
    private onSlateListener mOnSlateListener;

//    constructor
    public SlateRecyclerAdapter(ArrayList<SlateModels> slate, onSlateListener slateListener) {
        this.mSlate = slate;
        this.mOnSlateListener = slateListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.slate_list_item, viewGroup, false);
        return new ViewHolder(view, mOnSlateListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            String month = mSlate.get(position).getTimestamp().substring(0, 2);
            month = Utility.getMonthFromNumber(month);
            String year = mSlate.get(position).getTimestamp().substring(3);
            String timestamp = month + year;
            holder.timestamp.setText(timestamp);
            holder.title.setText(mSlate.get(position).getTitle());

        }catch (NullPointerException e){
            Log.d(TAG, "onBindViewHolder: Null Pointer Exception Handler" + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mSlate.size();
    }


//    viewholder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, timestamp;
        onSlateListener mslateListener;

        public ViewHolder(@NonNull View itemView, onSlateListener slateListener) {
            super(itemView);
            title = itemView.findViewById(R.id.slate_title);
            timestamp = itemView.findViewById(R.id.slate_timestamp);
            this.mslateListener = slateListener;
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        mslateListener.onSlateClick(getAdapterPosition());
    }
}

    public interface onSlateListener{
        void onSlateClick(int position);
    }

}
