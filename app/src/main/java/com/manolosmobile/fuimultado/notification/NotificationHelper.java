package com.manolosmobile.fuimultado.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.manolosmobile.fuimultado.MainActivity;
import com.manolosmobile.fuimultado.R;
import com.manolosmobile.fuimultado.models.Car;

import java.util.Random;

public class NotificationHelper {

    private static final String NOTIF_TITLE = "Fui Multado?";

    public static void notificateCarUpdate(Context context, Car currentCar, Car updatedCar) {
        if (currentCar.getBills().size() < updatedCar.getBills().size()) {
            String text = String.format("O carro de modelo %s e placa %s recebeu uma nova autuação",
                    updatedCar.getModel(), updatedCar.getPlate());
            NotificationHelper.createNotification(context, text);
        } else if (currentCar.getBills().size() > updatedCar.getBills().size()) {
            String text = String.format("O carro de modelo %s e placa %s teve seu cadastro de multas atualizado",
                    updatedCar.getModel(), updatedCar.getPlate());
            NotificationHelper.createNotification(context, text);
        }
    }

    private static void createNotification(Context context, String text) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notification_car)
                        .setContentTitle(NOTIF_TITLE)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentText(text)
                        .setContentIntent(resultPendingIntent);

        //Randomize id to generate different notifications
        int notificationId = new Random().nextInt(Integer.MAX_VALUE);
        NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(notificationId, builder.build());
    }
}
