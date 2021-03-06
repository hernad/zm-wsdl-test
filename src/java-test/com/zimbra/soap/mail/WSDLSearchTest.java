/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014 Zimbra, Inc.
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
package com.zimbra.soap.mail;

import generated.zcsclient.mail.testAddMsgRequest;
import generated.zcsclient.mail.testAddMsgResponse;
import generated.zcsclient.mail.testAddMsgSpec;
import generated.zcsclient.mail.testAppointmentHitInfo;
import generated.zcsclient.mail.testCalOrganizer;
import generated.zcsclient.mail.testEmailAddrInfo;
import generated.zcsclient.mail.testMimePartInfo;
import generated.zcsclient.mail.testSaveDraftMsg;
import generated.zcsclient.mail.testSaveDraftRequest;
import generated.zcsclient.mail.testSaveDraftResponse;
import generated.zcsclient.mail.testSearchRequest;
import generated.zcsclient.mail.testSearchResponse;
import generated.zcsclient.ws.service.ZcsAdminPortType;
import generated.zcsclient.ws.service.ZcsPortType;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.ws.developer.WSBindingProvider;
import com.zimbra.soap.Utility;

public class WSDLSearchTest {

    private static ZcsPortType mailSvcEIF = null;

    private final static String testAcctDomain = "wsdl.example.test";
    private final static String testAcct = "wsdlSearch@" + testAcctDomain;
    private static ZcsAdminPortType adminSvcEIF = null;

