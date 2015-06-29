package com.jiayantech.library.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.message.PushAgent;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

import com.jiayantech.library.R;

/**
 * Created by liangzili on 15/6/24.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    public BaseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启友盟统计
        PushAgent.getInstance(this).onAppStart();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setSwipeBackEnable(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }


    public View findViewById(int id) {
        View v = super.findViewById(id);
        return v == null && this.mHelper != null ? this.mHelper.findViewById(id) : v;
    }

    /**
     * 重写setContentView将方法传进来的layoutID，嵌套到一个带有toolbar的layout里，再调用super.SetContentView
     * 然后将该toolbar设置成activity的默认actionbar
     */
    @Override
    public void setContentView(int layoutResID) {
        View activityView = getLayoutInflater().inflate(R.layout.activity_base, null);
        View view = getLayoutInflater().inflate(layoutResID, (ViewGroup) activityView, false);
        ((ViewGroup) activityView).addView(view);
        Toolbar toolbar = (Toolbar) activityView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.setContentView(activityView);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean b) {
        mHelper.getSwipeBackLayout().setEnableGesture(b);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        this.getSwipeBackLayout().scrollToFinishActivity();
    }
}
