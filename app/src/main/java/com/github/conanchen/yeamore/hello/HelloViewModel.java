/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.conanchen.yeamore.hello;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;


import com.github.conanchen.yeamore.hello.repository.HelloRepository;
import com.github.conanchen.yeamore.hello.room.Hello;
import com.github.conanchen.yeamore.hello.util.AbsentLiveData;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class HelloViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<Long> helloTime = new MutableLiveData<>();
    private final LiveData<List<Hello>> hellos;
    private HelloRepository helloRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public HelloViewModel(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
        hellos = Transformations.switchMap(helloTime, time -> {
            if (time == null) {
                return AbsentLiveData.create();
            } else {
                return helloRepository.loadHellos(time);
            }
        });

    }

    @VisibleForTesting
    public void setHelloName(String helloName) {
        helloRepository.sayHello(helloName);
    }

    @VisibleForTesting
    public LiveData<List<Hello>> getHellos() {
        return hellos;
    }

    @VisibleForTesting
    public void reloadHellos() {
        this.helloTime.setValue(System.currentTimeMillis());
    }
}
