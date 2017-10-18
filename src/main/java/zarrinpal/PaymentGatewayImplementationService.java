
package zarrinpal;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PaymentGatewayImplementationService", targetNamespace = "http://zarinpal.com/", wsdlLocation = "https://www.zarinpal.com/pg/services/WebGate/wsdl?wsdl")
public class PaymentGatewayImplementationService
    extends Service
{

    private final static URL PAYMENTGATEWAYIMPLEMENTATIONSERVICE_WSDL_LOCATION;
    private final static WebServiceException PAYMENTGATEWAYIMPLEMENTATIONSERVICE_EXCEPTION;
    private final static QName PAYMENTGATEWAYIMPLEMENTATIONSERVICE_QNAME = new QName("http://zarinpal.com/", "PaymentGatewayImplementationService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://www.zarinpal.com/pg/services/WebGate/wsdl?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PAYMENTGATEWAYIMPLEMENTATIONSERVICE_WSDL_LOCATION = url;
        PAYMENTGATEWAYIMPLEMENTATIONSERVICE_EXCEPTION = e;
    }

    public PaymentGatewayImplementationService() {
        super(__getWsdlLocation(), PAYMENTGATEWAYIMPLEMENTATIONSERVICE_QNAME);
    }

    public PaymentGatewayImplementationService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PAYMENTGATEWAYIMPLEMENTATIONSERVICE_QNAME, features);
    }

    public PaymentGatewayImplementationService(URL wsdlLocation) {
        super(wsdlLocation, PAYMENTGATEWAYIMPLEMENTATIONSERVICE_QNAME);
    }

    public PaymentGatewayImplementationService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PAYMENTGATEWAYIMPLEMENTATIONSERVICE_QNAME, features);
    }

    public PaymentGatewayImplementationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PaymentGatewayImplementationService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PaymentGatewayImplementationServicePortType
     */
    @WebEndpoint(name = "PaymentGatewayImplementationServicePort")
    public PaymentGatewayImplementationServicePortType getPaymentGatewayImplementationServicePort() {
        return super.getPort(new QName("http://zarinpal.com/", "PaymentGatewayImplementationServicePort"), PaymentGatewayImplementationServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PaymentGatewayImplementationServicePortType
     */
    @WebEndpoint(name = "PaymentGatewayImplementationServicePort")
    public PaymentGatewayImplementationServicePortType getPaymentGatewayImplementationServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://zarinpal.com/", "PaymentGatewayImplementationServicePort"), PaymentGatewayImplementationServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PAYMENTGATEWAYIMPLEMENTATIONSERVICE_EXCEPTION!= null) {
            throw PAYMENTGATEWAYIMPLEMENTATIONSERVICE_EXCEPTION;
        }
        return PAYMENTGATEWAYIMPLEMENTATIONSERVICE_WSDL_LOCATION;
    }

}
