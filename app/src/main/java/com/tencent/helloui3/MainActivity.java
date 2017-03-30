package com.tencent.helloui3;

/**
 * Created by xinmei365 on 17/1/17.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tencent.helloui3.R;
//import com.tencent.helloui3.data.Contact;
//import com.tencent.helloui3.ui.adapters.ContactsAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_LOAD_CONTACTS = 0x01;
    private static final int REQ_CODE_PERMISSIONS_READ_CONTACTS = 0x02;

    private TextView contactsEmptyView;
    private View contactsProgress;

    private View permissionDeniedView;
    private TextView permissionDeniedRationaleView;

//    private ContactsAdapter contactsAdapter;

    // to avoid asking for permission after it has already been denied once during the same session
    private boolean isPermissionAlreadyDenied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
//        this.contactsAdapter = new ContactsAdapter(LayoutInflater.from(this));
//        contactsRecyclerView.setAdapter(contactsAdapter);

        this.contactsEmptyView = (TextView) findViewById(R.id.contacts_empty);
        this.contactsProgress = findViewById(R.id.contacts_progress);

        this.permissionDeniedView = findViewById(R.id.permission_denied_view);
        this.permissionDeniedRationaleView = (TextView) findViewById(R.id.permission_denied_rationale);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            loadContacts();
        } else if (!isPermissionAlreadyDenied) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    REQ_CODE_PERMISSIONS_READ_CONTACTS);
        }
    }

    private void loadContacts() {
        permissionDeniedView.setVisibility(View.INVISIBLE);
        contactsProgress.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(ID_LOAD_CONTACTS, null, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQ_CODE_PERMISSIONS_READ_CONTACTS && grantResults.length > 0) {
            int grantResult = grantResults[0];
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                isPermissionAlreadyDenied = true;
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_short);
                } else {
                    //permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_long);
                }
                permissionDeniedView.setVisibility(View.VISIBLE);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onGrantPermission(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    REQ_CODE_PERMISSIONS_READ_CONTACTS);
        } else {
            goToSettings();
        }
    }

    private void goToSettings() {
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(settingsIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = {Phone._ID, Phone.DISPLAY_NAME_PRIMARY, Phone.DATA};
        String selection = Data.MIMETYPE + " = '" + Phone.CONTENT_ITEM_TYPE + "'";
        String orderBy = Phone.DISPLAY_NAME_PRIMARY + " ASC";
        return new CursorLoader(this, Phone.CONTENT_URI, columns, selection, null, orderBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        contactsProgress.setVisibility(View.INVISIBLE);
//        List<Contact> contacts = new ArrayList<>();
//        if (data != null) {
//            while (data.moveToNext()) {
//                contacts.add(Contact.fromCursor(data));
//            }
//            data.close();
//        }
//        contactsAdapter.setContacts(contacts);
//        contactsEmptyView.setVisibility(contacts.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        contactsAdapter.setContacts(Collections.<Contact>emptyList());
    }
}
