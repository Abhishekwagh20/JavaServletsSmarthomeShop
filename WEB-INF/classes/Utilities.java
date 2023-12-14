import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;	
	HttpSession session; 
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

	public void printHtml(String file) {
		String result = HtmlToString(file);
		//to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
			// result=result+"<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username")!=null){
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				if(session.getAttribute("usertype").equals("manager"))
				{
					result = result + "<li><a href='ProductModify?button=Addproduct'><span class='glyphicon'>Addproduct</span></a></li>"
						+ "<li><a href='ProductModify?button=Updateproduct'><span class='glyphicon'>Updateproduct</span></a></li>"
						+"<li><a href='ProductModify?button=Deleteproduct'><span class='glyphicon'>Deleteproduct</span></a></li>"
						+"<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
						+"<li><a href='DataAnalytics'><span class='glyphicon'>DataAnalytics</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				
				else if(session.getAttribute("usertype").equals("retailer")){
					result = result + "<li><a href='Registration'><span class='glyphicon'>Create Customer</span></a></li>"
						+ "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
				}
				else
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
						+ "<li><a><span class='glyphicon'>Hello,"+username+"</span></a></li>"
						+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
						+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li></ul></div></nav>";
			}
			else
				result = result +"<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"
								+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
				result = result +"<li><a href='Cart'><span class='glyphicon'>Cart("+CartCount()+")</span></a></li></ul><div id='container'></div></nav>";
				pw.print(result);
		} else
				pw.print(result);
	}
	

	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} 
		catch (Exception e) {
		}
		return result;
	} 

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}
	
	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/
	
	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
	
	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}
	
	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm=new HashMap<String, User>();
		// String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{		
				// FileInputStream fileInputStream=new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\Homework1\\UserDetails.txt"));
				// ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				// hm= (HashMap)objectInputStream.readObject();
				hm=MySqlDataStoreUtilities.selectUser();
			}
			catch(Exception e)
			{
			}	
		User user = hm.get(username());
		return user;
	}
	
	// public HashMap<String, Inventory> getProducts(){
		
		// HashMap<String, Inventory> allProducts=new HashMap<String, Inventory>();
			// try
			// {		
				// allProducts=MySqlDataStoreUtilities.getProducts();
			// }
			// catch(Exception e)
			// {
			// }	
		// // User user = hm.get(username());
		// return allProducts;
		
	// }
	
	
	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>(); 
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		// String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				
				orderPayments =MySqlDataStoreUtilities.selectOrder();
				
				// FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\Homework1\\PaymentDetails.txt"));
				// ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				// orderPayments = (HashMap)objectInputStream.readObject();
			}
			catch(Exception e)
			{
			
			}
			int size=0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
				size=entry.getKey();
			}
			return size;		
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int CartCount(){
		if(isLoggedin())
		return getCustomerOrders().size();
		return 0;
	}
	
	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

