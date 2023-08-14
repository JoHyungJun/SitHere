package com.example.hyung;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BeaconActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final String TAG = "sampleCreateBeacon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        // 퍼미션 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                });
                builder.show();
            }
        }

        // 비콘 생성 후 시작. 실제 가장 필요한 소스
        Beacon beacon = new Beacon.Builder()
                .setId1("22222222-2222-2222-2222-222222222222")  // uuid for beacon
                .setId2("1")  // major
                .setId3("1")  // minor
                .setManufacturer(0x004C)  // Radius Networks. 0x0118 : Change this for other beacon layouts // 0x004C : for iPhone
                .setTxPower(-56)  // Power in dB
                .setDataFields(Arrays.asList(new Long[]{0l}))  // Remove this for beacon layouts without d: fields
                .build();
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");
        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

        });

    }



    // 퍼미션 요청후 callback
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }

        /*
        findViewById(R.id.quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //android.os.Process.killProcess(android.os.Process.myPid());
                //finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        */
        /*
        // xml 코드
        <Button
        android:id="@+id/quit"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:layout_marginTop="88dp"
        android:background="#E49C44"
        android:fontFamily="@font/nanumsquare"

        android:text="사용 종료"
        android:textColor="#fff"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.535"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_isConnect" />
         */
    }

}



/*
public class BeaconActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;

    private TextView tv_isConnect;
    private Button btn_isConnect;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        tv_isConnect = findViewById(R.id.tv_isConnect);
        btn_isConnect = findViewById(R.id.btn_isConnect);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                                                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        btn_isConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBeaconServiceConnect();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                tv_isConnect.setText("연결 됨.");
            }

            @Override
            public void didExitRegion(Region region) {
                tv_isConnect.setText("연결 안 됨.");
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                tv_isConnect.setText("Determine이 무슨 뜻이지?" + count);
                count++;
            }
        });

        try{
            beaconManager.startMonitoringBeaconsInRegion(new Region(
                    "JaeBalDwaeRa", null,
                    null,null)
            );
        }catch(RemoteException ignored){

        }
    }
}
*/











/*
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),1234,4321));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Toast.makeText(getApplicationContext(), "연결 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onExitedRegion(Region region) {
                Toast.makeText(getApplicationContext(), "연결이 해제 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

*/
