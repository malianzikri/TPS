package com.edii.tps.service;

import id.go.beacukai.services.TPSServices;
import id.go.beacukai.services.TPSServicesSoap;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;

public class Services {

    private final static QName TPSSERVICES_QNAME = new QName("http://services.beacukai.go.id/", "TPSServices");
    static String WSDL_URL = "https://tpsonline.beacukai.go.id/tps/service.asmx?wsdl";

    public static String cekDataTerkirim(java.lang.String userName, java.lang.String password, java.lang.String tglAwal, java.lang.String tglAkhir) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.cekDataTerkirim(userName, password, tglAwal, tglAkhir);
    }

    public static String cekDataGagalKirim(java.lang.String userName, java.lang.String password, java.lang.String tglAwal, java.lang.String tglAkhir) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.cekDataGagalKirim(userName, password, tglAwal, tglAkhir);
    }

    private static TPSServicesSoap createProxy(String alamatWsdl) {
        TPSServicesSoap port = null;
        TPSServices service = null;
       
        if (alamatWsdl != null && alamatWsdl.length() > 0) {
            try {
                service = new TPSServices(new URL(alamatWsdl), TPSSERVICES_QNAME);
                port = service.getTPSServicesSoap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            service = new TPSServices();
            port = service.getTPSServicesSoap();
        }
        return port;
    }
    
    public static String getImporSppb(java.lang.String userName, java.lang.String password, java.lang.String noSppb, java.lang.String tglSppb, java.lang.String npwpImp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getImporSppb(userName, password, noSppb, tglSppb, npwpImp);
    }

    public static String gantiPassword(java.lang.String userName, java.lang.String password, java.lang.String passwordBaru, java.lang.String passwordKonfirmasi) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.gantiPassword(userName, password, passwordBaru, passwordKonfirmasi);
    }

    public static String coarriCodecoContainer(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coarriCodecoContainer(fStream, username, password);
    }

    public static String coarriCodecoKemasan(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coarriCodecoKemasan(fStream, username, password);
    }

    public static String coCoContTes(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coCoContTes(fStream, username, password);
    }

    public static String coCoKmsTes(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coCoKmsTes(fStream, username, password);
    }

    public static String coCoTangki(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coCoTangki(fStream, username, password);
    }

    public static String coCoCarTer(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.coCoCarTer(fStream, username, password);
    }

    public static String getPendukungPLP(java.lang.String userName, java.lang.String password, java.lang.String noBc11, java.lang.String tglBc11, java.lang.String noCont) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getPendukungPLP(userName, password, noBc11, tglBc11, noCont);
    }

    public static String getImporSppb_1(java.lang.String userName, java.lang.String password, java.lang.String noSppb, java.lang.String tglSppb, java.lang.String npwpImp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getImporSppb(userName, password, noSppb, tglSppb, npwpImp);
    }

    public static String getImporPermit(java.lang.String userName, java.lang.String password, java.lang.String kdGudang) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getImporPermit(userName, password, kdGudang);
    }

    public static String getImporPermit200(java.lang.String userName, java.lang.String password, java.lang.String kdGudang) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getImporPermit200(userName, password, kdGudang);
    }

    public static String getImporPermitFASP(java.lang.String userName, java.lang.String password, java.lang.String kdASP) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getImporPermitFASP(userName, password, kdASP);
    }

    public static String getDataOB(java.lang.String userName, java.lang.String password, java.lang.String kdASP) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getDataOB(userName, password, kdASP);
    }

    public static String cekDataSPPB(java.lang.String userName, java.lang.String password, java.lang.String tglSPPB) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.cekDataSPPB(userName, password, tglSPPB);
    }

    public static String getSppbBc23(java.lang.String userName, java.lang.String password, java.lang.String noSppb, java.lang.String tglSppb, java.lang.String npwpImp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getSppbBc23(userName, password, noSppb, tglSppb, npwpImp);
    }

    public static String getBC23Permit(java.lang.String userName, java.lang.String password, java.lang.String kdGudang) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getBC23Permit(userName, password, kdGudang);
    }

    public static String getBC23PermitFASP(java.lang.String userName, java.lang.String password, java.lang.String kdASP) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getBC23PermitFASP(userName, password, kdASP);
    }

    public static String getSPPB12TPSAsal(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getSPPB12TPSAsal(userName, password, kdAsp);
    }

    public static String getSPPB12TPSTujuan(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getSPPB12TPSTujuan(userName, password, kdAsp);
    }

    public static String permohonanBC12(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.permohonanBC12(fStream, username, password);
    }

    public static String getResponPenolakanBC12(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponPenolakanBC12(userName, password, kdAsp);
    }

    public static String permohonanPLP(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.permohonanPLP(fStream, username, password);
    }

    public static String UploadMohonPLP(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.uploadMohonPLP(fStream, username, password);
    }

    public static String UploadBatalPLP(java.lang.String fStream, java.lang.String username, java.lang.String password) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.uploadBatalPLP(fStream, username, password);
    }

    public static String getResponPLP(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponPLP(userName, password, kdAsp);
    }

    public static String getResponPLPTujuan(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponPLPTujuan(userName, password, kdAsp);
    }
    
    public static String GetResponPLP_Tujuan(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponPLP_Tujuan(userName, password, kdAsp);
    }

    public static String getResponBatalPLP(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponBatalPLP(userName, password, kdAsp);
    }

    public static String getResponBatalPLPTujuan(java.lang.String userName, java.lang.String password, java.lang.String kdAsp) {
        
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getResponBatalPLPTujuan(userName, password, kdAsp);
    }

    public static String GetEksporPermit_FNPE(java.lang.String userName, java.lang.String password, java.lang.String noPE, java.lang.String npwp, java.lang.String kdKantor) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getEksporPermitFNPE(userName, password, noPE, npwp, kdKantor);
    }

    public static String getSPJM(java.lang.String userName, java.lang.String password, java.lang.String kdTps) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getSPJM(userName, password, kdTps);
    }
    public static String getSPJMOnDemand(java.lang.String userName, java.lang.String password, java.lang.String noPIB,java.lang.String tglPIB) {
        TPSServicesSoap port = createProxy(WSDL_URL);
        return port.getSPJMOnDemand(userName, password, noPIB,tglPIB);
    }

    public  static void httpsConnect(String strurlwsdl, String javahomepath) throws MalformedURLException, IOException {
//        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        //System.setProperty("javax.net.debug", "all");
        
        System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", javahomepath + "/jssecacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("javax.net.ssl.keyStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", javahomepath + "/jssecacerts");
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("sun.net.spi.nameservice.nameservers", "192.168.5.5");
        System.setProperty("sun.net.spi.nameservice.provider.1", "dns,sun");
        disableCertificateValidation();
        
        java.net.URL url = new URL(null, strurlwsdl, new sun.net.www.protocol.https.Handler());
        HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
        httpsCon.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                System.out.println(hostname);
                return hostname.equals("tpsonline.beacukai.go.id");

            }
        });
        httpsCon.connect();
        
    }

    public static void disableCertificateValidation() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

        // Ignore differences between given hostname and certificate hostname
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return hostname.equals("tpsonline.beacukai.go.id");
            }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
        }
    }
//     public static void main(String[] args) throws IOException {
//       String javapath="E:/services";
//       String wsdl_url="https://tpsonline.beacukai.go.id/tps/service.asmx?wsdl";
//       Services.httpsConnect(wsdl_url, javapath);
//       
//       String result=Services.coarriCodecoContainer("PLDC", "PLDC", "PLDC");
//       System.out.println(result);
//    }
}
