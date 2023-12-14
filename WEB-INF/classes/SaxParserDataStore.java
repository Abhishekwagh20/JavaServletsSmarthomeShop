import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserDataStore extends DefaultHandler {
    SmartSpeaker smartspeaker;
    SmartDoorlock smartdoorlock;
    SmartDoorbell smartdoorbell;
    SmartLighting smartlighting;
    static HashMap<String,SmartSpeaker> smartspeakers;
    static HashMap<String,SmartDoorlock> smartdoorlocks;
    static HashMap<String,SmartDoorbell> smartdoorbells;
    static HashMap<String,SmartLighting> smartlightings;
    String consoleXmlFileName;
	// HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
    // this.consoleXmlFileName = consoleXmlFileName;
	this.consoleXmlFileName = consoleXmlFileName;
    smartspeakers = new HashMap<String, SmartSpeaker>();
	smartdoorlocks=new  HashMap<String, SmartDoorlock>();
	smartdoorbells=new HashMap<String, SmartDoorbell>();
	smartlightings=new HashMap<String, SmartLighting>();
	// accessoryHashMap=new HashMap<String,String>();
	parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument() 
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try 
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   
	
	// when xml start element is parsed store the id into respective hashmap for smartspeaker,smartdoorlocks etc 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("smartspeaker")) 
		{
			currentElement="smartspeaker";
			smartspeaker = new SmartSpeaker();
            smartspeaker.setId(attributes.getValue("id"));
		}
        else if (elementName.equalsIgnoreCase("smartdoorbell"))
		{
			currentElement="smartdoorbell";
			smartdoorbell = new SmartDoorbell();
            smartdoorbell.setId(attributes.getValue("id"));
        }
        if (elementName.equalsIgnoreCase("smartdoorlock"))
		{
			currentElement="smartdoorlock";
			smartdoorlock= new SmartDoorlock();
            smartdoorlock.setId(attributes.getValue("id"));
        }
		if (elementName.equals("smartlighting"))
		{
			currentElement="smartlighting";
			smartlighting=new SmartLighting();
			smartlighting.setId(attributes.getValue("id"));
	    }

    }
	// when xml end element is parsed store the data into respective hashmap for smartspeaker,smartdoorlocks etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("smartspeaker")) {
			smartspeakers.put(smartspeaker.getId(),smartspeaker);
			return;
        }
 
        if (element.equals("smartdoorbell")) {	
			smartdoorbells.put(smartdoorbell.getId(),smartdoorbell);
			return;
        }
        if (element.equals("smartdoorlock")) {	  
			smartdoorlocks.put(smartdoorlock.getId(),smartdoorlock);
			return;
        }
        if (element.equals("smartlighting")){
			smartlightings.put(smartlighting.getId(),smartlighting);       
			return; 
        }

        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("smartspeaker"))
				smartspeaker.setImage(elementValueRead);
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setImage(elementValueRead);
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setImage(elementValueRead);
            if(currentElement.equals("smartlighting"))
				smartlighting.setImage(elementValueRead);          
			return;
        }
        

		if (element.equalsIgnoreCase("discount")) {
            if(currentElement.equals("smartspeaker"))
				smartspeaker.setDiscount(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setDiscount(Double.parseDouble(elementValueRead));
            if(currentElement.equals("smartlighting"))
				smartlighting.setDiscount(Double.parseDouble(elementValueRead));          
			return;
	    }


		if (element.equalsIgnoreCase("condition")) {
            if(currentElement.equals("smartspeaker"))
				smartspeaker.setCondition(elementValueRead);
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setCondition(elementValueRead);
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setCondition(elementValueRead);
            if(currentElement.equals("smartlighting"))
				smartlighting.setCondition(elementValueRead);          
			return;  
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("smartspeaker"))
				smartspeaker.setRetailer(elementValueRead);
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setRetailer(elementValueRead);
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setRetailer(elementValueRead);
            if(currentElement.equals("smartlighting"))
				smartlighting.setRetailer(elementValueRead);          
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("smartspeaker"))
				smartspeaker.setName(elementValueRead);
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setName(elementValueRead);
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setName(elementValueRead);
            if(currentElement.equals("smartlighting"))
				smartlighting.setName(elementValueRead);          
			return;
	    }
	
        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("smartspeaker"))
				smartspeaker.setPrice(Double.parseDouble(elementValueRead));
        	if(currentElement.equals("smartdoorlock"))
				smartdoorlock.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("smartdoorbell"))
				smartdoorbell.setPrice(Double.parseDouble(elementValueRead));
            if(currentElement.equals("smartlighting"))
				smartlighting.setPrice(Double.parseDouble(elementValueRead));          
			return;
        }

	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\smarthome\\ProductCatalog.xml");
    } 
}
