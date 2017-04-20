package com.example.bruce.androidlifecycle.lifecyclelog;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruce.androidlifecycle.R;

/**DialogFragment
 * 这种Fragment既可以作为其他界面的UI，也可以作为Dialog使用，更灵活
 * */
public class BruceDialogFragment extends DialogFragment implements View.OnClickListener {
    private Activity mActivity;

    public BruceDialogFragment() {
        Log.e(getClass().getSimpleName(), "BruceDialogFragment constructor invoke =" + this);
    }

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
        setCancelable(true);//是否可取消的状态也写到这里
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);//TODO:style等状态写到这里
        //TODO:getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bruce_dialog, container, false);
        view.findViewById(R.id.show_dialog).setOnClickListener(this);
        return view;
        //TODO:findViewById写在这里
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);//TODO:自定义的dialog，自定义后上面的setStyle(...)将不起作用
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onClick(View v) {
//        getFragmentManager().beginTransaction()
//                .add(R.id.dialog_fragment_container, BruceDialogFragment.newInstance())
//                .commit();
        getChildFragmentManager().beginTransaction()
                .add(R.id.dialog_fragment_container, BruceDialogFragment.newInstance())
                .commit();
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

    public void showDialog(FragmentActivity activity, String tag){
//        显示Fragment，写到对应的FragmentActivity中
//        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
//        android.app.Fragment prev = activity.getFragmentManager().findFragmentByTag(tag);
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);//TODO:视情况而定
        show(activity.getSupportFragmentManager(), tag);
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
        //其他Fragment和DialogFragment进行交互时，可以设置这个方法来调用Fragment的onActivityResult
    }
}
