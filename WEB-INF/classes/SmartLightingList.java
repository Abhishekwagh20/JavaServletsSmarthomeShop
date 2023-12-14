import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SmartLightingList")

public class SmartLightingList extends HttpServlet {

	/* SmartLighting Page Displays all the VoiceAssistants and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the SmartLighting maker whether it is microsft or sony or nintendo 
		   Add the respective product value to hashmap  */

		String name = null;
		String ProductName = request.getParameter("maker");
		
		HashMap<String, SmartLighting> hm = new HashMap<String, SmartLighting>();
		if(ProductName==null)
		{
			hm.putAll(SaxParserDataStore.smartlightings);
			name = "";
		}
		else
		{
			if(ProductName.equals("nanoleaf"))
			{
				for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
				{	
					if(entry.getValue().getRetailer().equals("Nanoleaf"))
					{
					 hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				name = "Nanoleaf";
				
			}
			else if(ProductName.equals("philips"))
			{	
				for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
				{	
				  if(entry.getValue().getRetailer().equals("Philips"))
				 { 
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				name = "Philips";
			}
			else if(ProductName.equals("tplink"))
			{	
				for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
				{	
				  if(entry.getValue().getRetailer().equals("TPLink"))
				 { 
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				name = "TPLink";
			}
			else if(ProductName.equals("govee"))
			{	
				for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
				{	
				  if(entry.getValue().getRetailer().equals("Govee"))
				 { 
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				name = "Govee";
			}
			else if(ProductName.equals("ring"))
			{	
				for(Map.Entry<String,SmartLighting> entry : SaxParserDataStore.smartlightings.entrySet())
				{	
				  if(entry.getValue().getRetailer().equals("Ring"))
				 { 
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				name = "Ring";
			}
		}
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+ name+" Smartlightings</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, SmartLighting> entry : hm.entrySet()) {
			SmartLighting SmartLighting = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + SmartLighting.getName() + "</h3>");
			pw.print("<strong>$" + SmartLighting.getPrice() + "</strong><ul>");
			pw.print("<li id='item'><img src='images/smartlightings/"+ SmartLighting.getImage() + "' alt='' style='width:150px;height:150px;' /></li>");
			pw.print("<ul><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartlightings'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartlightings'>"+
					"<input type='hidden' name='maker' value='"+name+"'>"+
					"<input type='hidden' name='price' value='"+SmartLighting.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li></ul>");
			pw.print("<ul><li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='smartlightings'>"+
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
