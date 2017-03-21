package com.nex3z.togglebuttongroup.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.Set;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
    private static final String LOG_TAG = SimpleAdapter.class.getSimpleName();

    private int mCount = 0;
    private SparseArray<Set<Integer>> mSelected;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_button_group, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvCount.setText(String.valueOf(position));
        Set<Integer> selectedPositions = mSelected.get(position);
        holder.mTgButtons.setCheckedPositions(selectedPositions);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
        mSelected = new SparseArray<>();
        Log.v(LOG_TAG, "count(): count = " + count + ". mSelected.size() = " + mSelected.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvCount;
        MultiSelectToggleGroup mTgButtons;
        public ViewHolder(final View itemView) {
            super(itemView);
            mTvCount = (TextView) itemView.findViewById(R.id.tv_count);
            mTgButtons = (MultiSelectToggleGroup) itemView.findViewById(R.id.tg_buttons);
            mTgButtons.setOnCheckedPositionChangeListener(new ToggleButtonGroup.OnCheckedPositionChangeListener() {
                @Override
                public void onCheckedPositionChange(Set<Integer> checkedPositions) {
                    int position = getAdapterPosition();
                    mSelected.put(position, checkedPositions);
                }
            });
        }
    }
}
