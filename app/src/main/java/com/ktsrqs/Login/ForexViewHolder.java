package com.ktsrqs.Login;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForexViewHolder extends RecyclerView.ViewHolder {
    TextView kodeTextView, kursTextView, description;

    public ForexViewHolder(@NonNull View itemView) {
        super(itemView);
        kodeTextView = itemView.findViewById(R.id.kodeTextView);
        kursTextView = itemView.findViewById(R.id.kursTextView);
        description = itemView.findViewById(R.id.descriptionTextView);
    }
}