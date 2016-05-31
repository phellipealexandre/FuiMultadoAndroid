package com.manolosmobile.fuimultado.web;

import android.content.Context;

import com.manolosmobile.fuimultado.models.Bill;
import com.manolosmobile.fuimultado.models.Car;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DetranParserRN extends DetranParser {

    private final String SERVICE_URL = "http://www2.detran.rn.gov.br/servicos/consultaveiculo.asp";
    private final String PAGE_CHARSET = "ISO-8859-1";
    private final String UTF8 = "UTF-8";

    public DetranParserRN(Context context) {
        super(context);
    }

    @Override
    public EEstate getEstate() {
        return EEstate.RN;
    }

    @Override
    protected String extractHtmlFromPage(String plate, String renavam) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            plate = plate.replace("-","").toUpperCase();
            String data = URLEncoder.encode("placa", UTF8) + "=" + URLEncoder.encode(plate, UTF8);
            data += "&" + URLEncoder.encode("renavam", UTF8) + "=" + URLEncoder.encode(renavam, UTF8);
            data += "&" + URLEncoder.encode("btnConsultaPlaca", UTF8) + "=" + URLEncoder.encode("", UTF8);

            URL url = new URL(SERVICE_URL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), PAGE_CHARSET));
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }

            wr.close();
            rd.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return stringBuilder.toString();
    }

    @Override
    protected String getCarModel(Document doc) {
        final int MODEL_ROW_INDEX = 1;

        Element content = doc.getElementById("div_servicos_02");
        Element modelRow = content.getElementsByTag("tr").get(MODEL_ROW_INDEX);
        Element modelCell = modelRow.getElementsByTag("td").first();
        String model = modelCell.getElementsByTag("span").text();
        return model;
    }

    @Override
    protected Car fillCarWithBills(Document doc, Car car) {
        List<Bill> bills = new ArrayList<>();

        Element content = doc.getElementById("div_servicos_10");
        Elements assessmentRows = content.select("td");

        final int INITAL_INDEX = 4;
        for (int i=INITAL_INDEX; i<assessmentRows.size(); i += 9) {
            String assessmentCode = assessmentRows.get(i).text();
            String assessmentDescription = assessmentRows.get(i+3).text();
            String assessmentDate = assessmentRows.get(i+4).text();
            String assessmentLocation = assessmentRows.get(i+6).text();
            bills.add(new Bill(assessmentCode, true, car.getPlate(), assessmentDescription,
                    assessmentLocation, assessmentDate));
        }

        content = doc.getElementById("div_servicos_04");
        Elements billRows = content.select("td");

        for (int i=INITAL_INDEX; i<billRows.size(); i += 9) {
            String billCode = billRows.get(i).text();
            String billDescription = billRows.get(i+3).text();
            String billDate= billRows.get(i+4).text();
            String billLocation = billRows.get(i+6).text();
            bills.add(new Bill(billCode, false, car.getPlate(), billDescription, billLocation, billDate));
        }

        car.setBills(bills);
        return car;
    }

    @Override
    protected boolean hasErrorOccurred(Document doc) {
        return !doc.select("div.alert-danger").isEmpty();
    }
}
