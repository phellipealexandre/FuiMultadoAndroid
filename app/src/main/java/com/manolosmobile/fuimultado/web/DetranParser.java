package com.manolosmobile.fuimultado.web;

import android.content.Context;
import android.os.AsyncTask;

import com.manolosmobile.fuimultado.callbacks.DetranCallbackResult;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnDetranParseFinishedCallback;
import com.manolosmobile.fuimultado.models.Car;
import com.manolosmobile.fuimultado.utils.InternetUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class DetranParser {

    private Context context;

    public DetranParser(Context context) {
        this.context = context;
    }

    public abstract EEstate getEstate();
    protected abstract String extractHtmlFromPage(String plate, String renavam);
    protected abstract String getCarModel(Document doc);
    protected abstract Car fillCarWithBills(Document doc, Car car);
    protected abstract boolean hasErrorOccurred(Document doc);

    public void getCarInfoFromWeb(final String plate, final String renavam, final OnDetranParseFinishedCallback callback) {
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {
                String plate = params[0];
                String renavam = params[1];
                String html = null;

                if(InternetUtils.isConnected(context)) {
                    html = extractHtmlFromPage(plate, renavam);
                }

                return html;
            }

            @Override
            protected void onPostExecute(String html) {
                super.onPostExecute(html);

                if (html == null || html.isEmpty()) {
                    callback.onFinish(new DetranCallbackResult("Não foi possível buscar as informações na internet, por favor verifique sua conexão"));
                } else {
                    Document htmlDoc = Jsoup.parse(html);
                    processHtml(htmlDoc, plate, renavam, callback);
                }
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, plate, renavam);
    }

    private void processHtml(Document doc, String plate, String renavam, OnDetranParseFinishedCallback callback) {
        if (hasErrorOccurred(doc)) {
            callback.onFinish(new DetranCallbackResult("Não foi possível encontrar a combinação placa/renavam"));
        } else {
            String model = getCarModel(doc);
            String estateName = getEstate().name();

            Car car = new Car(plate, renavam, estateName, model);
            car = fillCarWithBills(doc, car);
            callback.onFinish(new DetranCallbackResult(car));
        }
    }
}
