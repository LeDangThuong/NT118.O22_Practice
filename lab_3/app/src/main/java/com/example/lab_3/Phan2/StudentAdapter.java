package com.example.lab_3.Phan2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_3.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> studentList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Constructor
    StudentAdapter(Context context, List<Student> data) {
        this.mInflater = LayoutInflater.from(context);
        this.studentList = data;
    }

    // Inflates the row layout from XML when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // Binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.myTextView.setText(student.getName());
        // Set other attributes...
    }

    // Total number of rows
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvStudentName);
            // Find other views...
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    // Convenience method for getting data at click position
    Student getItem(int id) {
        return studentList.get(id);
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemLongClick(View view, int position);
    }

    // Allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}

