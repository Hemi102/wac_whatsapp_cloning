package com.example.wac_whatsapp_cloning;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class ft_user_chat extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ft_user_chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ft_user_chat, container, false);

        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);

        listView.setOnItemClickListener(this);
        final SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.swipeRefresh);



        try
        {
            final ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e==null && objects.size()>0)
                    {
                        for(ParseUser users: objects)
                        {
                            arrayList.add(users.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    }

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try
                {
                    ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username",arrayList);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(objects.size()>0)
                            {
                                if(e==null)
                                {
                                    for(ParseUser users: objects)
                                    {
                                        arrayList.add(users.getUsername());
                                    }
                                    arrayAdapter.notifyDataSetChanged();
                                    if(swipeRefreshLayout.isRefreshing())
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                                else
                                {
                                    FancyToast.makeText(getContext(),e.getMessage()+"",
                                            Toast.LENGTH_SHORT,
                                            FancyToast.ERROR,true).show();
                                    if(swipeRefreshLayout.isRefreshing())
                                    {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }
                            else
                            {
                                if(swipeRefreshLayout.isRefreshing())
                                {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if(swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        //ended here swipe layout


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(),userChat.class);
        intent.putExtra("selectedUser",arrayList.get(position));
        startActivity(intent);

    }
}
