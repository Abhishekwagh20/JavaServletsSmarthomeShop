import java.sql.*;
import java.util.*;
                	
public class MySqlDataStoreUtilities
{
static Connection conn = null;
static String message;
public static String getConnection()
{

	try
	{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smarthomes","root","root");							
	message="Successfull";
	return message;
	}
	catch(SQLException e)
	{
		message="unsuccessful";
		     return message;
	}
	catch(Exception e)
	{
		message=e.getMessage();
		return message;
	}
}
public static HashMap<String,Inventory> getDailyTransactions()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectDailyTransactionsQurey="select dayDate,sum(orderPrice) as totalSales from smarthomes.customerorders group by dayDate";
		ResultSet rs = stmt.executeQuery(selectDailyTransactionsQurey);
		
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("dayDate"),rs.getDouble("totalSales"));
			
				hm.put(rs.getString("dayDate"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getSoldProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectDailyTransactionsQurey="SELECT orderName, orderPrice, SUM(1) AS solditems, SUM(orderPrice) AS totalSales FROM smarthomes.customerorders GROUP BY orderName, orderPrice";
		ResultSet rs = stmt.executeQuery(selectDailyTransactionsQurey);
		
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getInt("solditems"),rs.getDouble("totalSales"));
			
				hm.put(rs.getString("orderName"), inventoryItems);
		}
		
		//String selectSoldProductsQurey="select orderName,orderPrice,count(orderName) as itemsSold,(orderPrice * count(orderName)) as totalSales from CustomerOrders group by orderName";
		
			//Inventory inventoryItems = new Inventory(rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getInt("itemsSold"),rs.getDouble("totalSales"));
				
	}
	catch(Exception e)
	{
	}
	return hm;
}

public static HashMap<String,Inventory> getAllProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="Select *from Productdetails";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getOnRebateProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="select * from smarthomes.productdetails where productDiscount <>0";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
	
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static HashMap<String,Inventory> getOnSaleProducts()
{
	HashMap<String,Inventory> hm=new HashMap<String,Inventory>();
	try {
		
		
		getConnection();
		Statement stmt=conn.createStatement();
		String selectProductQurey="select * from smarthomes.productdetails where productOnSale='YES';";
		ResultSet rs = stmt.executeQuery(selectProductQurey);
	
		while(rs.next())
		{	
			Inventory inventoryItems = new Inventory(rs.getString("ProductType"),rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"),rs.getString("productOnSale"),rs.getInt("productQuantity"));
			
				hm.put(rs.getString("ProductType"), inventoryItems);
		}
	}
	catch(Exception e)
	{
	}
	return hm;
}
public static void Insertproducts()
{
	try{
		
		
		getConnection();
		
		
		// String truncatetableacc = "delete from Product_accessories;";
		// PreparedStatement pstt = conn.prepareStatement(truncatetableacc);
		// pstt.executeUpdate();
		
		String truncatetableprod = "delete from  Productlist;";
		PreparedStatement psttprod = conn.prepareStatement(truncatetableprod);
		psttprod.executeUpdate();
		
				
		
		String insertProductQurey = "INSERT INTO  Productlist(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		for(Map.Entry<String,SmartSpeaker> entry : SaxParserDataStore.smartspeakers.entrySet())
		{   
			String name = "smartspeakers";
	        SmartSpeaker smartspeaker = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartspeaker.getId());
			pst.setString(3,smartspeaker.getName());
			pst.setDouble(4,smartspeaker.getPrice());
			pst.setString(5,smartspeaker.getImage());
			pst.setString(6,smartspeaker.getRetailer());
			pst.setString(7,smartspeaker.getCondition());
			pst.setDouble(8,smartspeaker.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		
		for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
		{   
	        SmartDoorlock smartdoorlock = entry.getValue();
			String name = "smartdoorlocks";		
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartdoorlock.getId());
			pst.setString(3,smartdoorlock.getName());
			pst.setDouble(4,smartdoorlock.getPrice());
			pst.setString(5,smartdoorlock.getImage());
			pst.setString(6,smartdoorlock.getRetailer());
			pst.setString(7,smartdoorlock.getCondition());
			pst.setDouble(8,smartdoorlock.getDiscount());
			
			pst.executeUpdate();
		}
		for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
		{   
			String name = "smartdoorbells";
	        SmartDoorbell smartdoorbell = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartdoorbell.getId());
			pst.setString(3,smartdoorbell.getName());
			pst.setDouble(4,smartdoorbell.getPrice());
			pst.setString(5,smartdoorbell.getImage());
			pst.setString(6,smartdoorbell.getRetailer());
			pst.setString(7,smartdoorbell.getCondition());
			pst.setDouble(8,smartdoorbell.getDiscount());
			
			pst.executeUpdate();
			
		}
		for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
		{   
			String name = "smartlightings";
	        SmartLighting smartlighting = entry.getValue();
			
			PreparedStatement pst = conn.prepareStatement(insertProductQurey);
			pst.setString(1,name);
			pst.setString(2,smartlighting.getId());
			pst.setString(3,smartlighting.getName());
			pst.setDouble(4,smartlighting.getPrice());
			pst.setString(5,smartlighting.getImage());
			pst.setString(6,smartlighting.getRetailer());
			pst.setString(7,smartlighting.getCondition());
			pst.setDouble(8,smartlighting.getDiscount());
			
			pst.executeUpdate();
			
			
		}
		
	}catch(Exception e)
	{
  		e.printStackTrace();
	}
} 

