package com.github.conanchen.yeamore.hello.room;


import javax.inject.Inject;

/**
 * Created by conanchen on 10/4/16.
 */
public class RoomFascade {

    public final DaoHello daoUser;

    @Inject
    String strAmir;


    @Inject
    public RoomFascade(DaoHello daoUser) {
        this.daoUser = daoUser;
        System.out.println(strAmir);

    }

    public String getConvertedStrAmir() {
        return "Convert " + strAmir;
    }


}