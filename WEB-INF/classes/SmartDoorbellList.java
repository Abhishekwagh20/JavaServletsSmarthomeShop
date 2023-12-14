import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartDoorbellList")

public class SmartDoorbellList extends HttpServlet {

	/* Laptops Page Displays all the Laptops and their Information in SmartDoorbell Speed */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the Laptops type whether it is electronicArts or activision or takeTwoInteractive */
				
		String name = null;
		String ProductName = request.getParameter("maker");
		HashMap<String, SmartDoorbell> hm = new HashMap<String, SmartDoorbell>();
		
		if(ProductName==null)
		{
			hm.putAll(SaxParserDataStore.smartdoorbells);
			name = "";
		}
		else
		{
		  if(ProductName.equals("nest"))
		  {
			for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Nest"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Nest";
		  }
		  else if(ProductName.equals("arlo"))
		  {
			for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Arlo"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Arlo";
		  }
		  else if(ProductName.equals("eufy"))
		  {
			for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Eufy"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Eufy";
		  }
		  else if(ProductName.equals("wyze"))
		  {
			for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Wyze"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Wyze";
		  }
		  else if(ProductName.equals("ringbell"))
		  {
			for(Map.Entry<String,SmartDoorbell> entry : SaxParserDataStore.smartdoorbells.entrySet())
				{
				if(entry.getValue().getRetailer().equals("Ringbell"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Ringbell";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the Laptops and Laptops information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" SmartDoorbell</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SmartDoorbell> entry : hm.entrySet()){
			SmartDoorbell SmartDoorbell = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+SmartDoorbell.getName()+"</h3>");
			pw.print("<strong>"+ "$" + SmartDoorbell.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/smartdoorbells/"+SmartDoorbell.getImage()+"' alt='' style='width:150px;height:150px;'/></li>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorbells'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorbells'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+SmartDoorbell.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartdoorbells'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li></ul>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}

}
