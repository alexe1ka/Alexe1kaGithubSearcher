package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;

/*

Написать приложение для Android, которое использует
GitHub API(https://developer.github.com/v3/search/)
для поиска репозитория по ключевым словам.
Нужно отобразить список найденных проектов,
при клике на элемент списка - открыть браузер и перейти на страницу проекта на GitHub.
https://developer.github.com/v3/search/
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_description) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
