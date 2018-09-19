package com.example.bruce.androidlifecycle.lifecyclelog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.bruce.androidlifecycle.R;

public class NotificationUtils {
	/**
	 * 新消息通知
	 * @param redirect 通知消息
	 */
    private static final String CHANNEL_ID = "changba_channel_01";
	public static void showNotification(Context context, String redirect, Bitmap largeIcon) {

		int messageId = 1;
		long when = System.currentTimeMillis();

		Intent resultIntent = new Intent(context, BruceFragmentActivity.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//		resultIntent.putExtra(BruceFragmentActivity.INTENT_NOTIFICATION, redirect);
//		resultIntent.setAction(NotificationBroadcastReceiver.ACTION_CLICK_BY_USER);

//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//		stackBuilder.addParentStack(BruceFragmentActivity.class);
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(messageId, PendingIntent.FLAG_UPDATE_CURRENT);

//		Intent intentCancel = new Intent(context, NotificationBroadcastReceiver.class);
//		intentCancel.setAction(NotificationBroadcastReceiver.ACTION_CANCEL_BY_USER);
//		intentCancel.putExtra(NotificationBroadcastReceiver.TYPE, 0);
//		PendingIntent cancelIntent = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);

		PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        createNotificationChannel(context, CHANNEL_ID);

        String contentText = redirect;
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_launcher)
//				.setLargeIcon(largeIcon)
				.setContentTitle(redirect)
				.setTicker(contentText)
				.setContentText(contentText)
				.setContentIntent(notifyPendingIntent)
//				.setDeleteIntent(cancelIntent)
				.setWhen(when)
//				.setSound(getNotificationSound(redirect, when))
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setStyle(new NotificationCompat.BigTextStyle()
						.bigText(contentText))
				.setAutoCancel(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			builder.setVisibility(Notification.VISIBILITY_PUBLIC);//用于锁屏时显示通知
		}

        NotificationManagerCompat.from(context).notify(messageId, builder.build());//messageId相同时更新通知
	}

    private static void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                CharSequence name = "唱吧";
                String description = "通知";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                channel.setShowBadge(true);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
