package com.example.tripmei.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tripmei.AppDatabase;
import com.example.tripmei.R;
import com.example.tripmei.Trip;
import com.example.tripmei.ViewHistory;


import java.util.List;

public class HistoryFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history,container,false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_history);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        TextView textView = view.findViewById(R.id.no_history);


        int con = refreshTrip();
        if(con==0){
            textView.setVisibility(View.VISIBLE);
            textView.setText("No any Trip History");
        }
        else {
            textView.setVisibility(View.INVISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                int con2 = refreshTrip();
                if(con2==0){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("No any Trip History");
                }
                else {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }
    private  int  refreshTrip(){
        AppDatabase db = AppDatabase.getDbInstance(this.getContext());
        List<Trip> listHistory =  db.tripDao().getAllHistory();
        ViewHistory viewHistory = new ViewHistory(this.getContext(),listHistory);
        recyclerView.setAdapter(viewHistory);
        if(listHistory.isEmpty()){
            return 0;
        }else {
            return 1;
        }
    }


}