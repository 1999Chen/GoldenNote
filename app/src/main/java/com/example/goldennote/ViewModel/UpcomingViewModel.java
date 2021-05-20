package com.example.goldennote.ViewModel;

import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goldennote.Model.Event;
import com.example.goldennote.View.TodoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.goldennote.View.MainActivity.databaseReference;
import static com.example.goldennote.View.UpcomingFragment.recyclerView;

public class UpcomingViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Event>> list;
    private ArrayList<Event> upcominglist;
    private Event event;
    private TodoAdapter todoAdapter;

    public UpcomingViewModel() {
        list = new MutableLiveData<ArrayList<Event>>();
        upcominglist=new ArrayList<>();
        list.setValue(upcominglist);
        getUpcoming();


        System.out.println("upcoming view model "+upcominglist.size());
    }

    public LiveData<ArrayList<Event>> getUpcomingList(){
        System.out.println("get list "+upcominglist.size());
        return list;
    }

    public void  getUpcoming(){

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String currentdate = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DATE,1);
        String tomorrowdate = dateFormat.format(calendar.getTime());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    String date=format(event.getDate());
                    if (date.equals(currentdate))
                    {
                        event.setDate("Today");
                        upcominglist.add(event);
                        System.out.println("get upcoming "+upcominglist.size());
                    }else if (date.equals(tomorrowdate)){
                        event.setDate("Tomorrow");
                        upcominglist.add(event);
                    }
                }
                todoAdapter = new TodoAdapter(upcominglist);
                recyclerView.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("------------------cancell-------------------");
            }
        });


    }



    public String format(String date) {
        String[] array = date.split("/");

        if (array[0].length() == 1) {
            array[0] = "0" + array[0];
            System.out.println(array[0]);
        }
        if (array[1].length() == 1) {
            array[1] = "0" + array[1];
            System.out.println(array[1]);
        }
        date = array[0] + "/" + array[1] + "/" + array[2];
        System.out.println(date);
        return date;
    }


}
