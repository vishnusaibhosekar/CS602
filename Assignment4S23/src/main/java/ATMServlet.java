import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class ATMServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ATMServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Get the absolute path of the HTML file
        String path = getServletContext().getRealPath("/ATM.html");

        // Read the contents of the file into a string
        String html = new String(Files.readAllBytes(Paths.get(path)));

        // Send the HTML content as the response
        response.getWriter().print(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String transaction = request.getParameter("transaction");
        String deposit = request.getParameter("deposit");
        String withdraw = request.getParameter("withdraw");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost/atm?user=root");

            // Prepare a PreparedStatement object using the SQL query and set the userID parameter
            stmt = conn.prepareStatement("SELECT * FROM accountinfo WHERE userID = ?");
            stmt.setInt(1, userID);

            // Execute the query using the executeQuery() method of the PreparedStatement object
            rs = stmt.executeQuery();

            // Check if the ResultSet returned by the query contains any rows
            int size = 0;
            int accNum = 0;
            String name = "defaultName";
            double bal = 0;
            if (rs != null) {
                rs.last(); // moves cursor to the last row
                size = rs.getRow(); // get row id
                if (size > 0) {
                    accNum = Integer.parseInt(rs.getString(1));
                    name = rs.getString(2);
                    bal = Double.parseDouble(rs.getString(3));
                }
            }

            System.out.println(accNum + " " + name + " " + bal);

            System.out.println(userID + " " + transaction + " " + deposit + " " + withdraw);

            PrintWriter out = response.getWriter();

            if (transaction == null) {
                if (size == 0) {
                    out.print("<!DOCTYPE html>\r\n" +
                        "<html>\r\n" +
                        "    <head>\r\n" +
                        "        <meta charset=\"utf-8\">\r\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                        "        <title>NJIT Credit Union</title>\r\n" +
                        "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/css/bulma.min.css\">\r\n" +
                        "        <script>\r\n" +
                        "            function enableTextBox(elem) {\r\n" +
                        "                var depositInput = document.getElementById(\"deposit\");\r\n" +
                        "                var withdrawInput = document.getElementById(\"withdraw\");\r\n" +
                        "                if (elem.value == \"deposit\") {\r\n" +
                        "                    depositInput.disabled = false;\r\n" +
                        "                    withdrawInput.disabled = true;\r\n" +
                        "                } else {\r\n" +
                        "                    depositInput.disabled = true;\r\n" +
                        "                    withdrawInput.disabled = false;\r\n" +
                        "                }\r\n" +
                        "            }\r\n" +
                        "        </script>\r\n" +
                        "    </head>\r\n" +
                        "    <body>\r\n" +
                        "        <section class=\"hero is-info is-fixed-top\">\r\n" +
                        "            <div class=\"hero-body is-spaced\">\r\n" +
                        "                <div class=\"container\">\r\n" +
                        "                    <h1 class=\"title has-text-centered is-size-1\"> NJIT Credit Union </h1>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "        <section class=\"section has-background is-primary\">\r\n" +
                        "            <div class=\"container\">\r\n" +
                        "                <div class=\"columns is-centered is-vcentered\">\r\n" +
                        "                    <div class=\"column is-two-thirds\">\r\n" +
                        "                        <form action=\"ATMServlet\" method=\"post\">\r\n" +
                        "                            <article class=\"message is-danger\">\r\n" +
                        "                                <div class=\"message-body\">\r\n" +
                        "                                    <p>Your authentication has failed, please try again</p>\r\n" +
                        "                                </div>\r\n" +
                        "                            </article>\r\n" +
                        "                            <div class=\"columns is-centered\">\r\n" +
                        "                                <div class=\"column is-3 has-text-right is-size-3\"> UserID: </div>\r\n" +
                        "                                <div class=\"column control\">\r\n" +
                        "                                    <input class=\"input\" type=\"text\" name=\"userID\" placeholder=\"Enter User ID\">\r\n" +
                        "                                </div>\r\n" +
                        "                                <div class=\"column\">\r\n" +
                        "                                    <button class=\"button is-dark\">Submit</button>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                            <div class=\"card has-background-grey-light\" style=\"pointer-events: none;\">\r\n" +
                        "                                <header class=\"card-header\">\r\n" +
                        "                                    <p class=\"card-header-title\"> Transaction </p>\r\n" +
                        "                                </header>\r\n" +
                        "                                <div class=\"card-content\">\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Deposit: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"deposit\" id=\"deposit\" placeholder=\"Enter deposit amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"deposit\" onclick=\"enableTextBox(this)\"> Deposit\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Withdraw: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"withdraw\" id=\"withdraw\" placeholder=\"Enter withdraw amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"withdraw\" onclick=\"enableTextBox(this)\"> Withdraw\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-three-fifths is-offset-one-fifth\">\r\n" +
                        "                                            <button class=\"button is-dark\">Submit Transaction</button>\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                        </form>\r\n" +
                        "                    </div>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "    </body>\r\n" +
                        "</html>");
                } else {
                    out.print("<!DOCTYPE html>\r\n" +
                        "<html>\r\n" +
                        "    <head>\r\n" +
                        "        <meta charset=\"utf-8\">\r\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                        "        <title>NJIT Credit Union</title>\r\n" +
                        "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/css/bulma.min.css\">\r\n" +
                        "        <script>\r\n" +
                        "            function enableTextBox(elem) {\r\n" +
                        "                var depositInput = document.getElementById(\"deposit\");\r\n" +
                        "                var withdrawInput = document.getElementById(\"withdraw\");\r\n" +
                        "                if (elem.value == \"deposit\") {\r\n" +
                        "                    depositInput.disabled = false;\r\n" +
                        "                    withdrawInput.disabled = true;\r\n" +
                        "                } else {\r\n" +
                        "                    depositInput.disabled = true;\r\n" +
                        "                    withdrawInput.disabled = false;\r\n" +
                        "                }\r\n" +
                        "            }\r\n" +
                        "        </script>\r\n" +
                        "    </head>\r\n" +
                        "    <body>\r\n" +
                        "        <section class=\"hero is-info is-fixed-top\">\r\n" +
                        "            <div class=\"hero-body is-spaced\">\r\n" +
                        "                <div class=\"container\">\r\n" +
                        "                    <h1 class=\"title has-text-centered is-size-1\"> NJIT Credit Union </h1>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "        <section class=\"section has-background is-primary\">\r\n" +
                        "            <div class=\"container\">\r\n" +
                        "                <div class=\"columns is-centered is-vcentered\">\r\n" +
                        "                    <div class=\"column is-two-thirds\">\r\n" +
                        "                        <form action=\"ATMServlet\" method=\"post\">\r\n" +
                        "                            <article class=\"message is-primary\">\r\n" +
                        "                                <div class=\"message-body\">\r\n" +
                        "                                    <p>Your authentication is successful</p>\r\n" +
                        "                                </div>\r\n" +
                        "                            </article>\r\n" +
                        "                            <div class=\"columns is-centered\" style=\"pointer-events: none;\">\r\n" +
                        "                                <div class=\"column is-3 has-text-right is-size-3\"> UserID: </div>\r\n" +
                        "                                <div class=\"column control\">\r\n" +
                        "                                    <input class=\"input\" type=\"text\" name=\"userID\" placeholder=\"Enter User ID\" value=\"" + userID + "\">\r\n" +
                        "                                </div>\r\n" +
                        "                                <div class=\"column\">\r\n" +
                        "                                    <button class=\"button is-dark\">Submit</button>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                            <article class=\"message is-info\">\r\n" +
                        "							  <div class=\"message-body\">\r\n" +
                        "							    Balance is " + bal + "\r\n" +
                        "							  </div>\r\n" +
                        "							</article>\r\n" +
                        "                            <div class=\"card has-background-grey-light\">\r\n" +
                        "                                <header class=\"card-header\">\r\n" +
                        "                                    <p class=\"card-header-title\"> Transaction </p>\r\n" +
                        "                                </header>\r\n" +
                        "                                <div class=\"card-content\">\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Deposit: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"deposit\" id=\"deposit\" placeholder=\"Enter deposit amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"deposit\" onclick=\"enableTextBox(this)\"> Deposit\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Withdraw: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"withdraw\" id=\"withdraw\" placeholder=\"Enter withdraw amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"withdraw\" onclick=\"enableTextBox(this)\"> Withdraw\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-three-fifths is-offset-one-fifth\">\r\n" +
                        "                                            <button class=\"button is-dark\">Submit Transaction</button>\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                        </form>\r\n" +
                        "                    </div>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "    </body>\r\n" +
                        "</html>");
                }
            } else {
            	Double newBal = 0.0;
                if (transaction.equals("deposit")) {
                    newBal = bal + Double.parseDouble(deposit);
                    String s = "UPDATE `accountinfo` SET `Balance`=" + newBal + " WHERE `userID`=" + userID + ";";
                    if (name != null)
                        stmt.execute(s);

                    out.print("<!DOCTYPE html>\r\n" +
                        "<html>\r\n" +
                        "    <head>\r\n" +
                        "        <meta charset=\"utf-8\">\r\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                        "        <title>NJIT Credit Union</title>\r\n" +
                        "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/css/bulma.min.css\">\r\n" +
                        "        <script>\r\n" +
                        "            function enableTextBox(elem) {\r\n" +
                        "                var depositInput = document.getElementById(\"deposit\");\r\n" +
                        "                var withdrawInput = document.getElementById(\"withdraw\");\r\n" +
                        "                if (elem.value == \"deposit\") {\r\n" +
                        "                    depositInput.disabled = false;\r\n" +
                        "                    withdrawInput.disabled = true;\r\n" +
                        "                } else {\r\n" +
                        "                    depositInput.disabled = true;\r\n" +
                        "                    withdrawInput.disabled = false;\r\n" +
                        "                }\r\n" +
                        "            }\r\n" +
                        "        </script>\r\n" +
                        "    </head>\r\n" +
                        "    <body>\r\n" +
                        "        <section class=\"hero is-info is-fixed-top\">\r\n" +
                        "            <div class=\"hero-body is-spaced\">\r\n" +
                        "                <div class=\"container\">\r\n" +
                        "                    <h1 class=\"title has-text-centered is-size-1\"> NJIT Credit Union </h1>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "        <section class=\"section has-background is-primary\">\r\n" +
                        "            <div class=\"container\">\r\n" +
                        "                <div class=\"columns is-centered is-vcentered\">\r\n" +
                        "                    <div class=\"column is-two-thirds\">\r\n" +
                        "                        <form action=\"ATMServlet\" method=\"post\">\r\n" +
                        "                            <div class=\"columns is-centered\" style=\"pointer-events: none;\">\r\n" +
                        "                                <div class=\"column is-3 has-text-right is-size-3\"> UserID: </div>\r\n" +
                        "                                <div class=\"column control\">\r\n" +
                        "                                    <input class=\"input\" type=\"text\" name=\"userID\" placeholder=\"Enter User ID\" value=\"" + userID + "\">\r\n" +
                        "                                </div>\r\n" +
                        "                                <div class=\"column\">\r\n" +
                        "                                    <button class=\"button is-dark\">Submit</button>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                            <article class=\"message is-primary\">\r\n" +
                        "							  <div class=\"message-body\">\r\n" +
                        "							    Deposit Successful! Balance Updated! New Balance is " + newBal + "\r\n" +
                        "							  </div>\r\n" +
                        "							</article>\r\n" +
                        "                            <div class=\"card has-background-grey-light\">\r\n" +
                        "                                <header class=\"card-header\">\r\n" +
                        "                                    <p class=\"card-header-title\"> Transaction </p>\r\n" +
                        "                                </header>\r\n" +
                        "                                <div class=\"card-content\">\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Deposit: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"deposit\" id=\"deposit\" placeholder=\"Enter deposit amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"deposit\" onclick=\"enableTextBox(this)\"> Deposit\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-3 has-text-right is-size-5\"> Withdraw: </div>\r\n" +
                        "                                        <div class=\"column control\">\r\n" +
                        "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"withdraw\" id=\"withdraw\" placeholder=\"Enter withdraw amount\" disabled>\r\n" +
                        "                                        </div>\r\n" +
                        "                                        <div class=\"column\">\r\n" +
                        "                                            <input type=\"radio\" name=\"transaction\" value=\"withdraw\" onclick=\"enableTextBox(this)\"> Withdraw\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                    <div class=\"columns is-centered\">\r\n" +
                        "                                        <div class=\"column is-three-fifths is-offset-one-fifth\">\r\n" +
                        "                                            <button class=\"button is-dark\">Submit Transaction</button>\r\n" +
                        "                                        </div>\r\n" +
                        "                                    </div>\r\n" +
                        "                                </div>\r\n" +
                        "                            </div>\r\n" +
                        "                        </form>\r\n" +
                        "                    </div>\r\n" +
                        "                </div>\r\n" +
                        "            </div>\r\n" +
                        "        </section>\r\n" +
                        "    </body>\r\n" +
                        "</html>");
                } else {
                	if (bal > Double.parseDouble(withdraw)) {
                		newBal = bal - Double.parseDouble(withdraw);
                		String s = "UPDATE `accountinfo` SET `Balance`=" + newBal + " WHERE `userID`=" + userID + ";";
                        if (name != null)
                            stmt.execute(s);
                		
                		out.print("<!DOCTYPE html>\r\n" +
                                "<html>\r\n" +
                                "    <head>\r\n" +
                                "        <meta charset=\"utf-8\">\r\n" +
                                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                                "        <title>NJIT Credit Union</title>\r\n" +
                                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/css/bulma.min.css\">\r\n" +
                                "        <script>\r\n" +
                                "            function enableTextBox(elem) {\r\n" +
                                "                var depositInput = document.getElementById(\"deposit\");\r\n" +
                                "                var withdrawInput = document.getElementById(\"withdraw\");\r\n" +
                                "                if (elem.value == \"deposit\") {\r\n" +
                                "                    depositInput.disabled = false;\r\n" +
                                "                    withdrawInput.disabled = true;\r\n" +
                                "                } else {\r\n" +
                                "                    depositInput.disabled = true;\r\n" +
                                "                    withdrawInput.disabled = false;\r\n" +
                                "                }\r\n" +
                                "            }\r\n" +
                                "        </script>\r\n" +
                                "    </head>\r\n" +
                                "    <body>\r\n" +
                                "        <section class=\"hero is-info is-fixed-top\">\r\n" +
                                "            <div class=\"hero-body is-spaced\">\r\n" +
                                "                <div class=\"container\">\r\n" +
                                "                    <h1 class=\"title has-text-centered is-size-1\"> NJIT Credit Union </h1>\r\n" +
                                "                </div>\r\n" +
                                "            </div>\r\n" +
                                "        </section>\r\n" +
                                "        <section class=\"section has-background is-primary\">\r\n" +
                                "            <div class=\"container\">\r\n" +
                                "                <div class=\"columns is-centered is-vcentered\">\r\n" +
                                "                    <div class=\"column is-two-thirds\">\r\n" +
                                "                        <form action=\"ATMServlet\" method=\"post\">\r\n" +
                                "                            <div class=\"columns is-centered\" style=\"pointer-events: none;\">\r\n" +
                                "                                <div class=\"column is-3 has-text-right is-size-3\"> UserID: </div>\r\n" +
                                "                                <div class=\"column control\">\r\n" +
                                "                                    <input class=\"input\" type=\"text\" name=\"userID\" placeholder=\"Enter User ID\" value=\"" + userID + "\">\r\n" +
                                "                                </div>\r\n" +
                                "                                <div class=\"column\">\r\n" +
                                "                                    <button class=\"button is-dark\">Submit</button>\r\n" +
                                "                                </div>\r\n" +
                                "                            </div>\r\n" +
                                "                            <article class=\"message is-primary\">\r\n" +
                                "							  <div class=\"message-body\">\r\n" +
                                "							    Withdraw Successful! Balance Updated! New Balance is " + newBal + "\r\n" +
                                "							  </div>\r\n" +
                                "							</article>\r\n" +
                                "                            <div class=\"card has-background-grey-light\">\r\n" +
                                "                                <header class=\"card-header\">\r\n" +
                                "                                    <p class=\"card-header-title\"> Transaction </p>\r\n" +
                                "                                </header>\r\n" +
                                "                                <div class=\"card-content\">\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-3 has-text-right is-size-5\"> Deposit: </div>\r\n" +
                                "                                        <div class=\"column control\">\r\n" +
                                "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"deposit\" id=\"deposit\" placeholder=\"Enter deposit amount\" disabled>\r\n" +
                                "                                        </div>\r\n" +
                                "                                        <div class=\"column\">\r\n" +
                                "                                            <input type=\"radio\" name=\"transaction\" value=\"deposit\" onclick=\"enableTextBox(this)\"> Deposit\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-3 has-text-right is-size-5\"> Withdraw: </div>\r\n" +
                                "                                        <div class=\"column control\">\r\n" +
                                "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"withdraw\" id=\"withdraw\" placeholder=\"Enter withdraw amount\" disabled>\r\n" +
                                "                                        </div>\r\n" +
                                "                                        <div class=\"column\">\r\n" +
                                "                                            <input type=\"radio\" name=\"transaction\" value=\"withdraw\" onclick=\"enableTextBox(this)\"> Withdraw\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-three-fifths is-offset-one-fifth\">\r\n" +
                                "                                            <button class=\"button is-dark\">Submit Transaction</button>\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                </div>\r\n" +
                                "                            </div>\r\n" +
                                "                        </form>\r\n" +
                                "                    </div>\r\n" +
                                "                </div>\r\n" +
                                "            </div>\r\n" +
                                "        </section>\r\n" +
                                "    </body>\r\n" +
                                "</html>");
                	} else {
                		out.print("<!DOCTYPE html>\r\n" +
                                "<html>\r\n" +
                                "    <head>\r\n" +
                                "        <meta charset=\"utf-8\">\r\n" +
                                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" +
                                "        <title>NJIT Credit Union</title>\r\n" +
                                "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.3/css/bulma.min.css\">\r\n" +
                                "        <script>\r\n" +
                                "            function enableTextBox(elem) {\r\n" +
                                "                var depositInput = document.getElementById(\"deposit\");\r\n" +
                                "                var withdrawInput = document.getElementById(\"withdraw\");\r\n" +
                                "                if (elem.value == \"deposit\") {\r\n" +
                                "                    depositInput.disabled = false;\r\n" +
                                "                    withdrawInput.disabled = true;\r\n" +
                                "                } else {\r\n" +
                                "                    depositInput.disabled = true;\r\n" +
                                "                    withdrawInput.disabled = false;\r\n" +
                                "                }\r\n" +
                                "            }\r\n" +
                                "        </script>\r\n" +
                                "    </head>\r\n" +
                                "    <body>\r\n" +
                                "        <section class=\"hero is-info is-fixed-top\">\r\n" +
                                "            <div class=\"hero-body is-spaced\">\r\n" +
                                "                <div class=\"container\">\r\n" +
                                "                    <h1 class=\"title has-text-centered is-size-1\"> NJIT Credit Union </h1>\r\n" +
                                "                </div>\r\n" +
                                "            </div>\r\n" +
                                "        </section>\r\n" +
                                "        <section class=\"section has-background is-primary\">\r\n" +
                                "            <div class=\"container\">\r\n" +
                                "                <div class=\"columns is-centered is-vcentered\">\r\n" +
                                "                    <div class=\"column is-two-thirds\">\r\n" +
                                "                        <form action=\"ATMServlet\" method=\"post\">\r\n" +
                                "                            <div class=\"columns is-centered\" style=\"pointer-events: none;\">\r\n" +
                                "                                <div class=\"column is-3 has-text-right is-size-3\"> UserID: </div>\r\n" +
                                "                                <div class=\"column control\">\r\n" +
                                "                                    <input class=\"input\" type=\"text\" name=\"userID\" placeholder=\"Enter User ID\" value=\"" + userID + "\">\r\n" +
                                "                                </div>\r\n" +
                                "                                <div class=\"column\">\r\n" +
                                "                                    <button class=\"button is-dark\">Submit</button>\r\n" +
                                "                                </div>\r\n" +
                                "                            </div>\r\n" +
                                "                            <article class=\"message is-danger\">\r\n" +
                                "							  <div class=\"message-body\">\r\n" +
                                "							    There is insufficient funds, please try a smaller amount\r\n" +
                                "							  </div>\r\n" +
                                "							</article>\r\n" +
                                "                            <div class=\"card has-background-grey-light\">\r\n" +
                                "                                <header class=\"card-header\">\r\n" +
                                "                                    <p class=\"card-header-title\"> Transaction </p>\r\n" +
                                "                                </header>\r\n" +
                                "                                <div class=\"card-content\">\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-3 has-text-right is-size-5\"> Deposit: </div>\r\n" +
                                "                                        <div class=\"column control\">\r\n" +
                                "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"deposit\" id=\"deposit\" placeholder=\"Enter deposit amount\" disabled>\r\n" +
                                "                                        </div>\r\n" +
                                "                                        <div class=\"column\">\r\n" +
                                "                                            <input type=\"radio\" name=\"transaction\" value=\"deposit\" onclick=\"enableTextBox(this)\"> Deposit\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-3 has-text-right is-size-5\"> Withdraw: </div>\r\n" +
                                "                                        <div class=\"column control\">\r\n" +
                                "                                            <input class=\"input\" type=\"number\" step=\"0.01\" name=\"withdraw\" id=\"withdraw\" placeholder=\"Enter withdraw amount\" disabled>\r\n" +
                                "                                        </div>\r\n" +
                                "                                        <div class=\"column\">\r\n" +
                                "                                            <input type=\"radio\" name=\"transaction\" value=\"withdraw\" onclick=\"enableTextBox(this)\"> Withdraw\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                    <div class=\"columns is-centered\">\r\n" +
                                "                                        <div class=\"column is-three-fifths is-offset-one-fifth\">\r\n" +
                                "                                            <button class=\"button is-dark\">Submit Transaction</button>\r\n" +
                                "                                        </div>\r\n" +
                                "                                    </div>\r\n" +
                                "                                </div>\r\n" +
                                "                            </div>\r\n" +
                                "                        </form>\r\n" +
                                "                    </div>\r\n" +
                                "                </div>\r\n" +
                                "            </div>\r\n" +
                                "        </section>\r\n" +
                                "    </body>\r\n" +
                                "</html>");
                	}
                }
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // Close the ResultSet, PreparedStatement, and Connection objects
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }
}