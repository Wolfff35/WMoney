package com.wolff.wmoney.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

/**
 * Created by wolff on 16.06.2017.
 */

public class GoogleDriveSync_activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 3;

    GoogleApiClient mGoogleApiClient;
//    public static GoogleDriveSync_activity newInstance(){
//        GoogleDriveSync_activity fragment = new GoogleDriveSync_activity();
//        return fragment;
//    }
public static Intent newIntent(Context context){
    Intent intent = new Intent(context,GoogleDriveSync_activity.class);
    return intent;

}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.e("ON START","connect");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("onConnected","YESSSSS");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("onConnectionSuspended","i = "+i);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("onConnectionFailed","connectionResult = "+connectionResult);
        if (connectionResult.hasResolution()) {
            Log.e("onConnectionFailed","-1 ");
            try {

                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
                Log.e("onConnectionFailed","-2 ");
            } catch (IntentSender.SendIntentException e) {
                Log.e("onConnectionFailed","-3 ");
                // Unable to resolve, message user appropriately
            }
        } else {
            Log.e("onConnectionFailed","-4 ");
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }

    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
       super.onActivityResult(requestCode,resultCode,data);
        Log.e("onActivityResult","requestCode - "+requestCode+"; resultCode - "+resultCode+"; RESULT_OK = "+RESULT_OK);
        //switch (requestCode) {
        //    case RESOLVE_CONNECTION_REQUEST_CODE:
        //        if (resultCode == RESULT_OK) {
                    Log.e("onActivityResult","OK");
                    mGoogleApiClient.connect();
        //        }
        //        break;
        //}
    }
}
