package cn.incongress.endorcrinemagazine.activity;

import android.app.Activity;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;
import cn.incongress.endorcrinemagazine.utils.ActivityUtils;
import cn.incongress.endorcrinemagazine.utils.TDevice;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_appversion)
    TextView mTvVersion;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTvVersion.setText(getString(R.string.splash_versionName,TDevice.getVersionName()));
    }

    @Override
    protected void initializeEvents() {
        mTvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(SplashActivity.this, HomeActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }
}