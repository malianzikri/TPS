/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edii.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aldi
 */
public class XmlReqpeks {

    private static final Logger logger = LoggerFactory.getLogger(XmlReqpeks.class);

    public Reqpeks parseFile(String pathfile) {

        Document document;
        String varxml = "/PE/";
        SAXReader saxreader;
        Reqpeks reqpeks = new Reqpeks();
        try {
            saxreader = new SAXReader();
            document = saxreader.read(new File(pathfile));
            Element root = document.getRootElement();
            reqpeks.setBeda_kantor(getString(root, "@beda_kantor"));
            reqpeks.setNO_PEB(getString(root, "NO_PEB"));
            String tempTgl = getString(root, "TGL_PEB");
            if (tempTgl.length() > 9) {
                tempTgl = tempTgl.substring(0, 10);
                tempTgl = tempTgl.replaceAll("/", "");
                reqpeks.setTGL_PEB(tempTgl.substring(0, 2) + "-" + tempTgl.substring(2, 4) + "-" + tempTgl.substring(4, 8));
            } else {
                reqpeks.setTGL_PEB(tempTgl);
            }

            reqpeks.setKPBC(getString(root, "KPBC"));
        } catch (Exception e) {
            logger.error("on method : parseFile(file)  " + e.getMessage());
        }

        return reqpeks;
    }

    public String getString(Element el, String tag) {
        String result = "";
        try {
            result = el.selectSingleNode(tag).getText();
        } catch (NullPointerException npe) {
            logger.error(" on method : getString(el, tag)  " + npe.getMessage());
            logger.error(" Check xml tag fot tag  " + tag + "in element " + el.asXML());
        } catch (Exception e) {
            logger.error(" on method : getString(el, tag)  " + e.getMessage());
        }
        return result;
    }

//    public static void main(String[] args) {
//                XmlReqsppb req = new XmlReqsppb();
//                List<Reqsppb> listsppb = req.parseFile("REQSPPB_07212011105617265.xml");
//                System.out.println(listsppb.size());
//                for (int i = 0; i < listsppb.size(); i++) {
//                    Reqsppb sppb = listsppb.get(i);
//                    System.out.println(sppb.getTYPE());
//                    System.out.println(sppb.getNO_SPPB());
//                    System.out.println(sppb.getTGL_SPPB());
//                    System.out.println(sppb.getNPWP_IMP());
//                }

//        XmlReqpeks req = new XmlReqpeks();
//        Reqpeks reqpeks = req.parseFile("REQPEKS_201108240001.XML");
//        System.out.println(reqpeks.getBeda_kantor());
//        System.out.println(reqpeks.getNO_PEB());
//        System.out.println(reqpeks.getTGL_PEB());
//        System.out.println(reqpeks.getKPBC());

        //    File files = new File(".");
        //
        //    String file[] = files.list();
        //
        //    for (String File : file) {
        //    System.out.println(File);
        //    logger.info(File);
        //logger.error(File);
        //}
//        String tempTgl = "20110728";
//        System.out.println(tempTgl.substring(6, 8) + "-" + tempTgl.substring(4, 6) + "-" + tempTgl.substring(0, 4));
//        System.out.println("2011-07-28".replaceAll("-", ""));
//    }
}
