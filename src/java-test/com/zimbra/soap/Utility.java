/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012, 2013, 2014 Zimbra, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.soap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Assume;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Maps;
import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.SchemaValidationFeature;
import com.sun.xml.ws.developer.WSBindingProvider;

import generated.zcsclient.account.testAuthRequest;
import generated.zcsclient.account.testAuthResponse;
import generated.zcsclient.admin.testAccountInfo;
import generated.zcsclient.admin.testAttr;
import generated.zcsclient.admin.testCalendarResourceBy;
import generated.zcsclient.admin.testCalendarResourceInfo;
import generated.zcsclient.admin.testCalendarResourceSelector;
import generated.zcsclient.admin.testCosBy;
import generated.zcsclient.admin.testCosInfo;
import generated.zcsclient.admin.testCosSelector;
import generated.zcsclient.admin.testCreateAccountRequest;
import generated.zcsclient.admin.testCreateAccountResponse;
import generated.zcsclient.admin.testCreateCalendarResourceRequest;
import generated.zcsclient.admin.testCreateCalendarResourceResponse;
import generated.zcsclient.admin.testCreateCosRequest;
import generated.zcsclient.admin.testCreateCosResponse;
import generated.zcsclient.admin.testCreateDistributionListRequest;
import generated.zcsclient.admin.testCreateDistributionListResponse;
import generated.zcsclient.admin.testCreateDomainRequest;
import generated.zcsclient.admin.testCreateDomainResponse;
import generated.zcsclient.admin.testCreateServerRequest;
import generated.zcsclient.admin.testCreateServerResponse;
import generated.zcsclient.admin.testCreateVolumeRequest;
import generated.zcsclient.admin.testCreateVolumeResponse;
import generated.zcsclient.admin.testDeleteAccountRequest;
import generated.zcsclient.admin.testDeleteAccountResponse;
import generated.zcsclient.admin.testDeleteCalendarResourceRequest;
import generated.zcsclient.admin.testDeleteCalendarResourceResponse;
import generated.zcsclient.admin.testDeleteCosRequest;
import generated.zcsclient.admin.testDeleteCosResponse;
import generated.zcsclient.admin.testDeleteDistributionListRequest;
import generated.zcsclient.admin.testDeleteDomainRequest;
import generated.zcsclient.admin.testDeleteServerRequest;
import generated.zcsclient.admin.testDeleteVolumeRequest;
import generated.zcsclient.admin.testDistributionListBy;
import generated.zcsclient.admin.testDistributionListInfo;
import generated.zcsclient.admin.testDistributionListSelector;
import generated.zcsclient.admin.testDomainBy;
import generated.zcsclient.admin.testDomainInfo;
import generated.zcsclient.admin.testDomainSelector;
import generated.zcsclient.admin.testGetAccountRequest;
import generated.zcsclient.admin.testGetAccountResponse;
import generated.zcsclient.admin.testGetAllVolumesRequest;
import generated.zcsclient.admin.testGetAllVolumesResponse;
import generated.zcsclient.admin.testGetCalendarResourceRequest;
import generated.zcsclient.admin.testGetCalendarResourceResponse;
import generated.zcsclient.admin.testGetCosRequest;
import generated.zcsclient.admin.testGetCosResponse;
import generated.zcsclient.admin.testGetDistributionListRequest;
import generated.zcsclient.admin.testGetDistributionListResponse;
import generated.zcsclient.admin.testGetDomainInfoRequest;
import generated.zcsclient.admin.testGetDomainInfoResponse;
import generated.zcsclient.admin.testGetDomainRequest;
import generated.zcsclient.admin.testGetDomainResponse;
import generated.zcsclient.admin.testGetMailboxRequest;
import generated.zcsclient.admin.testGetMailboxResponse;
import generated.zcsclient.admin.testGetServerRequest;
import generated.zcsclient.admin.testGetServerResponse;
import generated.zcsclient.admin.testMailboxByAccountIdSelector;
import generated.zcsclient.admin.testServerBy;
import generated.zcsclient.admin.testServerInfo;
import generated.zcsclient.admin.testServerSelector;
import generated.zcsclient.admin.testVolumeInfo;
import generated.zcsclient.ws.service.ZcsAdminPortType;
import generated.zcsclient.ws.service.ZcsAdminService;
import generated.zcsclient.ws.service.ZcsPortType;
import generated.zcsclient.ws.service.ZcsService;
import generated.zcsclient.zm.ObjectFactory;
import generated.zcsclient.zm.testAccountBy;
import generated.zcsclient.zm.testAccountSelector;
import generated.zcsclient.zm.testAuthTokenControl;
import generated.zcsclient.zm.testHeaderContext;

