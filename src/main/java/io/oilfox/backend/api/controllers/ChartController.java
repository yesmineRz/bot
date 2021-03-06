package io.oilfox.backend.api.controllers;

import io.oilfox.backend.Main;
import io.oilfox.backend.api.dto.DtoLineChart;
import io.oilfox.backend.api.http.AbstractController;
import io.oilfox.backend.api.shared.annotations.*;

import javax.imageio.ImageIO;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.oilfox.backend.api.shared.mapper.ObjectMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import sun.awt.image.OffScreenImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by ipusic on 1/22/16.
 */
@ApiDocController
public class ChartController extends AbstractController{



    @Produces("image/png")
    @ApiDocDescription("draws the chart and uploads it on server, under /getchart")
    public Response getLineChart(@PathParam("month1") String month1, @PathParam("value1") String value1, @PathParam("month2") String month2, @PathParam("value2") String value2, @PathParam("month3") String month3, @PathParam("value3") String value3) throws IOException {




        File f = new File("chart147852369.png");


        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( Integer.parseInt(value1) , "€" , month1 );
        dataset.addValue( Integer.parseInt(value2) , "€" , month2 );
        dataset.addValue( Integer.parseInt(value3) , "€" ,  month3 );


        JFreeChart lineChart = ChartFactory.createLineChart(
                "Account chart",
                "Month","Balance",
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartUtilities.saveChartAsPNG(f, lineChart, 560 , 367);






        BufferedImage img = ImageIO.read(f);


        return Response.ok(img).status(200).build();
    }

    @Produces("image/png")
    @ApiDocDescription("draws the chart")
    public Response getStaticChart(@PathParam("type") String type) throws IOException {


        BufferedImage img = null;
        try {
            if(type.equals("1")){
                img = ImageIO.read(new File("forecast_chart.png"));
            }else if(type.equals("2")){
                img = ImageIO.read(new File("report_chart.png"));
            }

            System.out.print("+++");
        } catch (IOException e) {
            System.out.print(e.toString());
        }
        return Response.ok(img).status(200).build();
    }




    @Authenticated
    @Produces("application/json")
    @ApiDocDescription("uuuuuuuu")
    public Response uuu(@PathParam("month1") String month1, @PathParam("value1") String value1, @PathParam("month2") String month2, @PathParam("value2") String value2, @PathParam("month3") String month3, @PathParam("value3") String value3) throws Exception {


        String message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"https://mantro-bot-api.herokuapp.com/getchart/"+month1+"/"+value1+"/"+month2+"/"+value2+"/"+month3+"/"+value3+"\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";


        return json(message);
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response accessJson() throws Exception {



        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");



        JSONObject jobj = new JSONObject(text);

        JSONArray jarr = new JSONArray(jobj.getJSONArray("keywords").toString());
        String x = jobj.getString("name");

       // for(int i = 0; i < jarr.length(); i++) {
         //   System.out.println("Keyword: " + jarr.getString(i));
       // }

        return Response.ok(jarr.getString(0)).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getCurrentBalance() throws Exception {

        String currentBalance = null;
        String message = "[{\"text\":\"Unable to retrieve your account balance\"}]";

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);
        currentBalance = jobj.getString("current_balance");
        message = "[{\"text\":\"Your current balance is " + currentBalance + " EUR\"}]";

        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getPreviousBalanceWithNumbers() throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the last months\"}]";

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);
        JSONArray previous = new JSONArray(jobj.getJSONArray("balance_previous").toString());

        message = "[{\"text\":\"Here is your account balance for the last 3 months :\\n";
        for (int j = 0; j < previous.length(); j++) {
            String month = previous.getJSONObject(j).getString("month").toString();
            String value = previous.getJSONObject(j).getString("value").toString();
            message += "\\n" + month + ": " + value + " EUR\\n";
        }
        message += "\"}]";


        return Response.ok(message).status(200).build();
    }

    public String readFile(String filename) throws IOException {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader !=null){reader.close();}
        }
        return content;
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getFutureBalanceWithNumbers() throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the future months\"}]";

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);

        JSONArray previous = new JSONArray(jobj.getJSONArray("balance_future").toString());

        message = "[{\"text\":\"Here is your balance forecast for the next 3 months:\\n";
        for (int j = 0; j < previous.length(); j++) {
            String month = previous.getJSONObject(j).getString("month").toString();
            String value = previous.getJSONObject(j).getString("value").toString();
            message += "\\n" + month + ": " + value + " EUR\\n";
        }
        message += "\"}]";

        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getPreviousBalanceWithChart() throws Exception {
        String message = "[{\"text\":\"Unable to retrieve your account balance for the last months\"}]";
        String url = "https://mantro-bot-api.herokuapp.com/getchart";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


                JSONArray previous = new JSONArray(jobj.getJSONArray("balance_previous").toString());

                //message = "[{\"text\":\"Your previous account balance is:";
                for(int j = 0; j < previous.length(); j++) {
                    String month = previous.getJSONObject(j).getString("month").toString();
                    String value = previous.getJSONObject(j).getString("value").toString();
                    String x = value.replace(".","");
                    String v = x.replace(",","");
                    url += "/" + month + "/" + v;
                    //message += "\n" + month +": " + value + "€\n";
                }
                url += "\"\n";
                //message += "\"}]";

            getStaticChart("2");
            String staticUrl = "https://mantro-bot-api.herokuapp.com/getstaticchart/2";

         staticUrl += "\"\n";
            message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"" + staticUrl +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";
/*

        message = "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"attachment\": {\n" +
                "        \"type\": \"image\",\n" +
                "        \"payload\": {\n" +
                "          \"url\": \""+url +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";*/


        return Response.ok(message).status(200).build();

    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getFutureBalanceWithChart() throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the future months\"}]";
        String url = "https://mantro-bot-api.herokuapp.com/getchart";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);



                JSONArray previous = new JSONArray(jobj.getJSONArray("balance_future").toString());

                //message = "[{\"text\":\"Your previous account balance is:";
                for(int j = 0; j < previous.length(); j++) {
                    String month = previous.getJSONObject(j).getString("month").toString();
                    String value = previous.getJSONObject(j).getString("value").toString();
                    String v = value.replace(".","");
                    url += "/" + month + "/" + v;
                    //message += "\n" + month +": " + value + "€\n";
                }
                url += "\"\n";
                //message += "\"}]";


        getStaticChart("1");
        String staticUrl = "https://mantro-bot-api.herokuapp.com/getstaticchart/1";

        staticUrl += "\"\n";
        message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"" + staticUrl +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";


        return Response.ok(message).status(200).build();
    }



    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getLastTransactions() throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your last transactions\"}]";



        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);



                JSONArray previous = new JSONArray(jobj.getJSONArray("recent_transactions").toString());

                message = "[{\"text\":\"Here are your recent transactions:\\n";
                for(int j = 0; j < previous.length(); j++) {
                    String date = previous.getJSONObject(j).getString("date").toString();
                    String type = previous.getJSONObject(j).getString("type").toString();
                    String amount = previous.getJSONObject(j).getString("amount").toString();
                    String description = previous.getJSONObject(j).getString("type").toString();
                    message += amount +" EUR for " + type;
                    if(j==(previous.length() -1)){
                        message += ".";
                    }else {
                        message += ", ";
                    }
                }

                message += "\"}]";



        return Response.ok(message).status(200).build();
    }



    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getMorningMessage(@PathParam("name") String name) throws Exception {

        String message = "[{\"text\":\"Good morning " + name + "!\\n";
        message += "Everything is fine.\\n";
        String currentBalance = "";
        String overdraft = "";
        String scheduled_payment = "";
        Boolean foundUser = false;

        DateFormat dateFormat = new SimpleDateFormat("MMM dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);

        foundUser = true;
        currentBalance = jobj.getString("current_balance");
        overdraft = jobj.getString("overdraft_facility");
        scheduled_payment = jobj.getString("scheduled_payment");
        message += "Here is your personal financial overview for " + currentDate + ".\\nYou have " + currentBalance + " EUR on your current account and an overdraft facility of " + overdraft + " EUR. Today you a have a scheduled outbound payment of " + scheduled_payment + " EUR for rent. You will be fine.\\nHave a nice day.\"}]";

        if(!foundUser){
            message += "\"}]";
        }

        return Response.ok(message).status(200).build();
    }


    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getBriefing(@PathParam("name") String name) throws Exception {

        String message = "[{\"text\":\"Everything is fine " + name + "!\\n";
        String currentBalance = "";
        String overdraft = "";
        String scheduled_payment = "";
        Boolean foundUser = false;

        DateFormat dateFormat = new SimpleDateFormat("MMM dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);

        foundUser = true;
        currentBalance = jobj.getString("current_balance");
        overdraft = jobj.getString("overdraft_facility");
        scheduled_payment = jobj.getString("scheduled_payment");
        message += "Here is your personal financial overview for " + currentDate + ".\\nYou have " + currentBalance + " EUR on your current account and an overdraft facility of " + overdraft + " EUR. Today you a have a scheduled outbound payment of " + scheduled_payment + " EUR for rent. You will be fine.\\nHave a nice day.\"}]";

        if(!foundUser){
            message += "\"}]";
        }

        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getFutureBalanceNextMonth() throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the next month\"}]";

        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");


        JSONObject jobj = new JSONObject(text);


        JSONArray previous = new JSONArray(jobj.getJSONArray("balance_future").toString());

        message = "[{\"text\":\"";

        String month = previous.getJSONObject(0).getString("month").toString();
        String value = previous.getJSONObject(0).getString("value").toString();
        message = "[{\"text\":\"Based on your regular in- and outflows you will have " + value + " EUR\"}]";


        message += "\"}]";


        return Response.ok(message).status(200).build();
    }


    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getReminder(@PathParam("delay") String delay, @PathParam("type") String type) throws Exception {

        if(delay.equals("1")){
            TimeUnit.SECONDS.sleep(25);

            Response r = null;

            if(type.equals("1")){
                r = getPreviousBalanceWithNumbers();
            }else if(type.equals("2")){
                r = getPreviousBalanceWithChart();
            }else if(type.equals("3")){
                r = getFutureBalanceWithNumbers();
            }else if(type.equals("4")){
                r = getFutureBalanceWithChart();
            }else if(type.equals("5")){
                String message = "[{\"text\":\"Reminder: Please check your payments\"}]";
                r = Response.ok(message).status(200).build();
            }else if(type.equals("6")){
                r = getLastTransactions();
            }

            return r;
        }else{
            //String message = "[{\"text\":\"Done\"}]";
            return null;
        }
    }
}
