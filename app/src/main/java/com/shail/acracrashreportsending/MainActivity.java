package com.shail.acracrashreportsending;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.acra.sender.EmailIntentSender;

import static com.shail.acracrashreportsending.GMailSender.ACCOUNT_TYPE;

public class MainActivity extends AppCompatActivity {

    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        authPreferences = MyApplication.getInstance().getAuthPreferences();
//        accountManager = AccountManager.get(this);
        final Button button = (Button) findViewById(R.id.cause_crash);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException();
            }
        });
    }

//    private void init() {
//
//        if (authPreferences.getUser() != null && authPreferences.getToken() != null) {
//            doCoolAuthenticatedStuff();
//        } else {
//            chooseAccount();
//        }
//    }
//
//    private void doCoolAuthenticatedStuff() {
//        // TODO: insert cool stuff with authPreferences.getToken()
//
//        Log.i("AuthApp", authPreferences.getToken());
//    }
//
//    private void chooseAccount() {
//        // use https://github.com/frakbot/Android-AccountChooser for
//        // compatibility with older devices
//        Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
//        startActivityForResult(intent, ACCOUNT_CODE);
//    }
//
//    private void requestToken() {
//        Account userAccount = null;
//        String user = authPreferences.getUser();
//        for (Account account : accountManager.getAccountsByType("com.google")) {
//            if (account.name.equals(user)) {
//                userAccount = account;
//                break;
//            }
//        }
//        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
//        for (Account account : accounts) {
//            Log.d("AuthToken", "account=" + account);
//        }
//        if (0 < accounts.length) {
//            Account account = accounts[0]; //You need to get a google account on the device, it changes if you have more than one
//            Log.d("AuthToken", "account=" + account);
//
//            String accountName = account.name;
//            authPreferences.setUser(accountName);
////            accountManager.getAuthToken(userAccount, GMailSender.AUTHTOKEN_TYPE, null, this, new OnTokenAcquired(), null);
//
//            accountManager.getAuthToken(account, "oauth2:https://mail.google.com/", null, true, new AccountManagerCallback<Bundle>() {
//                @Override
//                public void run(AccountManagerFuture<Bundle> result) {
//                    try {
//                        Bundle bundle = result.getResult();
//                        String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
//                        authPreferences.setToken(token);
//                        Log.d("getAuthToken callback", "token=" + token);
//
//                    } catch (Exception e) {
//                        Log.d("test", e.getMessage());
//                    }
//                }
//            }, null);
//        }
//    }
//
//    /**
//     * call this method if your token expired, or you want to request a new
//     * token for whatever reason. call requestToken() again afterwards in order
//     * to get a new token.
//     */
//    private void invalidateToken() {
//        String token = authPreferences.getToken();
//        Log.d("invalidateToken", "token=" + token);
//        accountManager.invalidateAuthToken("com.google", token);
//        authPreferences.setToken(null);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == AUTHORIZATION_CODE) {
//                requestToken();
//            } else if (requestCode == ACCOUNT_CODE) {
//                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//
//                authPreferences.setUser(accountName);
//
//                // invalidate old tokens which might be cached. we want a fresh
//                // one, which is guaranteed to work
//                invalidateToken();
//
//                requestToken();
//            }
//        }
//    }
//
//    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
//
//        @Override
//        public void run(AccountManagerFuture<Bundle> result) {
//            try {
//                Bundle bundle = result.getResult();
//                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
//                if (launch != null) {
//                    startActivityForResult(launch, AUTHORIZATION_CODE);
//                } else {
//                    String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
//                    Log.d("AuthToken", "token=" + token);
//                    authPreferences.setToken(token);
//                    doCoolAuthenticatedStuff();
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
