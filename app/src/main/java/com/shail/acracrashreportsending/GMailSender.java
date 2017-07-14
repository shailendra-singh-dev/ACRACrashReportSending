package com.shail.acracrashreportsending;

/**
 * Created by Shailendra Singh on 12-Jul-17.
 * iTexico
 * ssingh@itexico.net
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;


public class GMailSender {

    public static final String AUTHTOKEN_TYPE = "oauth2:https://mail.google.com/";
    public static final String ACCOUNT_TYPE = "com.google";
    private Session session;
    private String token;
    private String mAccountEmailID;


    public String getToken() {
        return token;
    }

    public GMailSender(Context ctx) {
        super();
        initToken(ctx);
    }

    public void initToken(Context ctx) {

        AccountManager accountManager = AccountManager.get(ctx);

        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        for (Account account : accounts) {
            Log.d("AuthToken", "account=" + account);
        }

        Account account = accounts[0]; //You need to get a google account on the device, it changes if you have more than one
        Log.d("AuthToken", "account=" + account);

        mAccountEmailID = account.name;

        Log.d("AuthToken", "token=" + token);
//        accountManager.invalidateAuthToken(ACCOUNT_TYPE, token);
        accountManager.getAuthToken(account, "oauth2:https://mail.google.com/", null, true, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> result) {
                try {
                    Bundle bundle = result.getResult();
                    token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                    Log.d("AuthToken(),callback", "token=" + token);

                } catch (Exception e) {
                    Log.d("AuthToken(),Exception", e.getMessage());
                }
            }
        }, null);

    }


    public SMTPTransport connectToSmtp(String host, int port, String userEmail, String oauthToken, boolean debug) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.sasl.enable", "false");

        session = Session.getInstance(props);
        session.setDebug(debug);

        final URLName unusedUrlName = null;
        SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
        // If the password is non-null, SMTP tries to do AUTH LOGIN.
        final String emptyPassword = null;

        /* enable if you use this code on an Activity (just for test) or use the AsyncTask
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
         */

        transport.connect(host, port, userEmail, emptyPassword);

        byte[] response = String.format("user=%s\1auth=Bearer %s\1\1",
                userEmail, oauthToken).getBytes();
        response = BASE64EncoderStream.encode(response);

        transport.issueCommand("AUTH XOAUTH2 " + new String(response), 235);

        return transport;
    }

    public synchronized void sendMail(String subject, String body, String user, String oauthToken, String recipients) {
        try {
            SMTPTransport smtpTransport = connectToSmtp("smtp.gmail.com", 587, user, oauthToken, true);

            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(
                    body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(user));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipients));
            smtpTransport.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            Log.d("sendMail(),Exception:", e.getMessage(), e);
        }
    }

    public Account[] getGoogleAccounts(Activity ctx) {
        AccountManager acctManager = AccountManager.get(ctx);
        return acctManager.getAccountsByType("com.google");
    }

    public String getAccountEmailID() {
        return mAccountEmailID;
    }
}

