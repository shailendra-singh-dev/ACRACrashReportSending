package com.shail.acracrashreportsending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.acra.config.ACRAConfiguration;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderFactory;


/**
 * Created by Shailendra Singh on 11-Jul-17.
 * iTexico
 * ssingh@itexico.net
 */
public class EmailReportSenderFactory implements ReportSenderFactory {

    private static final String TAG = EmailReportSenderFactory.class.getSimpleName();

    @NonNull
    @Override
    public ReportSender create(@NonNull Context context, @NonNull ACRAConfiguration config) {
        Log.i(TAG, "create():" + context + ",config:" + config);
        return new EmailReportSender(context, config);
    }
}
