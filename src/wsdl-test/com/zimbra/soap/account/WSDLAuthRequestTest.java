/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010 Zimbra, Inc.
 *
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.soap.account;

import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.zimbra.soap.account.wsimport.generated.AccountService_Service;
import com.zimbra.soap.account.wsimport.generated.AccountService;
import com.zimbra.soap.account.wsimport.generated.Account;
import com.zimbra.soap.account.wsimport.generated.AuthRequest;
import com.zimbra.soap.account.wsimport.generated.AuthResponse;
import com.zimbra.soap.account.wsimport.generated.By;

public class WSDLAuthRequestTest {

    // The AccountService interface is the Java type bound to
    // the portType section of the WSDL document.
    private static AccountService eif;

    @BeforeClass
    public static void init() throws Exception {
        // The AccountService_Service class is the Java type bound to
        // the service section of the WSDL document.
        AccountService_Service service = new AccountService_Service();
        eif = service.getAccountServicePort();
    }

    /**
     * Current assumption : user1 exists with password test123
     */
    @Test
    public void simple() throws Exception {
       AuthRequest authReq = new AuthRequest();
       Account acct = new Account();
       acct.setBy(By.NAME);
       acct.setValue("user1");
       authReq.setAccount(acct);
       authReq.setPassword("test123");
       authReq.setPreauth(null);
       authReq.setAuthToken(null);
       // Invoke the methods.
       AuthResponse authResponse = eif.authRequest(authReq);
       Assert.assertNotNull(authResponse);
       String authToken = authResponse.getAuthToken();
       Assert.assertTrue(authToken != null);
       Assert.assertTrue(authToken.length() > 10);
       long lifetime = authResponse.getLifetime();
       Assert.assertTrue(lifetime > 0);
       Assert.assertNull(authResponse.getRefer());
       Assert.assertEquals(authResponse.getSkin(), "carbon");  // If the default changes, this might change too?
    }

    /**
     * Current assumption : user1 exists with password test123
     */
    @Test
    public void badPasswd() throws Exception {
       AuthRequest authReq = new AuthRequest();
       Account acct = new Account();
       acct.setBy(By.NAME);
       acct.setValue("user1");
       authReq.setAccount(acct);
       authReq.setPassword("BAD-ONE");
       authReq.setPreauth(null);
       authReq.setAuthToken(null);
       // Invoke the methods.
       try {
           AuthResponse authResponse = eif.authRequest(authReq);
           Assert.fail("Should have had a fault resulting in an exception being thrown");
       } catch (SOAPFaultException sfe) {
           Assert.assertTrue(sfe.getMessage().startsWith("authentication failed for "));
       }
    }
}
