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
public class XmlReqsppb {

    private static final Logger logger = LoggerFactory.getLogger(XmlReqsppb.class);

    public List<Reqsppb> parseFile(String pathfile) {

        Document document;
        String varxml = "/DOCUMENT/";
        String varLoop = "/DOCUMENT/SPPB";
        SAXReader saxreader;
        List<Reqsppb> listSppb = new ArrayList<Reqsppb>();
        try {

            saxreader = new SAXReader();
            document = saxreader.read(new File(pathfile));
            Element root = document.getRootElement();
            //System.out.println(root.asXML());

            List listLoop = root.selectNodes(varLoop);

            for (int i = 0; i < listLoop.size(); i++) {
                Element eloop = (Element) listLoop.get(i);
                Reqsppb sppb = new Reqsppb();
                sppb.setTYPE(getString(eloop, "@tipe"));
                sppb.setNO_SPPB(getString(eloop, "NO_SPPB"));
                //2011-07-28 00:00:00.0
                String tempTgl = getString(eloop, "TGL_SPPB");

                if (tempTgl.length() > 9) {
                    tempTgl = tempTgl.substring(0, 10);
                    tempTgl = tempTgl.replaceAll("-", "");
                    sppb.setTGL_SPPB(tempTgl.substring(6, 8) + "-" + tempTgl.substring(4, 6) + "-" + tempTgl.substring(0, 4));
                } else {
                    sppb.setTGL_SPPB(tempTgl);
                }
                sppb.setNPWP_IMP(getString(eloop, "NPWP_IMP"));
                listSppb.add(sppb);
            }

        } catch (Exception e) {
            logger.error("on method : parseFile(file)  " + e.getMessage());
        }

        return listSppb;
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
    /*
    public static void main(String[] args) {
    //        XmlReqsppb req = new XmlReqsppb();
    //        List<Reqsppb> listsppb = req.parseFile("REQSPPB_07212011105617265.xml");
    //        System.out.println(listsppb.size());
    //        for (int i = 0; i < listsppb.size(); i++) {
    //            Reqsppb sppb = listsppb.get(i);
    //            System.out.println(sppb.getTYPE());
    //            System.out.println(sppb.getNO_SPPB());
    //            System.out.println(sppb.getTGL_SPPB());
    //            System.out.println(sppb.getNPWP_IMP());
    //        }
    //    File files = new File(".");
    //
    //    String file[] = files.list();
    //
    //    for (String File : file) {
    //    System.out.println(File);
    //    logger.info(File);
    //logger.error(File);
    //}
    String tempTgl = "20110728";
    System.out.println(tempTgl.substring(6,8)+"-"+tempTgl.substring(4,6)+"-"+tempTgl.substring(0,4));
    System.out.println("2011-07-28".replaceAll("-", ""));
    }
     */
}