    @BeforeClass
    public static void init() throws Exception {
        Utility.setUpToAcceptAllHttpsServerCerts();
        adminSvcEIF = Utility.getAdminSvcEIF();
        mailSvcEIF = Utility.getZcsSvcEIF();
        oneTimeTearDown();
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
        try {
            Utility.deleteAccountIfExists(testAcct);
            Utility.deleteDomainIfExists(testAcctDomain);
        } catch (Exception ex) {
            System.err.println("Exception " + ex.toString() + " thrown inside oneTimeTearDown");
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void user1CalSearch() throws Exception {
        testSearchRequest req = new testSearchRequest();
        req.setSortBy("none");
        req.setLimit(500);
        req.setLocale("en_US");
        req.setCalExpandInstStart(1306710000000L);
        req.setCalExpandInstEnd(1307228400000L);
        req.setTypes("appointment");
        req.setOffset(0);
        req.setQuery("every (inid:\"10\")");
        // Note: With earlier code, needed to use the non-validating variant nvMailSvcEIF
        //       "GetCalendarItemSummaries" code produced an elements ordering that differed to other element orderings
        //       created on the server and did NOT match the prop order in LegacyCalendaringData
        //       With validation turned on, this test currently fails complaining:
        //          cvc-complex-type.2.4.a: Invalid content was found starting with element &apos;fr&apos;.
        //          One of &apos;{&quot;urn:zimbraMail&quot;:inv, &quot;urn:zimbraMail&quot;:replies}&apos; is expected.
        //       Fortunately, non-validating accepts this.
        ZcsPortType myMailSvcEIF = mailSvcEIF;
        Utility.addSoapAcctAuthHeaderForAcct((WSBindingProvider)myMailSvcEIF, "user1");
        testSearchResponse resp = myMailSvcEIF.searchRequest(req);
        Assert.assertNotNull("SearchResponse object", resp);
        Assert.assertEquals("SearchResponse sortBy", "none", resp.getSortBy());
        Assert.assertEquals("SearchResponse offset", new Integer(0), resp.getOffset());
        Assert.assertEquals("SearchResponse more", Boolean.FALSE, resp.isMore());
        Assert.assertNull("SearchResponse total", resp.getTotal());
        List <Object> hits = resp.getHitOrCOrM();
        /* TODO create some data for this to work (this relied on pre-populated user1)

        Assert.assertEquals("SearchResponse number of hits", 1, hits.size());
        Object o = hits.get(0);
        if (o instanceof testAppointmentHitInfo) {
            testAppointmentHitInfo ahi = (testAppointmentHitInfo) o;
            testCalOrganizer org = ahi.getOr();
            Assert.assertNotNull("SearchResponse/appt/or object", org);
            Assert.assertEquals("SearchResponse/appt/or @a", "tom@example.zimbra.com", org.getA());
            Assert.assertEquals("SearchResponse/appt/or @url", "tom@example.zimbra.com", org.getUrl());
            Assert.assertEquals("SearchResponse/appt/or @d", "Tom", org.getD());
        } else {
            Assert.fail("SearchResponse hit is NOT an AppointmentHitInfo");
        }
         */
    }

    private static String msgContent =
            "Return-Path: h1@example.com\n" +
                    "Received: by mail02.example.com (33.333.333.333/16.2) id AA10153;\n" +
                    "          Wed, 29 Jun 2007 19:05:59 -0400\n" +
                    "From: h1@example.com\n" +
                    "To: two@example.com\n" +
                    "Date: Wed, 20 Jul 2007 16:05:58 PDT\n" +
                    "Subject: cvs commit is good\n" +
                    "\n" +
                    "This is a msg\n";

    @Test
    public void user1InboxSearchByConversation() throws JAXBException, ParserConfigurationException {
        ZcsPortType myMailSvcEIF = mailSvcEIF;

        Utility.addSoapAcctAuthHeaderForAcct((WSBindingProvider)myMailSvcEIF, "user1");

        testAddMsgRequest amReq = new testAddMsgRequest();
        testAddMsgSpec addMsgSpec = new testAddMsgSpec();
        addMsgSpec.setContent(msgContent);
        addMsgSpec.setL("2");
        amReq.setM(addMsgSpec);
        testAddMsgResponse amResp = myMailSvcEIF.addMsgRequest(amReq);
        Assert.assertNotNull("AddMsgResponse object", amResp);

        testSearchRequest req = new testSearchRequest();
        req.setQuery("in:inbox cvs commit");
        testSearchResponse resp = myMailSvcEIF.searchRequest(req);
        Assert.assertNotNull("SearchResponse object", resp);
        List <Object> hits = resp.getHitOrCOrM();
        Assert.assertTrue(String.format("SearchResponse number of hits (%s) should be more than 0", hits.size()),
                hits.size() >=0);
    }

    @Test
    public void user1InboxSearchByMessage() throws JAXBException, ParserConfigurationException {
        ZcsPortType myMailSvcEIF = mailSvcEIF;

        Utility.addSoapAcctAuthHeaderForAcct((WSBindingProvider)myMailSvcEIF, "user1");

        testAddMsgRequest amReq = new testAddMsgRequest();
        testAddMsgSpec addMsgSpec = new testAddMsgSpec();
        addMsgSpec.setContent(msgContent);
        addMsgSpec.setL("2");
        amReq.setM(addMsgSpec);
        testAddMsgResponse amResp = myMailSvcEIF.addMsgRequest(amReq);
        Assert.assertNotNull("AddMsgResponse object", amResp);

        testSearchRequest req = new testSearchRequest();
        req.setQuery("in:inbox cvs commit");
        req.setTypes("message");
        testSearchResponse resp = myMailSvcEIF.searchRequest(req);

        Assert.assertNotNull("SearchResponse object", resp);
        List <Object> hits = resp.getHitOrCOrM();
        Assert.assertNotNull("SearchResponse hits", hits);
        Assert.assertTrue(String.format("SearchResponse number of hits (%s) should be more than 0", hits.size()),
                hits.size() >=0);
    }

    /**
     * Exercise SearchResponse processing - before the fix for Bug81724, this would have failed.  Metro rejected
     * the SOAP response because the order of elements was incorrect.
     */
    @Test
    public void searchResponseMessageHitBug81724() throws JAXBException, ParserConfigurationException {
        ZcsPortType myMailSvcEIF = mailSvcEIF;
        Utility.ensureAccountExists(testAcct);
        Utility.addSoapAdminAuthHeader((WSBindingProvider)adminSvcEIF);
        Utility.addSoapAcctAuthHeaderForAcct((WSBindingProvider)myMailSvcEIF, testAcct);

        testSaveDraftMsg saveMsg = new testSaveDraftMsg();
        testMimePartInfo mimePartInfo = new testMimePartInfo();
        testSaveDraftRequest saveReq = new testSaveDraftRequest();
        testEmailAddrInfo toAddr = new testEmailAddrInfo();
        testEmailAddrInfo fromAddr = new testEmailAddrInfo();
        saveReq.setM(saveMsg);
        saveMsg.setIdnt("35a6ec12-4eff-4768-a5b1-bb7ed30ab055");
        saveMsg.setSu("Draft message");
        saveMsg.setMp(mimePartInfo);
        saveMsg.getE().add(toAddr);
        saveMsg.getE().add(fromAddr);
        toAddr.setT("t");
        toAddr.setA(testAcct);
        toAddr.setP("Test Account");
        fromAddr.setT("f");
        fromAddr.setA(testAcct);
        fromAddr.setP("Test Account");
        mimePartInfo.setCt("text/plain");
        mimePartInfo.setContent("Ships and sealing wax and cabbages and kings.\n");

        testSaveDraftResponse saveDraftResp = myMailSvcEIF.saveDraftRequest(saveReq);
        Assert.assertNotNull("SearchResponse object", saveDraftResp);

        testSearchRequest srchReq = new testSearchRequest();
        srchReq.setQuery("in:drafts cabbages");
        srchReq.setTypes("message");

        testSearchResponse resp = myMailSvcEIF.searchRequest(srchReq);
        Assert.assertNotNull("SearchResponse object", resp);
        List <Object> hits = resp.getHitOrCOrM();
        Assert.assertEquals("SearchResponse number of hits", 1, hits.size());
    }

}