/**
 * Current assumption : user1 exists with password test123
 */
public class Utility {
    private static final String DEFAULT_PASS = "test123";
    private static final String PROP_ADMIN_NAME = "adminName";
    private static final String PROP_ADMIN_PASS = "adminPass";
    private static final String PROP_OTHER_USERS_PASS = "otherUsersPass";
    private static ZcsPortType zcsSvcEIF = null;
    private static ZcsPortType nvZcsSvcEIF = null;
    private static ZcsAdminPortType adminSvcEIF = null;
    private static ZcsAdminPortType nvAdminSvcEIF = null;
    private static String adminAuthToken = null;
    private static Map<String,String> acctAuthToks = Maps.newHashMap();
    private static Properties log4jProperties = null;
    private static Properties properties = null;

    public static final Logger LOG = Logger.getLogger("zimbra.test");

    static {
        log4jProperties = new Properties();
        try (final InputStream propStream = Utility.class.getResourceAsStream("/log4j-test.properties")) {
            if (propStream != null) {
                log4jProperties.load(propStream);
            }
            PropertyConfigurator.configure(log4jProperties);
        } catch (IOException e) {
            LOG.info("Problem loading properties from log4j-test.properties", e);
            e.printStackTrace(System.out);
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.INFO);
            LOG.setLevel(Level.INFO);
        }
        properties = new Properties();
        try (InputStream propStream = Utility.class.getResourceAsStream("/test.properties")) {
            if (propStream != null) {
                properties.load(propStream);
            }
        } catch (IOException e) {
            LOG.info("Problem loading properties from test.properties", e);
        }
        if (!properties.containsKey(PROP_ADMIN_NAME)) {
            properties.setProperty(PROP_ADMIN_NAME, "admin");
        }
        if (!properties.containsKey(PROP_ADMIN_PASS)) {
            properties.setProperty(PROP_ADMIN_PASS, DEFAULT_PASS);
        }
        if (!properties.containsKey(PROP_OTHER_USERS_PASS)) {
            properties.setProperty(PROP_OTHER_USERS_PASS, DEFAULT_PASS);
        }
    }

    public static void addSoapAuthHeader(WSBindingProvider bp, String authToken)
    throws JAXBException, ParserConfigurationException {
        try {
            Thread.sleep(50);  /* avoid danger of number of requests getting throttled */
        } catch (InterruptedException ie) {
        }
        JAXBRIContext.newInstance(testHeaderContext.class);
        testHeaderContext zimbraSoapHdrContext = new testHeaderContext();
        zimbraSoapHdrContext.setAuthToken(authToken);
        testAuthTokenControl tokenControl = new testAuthTokenControl();
        tokenControl.setVoidOnExpired(true);
        zimbraSoapHdrContext.setAuthTokenControl(tokenControl);
        // Seen failing intermittently on Mac OSX Mountain Lion claiming the create method does not exist - CP problem?
        // so, use method based on Element instead
        // Header soapHdr = Headers.create(jaxbriContext, jaxbHeaderContext);
        Header soapHdr = Headers.create(makeZimbraSoapHeaderContext(zimbraSoapHdrContext));
        bp.setOutboundHeaders(soapHdr);
    }
    public static void addSoapAcctAuthHeader(WSBindingProvider bp, String authToken)
    throws JAXBException, ParserConfigurationException {
        addSoapAuthHeader(bp, authToken);
    }
    public static void addSoapAdminAuthHeader(WSBindingProvider bp) throws JAXBException, ParserConfigurationException {
        Utility.getAdminServiceAuthToken();
        addSoapAuthHeader(bp, adminAuthToken);
    }

    public static Element makeZimbraSoapHeaderContext(testHeaderContext contextJaxb)
    throws JAXBException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();

        JAXBContext jaxb = JAXBContext.newInstance(testHeaderContext.class);
        Marshaller marshaller = jaxb.createMarshaller();
        // testHeaderContext doesn't have an @XmlRootElement annotation for some reason, however the JAXBElement
        // created using fact.createContext knows what the element name should be called, so use that.
        ObjectFactory fact = new ObjectFactory();
        JAXBElement<testHeaderContext> zimbraSoapHdrCtxt = fact.createContext(contextJaxb);
        marshaller.marshal(zimbraSoapHdrCtxt, doc);
        return doc.getDocumentElement();
    }

    public static String addSoapAcctAuthHeaderForAcct(WSBindingProvider bp, String acctName)
    throws JAXBException, ParserConfigurationException {
        String authTok;
        if (acctAuthToks.containsKey(acctName))
            authTok = acctAuthToks.get(acctName);
        else {
            authTok = getAccountServiceAuthToken(acctName, properties.getProperty(PROP_OTHER_USERS_PASS));
            acctAuthToks.put(acctName, authTok);
        }
        addSoapAcctAuthHeader(bp, authTok);
        return authTok;
    }

    public static void addSoapAcctAuthHeader(WSBindingProvider bp)
    throws Exception {
        String authTok = Utility.getAccountServiceAuthToken();
        addSoapAcctAuthHeader(bp, authTok);
    }

    public static String getAccountServiceAuthToken() throws Exception {
        String acctName = "user1";
        if (acctAuthToks.containsKey(acctName))
            return acctAuthToks.get(acctName);
        return getAccountServiceAuthToken(acctName, properties.getProperty(PROP_OTHER_USERS_PASS));
    }

    public static String getAccountServiceAuthToken(String acctName, String password)
    {
        Utility.getZcsSvcEIF();
        testAuthRequest authReq = new testAuthRequest();
        testAccountSelector acct = new testAccountSelector();
        acct.setBy(testAccountBy.NAME);
        acct.setValue(acctName);
        authReq.setAccount(acct);
        authReq.setPassword(password);
        authReq.setPreauth(null);
        authReq.setAuthToken(null);
        // Invoke the methods.
        testAuthResponse authResponse = getZcsSvcEIF().authRequest(authReq);
        Assert.assertNotNull(authResponse);
        return authResponse.getAuthToken();
    }

    private static void setZcsSvcEIF(ZcsPortType svcEIF) {
        Utility.zcsSvcEIF = svcEIF;
    }

    private static void setNvZcsSvcEIF(ZcsPortType svcEIF) {
        Utility.nvZcsSvcEIF = svcEIF;
    }

    private static void setAdminSvcEIF(ZcsAdminPortType svcEIF) {
        Utility.adminSvcEIF = svcEIF;
    }

    private static void setNvAdminSvcEIF(ZcsAdminPortType svcEIF) {
        Utility.nvAdminSvcEIF = svcEIF;
    }

    public static ZcsPortType getZcsSvcEIF() {
        if (zcsSvcEIF == null) {
            setUpToAcceptAllHttpsServerCerts();
            // The ZcsService class is the Java type bound to the service section of the WSDL document.
            ZcsService zcsSvc = new ZcsService();
            SchemaValidationFeature feature = new SchemaValidationFeature();
            setZcsSvcEIF(zcsSvc.getZcsServicePort(feature));
        }
        return zcsSvcEIF;
    }

    public static ZcsPortType getNonValidatingZcsSvcEIF() throws Exception {
        if (nvZcsSvcEIF == null) {
            setUpToAcceptAllHttpsServerCerts();
            ZcsService zcsSvc = new ZcsService();
            setNvZcsSvcEIF(zcsSvc.getZcsServicePort());
        }
        return nvZcsSvcEIF;
    }

    public static ZcsAdminPortType getAdminSvcEIF() {
        if (adminSvcEIF == null) {
            setUpToAcceptAllHttpsServerCerts();
            // The ZcsAdminService class is the Java type bound to the service section of the WSDL document.
            ZcsAdminService zcsSvc = new ZcsAdminService();
            SchemaValidationFeature feature = new SchemaValidationFeature();
            setAdminSvcEIF(zcsSvc.getZcsAdminServicePort(feature));
        }
        return adminSvcEIF;
    }

    public static ZcsAdminPortType getNonValidatingAdminSvcEIF() throws Exception {
        if (nvAdminSvcEIF == null) {
            setUpToAcceptAllHttpsServerCerts();
            ZcsAdminService zcsSvc = new ZcsAdminService();
            setNvAdminSvcEIF(zcsSvc.getZcsAdminServicePort());
        }
        return nvAdminSvcEIF;
    }

    public static String getAdminServiceAuthToken() {
        Utility.getAdminSvcEIF();
        if (adminAuthToken == null) {
            Utility.getAdminSvcEIF();
            generated.zcsclient.admin.testAuthRequest authReq = new generated.zcsclient.admin.testAuthRequest();
            generated.zcsclient.zm.testAccountSelector acct = new generated.zcsclient.zm.testAccountSelector();
            acct.setBy(generated.zcsclient.zm.testAccountBy.NAME);
            acct.setValue(properties.getProperty(PROP_ADMIN_NAME));
            authReq.setAccount(acct);
            authReq.setPassword(properties.getProperty(PROP_ADMIN_PASS));
            authReq.setAuthToken(null);
            generated.zcsclient.admin.testAuthResponse authResponse = getAdminSvcEIF().authRequest(authReq);
            Assert.assertNotNull(authResponse);
            adminAuthToken = authResponse.getAuthToken();
            Assert.assertTrue(adminAuthToken != null);
            Assert.assertTrue(adminAuthToken.length() > 10);
        }
        return adminAuthToken;
    }

    public static void setUpToAcceptAllHttpsServerCerts() {
        // Create a trust manager that does not validate certificate chains
        // without this, we need to import the server certificate into the trust store.
        // when using https as is required for Admin
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[]{
                new javax.net.ssl.X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                    @Override
                    public void checkClientTrusted( java.security.cert.X509Certificate[] certs, String authType) { }
                    @Override
                    public void checkServerTrusted( java.security.cert.X509Certificate[] certs, String authType) { }
                    }
                };
        // Install the all-trusting trust manager
        try {
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            LOG.debug("Problem installing the all-trusting trust manager", e);
        }

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });
    }

    public static void deleteDomainIfExists(String domainName) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        try {
            // Delete the test domain if it hasn't already been deleted
            testGetDomainRequest getReq = new testGetDomainRequest();
            testDomainSelector domainSel = new testDomainSelector();
            domainSel.setBy(testDomainBy.NAME);
            domainSel.setValue(domainName);
            getReq.setDomain(domainSel);
            getReq.setApplyConfig(true);
            testGetDomainResponse getResp = getAdminSvcEIF().getDomainRequest(getReq);
            if (getResp != null) {
                testDomainInfo domainInfo = getResp.getDomain();
                testDeleteDomainRequest delReq = new testDeleteDomainRequest();
                delReq.setId(domainInfo.getId());
                getAdminSvcEIF().deleteDomainRequest(delReq);
            }
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such domain:")) {
                LOG.debug(String.format(
                        "Exception %s thrown attempting to delete domain %s", sfe.toString(), domainName));
            }
        }
    }

    public static void deleteServerIfExists(String serverName) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        try {
            // Delete the test server if it hasn't already been deleted
            testGetServerRequest getSvrReq = new testGetServerRequest();
            testServerSelector svrSel = new testServerSelector();
            svrSel.setBy(testServerBy.NAME);
            svrSel.setValue(serverName);
            getSvrReq.setServer(svrSel);
            getSvrReq.setApplyConfig(true);
            testGetServerResponse getSvrResp = getAdminSvcEIF().getServerRequest(getSvrReq);
            if (getSvrResp != null) {
                testServerInfo serverInfo = getSvrResp.getServer();
                testDeleteServerRequest delReq = new testDeleteServerRequest();
                delReq.setId(serverInfo.getId());
                getAdminSvcEIF().deleteServerRequest(delReq);
            }
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such server:")) {
                LOG.debug(String.format(
                        "Exception %s thrown attempting to delete server %s", sfe.toString(), serverName));
            }
        }
    }

    public static void deleteAccountIfExists(String accountName)
    throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        try {
            testGetAccountRequest getReq = new testGetAccountRequest();
            testAccountSelector accountSel = new testAccountSelector();
            accountSel.setBy(testAccountBy.NAME);
            accountSel.setValue(accountName);
            getReq.setAccount(accountSel);
            testGetAccountResponse getResp =
                    getAdminSvcEIF().getAccountRequest(getReq);
            Assert.assertNotNull(getResp);
            testAccountInfo accountInfo = getResp.getAccount();
            Assert.assertNotNull(accountInfo);
            String respId = accountInfo.getId();
            testDeleteAccountRequest delReq = new testDeleteAccountRequest();
            delReq.setId(respId);
            testDeleteAccountResponse delResp =
                    getAdminSvcEIF().deleteAccountRequest(delReq);
            Assert.assertNotNull(delResp);
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such account:")) {
                LOG.debug(String.format(
                        "Exception %s thrown attempting to delete account %s", sfe.toString(), accountName));
            }
        }
    }

    public static void deleteCalendarResourceIfExists(String calResourceName)
    throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        try {
            testGetCalendarResourceRequest getReq =
                    new testGetCalendarResourceRequest();
            testCalendarResourceSelector calResourceSel =
                    new testCalendarResourceSelector();
            calResourceSel.setBy(testCalendarResourceBy.NAME);
            calResourceSel.setValue(calResourceName);
            getReq.setCalresource(calResourceSel);
            testGetCalendarResourceResponse getResp =
                getAdminSvcEIF().getCalendarResourceRequest(getReq);
            Assert.assertNotNull(getResp);
            testCalendarResourceInfo calResourceInfo = getResp.getCalresource();
            Assert.assertNotNull(calResourceInfo);
            String respId = calResourceInfo.getId();
            testDeleteCalendarResourceRequest delReq =
                    new testDeleteCalendarResourceRequest();
            delReq.setId(respId);
            testDeleteCalendarResourceResponse delResp =
                getAdminSvcEIF().deleteCalendarResourceRequest(delReq);
            Assert.assertNotNull(delResp);
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such calendar resource:")) {
                LOG.debug(String.format("Exception %s thrown attempting to delete calendar resource %s",
                        sfe.toString(), calResourceName));
            }
        }
    }

    public static void deleteCosIfExists(String cosName) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        try {
            testGetCosRequest getReq = new testGetCosRequest();
            testCosSelector cosSel = new testCosSelector();
            cosSel.setBy(testCosBy.NAME);
            cosSel.setValue(cosName);
            getReq.setCos(cosSel);
            testGetCosResponse getResp = getAdminSvcEIF().getCosRequest(getReq);
            Assert.assertNotNull(getResp);
            testCosInfo cosInfo = getResp.getCos();
            Assert.assertNotNull(cosInfo);
            String respId = cosInfo.getId();
            testDeleteCosRequest delReq = new testDeleteCosRequest();
            delReq.setId(respId);
            testDeleteCosResponse delResp = getAdminSvcEIF().deleteCosRequest(delReq);
            Assert.assertNotNull(delResp);
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such cos:")) {
                LOG.debug(String.format("Exception %s thrown attempting to delete COS %s",
                        sfe.toString(), cosName));
            }
        }
    }

    public static void deleteVolumeIfExists(String name) throws JAXBException, ParserConfigurationException {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetAllVolumesRequest gavReq = new testGetAllVolumesRequest();
        testGetAllVolumesResponse gavResp = getAdminSvcEIF().getAllVolumesRequest(gavReq);
        for (testVolumeInfo volume : gavResp.getVolume()) {
            if (name.equals(volume.getName())) {
                testDeleteVolumeRequest delReq = new testDeleteVolumeRequest();
                delReq.setId(volume.getId());
                getAdminSvcEIF().deleteVolumeRequest(delReq);
                String volRootpath = volume.getRootpath();
                try {
                    if (volRootpath != null && (volRootpath.length() > 0)) {
                        new File(volume.getRootpath()).deleteOnExit();
                    }
                } catch (Exception ex) {
                    LOG.debug(String.format(
                            "Exception %s thrown deleting rootPath=%s for volume=%s", ex.toString(), volRootpath,name));
                    return;
                }
            }
        }
    }

    public static void deleteDistributionListIfExists(String name) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetDistributionListRequest getInfoReq = new testGetDistributionListRequest();
        testDistributionListSelector dlSel = new testDistributionListSelector();
        dlSel.setBy(testDistributionListBy.NAME);
        dlSel.setValue(name);
        getInfoReq.setDl(dlSel);
        try {
            testGetDistributionListResponse getResp = adminSvcEIF.getDistributionListRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            testDeleteDistributionListRequest delReq = new testDeleteDistributionListRequest();
            delReq.setId(getResp.getDl().getId());
            Assert.assertNotNull("DeleteDistributionListResponse object",
                    getAdminSvcEIF().deleteDistributionListRequest(delReq));
        } catch (SOAPFaultException sfe) {
            String missive = sfe.getMessage();
            if (!missive.startsWith("no such distribution list:")) {
                LOG.debug(String.format("Exception %s thrown attempting to delete distribution list %s",
                        sfe.toString(), name));
            }
        }
    }

    public static String ensureDomainExists(String domainName) throws JAXBException, ParserConfigurationException {
        String domainId = null;
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetDomainInfoRequest getInfoReq = new testGetDomainInfoRequest();
        getInfoReq.setApplyConfig(false);
        testDomainSelector domainSel = new testDomainSelector();
        domainSel.setBy(testDomainBy.NAME);
        domainSel.setValue(domainName);
        getInfoReq.setDomain(domainSel);
        try {
            testGetDomainInfoResponse getInfoResp = adminSvcEIF.getDomainInfoRequest(getInfoReq);
            Assert.assertNotNull(getInfoResp);
            domainId = getInfoResp.getDomain().getId();
            if (domainId.equals("globalconfig-dummy-id"))
                domainId = null;
        } catch (SOAPFaultException sfe) {
        }
        if (domainId != null) {
            return domainId;
        }
        else {
            testCreateDomainRequest req = new testCreateDomainRequest();
            req.setName(domainName);
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateDomainResponse resp = adminSvcEIF.createDomainRequest(req);
            Assert.assertNotNull(resp);
            testDomainInfo domainInfo = resp.getDomain();
            Assert.assertNotNull(domainInfo);
            Assert.assertEquals("createDomainResponse <domain> 'name' attribute", domainName, domainInfo.getName());
            return domainInfo.getId();
        }
    }

    public static String ensureServerExists(String serverName) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetServerRequest getInfoReq = new testGetServerRequest();
        testServerSelector serverSel = new testServerSelector();
        serverSel.setBy(testServerBy.NAME);
        serverSel.setValue(serverName);
        getInfoReq.setServer(serverSel);
        try {
            testGetServerResponse getResp = adminSvcEIF.getServerRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            return getResp.getServer().getId();
        } catch (SOAPFaultException sfe) {
            testCreateServerRequest createAcctReq = new testCreateServerRequest();
            createAcctReq.setName(serverName);
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateServerResponse resp = adminSvcEIF.createServerRequest(createAcctReq);
            Assert.assertNotNull(resp);
            testServerInfo serverInfo = resp.getServer();
            return serverInfo.getId();
        }
    }

    public static String ensureAccountExists(String accountName) throws JAXBException, ParserConfigurationException {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        String domainName = accountName.substring(accountName.indexOf('@') + 1);
        ensureDomainExists(domainName);
        testGetAccountRequest getInfoReq = new testGetAccountRequest();
        testAccountSelector accountSel = new testAccountSelector();
        accountSel.setBy(testAccountBy.NAME);
        accountSel.setValue(accountName);
        getInfoReq.setAccount(accountSel);
        try {
            testGetAccountResponse getResp =
                    adminSvcEIF.getAccountRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            return getResp.getAccount().getId();
        } catch (SOAPFaultException sfe) {
            testCreateAccountRequest createAcctReq = new testCreateAccountRequest();
            createAcctReq.setName(accountName);
            createAcctReq.setPassword(properties.getProperty(PROP_OTHER_USERS_PASS));
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateAccountResponse resp =
                    adminSvcEIF.createAccountRequest(createAcctReq);
            Assert.assertNotNull(resp);
            testAccountInfo accountInfo = resp.getAccount();
            return accountInfo.getId();
        }
    }

    public static String ensureCalendarResourceExists(
            String calResourceName, String displayName)
    throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        String domainName = calResourceName.substring(calResourceName.indexOf('@') + 1);
        ensureDomainExists(domainName);
        testGetCalendarResourceRequest getInfoReq = new testGetCalendarResourceRequest();
        testCalendarResourceSelector calResourceSel = new testCalendarResourceSelector();
        calResourceSel.setBy(testCalendarResourceBy.NAME);
        calResourceSel.setValue(calResourceName);
        getInfoReq.setCalresource(calResourceSel);
        try {
            testGetCalendarResourceResponse getResp = adminSvcEIF.getCalendarResourceRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            return getResp.getCalresource().getId();
        } catch (SOAPFaultException sfe) {
            testCreateCalendarResourceRequest createAcctReq = new testCreateCalendarResourceRequest();
            createAcctReq.setName(calResourceName);
            createAcctReq.setPassword(properties.getProperty(PROP_OTHER_USERS_PASS));
            createAcctReq.getA().add(Utility.mkAttr("displayName", displayName));
            createAcctReq.getA().add(Utility.mkAttr("zimbraCalResType", "Location"));
            createAcctReq.getA().add(Utility.mkAttr("zimbraCalResLocationDisplayName", "Harare"));
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateCalendarResourceResponse resp = adminSvcEIF.createCalendarResourceRequest(createAcctReq);
            Assert.assertNotNull(resp);
            testCalendarResourceInfo calResourceInfo = resp.getCalresource();
            return calResourceInfo.getId();
        }
    }

    /**
     * Creating an account does not create the associated mailbox until a Get is done for the mailbox.
     *
     * @param accountName - name of account - must have password "test123" if exists already
     * @return
     * @throws Exception
     */
    public static String ensureMailboxExistsForAccount(String accountName) throws Exception {
        String accountId = ensureAccountExists(accountName);
        testGetMailboxRequest gmReq = new testGetMailboxRequest();
        testMailboxByAccountIdSelector mbox = new testMailboxByAccountIdSelector();
        mbox.setId(accountId);
        gmReq.setMbox(mbox);
        Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
        testGetMailboxResponse gmResp = adminSvcEIF.getMailboxRequest(gmReq);
        Assert.assertNotNull(gmResp);
        return accountId;
    }

    public static String ensureDistributionListExists(String name) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        String domainName = name.substring(name.indexOf('@') + 1);
        ensureDomainExists(domainName);
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetDistributionListRequest getInfoReq = new testGetDistributionListRequest();
        testDistributionListSelector dlSel = new testDistributionListSelector();
        dlSel.setBy(testDistributionListBy.NAME);
        dlSel.setValue(name);
        getInfoReq.setDl(dlSel);
        try {
            testGetDistributionListResponse getResp = adminSvcEIF.getDistributionListRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            return getResp.getDl().getId();
        } catch (SOAPFaultException sfe) {
            testCreateDistributionListRequest createAcctReq = new testCreateDistributionListRequest();
            createAcctReq.setName(name);
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateDistributionListResponse resp = adminSvcEIF.createDistributionListRequest(createAcctReq);
            Assert.assertNotNull(resp);
            testDistributionListInfo dlInfo = resp.getDl();
            return dlInfo.getId();
        }
    }

    public static String ensureCosExists(String cosName) throws Exception {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetCosRequest getInfoReq = new testGetCosRequest();
        testCosSelector cosSel = new testCosSelector();
        cosSel.setBy(testCosBy.NAME);
        cosSel.setValue(cosName);
        getInfoReq.setCos(cosSel);
        try {
            testGetCosResponse getResp = adminSvcEIF.getCosRequest(getInfoReq);
            Assert.assertNotNull(getResp);
            return getResp.getCos().getId();
        } catch (SOAPFaultException sfe) {
            testCreateCosRequest createAcctReq = new testCreateCosRequest();
            createAcctReq.setName(cosName);
            Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
            testCreateCosResponse resp = adminSvcEIF.createCosRequest(createAcctReq);
            Assert.assertNotNull(resp);
            testCosInfo cosInfo = resp.getCos();
            return cosInfo.getId();
        }
    }

    public static Short ensureVolumeExists(String name, String rootPath)
            throws JAXBException, ParserConfigurationException
    {
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testGetAllVolumesRequest gavReq = new testGetAllVolumesRequest();
        testGetAllVolumesResponse gavResp = getAdminSvcEIF().getAllVolumesRequest(gavReq);
        for (testVolumeInfo volume : gavResp.getVolume()) {
            if (name.equals(volume.getName())) {
                if (rootPath.equals(volume.getRootpath())) {
                    return volume.getId();
                }
                deleteVolumeIfExists(name);
                break;
            }
        }
        File rootPathFile = new File(rootPath);
        if (!rootPathFile.exists()) {
            if (!rootPathFile.mkdir()) {
                LOG.info(String.format(
                        "Unable to create dir='%s' for volumeName=%s - test runner MUST have permissions to do that",
                        rootPath, name));
                Assume.assumeTrue(false);
            }
            rootPathFile.setWritable(true);
            try {
                Files.setPosixFilePermissions(Paths.get(rootPath), PosixFilePermissions.fromString("rwxrwxrwx"));
            } catch (IOException e) {
                LOG.info(String.format(
                        "Unable to make dir='%s' for volumeName=%s fully writable - test runner MUST have permissions to do that",
                        rootPath, name));
                Assume.assumeNoException(e);
            }
        }
        testCreateVolumeRequest req = new testCreateVolumeRequest();
        testVolumeInfo volume = new testVolumeInfo();
        volume.setName(name);
        volume.setRootpath(rootPath);
        volume.setCompressionThreshold(4096L);
        volume.setType((short)1);
        volume.setCompressBlobs(true);
        req.setVolume(volume);
        Utility.addSoapAdminAuthHeader((WSBindingProvider)getAdminSvcEIF());
        testCreateVolumeResponse resp = getAdminSvcEIF().createVolumeRequest(req);
        Assert.assertNotNull(resp);
        testVolumeInfo volumeInfo = resp.getVolume();
        Assert.assertNotNull(volumeInfo);
        Assert.assertEquals("CreateVolumeResponse <volume> 'name' attribute",
                name, volumeInfo.getName());
        Short testVolumeId = volumeInfo.getId();
        Assert.assertTrue(
                "CreateVolumeResponse <volume> 'id' attribute " +
                testVolumeId + " - should be at least 1", testVolumeId >= 1);
        return testVolumeId;
    }

    public static testAttr mkAttr(String name, String value) {
        testAttr attr = new testAttr();
        attr.setN(name);
        attr.setValue(value);
        return attr;
    }

    public static String getTestProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Will create the account.  If the string doesn't contain an @, will assume it is in the default domain
     */
    public void createAccount(String testAcct) throws Exception {
        testCreateAccountRequest createAcctReq = new testCreateAccountRequest();
        createAcctReq.setName(testAcct);
        createAcctReq.setPassword(Utility.getOtherUsersPassword());
        ZcsAdminPortType eif =Utility.getAdminSvcEIF();
        Utility.addSoapAdminAuthHeader((WSBindingProvider)eif);
        testCreateAccountResponse resp = eif.createAccountRequest(createAcctReq);
        Assert.assertNotNull("CreateAccountResponse object", resp);
    }

    public static String getAdminName() {
        return getTestProperty(PROP_ADMIN_NAME);
    }

    public static String getAdminPassword() {
        return getTestProperty(PROP_ADMIN_PASS);
    }

    public static String getOtherUsersPassword() {
        return getTestProperty(PROP_OTHER_USERS_PASS);
    }
}
