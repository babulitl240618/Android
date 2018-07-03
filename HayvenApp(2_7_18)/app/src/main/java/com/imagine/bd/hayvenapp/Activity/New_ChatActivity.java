package com.imagine.bd.hayvenapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.imagine.bd.hayvenapp.Adapter.ContactsAdapter;
import com.imagine.bd.hayvenapp.Adapter.NewChatAdapter;
import com.imagine.bd.hayvenapp.Model.DataModel;
import com.imagine.bd.hayvenapp.R;

import java.util.ArrayList;

public class New_ChatActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModels;
    ListView listView;
    ImageView imageView,imgsearch;
    LinearLayout linearLayout,linearLayout1,llCrossbar;
    //private RecyclerView mRecyclerView;
    private Context con;
  //  private NewChatAdapter mAdapter;
    private NewChatAdapter adapter;
   private EditText etSearch;

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chat);
        listView=(ListView)findViewById(R.id.list);
        linearLayout=(LinearLayout) findViewById(R.id.addgroupchat);
        linearLayout1=(LinearLayout) findViewById(R.id.joingroupchat);
        imageView = (ImageView) findViewById(R.id.notificationChat);
        etSearch=(EditText)findViewById(R.id.etSearch) ;
        llCrossbar=(LinearLayout)findViewById(R.id.llCrossbar);
        imgsearch=(ImageView) findViewById(R.id.imgsearch);

        llCrossbar.setVisibility(View.VISIBLE);
        etSearch.setVisibility(View.GONE);

        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llCrossbar.setVisibility(View.GONE);
                etSearch.setVisibility(View.VISIBLE);
            }
        });



        // search contactlist by edittext textwatcher
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int start, int count, int after) {
                // contactadapter.getFilter().filter(s);

                ArrayList<DataModel> tempArrayList=new ArrayList<DataModel>();
                int textlength = cs.length();


                for(DataModel c: dataModels){

                    String name = etSearch.getText().toString();
                   /* if (name.matches("")) {
                        Toast.makeText(con, "You did not enter a username", Toast.LENGTH_SHORT).show();
                        listViewcontacts.setVisibility(View.VISIBLE);
                        return;
                    }else {
                        listViewcontacts.setVisibility(View.GONE);
                    }*/

                    // chack data by character sequence and get searching list data
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }

                }
                adapter= new NewChatAdapter(tempArrayList,getApplicationContext());
                listView.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });   // end of search view




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_ChatActivity.this,NewGroupChatActivity.class));
                //Intent i = new Intent(context, Contacts.class);
                //startActivity(i);
            }
        });
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(New_ChatActivity.this,JoinGroupChatActivity.class));
                //Intent i = new Intent(context, Contacts.class);
                //startActivity(i);
            }
        });
        //mRecyclerView =(RecyclerView)findViewById(R.id.list);
        dataModels= new ArrayList<>();
        dataModels.add(new DataModel("Michel john", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("Jraft joy", "The ArrayAdapter will ..", "9.58am","February 9, 2009"));
        dataModels.add(new DataModel("Fajleh Rabbi", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("Alexjon hesle", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("Nexon lie", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("SM Jubayer", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("Soni Jubayer", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        dataModels.add(new DataModel("Josim Jon", "The ArrayAdapter will ..", "9.58am","September 23, 2008"));
        adapter= new NewChatAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
    }
}
