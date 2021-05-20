package com.example.goldennote.View;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldennote.Model.Event;
import com.example.goldennote.R;
import com.example.goldennote.ViewModel.AddViewModel;
import com.example.goldennote.ViewModel.TodoViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private DatabaseReference databaseReference;
    private View view;
    private RecyclerView recyclerView;
    private AddViewModel addViewModel;
    EditText editTextTitle;
    EditText editTextDescription;
    TextView dateText;
    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar;
    DateFormat dateFormat;
    Button button;



    public AddNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewFragment newInstance(String param1, String param2) {
        AddNewFragment fragment = new AddNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new2, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        button = view.findViewById(R.id.addEventButton);
        dateText = view.findViewById(R.id.dateText);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        addViewModel = new AddViewModel();

         final int year = calendar.get(Calendar.YEAR);
         final int month = calendar.get(Calendar.MONTH);
         final int day = calendar.get(Calendar.DAY_OF_MONTH);

         dateText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog datePickerDialog = new DatePickerDialog(
                         getActivity(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         month=month+1;
                         String date = month+"/"+ dayOfMonth +"/"+year;
                         dateText.setText(date);
                     }
                 },year,month,day);
                 datePickerDialog.show();
             }
         });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataToDatabase(dateText.getText().toString().trim(),editTextTitle.getText().toString(),editTextDescription.getText().toString());
                Toast.makeText(getActivity(), "aha!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public void insertDataToDatabase(String date,String title, String description) {
        if (date.equals("Today"))
        {
            date = dateFormat.format(calendar.getTime());
        }

        addViewModel.addEventToDatabase(date,title,description);

    }


}