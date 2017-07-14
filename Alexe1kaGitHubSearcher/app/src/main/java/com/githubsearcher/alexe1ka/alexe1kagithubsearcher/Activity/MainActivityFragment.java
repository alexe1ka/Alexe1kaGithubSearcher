package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.RepositoryActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    public EditText mInputKeyword;
    public FloatingActionButton mSearchActionButton;
    public String searchKeyword;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mInputKeyword = (EditText) view.findViewById(R.id.keywords_edit_text);

        mSearchActionButton = (FloatingActionButton) view.findViewById(R.id.searchingActionButton);


        mSearchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make intent
                Intent intent = new Intent(getActivity(),RepositoryActivity.class);
                makeSearchKeyword();
                intent.putExtra("searchKeyword",searchKeyword);
                startActivity(intent);
                Log.i(getContext().toString(),"searchKeyword="+searchKeyword);

                //finish();


            }
        });
        return view;
    }

    //генерирует ?q = " search keyword"
    private String makeSearchKeyword() {
        String[] words = mInputKeyword.getText().toString().split("[\\p{P} \\t\\n\\r]");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == words.length - 1) {
                stringBuilder.append(words[i]);
                continue;
            }
            stringBuilder.append(words[i]);
            stringBuilder.append('+');
        }
        searchKeyword = stringBuilder.toString();
        return searchKeyword;
    }


}