public void storeProduct(String name,String type,String maker,String acc){
		if(!OrdersHashMap.orders.containsKey(username())){	
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		HashMap<String,SmartSpeaker> allsmartspeaker = new HashMap<String,SmartSpeaker> ();
		HashMap<String,SmartDoorbell> allsmartdoorbells = new HashMap<String,SmartDoorbell> ();
		HashMap<String,SmartDoorlock> allsmartdoorlocks = new HashMap<String,SmartDoorlock> ();
		HashMap<String,SmartLighting> allsmartlightings=new HashMap<String,SmartLighting>();
		
		if(type.equals("smartspeakers")){
			SmartSpeaker smartspeaker;
			try{
			allsmartspeaker = MySqlDataStoreUtilities.getSmartspeakers();
			
			}
			catch(Exception e){
				
			}
			smartspeaker = SaxParserDataStore.smartspeakers.get(name);
			//smartspeaker = allsmartspeaker.get(name);
			OrderItem orderitem = new OrderItem(smartspeaker.getName(), smartspeaker.getPrice(), smartspeaker.getImage(), smartspeaker.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("smartdoorlocks")){
			SmartDoorlock smartDoorlocks = null;
			try{
			allsmartdoorlocks = MySqlDataStoreUtilities.getSmartdoorlocks();
			}
			catch(Exception e){
				
			}
			smartDoorlocks = SaxParserDataStore.smartdoorlocks.get(name);
			//smartDoorlocks = allsmartdoorlocks.get(name);
			OrderItem orderitem = new OrderItem(smartDoorlocks.getName(), smartDoorlocks.getPrice(), smartDoorlocks.getImage(), smartDoorlocks.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("smartdoorbells")){
			SmartDoorbell smartDoorbells = null;
			try{
			allsmartdoorbells = MySqlDataStoreUtilities.getSmartDoorbells();
			}
			catch(Exception e){
				
			}
			smartDoorbells = SaxParserDataStore.smartdoorbells.get(name);
			//smartDoorbells = allsmartdoorbells.get(name);
			OrderItem orderitem = new OrderItem(smartDoorbells.getName(), smartDoorbells.getPrice(), smartDoorbells.getImage(), smartDoorbells.getRetailer());
			orderItems.add(orderitem);
		}
		if(type.equals("smartlightings")){	
			SmartLighting smartLightings =null;
			try{
			
			allsmartlightings = MySqlDataStoreUtilities.getSmartlightings();
			}
			catch(Exception e){
				
			}
			smartLightings = SaxParserDataStore.smartlightings.get(name);
			//smartLightings = allsmartlightings.get(name); 
			OrderItem orderitem = new OrderItem(smartLightings.getName(), smartLightings.getPrice(), smartLightings.getImage(), smartLightings.getRetailer());
			orderItems.add(orderitem);
		}
		
	}
	// store the payment details for orders
	public void storePayment(int orderId,
		String orderName,double orderPrice,String userAddress,String creditCardNo,String dayDate){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		// String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file 
			try
			{
				orderPayments=MySqlDataStoreUtilities.selectOrder();
				
			}
			catch(Exception e)
			{
			
			}
			if(orderPayments==null)
			{
				orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			}
			// if there exist order id already add it into same list for order id or create a new record with order id
			
			if(!orderPayments.containsKey(orderId)){	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(orderId, arr);
			}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);		
		OrderPayment orderpayment = new OrderPayment(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,dayDate);
		listOrderPayment.add(orderpayment);	
			
			// add order details into the database

		try
		{	
			if(session.getAttribute("usertype").equals("retailer"))
			
			{
				// FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"\\webapps\\Homework1\\PaymentDetails.txt"));
				// ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            	// objectOutputStream.writeObject(orderPayments);
				// objectOutputStream.flush();
				// objectOutputStream.close();       
				// fileOutputStream.close();
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,dayDate);
			}
			else{
				MySqlDataStoreUtilities.insertOrder(orderId,username(),orderName,orderPrice,userAddress,creditCardNo,dayDate);
			}
		}
		catch(Exception e)
		{
		System.out.println("inside exception file not written properly");
		}	
	}
	public String storeReview(String productname,String producttype,String productmaker,String productonsale,String manufacturerrebate,String userage,String usergender,String useroccupation,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city)
	{
		String message=MongoDBDataStoreUtilities.insertReview(productname,username(),producttype,productmaker,productonsale,manufacturerrebate,userage,usergender,useroccupation,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
		if(!message.equals("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
		try
		{
			reviews=MongoDBDataStoreUtilities.selectReview();
		}
		catch(Exception e)
		{
			
		}
		if(reviews==null)
		{
			reviews = new HashMap<String, ArrayList<Review>>();
		}
			// if there exist product review already add it into same list for productname or create a new record with product name
			
		if(!reviews.containsKey(productname)){	
			ArrayList<Review> arr = new ArrayList<Review>();
			reviews.put(productname, arr);
		}
		ArrayList<Review> listReview = reviews.get(productname);		
		Review review = new Review(productname,username(),producttype,productmaker,productonsale,manufacturerrebate,userage,usergender,useroccupation,reviewrating,reviewdate,reviewtext,reatilerpin,price,city);
		listReview.add(review);	
			
			// add Reviews into database
		
		return "Successfull";	
		}
	}
	/* smartspeakers Functions returns the Hashmap with all smartspeakers in the store.*/

	public HashMap<String, SmartSpeaker> getSmartspeakers(){
			HashMap<String, SmartSpeaker> hm = new HashMap<String, SmartSpeaker>();
			hm.putAll(SaxParserDataStore.smartspeakers);
			return hm;
	}
	
	/* smartdoorlocks Functions returns the  Hashmap with all Phones in the store.*/

	public HashMap<String, SmartDoorlock> getSmartdoorlocks(){
			HashMap<String, SmartDoorlock> hm = new HashMap<String, SmartDoorlock>();
			hm.putAll(SaxParserDataStore.smartdoorlocks);
			return hm;
	}
	
	/* smartdoorbells Functions returns the Hashmap with all SmartDoorbell in the store.*/

	public HashMap<String, SmartDoorbell> getSmartDoorbells(){
			HashMap<String, SmartDoorbell> hm = new HashMap<String, SmartDoorbell>();
			hm.putAll(SaxParserDataStore.smartdoorbells);
			return hm;
	}
	public HashMap<String, SmartLighting> getSmartlightings(){
			HashMap<String, SmartLighting> hm = new HashMap<String, SmartLighting>();
			hm.putAll(SaxParserDataStore.smartlightings);
			return hm;
	}
	
	
	/* getProducts Functions returns the Arraylist of smartspeakers in the store.*/

	public ArrayList<String> getProductsSmartSpeakers(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartSpeaker> entry : getSmartspeakers().entrySet()){			
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of smartdoorlocks in the store.*/

	public ArrayList<String> getProductsSmartDoorlocks(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartDoorlock> entry : getSmartdoorlocks().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
	
	/* getProducts Functions returns the Arraylist of Laptops in the store.*/

	public ArrayList<String> getProductsSmartDoorbells(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartDoorbell> entry : getSmartDoorbells().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}
		/* getProducts Functions returns the Arraylist of SmartLighting in the store.*/
	public ArrayList<String> getProductsSmartLightings(){		
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SmartLighting> entry : getSmartlightings().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
}
}