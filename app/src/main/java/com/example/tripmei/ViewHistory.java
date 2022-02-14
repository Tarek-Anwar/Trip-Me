package com.example.tripmei;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHistory extends  RecyclerView.Adapter<ViewHistory.viewholder>{

    List<Trip> list;
    Context context;
    Dialog dialog;
    AppDatabase db = AppDatabase.getDbInstance(context);

    public ViewHistory(Context context, List<Trip>list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dialog =new Dialog(context);
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_view,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_view_state.setText(list.get(position).getTripState());
        holder.txt_view_TripName.setText(list.get(position).getName());
        holder.txt_view_clock.setText(list.get(position).getTime());
        holder.txt_view_day.setText(list.get(position).getDate());
        holder.txt_address_start.setText(list.get(position).getFrom());
        holder.txt_address_end.setText(list.get(position).getTo());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.tripDao().delete(list.get(position));
            }
        });

        holder.notes_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewNotes.class);
                intent.putExtra("notesId",list.get(position).getId());
                startActivity(context,intent,Bundle.EMPTY);
            }
        });
        holder.btn_path_old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Path_old.class);
                intent.putExtra("idmap",list.get(position).getId());
                startActivity(context,intent,Bundle.EMPTY);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewholder extends  RecyclerView.ViewHolder{
        TextView txt_view_day , txt_view_clock,txt_view_TripName , txt_view_state , txt_address_start , txt_address_end;
        ImageView notes_page;
        Button btn_delete , btn_path_old ;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt_view_day = itemView.findViewById(R.id.txt_view_day_h);
            txt_address_end  =itemView.findViewById(R.id.txt_address_end_h);
            txt_address_start  =itemView.findViewById(R.id.txt_address_start_h);
            txt_view_clock  =itemView.findViewById(R.id.txt_view_clock_h);
            txt_view_TripName  =itemView.findViewById(R.id.txt_view_TripName_h);
            txt_view_state  =itemView.findViewById(R.id.txt_view_state_h);
            notes_page  =itemView.findViewById(R.id.notes_page_h);
            btn_delete = itemView.findViewById(R.id.btn_deleted);
            btn_path_old = itemView.findViewById(R.id.btn_map_show);
        }
    }
}
