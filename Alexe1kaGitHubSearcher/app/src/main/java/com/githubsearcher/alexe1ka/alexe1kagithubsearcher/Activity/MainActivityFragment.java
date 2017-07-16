package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private EditText mInputKeyword;
    private FloatingActionButton mSearchActionButton;
    private String mSearchKeyword;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        mInputKeyword = (EditText) view.findViewById(R.id.keywords_edit_text);
        mSearchActionButton = (FloatingActionButton) view.findViewById(R.id.searchingActionButton);
        mSearchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //введено ли ключевое слово
                if (mInputKeyword.getText().toString().trim().length() != 0) {
                    //check internet connection
                    if ((isWifiConnected() || isNetworkConnected())) {
                        //make intent
                        Intent intent = new Intent(getActivity(), RepositoryActivity.class);
                        makeSearchKeyword();
                        intent.putExtra("mSearchKeyword", mSearchKeyword);
                        startActivity(intent);

                        Log.i(getContext().toString(), "mSearchKeyword=" + mSearchKeyword);
                        //getActivity().finish();
                    } else {
                        //noInternet-> intent в настройки
                        Snackbar.make(view, "Turn on internet", Snackbar.LENGTH_LONG).setAction("Open settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent settingsIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(settingsIntent);
                            }
                        }).show();
                    }
                } else {
                    //ключевое слово не введено
                    Snackbar.make(view, "Please insert a keyword", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }






    //генерирует ?q = " search keyword"
    private String makeSearchKeyword() {
        String[] words = mInputKeyword.getText().toString().split("[\\p{P} \\t\\n\\r]");//разобраться
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i == words.length - 1) {
                stringBuilder.append(words[i]);
                continue;
            }
            stringBuilder.append(words[i]);
            stringBuilder.append('+');
        }
        mSearchKeyword = stringBuilder.toString();
        return mSearchKeyword;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }


}
