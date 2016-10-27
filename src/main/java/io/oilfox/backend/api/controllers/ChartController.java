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
import java.util.ArrayList;


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
    public Response getCurrentBalance(@PathParam("id") String id) throws Exception {

        String currentBalance = null;
        String message = "[{\"text\":\"Unable to retrieve your account balance\"}]";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

         for(int i = 0; i < jarr.length(); i++) {
             JSONObject user = jarr.getJSONObject(i);
             if(user.getString("fb_id").equals(id)){
                 currentBalance = user.getString("current_balance");
                 message = "[{\"text\":\"Your current account balance is: "+currentBalance+"\"}]";
             }


         }

        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getPreviousBalanceWithNumbers(@PathParam("id") String id) throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the last months\"}]";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

        for(int i = 0; i < jarr.length(); i++) {
            JSONObject user = jarr.getJSONObject(i);
            if(user.getString("fb_id").equals(id)){
                JSONArray previous = new JSONArray(user.getJSONArray("balance_previous").toString());

                message = "[{\"text\":\"Your previous account balance is:";
                for(int j = 0; i < previous.length(); i++) {
                    String month = previous.getJSONObject(i).getString("month").toString();
                    String value = previous.getJSONObject(i).getString("value").toString();
                    message += "\n" + month +": " + value + "€\n";
                }
                message += "\"}]";
            }
        }

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
    public Response getFutureBalanceWithNumbers(@PathParam("id") String id) throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the future months\"}]";

        String path = new File("./data.json").getAbsolutePath();


        String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);


        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

        for(int i = 0; i < jarr.length(); i++) {
            JSONObject user = jarr.getJSONObject(i);
            if(user.getString("fb_id").equals(id)){
                JSONArray previous = new JSONArray(user.getJSONArray("balance_future").toString());

                message = "[{\"text\":\"Your future account balance is:";
                for(int j = 0; i < previous.length(); i++) {
                    String month = previous.getJSONObject(i).getString("month").toString();
                    String value = previous.getJSONObject(i).getString("value").toString();
                    message += "\n" + month +": " + value + "€\n";
                }
                message += "\"}]";
            }
        }

        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getPreviousBalanceWithChart(@PathParam("id") String id) throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the last months\"}]";
        String url = "https://mantro-bot-api.herokuapp.com/getchart";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

        for(int i = 0; i < jarr.length(); i++) {
            JSONObject user = jarr.getJSONObject(i);
            if(user.getString("fb_id").equals(id)){
                JSONArray previous = new JSONArray(user.getJSONArray("balance_previous").toString());

                //message = "[{\"text\":\"Your previous account balance is:";
                for(int j = 0; i < previous.length(); i++) {
                    String month = previous.getJSONObject(i).getString("month").toString();
                    String value = previous.getJSONObject(i).getString("value").toString();
                    url += "/" + month + "/" + value;
                    //message += "\n" + month +": " + value + "€\n";
                }
                url += "\"\n";
                //message += "\"}]";
            }
        }


            message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"" + url +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";



        return Response.ok(message).status(200).build();
    }

    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getFutureBalanceWithChart(@PathParam("id") String id) throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your account balance for the future months\"}]";
        String url = "https://mantro-bot-api.herokuapp.com/getchart";


        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

        for(int i = 0; i < jarr.length(); i++) {
            JSONObject user = jarr.getJSONObject(i);
            if(user.getString("fb_id").equals(id)){
                JSONArray previous = new JSONArray(user.getJSONArray("balance_future").toString());

                //message = "[{\"text\":\"Your previous account balance is:";
                for(int j = 0; i < previous.length(); i++) {
                    String month = previous.getJSONObject(i).getString("month").toString();
                    String value = previous.getJSONObject(i).getString("value").toString();
                    url += "/" + month + "/" + value;
                    //message += "\n" + month +": " + value + "€\n";
                }
                url += "\"\n";
                //message += "\"}]";
            }
        }


        message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"" + url +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";



        return Response.ok(message).status(200).build();
    }



    @Authenticated
    @Produces("text/plain")
    @ApiDocDescription("test json")
    public Response getLastTransactions(@PathParam("id") String id) throws Exception {

        String message = "[{\"text\":\"Unable to retrieve your last transactions\"}]";



        //String text = new String(Files.readAllBytes(Paths.get("data.json")), StandardCharsets.UTF_8);
        String text = readFile("data.json");

        JSONObject jobj = new JSONObject(text);


        JSONArray jarr = new JSONArray(jobj.getJSONArray("users").toString());

        for(int i = 0; i < jarr.length(); i++) {
            JSONObject user = jarr.getJSONObject(i);
            if(user.getString("fb_id").equals(id)){
                JSONArray previous = new JSONArray(user.getJSONArray("recent_transactions").toString());

                message = "[{\"text\":\"Your previous last transactions are:";
                for(int j = 0; i < previous.length(); i++) {
                    String date = previous.getJSONObject(i).getString("date").toString();
                    String type = previous.getJSONObject(i).getString("type").toString();
                    String amount = previous.getJSONObject(i).getString("amount").toString();
                    String description = previous.getJSONObject(i).getString("type").toString();
                    message += "\n" + date +": " + type + " - "+amount+"€ - description: "+description+"\n";
                }

                message += "\"}]";
            }
        }


        return Response.ok(message).status(200).build();
    }
}
