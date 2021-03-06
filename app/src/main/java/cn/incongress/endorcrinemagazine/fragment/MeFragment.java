package cn.incongress.endorcrinemagazine.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.activity.FeedbackActivity;
import cn.incongress.endorcrinemagazine.activity.LoginActivity;
import cn.incongress.endorcrinemagazine.activity.MyCollectionActivity;
import cn.incongress.endorcrinemagazine.activity.RegisterActivity;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.base.Constants;
import cn.incongress.endorcrinemagazine.bean.CollectionBean;
import cn.incongress.endorcrinemagazine.utils.CircleImageView;
import cn.incongress.endorcrinemagazine.utils.ListDataSave;
import cn.incongress.endorcrinemagazine.utils.LogUtils;
import cn.incongress.endorcrinemagazine.utils.PicUtils;
import cn.incongress.endorcrinemagazine.utils.ShareUtils;
import cn.incongress.endorcrinemagazine.utils.SharedPreferenceUtils;
import cn.incongress.endorcrinemagazine.utils.ToastUtils;
import cn.incongress.endorcrinemagazine.utils.XhyGo;
import cn.incongress.endorcrinemagazine.utils.popup.PhotoPopupWindow;
import okhttp3.Call;
import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jacky on 2017/4/7.
 * 我
 */

public class MeFragment extends BaseLazyFragment implements View.OnClickListener{
    private CircleImageView tx_img;
    private Button login_btn;
    private TextView loginName,mCollectionsize;
    private LinearLayout myCollection,recommend,feedback,loginXS,loginSet;
    private String USERID;
    private SharedPreferences mSp;
    private PhotoPopupWindow mPhotoPopupWindow;
    private ListDataSave mDataSave;
    private List<CollectionBean> mResultList;

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mResultList = mDataSave.getDataList("All");
        mCollectionsize.setText(""+mResultList.size());
        USERID = mSp.getString(Constants.USER_USER_ID,"");
        loginName.setText(mSp.getString(Constants.USER_TRUE_NAME,""));
        if(!"".equals(USERID)){
            login_btn.setText(getActivity().getString(R.string.back));
            login_btn.setBackgroundColor(getActivity().getResources().getColor(R.color.umeng_divide));
            loginXS.setVisibility(View.VISIBLE);
            updatePersonUserIcon();
        }
        MobclickAgent.onPageStart(Constants.UMENG_ME);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mSp = getActivity().getSharedPreferences("personInfo", MODE_PRIVATE);
        mDataSave = new ListDataSave(mContext, "collection");
        USERID = mSp.getString(Constants.USER_USER_ID,"");
        myCollection = (LinearLayout) view.findViewById(R.id.myCollection);
        feedback = (LinearLayout) view.findViewById(R.id.feedback);
        recommend = (LinearLayout) view.findViewById(R.id.recommend);
        loginXS = (LinearLayout) view.findViewById(R.id.login_xs);
        tx_img = (CircleImageView) view.findViewById(R.id.img_tx);
        login_btn = (Button) view.findViewById(R.id.btn_me_login);
        loginName = (TextView) view.findViewById(R.id.loginName);
        loginSet = (LinearLayout) view.findViewById(R.id.loginSet);
        mCollectionsize = (TextView) view.findViewById(R.id.myCollection_size);
        myCollection.setOnClickListener(this);
        feedback.setOnClickListener(this);
        recommend.setOnClickListener(this);
        tx_img.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        loginSet.setOnClickListener(this);
        updatePersonUserIcon();
        return view;
    }
    private void updatePersonUserIcon() {
        Glide.with(getActivity()).load(mSp.getString(Constants.USER_PIC,"")).placeholder(R.mipmap.toux).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                tx_img.setImageDrawable(resource);
            }
        });
    }
