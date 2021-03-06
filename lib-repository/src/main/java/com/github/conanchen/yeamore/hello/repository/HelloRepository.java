package com.github.conanchen.yeamore.hello.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.github.conanchen.yeamore.hello.grpc.GrpcFascade;
import com.github.conanchen.yeamore.hello.grpc.HelloReply;
import com.github.conanchen.yeamore.hello.grpc.HelloService;
import com.github.conanchen.yeamore.hello.room.Hello;
import com.github.conanchen.yeamore.hello.room.RoomFascade;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by conanchen on 2017/12/20.
 */

@Singleton
public class HelloRepository {

    private final static String TAG = HelloRepository.class.getSimpleName();

    private final static Gson gson = new Gson();
    private RoomFascade roomFascade;
    private GrpcFascade grpcFascade;

    @Inject
    public HelloRepository(RoomFascade roomFascade, GrpcFascade grpcFascade) {
        this.roomFascade = roomFascade;
        this.grpcFascade = grpcFascade;
    }

    public void sayHello(String s) {
        grpcFascade.helloService.sayHello(s, new HelloService.HelloCallback() {
            @Override
            public void onHelloReply(HelloReply helloReply) {
                Log.i(TAG, String.format("HelloReply: %s", helloReply.getMessage()));
                Hello hello = Hello.builder()
                        .setId(helloReply.getId())
                        .setMessage(helloReply.getMessage())
                        .setCreated(helloReply.getCreated())
                        .setLastUpdated(helloReply.getLastUpdated())
                        .build();
                roomFascade.daoHello.save(hello);
            }
        });
    }

    public LiveData<List<Hello>> loadHellos(Long time) {
        return roomFascade.daoHello.getLiveHellos(100);
    }
}
