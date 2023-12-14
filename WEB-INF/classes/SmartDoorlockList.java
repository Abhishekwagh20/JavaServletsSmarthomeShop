import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartDoorlockList")

public class SmartDoorlockList extends HttpServlet {

	/* Trending Page Displays all the SmartDoorlock and their Information in Samrt Portables*/

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the SmartDoorlock type whether it is microsft or apple or samsung */

		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, SmartDoorlock> hm = new HashMap<String, SmartDoorlock>();

		if (ProductName == null)	
		{
			hm.putAll(SaxParserDataStore.smartdoorlocks);
			name = "";
		} 
		else 
		{
			if(ProductName.equals("august")) 
			{	
				for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("August"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="August";
			} 
			else if (ProductName.equals("schlage"))
			{
				for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Schlage"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Schlage";
			} 
			else if (ProductName.equals("yale")) 
			{
				for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Yale"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Yale";
			}
			else if (ProductName.equals("level")) 
			{
				for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Level"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Level";
			}
			else if (ProductName.equals("lockly")) 
			{
				for(Map.Entry<String,SmartDoorlock> entry : SaxParserDataStore.smartdoorlocks.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Lockly"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Lockly";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the smartdoorlocks and tablet information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+ name+" Smartdoorlocks</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, SmartDoorlock> entry : hm.entrySet()) {
			SmartDoorlock SmartDoorlock = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + SmartDoorlock.getName() + "</h3>");
			pw.print("<strong>$" + SmartDoorlock.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/smartdoorlocks/"+ SmartDoorlock.getImage() + "' alt='' style='width:150px;height:150px;'/></li>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorlocks'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorlocks'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+SmartDoorlock.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorlocks'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li></ul>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
