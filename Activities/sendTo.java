package com.example.bigfamilyv20.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bigfamilyv20.Entities.namesArray;
import com.example.bigfamilyv20.R;

import java.util.ArrayList;
import java.util.List;

public class sendTo extends AppCompatActivity {
private ListView view;
private String[] names;
private static Button next;
private List<String> nameslist;
private static EditText numbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to);
        view=(ListView)findViewById(R.id.list_before_sending);
        nameslist=new ArrayList<>();
        nameslist= namesArray.getName();
        next=(Button)findViewById(R.id.next_to_send);
        numbers=(EditText)findViewById(R.id.number_to_send_to_edittext);

        // Create the array adapter to bind the array to the listView
        names=new String[nameslist.size()];
        for (int x=0;x<nameslist.size();x++)
        {
            names[x]=nameslist.get(x);
        }

        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(  this,
                android.R.layout.simple_list_item_1,
                names
        );
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NUMBER=numbers.getText().toString().trim().toLowerCase();
                Intent intent=new Intent(getApplicationContext(),Cart_Activity.class);
                intent.putExtra("price",String.valueOf(0));
                intent.putExtra("Service",String.valueOf(10));
                intent.putExtra("reason","sending of goods");
                intent.putExtra("number",NUMBER);
                startActivity(intent);
            }
        });
        view.setAdapter(aa);

    }
}
