package com.luv.intentapprunner;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent intent = getIntent();
            if (intent.getAction().equals(Intent.ACTION_VIEW)) {
                Uri uri = intent.getData();
                if (uri != null) {
                    String pkg = uri.getQueryParameter("pkg");
                    String cls = uri.getQueryParameter("cls");

                    if (pkg != null && cls != null) {
                        Intent runIntent = new Intent();
                        runIntent.setComponent(new ComponentName(pkg, cls));
                        startActivity(runIntent);
                    } else if (pkg != null) {
                        Intent runIntent = getPackageManager().getLaunchIntentForPackage(pkg);
                        if (runIntent == null) {
                            Toast.makeText(this, "application not installed", Toast.LENGTH_SHORT).show();
                        } else {
                            runIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(runIntent);
                        }
                    }
                }
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "activity not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "failed to run application", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            finish();
        }
    }
}
