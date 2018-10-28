package com.example.anid.permission;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUtil {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PermissionUtil(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.permission_preference), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void updatePermissionPreference(String permission) {
        switch (permission) {

            case "camera":
                mEditor.putBoolean(mContext.getString(R.string.permission_camera), true);
                mEditor.commit();
                break;
            case "storage":
                mEditor.putBoolean(mContext.getString(R.string.permission_storage), true);
                mEditor.commit();
                break;
            case "contacts":
                mEditor.putBoolean(mContext.getString(R.string.permission_contacts), true);
                mEditor.commit();
                break;
        }
    }

    public boolean checkPermissionPreference(String permission) {
        boolean isShown = false;
        switch (permission) {
            case "camera":
                isShown = mSharedPreferences.getBoolean(mContext.getString(R.string.permission_camera), false);
                break;
            case "storage":
                isShown = mSharedPreferences.getBoolean(mContext.getString(R.string.permission_storage), false);
                break;
            case "contacts":
                isShown = mSharedPreferences.getBoolean(mContext.getString(R.string.permission_contacts), false);
                break;
        }

        return isShown;
    }

}
