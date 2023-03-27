import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Dropdown")
public class Dropdown extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dropdown() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

	    // Get the absolute path of the HTML file
	    String path = getServletContext().getRealPath("/Dropdown.html");

	    // Read the contents of the file into a string
	    String html = new String(Files.readAllBytes(Paths.get(path)));

	    // Send the HTML content as the response
	    response.getWriter().print(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
	    String term = request.getParameter("term");
	    String course = request.getParameter("course");
	    
	 // Check for errors
	    List<String> errors = new ArrayList<>();
	    if (term == null || term.equals("")) {
	    	errors.add("Please select a term");
	    }
	    if (course == null || course.equals("")) {
	    	errors.add("Please select a course");
	    }

	    // Generate the HTML response
	    String htmlResponse = "";
	    PrintWriter out = response.getWriter();
	    //If there are errors, display them on top of the page and show the form again
	    if (!errors.isEmpty()) {
	        response.setContentType("text/html");
	        out.println("<!DOCTYPE html>\r\n"
	        		+ "<html>\r\n"
	        		+ "<head>\r\n"
	        		+ "	<title>Select Courses from Dropdown</title>\r\n"
	        		+ "	<meta charset=\"utf-8\">\r\n"
	        		+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
	        		+ "	<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\">\r\n"
	        		+ "	<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n"
	        		+ "	<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"></script>\r\n"
	        		+ "	<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"></script>\r\n"
	        		+ "	<style>\r\n"
	        		+ "		body {\r\n"
	        		+ "			background-color: gray;\r\n"
	        		+ "		}\r\n"
	        		+ "		.container {\r\n"
	        		+ "			display: flex;\r\n"
	        		+ "			align-items: center;\r\n"
	        		+ "			justify-content: center;\r\n"
	        		+ "			height: 100vh;\r\n"
	        		+ "		}\r\n"
	        		+ "		.card {\r\n"
	        		+ "			width: 700px;\r\n"
	        		+ "			background-color: #fff;\r\n"
	        		+ "			box-shadow: 0 0 20px rgba(0,0,0,.1);\r\n"
	        		+ "			padding: 30px;\r\n"
	        		+ "			text-align: center;\r\n"
	        		+ "		}\r\n"
	        		+ "		.form-group label {\r\n"
	        		+ "			margin-right: 20px;\r\n"
	        		+ "			display: inline-block;\r\n"
	        		+ "			vertical-align: middle;\r\n"
	        		+ "		}\r\n"
	        		+ "		.form-check {\r\n"
	        		+ "			display: inline-block;\r\n"
	        		+ "			vertical-align: middle;\r\n"
	        		+ "			margin-right: 20px;\r\n"
	        		+ "		}\r\n"
	        		+ "		.form-control {\r\n"
	        		+ "			display: inline-block;\r\n"
	        		+ "			vertical-align: middle;\r\n"
	        		+ "			width: auto;\r\n"
	        		+ "		}\r\n"
	        		+ "		.btn {\r\n"
	        		+ "			display: block;\r\n"
	        		+ "			margin: 0 auto;\r\n"
	        		+ "			margin-top: 20px;\r\n"
	        		+ "		}\r\n"
	        		+ "	</style>\r\n"
	        		+ "</head>\r\n"
	        		+ "<body style=\"background-color: gray;\">");

	        out.println("<div class='alert alert-danger'>");
	        out.println("<h1>Error</h1>");
	        out.println("<ul>");
	        for (String error : errors) {
	            out.println("<li>" + error + "</li>");
	        }
	        out.println("</ul>");
	        out.println("</div>");

	        out.println("<div class=\"container\">\r\n"
	        		+ "		<div class=\"card\">\r\n"
	        		+ "			<h2>Select Courses from Dropdown</h2>\r\n"
	        		+ "			<hr>\r\n"
	        		+ "			<form action=\"Main\" method=\"post\">\r\n"
	        		+ "				<div class=\"form-group\">\r\n"
	        		+ "					<label for=\"term\" style=\"width: 125px;text-align: left;\">Select a term:</label>\r\n"
	        		+ "					<div class=\"form-check form-check-inline\">\r\n"
	        		+ "						<input type=\"radio\" class=\"form-check-input\" name=\"term\" value=\"Fall\">\r\n"
	        		+ "						<label class=\"form-check-label\" for=\"term\">Fall</label>\r\n"
	        		+ "					</div>\r\n"
	        		+ "					<div class=\"form-check form-check-inline\">\r\n"
	        		+ "						<input type=\"radio\" class=\"form-check-input\" name=\"term\" value=\"Spring\">\r\n"
	        		+ "						<label class=\"form-check-label\" for=\"term\">Spring</label>\r\n"
	        		+ "					</div>\r\n"
	        		+ "				</div>\r\n"
	        		+ "				<div class=\"form-group\">\r\n"
	        		+ "					<label for=\"course\" style=\"width: 109px;text-align: left;\">Select a course:</label>\r\n"
	        		+ "					<select class=\"form-control\" name=\"course\" id=\"course\">\r\n"
	        		+ "						<option value=\"\" disabled selected>Select your course</option>\r\n"
	        		+ "						<option value=\"Java Programming\">Java Programming</option>\r\n"
	        		+ "						<option value=\"Data Mining\">Data Mining</option>\r\n"
	        		+ "						<option value=\"Artificial Intelligence\">Artificial Intelligence</option>\r\n"
	        		+ "						<option value=\"Supply Chain\" disabled>Supply Chain (not offered)</option>\r\n"
	        		+ "					</select>\r\n"
	        		+ "				</div>\r\n"
	        		+ "				<button type=\"submit\" class=\"btn btn-primary\">Submit</button>\r\n"
	        		+ "			</form>\r\n"
	        		+ "		</div>\r\n"
	        		+ "	</div>\r\n"
	        		+ "</body>\r\n"
	        		+ "</html>");
	    } else {
	        // Generate greeting message based on term and course
	        String greeting = "You have selected " + course + " for " + term;

	        // Display greeting message
	        htmlResponse = "<div class=\"container\"><h1 style=\"font-family: sans-serif;\"\">Course Selection Display</h1><div class=\"alert alert-success mt-4\">" + greeting + "</div></div>";
	    }

	    // Send the HTML content as the response
	    out.println("<!DOCTYPE html>"
	    			+ "<html>"
	    			+ "<head>"
	    			+ "<meta charset=\"UTF-8\">"
	    			+ "<title>My Shop</title>"
	    			+ "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">"
	    			+ "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\r\n"
	    			+ "	<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>\r\n"
	    			+ "	<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>"
	    			+ "</head>"
	    			+ "<body style=\"background-color: gray;\">"
	    			+ htmlResponse
	    			+ "</body></html>");
	}

}
