package com.example.anid.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAllPermissionBtn;
    private Button mCameraPermissionBtn;
    private Button mStoragePermissionBtn;
    private Button mContactsPermissionBtn;

    private static final int REQUEST_CAMERA = 125;
    private static final int REQUEST_STORAGE = 225;
    private static final int REQUEST_CONTACTS = 325;
    private static final int REQUEST_GROUP_PERMISSION = 425;

    private static final int TXT_CAMERA = 1;
    private static final int TXT_STORAGE = 2;
    private static final int TXT_CONTACTS = 3;

    private PermissionUtil mPermissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPermissionUtil = new PermissionUtil(this);

        mAllPermissionBtn = (Button) findViewById(R.id.btn_all_permission);
        mCameraPermissionBtn = (Button) findViewById(R.id.btn_camera_permission);
        mStoragePermissionBtn = (Button) findViewById(R.id.btn_storage_permission);
        mContactsPermissionBtn = (Button) findViewById(R.id.btn_contacts_permission);

        mAllPermissionBtn.setOnClickListener(this);
        mCameraPermissionBtn.setOnClickListener(this);
        mStoragePermissionBtn.setOnClickListener(this);
        mContactsPermissionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.btn_all_permission:
                break;
            case R.id.btn_camera_permission:
                openCamera();
                break;
            case R.id.btn_storage_permission:
                openStorage();
                break;
            case R.id.btn_contacts_permission:
                readContacts();
                break;
        }
    }


    private int checkPermissionn(int permission) {
        int status = PackageManager.PERMISSION_DENIED;

        switch (permission) {

            case TXT_CAMERA:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                break;
            case TXT_STORAGE:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case TXT_CONTACTS:
                status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                break;
        }

        return status;
    }

    private void requestPermission(int permission) {
        switch (permission) {

            case TXT_CAMERA:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
                break;
            case TXT_STORAGE:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
                break;
            case TXT_CONTACTS:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                break;
        }
    }

    private void showPermissionExplanation(final int permission) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(permission == TXT_CAMERA) {
            builder.setMessage("This app need to access your device camera. Please allow");
            builder.setTitle("Camera Permission needed..");
        }else if(permission == TXT_CONTACTS) {
            builder.setMessage("This app need access to your contacts. Please allow");
            builder.setTitle("Contacts Permission Needed..");
        }else if(permission == TXT_STORAGE) {
            builder.setMessage("This app need to write to your device storage. Please allow");
            builder.setTitle("Storage Permission Needed");
        }

        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(permission == TXT_CAMERA) {
                    requestPermission(TXT_CAMERA);
                }else if(permission == TXT_STORAGE) {
                    requestPermission(TXT_STORAGE);
                }else if(permission == TXT_CONTACTS) {
                    requestPermission(TXT_CONTACTS);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void openCamera() {

        if(checkPermissionn(TXT_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                showPermissionExplanation(TXT_CAMERA);
            } else if(!mPermissionUtil.checkPermissionPreference("camera")) {
                requestPermission(TXT_CAMERA);
                mPermissionUtil.updatePermissionPreference("camera");
            }else {
                Toast.makeText(this, "Please allow camera in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("message", "You can now take photos and record videos");
            startActivity(intent);
        }

    }

    public void openStorage() {

        if(checkPermissionn(TXT_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showPermissionExplanation(TXT_STORAGE);
            } else if(!mPermissionUtil.checkPermissionPreference("storage")) {
                requestPermission(TXT_STORAGE);
                mPermissionUtil.updatePermissionPreference("storage");
            }else {
                Toast.makeText(this, "Please allow storage permission in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "You have storage permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("message", "You can now store in memory");
            startActivity(intent);
        }

    }

    public void  readContacts() {

        if(checkPermissionn(TXT_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                showPermissionExplanation(TXT_CONTACTS);
            } else if(!mPermissionUtil.checkPermissionPreference("contacts")) {
                requestPermission(TXT_CONTACTS);
                mPermissionUtil.updatePermissionPreference("contacts");
            }else {
                Toast.makeText(this, "Please allow contacts permission in your app settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }else {
            Toast.makeText(this, "You have contacts permission", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("message", "You can now read contacts from this device");
            startActivity(intent);
        }

    }

    private void requestGroupPermission(ArrayList<String> permissions) {

        String[] permissionList = new String[permissions.size()];
        permissions.toArray(permissionList);

        ActivityCompat.requestPermissions(MainActivity.this, permissionList, REQUEST_GROUP_PERMISSION);
    }

    public void requestAllPermission() {

        ArrayList<String> permissionNeeded = new ArrayList<>();
        ArrayList<String> permissionAvailable = new ArrayList<>();
        permissionAvailable.add(Manifest.permission.READ_CONTACTS);
        permissionAvailable.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionAvailable.add(Manifest.permission.CAMERA);

        for(String permission : permissionAvailable) {

            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(permission);
            }
        }

        requestGroupPermission(permissionNeeded);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have camera permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now take photos and record videso");
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Camera permission is denied. Turn off camera modules of the app", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONTACTS:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have contacts permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now read contacts");
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Read contacts permission is denied. Turn off read contacts modules of the app", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You have storage permission", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra("message", "You can now write to storage");
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Storage permission is denied. Turn off read storage modules of the app", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GROUP_PERMISSION:

                String result = "";
                int i = 0;
                for(String perm : permissions) {

                    String status = "";
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        status = "GRANTED";
                    }else {
                        status = "DENIED";
                    }
                    result = result + "\n" + perm + " : " + status;
                    i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Group Permissions Details");
                builder.setMessage(result);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