private UMShareListener listener = new UMShareListener() {
    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.e("GYW",share_media.toString());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.e("GYW",share_media.toString());
    }
};
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myCollection:
                if(!"".equals(USERID)) {
                    startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.feedback:
                if(!"".equals(USERID)) {
                    startActivity(new Intent(getActivity(), FeedbackActivity.class));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.recommend:
              new ShareUtils().shareTextWithUrl(getActivity(),"分享","中华内分泌杂志","http://app.incongress.cn/zhnfm",listener);
                break;
            case R.id.img_tx:
                if(!"".equals(USERID)) {
                    mPhotoPopupWindow = new PhotoPopupWindow(getActivity(), new PhotoPopupWindow.OnTakePhotoListener() {
                        @Override
                        public void takePhotoListen() {

                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, new GalleryFinal.OnHanlderResultCallback() {
                                @Override
                                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                    if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing())
                                        mPhotoPopupWindow.dismiss();
                                    String photoPath = "";
                                    if (reqeustCode == REQUEST_CODE_GALLERY) {
                                        photoPath = resultList.get(0).getPhotoPath();
                                        LogUtils.println("photo:" + photoPath);
                                    } else if (reqeustCode == REQUEST_CODE_CAMERA) {
                                        photoPath = resultList.get(0).getPhotoPath();
                                        LogUtils.println("photo:" + photoPath);
                                    }
                                    //图片进行压缩
                                    String filePhth = "";
                                    try {
                                        filePhth = PicUtils.saveFile(PicUtils.getSmallBitmap(photoPath));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    XhyGo.goUploadUserIcon(getActivity(), USERID, System.currentTimeMillis() + "", new File(filePhth), new StringCallback() {
                                        @Override
                                        public void onAfter() {
                                            super.onAfter();
                                        }

                                        @Override
                                        public void onBefore(Request request) {
                                            super.onBefore(request);
                                        }

                                        @Override
                                        public void onError(Call call, Exception e) {

                                        }

                                        @Override
                                        public void onResponse(String response) {
                                            //更新头像
                                            try {
                                                JSONObject obj = new JSONObject(response);
                                                if (obj.getInt("state") == 1) {
                                                    SharedPreferences.Editor edit = mSp.edit();
                                                    edit.putString(Constants.USER_PIC, obj.getString("imgUrl"));
                                                    edit.commit();
                                                    updatePersonUserIcon();
                                                } else {

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onHanlderFailure(int requestCode, String errorMsg) {
                                    //   ToastUtils.showShortToast(getString(R.string.fail_reason, errorMsg), getActivity());
                                }
                            });
                        }
                    }, new PhotoPopupWindow.OnAlbumListener() {
                        @Override
                        public void albumListen() {
                            FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(1).build();
                            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
                                @Override
                                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                    if (mPhotoPopupWindow != null && mPhotoPopupWindow.isShowing())
                                        mPhotoPopupWindow.dismiss();
                                    String photoPath = "";
                                    if (reqeustCode == REQUEST_CODE_GALLERY) {
                                        photoPath = resultList.get(0).getPhotoPath();
                                        LogUtils.println("photo:" + photoPath);
                                    } else if (reqeustCode == REQUEST_CODE_CAMERA) {
                                        photoPath = resultList.get(0).getPhotoPath();
                                        LogUtils.println("photo:" + photoPath);
                                    }
                                    //图片进行压缩
                                    String filePhth = "";
                                    try {
                                        filePhth = PicUtils.saveFile(PicUtils.getSmallBitmap(photoPath));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    XhyGo.goUploadUserIcon(getActivity(), USERID, System.currentTimeMillis() + "", new File(filePhth), new StringCallback() {
                                        @Override
                                        public void onAfter() {
                                            super.onAfter();
                                        }

                                        @Override
                                        public void onBefore(Request request) {
                                            super.onBefore(request);
                                        }

                                        @Override
                                        public void onError(Call call, Exception e) {

                                        }

                                        @Override
                                        public void onResponse(String response) {
                                            //更新头像
                                            try {
                                                JSONObject obj = new JSONObject(response);
                                                if (obj.getInt("state") == 1) {
                                                    SharedPreferences.Editor edit = mSp.edit();
                                                    edit.putString(Constants.USER_PIC, obj.getString("imgUrl"));
                                                    edit.commit();
                                                    updatePersonUserIcon();
                                                } else {

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onHanlderFailure(int requestCode, String errorMsg) {
                                    ToastUtils.showShortToast(getString(R.string.fail_reason, errorMsg), getActivity());
                                }
                            });
                        }
                    });
                    mPhotoPopupWindow.showPopupWindow();
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.btn_me_login:
                if(getActivity().getString(R.string.login_me).equals(login_btn.getText().toString())){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else if(getActivity().getString(R.string.back).equals(login_btn.getText().toString())){
                    new AlertDialog.Builder(getActivity())//设置对话框标题
                            .setMessage(getActivity().getString(R.string.back_ts))//设置显示的内容
                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    SharedPreferences.Editor edit = mSp.edit();
                                    edit.clear();
                                    edit.commit();
                                    login_btn.setText(getActivity().getString(R.string.login_me));
                                    login_btn.setBackgroundColor(getActivity().getResources().getColor(R.color.details));
                                    loginXS.setVisibility(View.GONE);
                                    tx_img.setImageResource(R.mipmap.toux);
                                    USERID = mSp.getString(Constants.USER_USER_ID,"");
                                }
                            }).setNegativeButton("取消",null).show();//在按键响应事件中显示此对话框
                }
                break;
            case R.id.loginSet:
                Intent it= new Intent(getActivity(),RegisterActivity.class);
                it.putExtra("type",2);
                startActivity(it);
                break;
            default:
                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(Constants.UMENG_ME);
    }
}
