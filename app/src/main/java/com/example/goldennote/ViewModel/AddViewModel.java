package com.example.goldennote.ViewModel;

import android.view.View;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.ViewModel;

import com.example.goldennote.Model.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.goldennote.View.MainActivity.databaseReference;

public class AddViewModel extends ViewModel {

    private Event event;

    public AddViewModel() {
    }

    public void addEventToDatabase(String date,String title, String description){
        String id = databaseReference.push().getKey();
        event = new Event(id,date,title,description);
        databaseReference.child(id).setValue(event);

    }






}
