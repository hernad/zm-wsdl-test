
package zimbra.generated.accountclient.ws.service;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "AccountService", targetNamespace = "http://www.zimbra.com/wsdl/AccountService.wsdl", wsdlLocation = "file:/p4/coco/main/ZimbraSoap/build/classes/com/zimbra/soap/AccountService.wsdl")
public class AccountService_Service
    extends Service
{

    private final static URL ACCOUNTSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(zimbra.generated.accountclient.ws.service.AccountService_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = zimbra.generated.accountclient.ws.service.AccountService_Service.class.getResource(".");
            url = new URL(baseUrl, "file:/p4/coco/main/ZimbraSoap/build/classes/com/zimbra/soap/AccountService.wsdl");
        } catch (MalformedURLException e) {
            logger.warn("Failed to create URL for the wsdl Location: 'file:/p4/coco/main/ZimbraSoap/build/classes/com/zimbra/soap/AccountService.wsdl', retrying as a local file");
            logger.warn(e.getMessage());
        }
        ACCOUNTSERVICE_WSDL_LOCATION = url;
    }

    public AccountService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccountService_Service() {
        super(ACCOUNTSERVICE_WSDL_LOCATION, new QName("http://www.zimbra.com/wsdl/AccountService.wsdl", "AccountService"));
    }

    /**
     * 
     * @return
     *     returns AccountService
     */
    @WebEndpoint(name = "AccountServicePort")
    public AccountService getAccountServicePort() {
        return super.getPort(new QName("http://www.zimbra.com/wsdl/AccountService.wsdl", "AccountServicePort"), AccountService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccountService
     */
    @WebEndpoint(name = "AccountServicePort")
    public AccountService getAccountServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.zimbra.com/wsdl/AccountService.wsdl", "AccountServicePort"), AccountService.class, features);
    }

}
