package com.example.wac_whatsapp_cloning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class userChat extends AppCompatActivity implements View.OnClickListener {
    private String selectedUserName;
    private ListView listView;
    private ArrayList<String> waMessages;
    private ArrayAdapter arrayAdapter;
    private Button sendMessage;
    private Boolean isRuning;
    private Date date;
    //private Handler handler;
    public  static Handler handler;
    //private Activity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRuning=true;
        setContentView(R.layout.activity_user_chat);
        selectedUserName=getIntent().getStringExtra("selectedUser");
        setTitle(selectedUserName);
        sendMessage=findViewById(R.id.textButton);
        sendMessage.setOnClickListener(this);
        listView=findViewById(R.id.chatListView);
        waMessages=new ArrayList();
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                waMessages);
        listView.setAdapter(arrayAdapter);


        /**
        while(isRuning)
        {
            try
            {
                conversationLoadMessages();
                Thread.sleep(1000);

            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }

        }
         **/
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isRuning)
                        conversationLoadMessages();
                    }
                });
            }
        },0,10000);



    }

    @Override
    protected void onResume() {
        super.onResume();
        isRuning=true;
        //conversationLoadMessages();


    }

    @Override
    protected void onPause() {
        super.onPause();
        isRuning=false;
    }

    private void conversationLoadMessages() {


        FancyToast.makeText(userChat.this,"this is message",
                Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        ParseQuery<ParseObject> firstUserQuery= ParseQuery.getQuery("chat");
        ParseQuery<ParseObject> secondUserQuery=ParseQuery.getQuery("chat");
        ArrayList<ParseQuery<ParseObject>> allQuries=new ArrayList<>();

        if(waMessages.size()==0)
        {


            try {
                firstUserQuery.whereEqualTo("messageSender",ParseUser.getCurrentUser().getUsername());
                firstUserQuery.whereEqualTo("messageRecipent",selectedUserName);

                secondUserQuery.whereEqualTo("messageRecipent",ParseUser.getCurrentUser().getUsername());
                secondUserQuery.whereEqualTo("messageSender",selectedUserName);


                allQuries.add(firstUserQuery);
                allQuries.add(secondUserQuery);



            }
            catch (Exception e)
            {e.printStackTrace();}
        }
        else
        {
            if(date!=null)
            {
                FancyToast.makeText(userChat.this,date+"",
                        Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                try {






                    secondUserQuery.whereEqualTo("messageRecipent", ParseUser.getCurrentUser().getUsername());
                    secondUserQuery.whereEqualTo("messageSender", selectedUserName);
                    secondUserQuery.whereGreaterThan("createdAt", date);
                    allQuries.add(secondUserQuery);

                }
                catch (Exception e)
                {e.printStackTrace();}



            }
        }


        try
        {
            ParseQuery<ParseObject> myQuery=ParseQuery.or(allQuries);
            myQuery.orderByAscending("createdAt");



            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e==null)
                    {
                        for(ParseObject users: objects)
                        {

                            date=users.getCreatedAt();
                            String waMessage=users.get("message")+"";
                            if(users.get("messageSender").equals(ParseUser.getCurrentUser().getUsername()))
                            {
                                waMessage=ParseUser.getCurrentUser().getUsername()+": "+waMessage;
                            }
                            if (users.get("messageSender").equals(selectedUserName))
                            {
                                waMessage=selectedUserName+": "+waMessage;
                            }
                            waMessages.add(waMessage);

                        }
                        arrayAdapter.notifyDataSetChanged();
                    }










                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.textButton:
                final EditText message=findViewById(R.id.userText);
                if(!message.getText().toString().equals(""))
                {
                    ParseObject chat=new ParseObject("chat");
                    chat.put("messageSender", ParseUser.getCurrentUser().getUsername());
                    chat.put("messageRecipent",selectedUserName);
                    chat.put("message",message.getText().toString());

                    chat.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                final String text=ParseUser.getCurrentUser().getUsername()+": "+message.getText().toString();

                                waMessages.add(text);
                                arrayAdapter.notifyDataSetChanged();
                                message.setText("");
                            }
                            else
                            {
                                conversationLoadMessages();
                            }



                        }
                    });

                }
                break;
        }
    }
}
