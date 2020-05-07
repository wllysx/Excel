package com.wll.myproject.excel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private int REQUEST_CODE_PERMISSION = 0x00099;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_base );
        checkPermession( this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION );
    }

    /**
     * 检查权限
     * @param activity
     * @param permissions 权限列表
     * @param requestCode 对应code
     */
    private void checkPermession(Activity activity, String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        this.activity = activity;
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            int r = ActivityCompat.checkSelfPermission( activity, permission );

            if (r == PackageManager.PERMISSION_DENIED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //权限未添加成功,将未添加权限的字符串权限都放入此集合中。
                needRequestPermissionList.add( permission );

            }else{
                //权限全部添加成功
            }

        }
        //尝试添加权限。
        if (!needRequestPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions( activity,needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    //这里对动态添加权限的结果进行判断。
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if(requestCode==REQUEST_CODE_PERMISSION){
            //false就是添加失败。
            if(verifyPermissions(grantResults)){
                //添加成功
                Toast.makeText( activity, "所有权限添加成功", Toast.LENGTH_SHORT ).show();

            }else {
                //添加失败。
                Toast.makeText( activity, "部分权限添加失败", Toast.LENGTH_SHORT ).show();

            }

        }
    }
    //对动态添加权限是否全部添加成功的结果进行判断。
    private Boolean verifyPermissions(int[] grantResults ) {
        for (int grantResult:grantResults) {
            //如果走这里了就是遇到不等就不对下面的进行遍历了。
            if (grantResult!= PackageManager.PERMISSION_GRANTED){
                //表示添加失败
                return false;
            }
        }
        return true;

    }
}
