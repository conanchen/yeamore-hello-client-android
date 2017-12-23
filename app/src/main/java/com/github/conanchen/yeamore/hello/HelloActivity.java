package com.github.conanchen.yeamore.hello;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.conanchen.yeamore.hello.di.BaseActivity;
import com.github.conanchen.yeamore.hello.room.Hello;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class HelloActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HelloViewModel helloViewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.listbutton)
    AppCompatButton listButton;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.listitems)
    AppCompatTextView hellosText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    private void setupViewModel() {
        helloViewModel = ViewModelProviders.of(this, viewModelFactory).get(HelloViewModel.class);
        helloViewModel.getHellos().observe(this, hellos -> {
            if (hellos != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Hello h : hellos) {
                    stringBuilder.append(String.format("%d:%s@%d\n", h.id, h.message, h.lastUpdated));
                }
                hellosText.setText(stringBuilder.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hello, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.listbutton)
    public void onListButtonClicked(View view) {
        helloViewModel.setHelloName("Conan Chen");
    }
}