public static HashMap<String,SmartDoorlock> getSmartdoorlocks()
{	
	HashMap<String,SmartDoorlock> hm=new HashMap<String,SmartDoorlock>();
	try 
	{
		getConnection();
		
		String selectSmartdoorlock="select * from  Productlist where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartdoorlock);
		pst.setString(1,"smartdoorlocks");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartDoorlock smartdoorlock = new SmartDoorlock(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartdoorlock);
				smartdoorlock.setId(rs.getString("Id"));									
		}
	}
	catch(Exception e)
	{
	}
	return hm;				
}

public static HashMap<String,SmartLighting> getSmartlightings()
{	
	HashMap<String,SmartLighting> hm=new HashMap<String,SmartLighting>();
	try 
	{
		getConnection();
		
		String selectSmartLighting="select * from  Productlist where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartLighting);
		pst.setString(1,"smartlightings");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartLighting smartlighting = new SmartLighting(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartlighting);
				smartlighting.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,SmartDoorbell> getSmartDoorbells()
{	
	HashMap<String,SmartDoorbell> hm=new HashMap<String,SmartDoorbell>();
	try 
	{
		getConnection();
		
		String selectSmartDoorbell="select * from  Productlist where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartDoorbell);
		pst.setString(1,"smartdoorbells");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	SmartDoorbell smartdoorbell = new SmartDoorbell(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartdoorbell);
				smartdoorbell.setId(rs.getString("Id"));
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static HashMap<String,SmartSpeaker> getSmartspeakers()
{	
	HashMap<String,SmartSpeaker> hm=new HashMap<String,SmartSpeaker>();
	try 
	{
		getConnection();
		
		String selectSmartSpeaker="select * from  Productlist where ProductType=?";
		PreparedStatement pst = conn.prepareStatement(selectSmartSpeaker);
		pst.setString(1,"smartspeakers");
		ResultSet rs = pst.executeQuery();
	
		while(rs.next())
		{	
	SmartSpeaker smartspeaker = new SmartSpeaker(rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getDouble("productDiscount"));
				hm.put(rs.getString("Id"), smartspeaker);
				smartspeaker.setId(rs.getString("Id"));

		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

public static String addproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,String prod)
{
	String msg = "Product is added successfully";
	try{
		
		getConnection();
		String addProductQurey = "INSERT INTO  Productlist(ProductType,Id,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount)" +
		"VALUES (?,?,?,?,?,?,?,?);";
		   
			String name = producttype;
	        			
			PreparedStatement pst = conn.prepareStatement(addProductQurey);
			pst.setString(1,name);
			pst.setString(2,productId);
			pst.setString(3,productName);
			pst.setDouble(4,productPrice);
			pst.setString(5,productImage);
			pst.setString(6,productManufacturer);
			pst.setString(7,productCondition);
			pst.setDouble(8,productDiscount);
			
			pst.executeUpdate();
			try{
				if (!prod.isEmpty())
				{
					String addaprodacc =  "INSERT INTO  Product_accessories(productName,accessoriesName)" +
					"VALUES (?,?);";
					PreparedStatement pst1 = conn.prepareStatement(addaprodacc);
					pst1.setString(1,prod);
					pst1.setString(2,productId);
					pst1.executeUpdate();
					
				}
			}catch(Exception e)
			{
				msg = "Erro while adding the product";
				e.printStackTrace();
		
			}
			
			
		
	}
	catch(Exception e)
	{
		msg = "Erro while adding the product";
		e.printStackTrace();
		
	}
	return msg;
}
public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount)
{ 
    String msg = "Product is updated successfully";
	try{
		
		getConnection();
		String updateProductQurey = "UPDATE Productlist SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=? where Id =?;" ;
		
		   
				        			
			PreparedStatement pst = conn.prepareStatement(updateProductQurey);
			
			pst.setString(1,productName);
			pst.setDouble(2,productPrice);
			pst.setString(3,productImage);
			pst.setString(4,productManufacturer);
			pst.setString(5,productCondition);
			pst.setDouble(6,productDiscount);
			pst.setString(7,productId);
			pst.executeUpdate();
			
			
		
	}
	catch(Exception e)
	{
		msg = "Product cannot be updated";
		e.printStackTrace();
		
	}
 return msg;	
}
public static String deleteproducts(String productId)
{   String msg = "Product is deleted successfully";
	try
	{
		
		getConnection();
		String deleteproductsQuery ="Delete from Productlist where Id=?";
		PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
		pst.setString(1,productId);
		
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			msg = "Proudct cannot be deleted";
	}
	return msg;
}

public static void deleteOrder(int orderId,String orderName)
{
	try
	{
		
		getConnection();
		String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
		PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
		pst.setInt(1,orderId);
		pst.setString(2,orderName);
		pst.executeUpdate();
	}
	catch(Exception e)
	{
			
	}
}

public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo, String dayDate)
{
	try
	{
	
		getConnection();
		
		String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo) "
		+ "VALUES (?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setInt(1,orderId);
		pst.setString(2,userName);
		pst.setString(3,orderName);
		pst.setDouble(4,orderPrice);
		pst.setString(5,userAddress);
		pst.setString(6,creditCardNo);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}		
}

public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{	

	HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from customerorders";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
		while(rs.next())
		{
			if(!orderPayments.containsKey(rs.getInt("OrderId")))
			{	
				ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
				orderPayments.put(rs.getInt("orderId"), arr);
			}
			ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));		
			

			//add to orderpayment hashmap
			OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("orderName"),rs.getDouble("orderPrice"),rs.getString("userAddress"),rs.getString("creditCardNo"),rs.getString("dayDate"));
			listOrderPayment.add(order);
					
		}
				
					
	}
	catch(Exception e)
	{
		
	}
	return orderPayments;
}


public static void insertUser(String username,String password,String repassword,String usertype)
{
	try
	{	

		getConnection();
		String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
		+ "VALUES (?,?,?,?);";	
				
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
		pst.setString(1,username);
		pst.setString(2,password);
		pst.setString(3,repassword);
		pst.setString(4,usertype);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
}

public static HashMap<String,User> selectUser()
{	
	HashMap<String,User> hm=new HashMap<String,User>();
	try 
	{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from  Registration";
		ResultSet rs = stmt.executeQuery(selectCustomerQuery);
		while(rs.next())
		{	User user = new User(rs.getString("username"),rs.getString("password"),rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
		}
	}
	catch(Exception e)
	{
	}
	return hm;			
}

	
}	