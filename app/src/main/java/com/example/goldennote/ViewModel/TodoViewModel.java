package com.example.goldennote.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.goldennote.Model.Event;
import com.example.goldennote.View.TodoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.example.goldennote.View.MainActivity.database;
import static com.example.goldennote.View.MainActivity.databaseReference;
import static com.example.goldennote.View.TodoFragment.recyclerView;
import static com.example.goldennote.View.TodoFragment.todoAdapter;
public class TodoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Event>> uplist ;
    private ArrayList<Event> todolist;
    private Event event;


    public TodoViewModel() {
        uplist = new MutableLiveData<ArrayList<Event>>();
        todolist = new ArrayList<Event>();
        uplist.setValue(todolist);
        getAll();
    }

    public LiveData<ArrayList<Event>> getEventList(){
        System.out.println("get todolist "+todolist.size());
        return uplist;
    }


    public void getAll() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String currentdate = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DATE, 1);
        String tomorrowdate = dateFormat.format(calendar.getTime());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    String date = event.getDate();
                    date= format(date);
                    if (date.equals(currentdate)) {
                        event.setDate("Today");
                    } else if (date.equals(tomorrowdate)) {
                        event.setDate("Tomorrow");
                    }

                    todolist.add(event);
                    System.out.println("get all " + todolist.size());
                }

                todoAdapter = new TodoAdapter(todolist);
                todoAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(todoAdapter);

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



















