package com.shail.acracrashreportsending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfiguration;
import org.acra.model.Element;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Shailendra Singh on 11-Jul-17.
 * iTexico
 * ssingh@itexico.net
 */

public class EmailReportSender implements ReportSender {

    private static final String TAG = EmailReportSender.class.getSimpleName();
    private final ACRAConfiguration mACRAConfiguration;

    private GMailSender mMailSender;

    public EmailReportSender(@NonNull Context context, @NonNull ACRAConfiguration config) {
        mACRAConfiguration = config;
        mMailSender = new GMailSender(context);
        Log.i(TAG, "EmailReportSender():" + context);
    }

    @Override
    public void send(@NonNull Context context, @NonNull CrashReportData report) throws ReportSenderException {

//        parameters.add(new BasicNameValuePair("BRAND", report.get(ReportField.BRAND)));
//        parameters.add(new BasicNameValuePair("PRODUCT", report.get(ReportField.PRODUCT)));
//        parameters.add(new BasicNameValuePair("TOTAL_MEM_SIZE", report.get(ReportField.TOTAL_MEM_SIZE)));
//        parameters.add(new BasicNameValuePair("AVAILABLE_MEM_SIZE", report.get(ReportField.AVAILABLE_MEM_SIZE)));
//        parameters.add(new BasicNameValuePair("CUSTOM_DATA", report.get(ReportField.CUSTOM_DATA)));
//        parameters.add(new BasicNameValuePair("STACK_TRACE", report.get(ReportField.STACK_TRACE)));
//        parameters.add(new BasicNameValuePair("INITIAL_CONFIGURATION", report.get(ReportField.INITIAL_CONFIGURATION)));
//        parameters.add(new BasicNameValuePair("CRASH_CONFIGURATION", report.get(ReportField.CRASH_CONFIGURATION)));
//        parameters.add(new BasicNameValuePair("DISPLAY", report.get(ReportField.DISPLAY)));
//        parameters.add(new BasicNameValuePair("USER_COMMENT", report.get(ReportField.USER_COMMENT)));
//        parameters.add(new BasicNameValuePair("USER_APP_START_DATE", report.get(ReportField.USER_APP_START_DATE)));
//        parameters.add(new BasicNameValuePair("USER_CRASH_DATE", report.get(ReportField.USER_CRASH_DATE)));
//        parameters.add(new BasicNameValuePair("DUMPSYS_MEMINFO", report.get(ReportField.DUMPSYS_MEMINFO)));
//        parameters.add(new BasicNameValuePair("DROPBOX", report.get(ReportField.DROPBOX)));
//        parameters.add(new BasicNameValuePair("LOGCAT", report.get(ReportField.LOGCAT)));
//        parameters.add(new BasicNameValuePair("EVENTSLOG", report.get(ReportField.EVENTSLOG)));
//        parameters.add(new BasicNameValuePair("RADIOLOG", report.get(ReportField.RADIOLOG)));
//        parameters.add(new BasicNameValuePair("IS_SILENT", report.get(ReportField.IS_SILENT)));
//        parameters.add(new BasicNameValuePair("DEVICE_ID", report.get(ReportField.DEVICE_ID)));
//        parameters.add(new BasicNameValuePair("INSTALLATION_ID", report.get(ReportField.INSTALLATION_ID)));
//        parameters.add(new BasicNameValuePair("USER_EMAIL", report.get(ReportField.USER_EMAIL)));
//        parameters.add(new BasicNameValuePair("DEVICE_FEATURES", report.get(ReportField.DEVICE_FEATURES)));
//        parameters.add(new BasicNameValuePair("ENVIRONMENT", report.get(ReportField.ENVIRONMENT)));
//        parameters.add(new BasicNameValuePair("SETTINGS_SYSTEM", report.get(ReportField.SETTINGS_SYSTEM)));
//        parameters.add(new BasicNameValuePair("SETTINGS_SECURE", report.get(ReportField.SETTINGS_SECURE)));
//        parameters.add(new BasicNameValuePair("SHARED_PREFERENCES", report.get(ReportField.SHARED_PREFERENCES)));
//        parameters.add(new BasicNameValuePair("APPLICATION_LOG", report.get(ReportField.APPLICATION_LOG)));
//        parameters.add(new BasicNameValuePair("MEDIA_CODEC_LIST", report.get(ReportField.MEDIA_CODEC_LIST)));
//        parameters.add(new BasicNameValuePair("THREAD_DETAILS", report.get(ReportField.THREAD_DETAILS)));

        String user = MyApplication.getInstance().getAuthPreferences().getUser();
        Log.i(TAG, "send():" + "context:" + context);

        JSONObject jsonObject = report.toJSON();
        StringBuilder messageBodyStringBuilder = new StringBuilder();
        try {
            List<String> flatten = JsonUtils.flatten(jsonObject);
            for (String entry : flatten) {
                messageBodyStringBuilder.append(entry);
                messageBodyStringBuilder.append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String messageBody = messageBodyStringBuilder.toString();
        String recepientEmail = mACRAConfiguration.mailTo();

        Log.i(TAG, "send(),:" + ",mMailSender.getAccountEmailID():" + mMailSender.getAccountEmailID() +
                ",mMailSender.getToken():" + mMailSender.getToken() + ",recepientEmail:" + recepientEmail);

        mMailSender.sendMail("GoTrashy Crash Info", messageBody, mMailSender.getAccountEmailID(), mMailSender.getToken(), recepientEmail);
    }

    private String createCrashReport(CrashReportData crashReportData) {
        StringBuilder body = new StringBuilder();
        body.append("Device : " + crashReportData.getProperty(ReportField.BRAND) + " - " + crashReportData.getProperty(ReportField.PHONE_MODEL))
                .append("\n")
                .append("Android Version : " + crashReportData.getProperty(ReportField.ANDROID_VERSION))
                .append("\n")
                .append("App Version : " + crashReportData.getProperty(ReportField.APP_VERSION_CODE))
                .append("\n")
                .append("STACK TRACE : \n" + crashReportData.getProperty(ReportField.STACK_TRACE));
        return body.toString();
    }
}
