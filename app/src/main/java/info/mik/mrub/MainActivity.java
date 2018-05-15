package info.mik.mrub;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


public class MainActivity
        extends AppCompatActivity
        implements LoaderCallbacks<Cursor>, ActivityCompat.OnRequestPermissionsResultCallback {
    ListView lstContact;
    CustomContactAdapter adapter;
    private static final int PERMISSION_REQUEST_CONTACT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstContact = findViewById(R.id.lstContacts);
        getSupportLoaderManager().initLoader(1, null, this);

        checkPermission();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri CONTACT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        CursorLoader cursorLoader = new CursorLoader(
                this,
                CONTACT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        adapter = new CustomContactAdapter(this, cursor);
        lstContact.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                if ((grantResults.length > 0) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    // unnecessary, implements OnRequestPermissionsResultCallback handles that
                    //prepareData();
                } else {
                    AlertDialog.Builder builder = new
                            AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            checkPermission();
                        }
                    });
                    builder.show();
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void checkPermission() {
        if ((android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) &&
                (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.READ_CONTACTS },
                    PERMISSION_REQUEST_CONTACT);
        } else {
            // unnecessary TOO, implements OnRequestPermissionsResultCallback handles that too
            //prepareData();
        }
    }
}