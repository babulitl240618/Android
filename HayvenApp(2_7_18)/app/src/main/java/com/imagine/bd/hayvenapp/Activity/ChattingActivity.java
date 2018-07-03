package com.imagine.bd.hayvenapp.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imagine.bd.hayvenapp.Adapter.ChatAppMsgAdapter;
import com.imagine.bd.hayvenapp.Model.ChatAppMsgDTO;
import com.imagine.bd.hayvenapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {
    private ImageView imggroupcall,imageView1,imgmenu,imggroupvideocall;
    private TextView create_conversation;
    ChatAppMsgAdapter chatAppMsgAdapter;
    EditText msgInputText;
    RecyclerView msgRecyclerView;
    LinearLayout linearLayout,linearLayout1;
    List<ChatAppMsgDTO> msgDtoList;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        context=this;
        imageView1 = (ImageView) findViewById(R.id.notificationChat);
        imgmenu = (ImageView) findViewById(R.id.menu);
        linearLayout1=(LinearLayout) findViewById(R.id.searchsms);
        linearLayout=(LinearLayout)findViewById(R.id.lin_sms) ;
        create_conversation=(TextView)findViewById(R.id.create_conversation) ;

      /*  linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                //imgnewchat.setImageResource(R.drawable.new_chat_black);

            }
        });*/


        imgmenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.chat_menu_dialogue);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.TOP | Gravity.RIGHT;

                wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                // set the custom dialog components - text, image and button
               /* TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_launcher);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);*/
                // if button is clicked, close the custom dialog
              /*  dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
*/
                dialog.show();
            }
        });


        create_conversation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialogue_create_conversation);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM | Gravity.CENTER;

                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                // set the custom dialog components - text, image and button
               /* TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.ic_launcher);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);*/
                // if button is clicked, close the custom dialog
              /*  dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
*/
                dialog.show();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        setTitle("dev2qa.com - Android Chat App Example");

        // Get RecyclerView object.
        msgRecyclerView = (RecyclerView)findViewById(R.id.chat_recycler_view);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        msgDtoList = new ArrayList<ChatAppMsgDTO>();
        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, "hello");
        ChatAppMsgDTO msgDto1 = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, "Get as much of that done today as you u can,but we have more then enough bugs to get started");
        ChatAppMsgDTO msgDto2 = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, "ok");
        msgDtoList.add(msgDto);
        msgDtoList.add(msgDto1);
        msgDtoList.add(msgDto2);
        defultValueadd();

        // Create the data adapter with above data list.
        chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList,context);

        // Set data adapter to RecyclerView.
        msgRecyclerView.setAdapter(chatAppMsgAdapter);

        msgInputText = (EditText)findViewById(R.id.chat_input_msg);
        imggroupcall = (ImageView) findViewById(R.id.audio);
        imggroupvideocall = (ImageView) findViewById(R.id.search);
        imggroupcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingActivity.this, GroupCallActivity.class));
                //Intent i = new Intent(context, Contacts.class);
                //startActivity(i);
            }
        });
        imggroupvideocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingActivity.this, GroupCallActivity.class));
                //Intent i = new Intent(context, Contacts.class);
                //startActivity(i);
            }
        });
        Button msgSendButton = (Button)findViewById(R.id.chat_send_msg);

        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent = msgInputText.getText().toString();
                if(!TextUtils.isEmpty(msgContent))
                {
                    // Add a new sent message to the list.
                    ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);

                    int newMsgPosition = msgDtoList.size() - 1;

                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    msgRecyclerView.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    msgInputText.setText("");
                }
            }
        });

    }

    public void defultValueadd(){


            // Add a new sent message to the list.

            ChatAppMsgDTO msgDto1 = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, "Perfect!");
            ChatAppMsgDTO msgDto2 = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, "Amy and I are going for pho.Wanna join?");
            ChatAppMsgDTO msgDto3 = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, "Guess I am working late tonight.");
            msgDtoList.add(msgDto1);
            msgDtoList.add(msgDto2);
            msgDtoList.add(msgDto3);


          /*  int newMsgPosition = msgDtoList.size() - 1;

            // Scroll RecyclerView to the last message.
            msgRecyclerView.scrollToPosition(newMsgPosition);

            // Empty the input edit text box.
            msgInputText.setText("");*/

    }

}