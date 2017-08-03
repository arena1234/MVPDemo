package com.wq.demo.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.FrameLayout;

public class PermissionActivity extends BaseActivity {
    private static final String TAG = "PermissionActivity";
    private static final int REQUEST_PERMISSION = 1;
    private boolean isPermission;
    private static final String[] PERMISSIONS = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.CAMERA,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
        isPermission = true;
        for(String permission:PERMISSIONS){
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissionForApp();
                isPermission = false;
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (isPermission) {
            startActivity(PermissionActivity.this, MainActivity.class);
            finish();
        }
    }

    @Override
    protected void setScreenState() {
        setScreenNormal();
    }

    private void requestPermissionForApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            isPermission = true;
        }
    }
}
