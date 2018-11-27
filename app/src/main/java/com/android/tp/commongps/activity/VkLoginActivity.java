package com.android.tp.commongps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.tp.commongps.R;
import com.android.tp.commongps.core.VkLoginCallback;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;

public class VkLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_login);

        VKSdk.login(this, "friends", "messages");

        //String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        //Log.i("fingerprints", String.valueOf(fingerprints));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VkLoginCallback<VKAccessToken>())) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        finish();
    }
}
