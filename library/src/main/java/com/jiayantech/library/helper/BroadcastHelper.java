package com.jiayantech.library.helper;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.jiayantech.library.base.BaseApplication;

/**
 * @author 健兴
 * @version 1.0
 * @Description context 广播管理
 * @date 2014-8-13
 * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc. All
 * rights reserved.
 */
public class BroadcastHelper {
    private static final LocalBroadcastManager sLocalBroadcastManager = LocalBroadcastManager.getInstance(BaseApplication.getContext());

    public static void send(String action) {
        sLocalBroadcastManager.sendBroadcast(new Intent(action));
    }

    public static void send(Intent intent) {
        sLocalBroadcastManager.sendBroadcast(intent);
    }

    // /////////////////////////////注册广播
    private ArrayList<BroadcastReceiver> mRreceivers;

    public void registerReceiver(BroadcastReceiver receiver, String... actions) {
        registerReceiver(false, receiver, actions);
    }

    public void registerReceiver(BroadcastReceiver[] receivers, String... actions) {
        if (mRreceivers == null) {
            mRreceivers = new ArrayList<>();
        }
        int length = receivers.length;
        for (int i = 0; i < length; i++) {
            String action = actions[i];
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            sLocalBroadcastManager.registerReceiver(receivers[i], filter);
            mRreceivers.add(receivers[i]);
        }
    }

    public void registerReceiver(boolean firstRun, BroadcastReceiver receiver, String... actions) {
        if (mRreceivers == null) {
            mRreceivers = new ArrayList<>();
        }
        mRreceivers.add(receiver);
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        sLocalBroadcastManager.registerReceiver(receiver, filter);
        if (firstRun) {
            Intent intent = new Intent(actions[0]);
            receiver.onReceive(null, intent);
        }
    }

    public void unReceiver(BroadcastReceiver... rs) {
        if (mRreceivers != null) {
            ArrayList<BroadcastReceiver> removeRreceivers = new ArrayList<>();
            for (BroadcastReceiver r : rs) {
                if (mRreceivers.contains(r)) {
                    sLocalBroadcastManager.unregisterReceiver(r);
                    removeRreceivers.add(r);
                }
            }
            mRreceivers.removeAll(removeRreceivers);
        }
    }

    public void onDestroy() {
        if (mRreceivers != null) {
            for (BroadcastReceiver receiver : mRreceivers) {
                sLocalBroadcastManager.unregisterReceiver(receiver);
            }
            mRreceivers.clear();
        }
    }
}
