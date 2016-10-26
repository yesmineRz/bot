package io.oilfox.backend.api.controllers;

import io.oilfox.backend.api.dto.DtoLineChart;
import io.oilfox.backend.api.http.AbstractController;
import io.oilfox.backend.api.shared.annotations.*;

import javax.imageio.ImageIO;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import sun.awt.image.OffScreenImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by ipusic on 1/22/16.
 */
@ApiDocController
public class ChartController extends AbstractController{



    @Produces("image/png")
    @ApiDocDescription("draws the chart and uploads it on server, under /getchart")
    public Response getLineChart() throws IOException {




        File f = new File("chart147852369.png");


        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( 1400 , "€" , "August" );
        dataset.addValue( 3200 , "€" , "September" );
        dataset.addValue( 2000 , "€" ,  "October" );
        dataset.addValue( 3800 , "€" , "November" );


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
    public Response uuu() throws Exception {


        String message = "[  \n" +
                "  {\n" +
                "    \"attachment\": {\n" +
                "      \"type\": \"image\",\n" +
                "      \"payload\": {\n" +
                "        \"url\": \"https://mantro-bot-api.herokuapp.com/getchart\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";


        return json(message);
    }
}
