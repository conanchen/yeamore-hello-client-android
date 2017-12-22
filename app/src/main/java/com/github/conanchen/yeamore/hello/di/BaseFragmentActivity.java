package com.github.conanchen.yeamore.hello.di;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseFragmentActivity extends AppCompatActivity implements HasSupportFragmentInjector {


    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


}