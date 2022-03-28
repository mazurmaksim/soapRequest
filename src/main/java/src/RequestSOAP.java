package src;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;

public class RequestSOAP {

    private static final String SOAP_END_POINT_URL = "https://localhost:8310/AmountChargingService/services/AmountCharging";

    public static void main(String[] args) {
        String soapAction = "";
        System.out.println(callSoapWebService(soapAction));
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String soapNameSpace = "loc";
        String nameSpaceUri = "http://www.dsdsd.org/schema/parlayx/payment/amount_charging/v2_1/local";

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(soapNameSpace, nameSpaceUri);

        DtoObject dtoObject = new DtoObject();
        dtoObject.setAmount("Prix");
        dtoObject.setId(1145);
        dtoObject.setCode("4");
        dtoObject.setCurrency("INR");
        dtoObject.setReferenceCode("0018072000001859");
        dtoObject.setDelimiterToReferenceCode();
        dtoObject.setDescription("1");

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("chargeAmount", soapNameSpace);
        SOAPElement userIdentifier = soapBodyElem.addChildElement("endUserIdentifier", soapNameSpace);
        SOAPElement charge = soapBodyElem.addChildElement("charge", soapNameSpace);
        SOAPElement description = charge.addChildElement("description");
        SOAPElement currency = charge.addChildElement("currency");
        SOAPElement amount = charge.addChildElement("amount");
        SOAPElement code = charge.addChildElement("code");
        SOAPElement referenceCode = soapBodyElem.addChildElement("referenceCode", soapNameSpace);

        userIdentifier.addTextNode(String.valueOf(dtoObject.getId()));
        description.addTextNode(dtoObject.getDescription());
        amount.addTextNode(dtoObject.getAmount());
        currency.addTextNode(dtoObject.getCurrency());
        code.addTextNode(dtoObject.getCode());
        referenceCode.addTextNode(dtoObject.getReferenceCode());

    }

    private static String callSoapWebService(String soapAction) {
        String soupResponse = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), RequestSOAP.SOAP_END_POINT_URL);

            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(out);
            soupResponse = out.toString();
            System.out.println(soupResponse);

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return soupResponse;
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}