package eu.ehri.helpdesk;


import com.fasterxml.jackson.databind.ObjectMapper;
import eu.ehri.helpdesk.nlp.Workflow;
import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class HelpdeskServletTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @TestSubject
    private static final HelpdeskServlet servlet = new HelpdeskServlet();

    @Test
    public void testGetInstitutions() throws Exception {
        assertFalse(servlet.getInstitutions().isEmpty());
    }

    @Test
    public void testDoGetWithoutQuery() throws Exception {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter("q")).andReturn(null);
        HttpServletResponse response = createMock(HttpServletResponse.class);
        PrintWriter writer = createMock(PrintWriter.class);
        mapper.writeValue(writer, servlet.getInstitutions());
        response.setContentType("application/json; charset=UTF-8");
        expectLastCall();
        expect(response.getWriter()).andReturn(writer);
        replay(request, response, writer);
        servlet.doGet(request, response);
        verify(request, response, writer);
    }


    @Test
    public void testDoGetWithQuery() throws Exception {
        String query = "Yad Vashem";
        Map<String,Double> result = Workflow.processQuery(query, servlet.getInstitutions().keySet());

        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getParameter("q")).andReturn(query);

        HttpServletResponse response = createMock(HttpServletResponse.class);
        response.setContentType("application/json; charset=UTF-8");
        expectLastCall();
        PrintWriter writer = createMock(PrintWriter.class);
        mapper.writeValue(writer, result);
        expect(response.getWriter()).andReturn(writer);

        replay(request, response, writer);
        servlet.doGet(request, response);
        verify(request, response, writer);
    }
}