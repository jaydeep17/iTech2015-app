package com.pingbits.itchack;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HtmlParser {


    public static String parse(String html) {
//        String html = "<HTML><HEAD><TITLE>ThingWorx&#x3a; Thing com.thingworx.things.ConfiguredThing&#x40;7d3aa373 &#x3a; Response From Execution Of abc</TITLE><LINK rel='Stylesheet' href='/Thingworx/css/thingworxapi.css' type='text/css'></LINK><META http-equiv='Content-Type' content='text/html'></META><META http-equiv='cache-control' content='no-cache, no-store'></META><META http-equiv='expires' content='-1'></META><META http-equiv='pragma' content='no-cache, no-store'></META></HEAD><BODY><IMG SRC=\"/Thingworx/images/ThingworxLogo.png\"/><BR/><H1>abc</H1><TABLE><TR><TH>result</TH></TR><TR><TD>true</TD></TR></TABLE></BODY></HTML>";
        Document doc = Jsoup.parse(html);
        Elements td = doc.select("td");
        return td.text();
    }


}
