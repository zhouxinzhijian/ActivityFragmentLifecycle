package com.example.bruce.androidlifecycle.lifecyclelog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**强制更新弹窗*/
public class BruceDialogFragment extends DialogFragment implements View.OnClickListener {
    private Activity mActivity;
    
    public static BruceDialogFragment newInstance(){
        return new BruceDialogFragment();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);//不要标题
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSaveInstanceState(Bundle arg0) {
        super.onSaveInstanceState(arg0);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(getDialog() != null && getDialog().isShowing()){
            return; //防止快速点击按钮弹窗导致的崩溃问题
        }
        super.show(manager, tag);
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
        //其他Fragment和DialogFragment进行交互时，可以设置这个方法来调用Fragment的onActivityResult
    }
}
