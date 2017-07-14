package com.shail.acracrashreportsending;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.HashMap;


/**
 * Created by Shailendra Singh on 11-Jul-17.
 * iTexico
 * ssingh@itexico.net
 */

@ReportsCrashes(
        //Toast notification
        mode = ReportingInteractionMode.DIALOG,

//        resToastText = R.string.crash_toast_text,
//        resDialogOkToast = R.string.crash_dialog_ok_toast, // optional. displays a Toast message when the user accepts to send a report.
        //Dialog
        reportDialogClass = CustomCrashReportDialog.class,
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogPositiveButtonText = R.string.crash_dialog_positive_button_title,
        resDialogNegativeButtonText = R.string.crash_dialog_negative_button_title,
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. When defined, adds a user text field input with this text resource as a label
        resDialogEmailPrompt = R.string.crash_user_email_label, // optional. When defined, adds a user email text entry with this text resource as label. The email address will be populated from SharedPreferences and will be provided as an ACRA field if configured.
        resDialogTheme = R.style.AppTheme_Dialog,//optional. default is Theme.Dialog
        mailTo = "ssingh@itexico.net",
        customReportContent = {ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT},
        reportSenderFactoryClasses = {EmailReportSenderFactory.class}

)
public class MyApplication extends Application {

    private static MyApplication MY_APPLICATION;
    private AuthPreferences mAuthPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        MY_APPLICATION = this;
        mAuthPreferences = new AuthPreferences(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }

    public static MyApplication getInstance() {
        return MY_APPLICATION;
    }

    public AuthPreferences getAuthPreferences() {
        return mAuthPreferences;
    }
}
