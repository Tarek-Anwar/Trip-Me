package com.example.tripmei;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.MoreObjects;

import java.util.List;

public class ViewTrips extends RecyclerView.Adapter<ViewTrips.viewholder>{

    List<Trip> list;
    Context context;
    public ViewTrips(Context context, List<Trip>list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_one_trip,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_view_state.setText(list.get(position).getTripState());
        holder.txt_view_TripName.setText(list.get(position).getName());
        holder.txt_view_clock.setText(list.get(position).getTime());
        holder.txt_view_day.setText(list.get(position).getDate());
        holder.txt_address_start.setText(list.get(position).getFrom());
        holder.txt_address_end.setText(list.get(position).getTo());
        AppDatabase db = AppDatabase.getDbInstance(context);

        holder.tools_spin.setVisibility(View.INVISIBLE);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tools_spin.getVisibility()==View.VISIBLE){
                    holder.tools_spin.setVisibility(View.INVISIBLE);
                }else {
                    holder.tools_spin.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(context, AddTripActivity.class);
                intent2.putExtra("edit", 1);
                intent2.putExtra("ID", list.get(position).getId());
                startActivity(context, intent2, Bundle.EMPTY);
            }
        });

        holder.card_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("ID", list.get(position).getId());
                startActivity(context, intent, Bundle.EMPTY);
            }
        });

        holder.card_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txt_view_state.setText("CANCELLED");
                db.tripDao().updateState("CANCELLED", list.get(position).getId());
            }
        });

        holder.card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.tripDao().delete(list.get(position));
            }
        });


        holder.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = AppDatabase.getDbInstance(context);
                desplayTrip(list.get(position).getLatitudeStart(),list.get(position).getLongitudeStart()
                        ,list.get(position).getLatitudeEnd(), list.get(position).getLongitudeEnd());
                db.tripDao().updateState("DONE", list.get(position).getId());
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


    }

    private  void desplayTrip(double latStar, double logStar , double latEnd , double logEnd){
        try {
            Uri url = Uri.parse("http://maps.google.com/maps?saddr="+latStar+","+logStar+"&daddr="+latEnd+","+logEnd);
            Intent intent = new Intent(Intent.ACTION_VIEW,url);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context,intent,Bundle.EMPTY);

        }catch (ActivityNotFoundException e){
            Uri url = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context,intent,Bundle.EMPTY);
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends  RecyclerView.ViewHolder{

        TextView txt_view_day , txt_view_clock,txt_view_TripName , txt_view_state , txt_address_start , txt_address_end;
        ImageView  notes_page , more;
        Button btn_start , btn_map_show;
        LinearLayout tools_spin;
        CardView card_delete , card_cancel , card_edit , card_note;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt_view_day = itemView.findViewById(R.id.txt_view_day);
            txt_address_end  =itemView.findViewById(R.id.txt_address_end);
            txt_address_start  =itemView.findViewById(R.id.txt_address_start);
            txt_view_clock  =itemView.findViewById(R.id.txt_view_clock);
            txt_view_TripName  =itemView.findViewById(R.id.txt_view_TripName);
            txt_view_state  =itemView.findViewById(R.id.txt_view_state);
            notes_page  =itemView.findViewById(R.id.notes_page);
            btn_start  =itemView.findViewById(R.id.btn_start);
            more = itemView.findViewById(R.id.btn_tools);
            tools_spin =itemView.findViewById(R.id.tools_spin);
            card_note = itemView.findViewById(R.id.card_note);
            card_edit = itemView.findViewById(R.id.card_edit);
            card_cancel = itemView.findViewById(R.id.card_cancel);
            card_delete = itemView.findViewById(R.id.card_delete);
            btn_map_show = itemView.findViewById(R.id.btn_map_show);

        }
    }
}
