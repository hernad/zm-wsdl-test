/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013, 2014 Zimbra, Inc.
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
package com.zimbra.soap.admin;

import javax.xml.ws.soap.SOAPFaultException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.xml.ws.developer.WSBindingProvider;
import com.zimbra.soap.Utility;

import generated.zcsclient.admin.testAttr;
import generated.zcsclient.admin.testCheckAuthConfigRequest;
import generated.zcsclient.ws.service.ZcsAdminPortType;

public class WSDLCheckAuthConfigTest {

    private final static String testAcctDomain = "wsdl.acct.domain.example.test";
    private final static String testAcct = "wsdl1@" + testAcctDomain;
    private static ZcsAdminPortType eif = null;

    @BeforeClass
    public static void init() throws Exception {
        Utility.setUpToAcceptAllHttpsServerCerts();
        eif = Utility.getAdminSvcEIF();
        oneTimeTearDown();
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
        try {
            Utility.deleteAccountIfExists(testAcct);
            Utility.deleteDomainIfExists(testAcctDomain);
        } catch (Exception ex) {
            System.err.println("Exception " + ex.toString() +
            " thrown inside oneTimeTearDown");
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // TODO: Add a test with a valid LDAP URL.
    @Test
    public void checkAuthConfigBadAuthURLTest() throws Exception {
        Utility.ensureAccountExists(testAcct);
        Utility.addSoapAdminAuthHeader((WSBindingProvider)eif);
        testCheckAuthConfigRequest req = new testCheckAuthConfigRequest();
        req.setName(testAcct);
        req.setPassword(Utility.getOtherUsersPassword());
        testAttr attr;
        attr = new testAttr(); attr.setN("zimbraAuthMech");
        attr.setValue("ldap");
        req.getA().add(attr);
        attr = new testAttr(); attr.setN("zimbraAuthLdapURL");
        attr.setValue("ldaps://localhost:3269");
        req.getA().add(attr);
        attr = new testAttr(); attr.setN("zimbraAuthLdapBindDn");
        attr.setValue("%u@example.test");
        req.getA().add(attr);
        try {
            eif.checkAuthConfigRequest(req);
        } catch (SOAPFaultException sfe) {
            Assert.assertTrue("Soap fault message [" +
                    sfe.getMessage() + "] should start with 'javax.naming.CommunicationException'",
                    sfe.getMessage().startsWith("javax.naming.CommunicationException"));
        }
        Utility.deleteAccountIfExists(testAcct);
    }

}
