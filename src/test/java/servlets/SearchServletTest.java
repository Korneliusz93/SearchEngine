package servlets;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SearchServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private SearchServlet servlet;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        servlet = new SearchServlet();
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        // Set up request mock
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("query")).thenReturn("example");

        // Set up response
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Call the doGet method
       servlet.doGet(request, response);

        // Verify the response
        verify(response).setContentType("text/plain");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).write("Hello from SearchServlet doGet!");
        verify(response.getWriter()).flush();
        verify(response.getWriter()).close();
    }


}