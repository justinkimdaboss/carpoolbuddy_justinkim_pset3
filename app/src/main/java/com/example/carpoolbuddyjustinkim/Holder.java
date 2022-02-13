package com.example.carpoolbuddyjustinkim;

import android.content.ClipData;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    // Holder for my recyclerViewer//

    protected TextView nameText;
    protected TextView capacityText;
    protected TextView makerText;
    protected TextView typeText;
    private MyRecylerViewAdapter.ItemClickListener mClickListener;

    public Holder(@NonNull View itemView, MyRecylerViewAdapter.ItemClickListener mClickListener)
    {
        super(itemView);
        this.mClickListener = mClickListener;
        nameText = itemView.findViewById(R.id.nameTextView);
        capacityText = itemView.findViewById(R.id.capacityTextView);
        makerText = itemView.findViewById(R.id.makerText);
        typeText = itemView.findViewById(R.id.typeText);
    }

    public ConstraintLayout returnLayout()
    {
        return itemView.findViewById(R.id.constraint);
    }

    @Override
    public void onClick(View view)
    {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}
