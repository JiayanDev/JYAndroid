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
    private ArrayList<BroadcastReceiver> mReceivers;

    public void registerReceiver(BroadcastReceiver receiver, String... actions) {
        registerReceiver(false, receiver, actions);
    }

    public void registerReceiver(BroadcastReceiver[] receivers, String... actions) {
        if (mReceivers == null) {
            mReceivers = new ArrayList<>();
        }
        int length = receivers.length;
        for (int i = 0; i < length; i++) {
            String action = actions[i];
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            sLocalBroadcastManager.registerReceiver(receivers[i], filter);
            mReceivers.add(receivers[i]);
        }
    }

    public void registerReceiver(boolean firstRun, BroadcastReceiver receiver, String... actions) {
        if (mReceivers == null) {
            mReceivers = new ArrayList<>();
        }
        mReceivers.add(receiver);
        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        sLocalBroadcastManager.registerReceiver(receiver, filter);
        if (firstRun) {
            Intent intent = new Intent(actions[0]);
            receiver.onReceive(null, intent);
        }

        if (receiver instanceof OnceBroadcastReceiver) {
            ((OnceBroadcastReceiver) receiver).mBroadcastHelper = this;
        }
    }

    public void unregisterReceiver(BroadcastReceiver... rs) {
        if (mReceivers != null) {
            ArrayList<BroadcastReceiver> removeReceivers = new ArrayList<>();
            for (BroadcastReceiver r : rs) {
                if (mReceivers.contains(r)) {
                    sLocalBroadcastManager.unregisterReceiver(r);
                    removeReceivers.add(r);
                }
            }
            mReceivers.removeAll(removeReceivers);
        }
    }

    public void onDestroy() {
        if (mReceivers != null) {
            for (BroadcastReceiver receiver : mReceivers) {
                sLocalBroadcastManager.unregisterReceiver(receiver);
            }
            mReceivers.clear();
        }
    }

    public static abstract class OnceBroadcastReceiver extends BroadcastReceiver {
        public abstract void onOnceReceive(Context context, Intent intent);

        private BroadcastHelper mBroadcastHelper;

        @Override
        public final void onReceive(Context context, Intent intent) {
            if (mBroadcastHelper != null) {
                mBroadcastHelper.unregisterReceiver(this);
            }
            onOnceReceive(context, intent);
        }
    }
}
