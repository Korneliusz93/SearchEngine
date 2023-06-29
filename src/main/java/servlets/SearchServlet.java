package servlets;

import Application.App;
import dbconnectors.DriverSQLite;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private App app; // Instance of the App class

    @Override
    public void init() throws ServletException {
        super.init();
        app = App.getInstance(); // Retrieve the singleton instance
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Logika wyszukiwania
        String query = request.getParameter("query");

        List<String> results = new ArrayList<>();
        List<String> definitions = new ArrayList<>();
        if (query == null) {
            // Display the search form to the user
            request.getRequestDispatcher("search-form.jsp").forward(request, response);
        } else {
            try {
                //rule to sort out already created databases
                DriverSQLite.createTable("search_hits.db");
                if (new File(app.indexDir).list().length == 1)
                    app.createIndex();
                results = app.search(query);
                definitions = app.getDefinitions(query);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Ustaw atrybut z wynikami na żądaniu
            request.setAttribute("results", results);
            request.setAttribute("definitions",definitions);
            // Przekieruj do strony JSP
            request.getRequestDispatcher("results.jsp").forward(request, response);
            //response.sendRedirect("results.jsp");


        }

    }

}
