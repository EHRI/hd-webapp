package eu.ehri.helpdesk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import eu.ehri.helpdesk.nlp.Workflow;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mike Bryant (http://github.com/mikesname)
 */
public class HelpdeskServlet extends HttpServlet {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String QUERY_PARAM = "q";

    private static final TypeReference<HashMap<String, String>> mapTypeRef =
            new TypeReference<HashMap<String, String>>() {
    };

    public Map<String, String> getInstitutions() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/institutions.json");
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>() {
        };
        try {
            return mapper.readValue(inputStream, typeReference);
        } finally {
            inputStream.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> institutions = getInstitutions();
        String parameter = request.getParameter(QUERY_PARAM);
        response.setContentType("application/json; charset=UTF-8");
        if (parameter == null || parameter.isEmpty()) {
            mapper.writeValue(response.getWriter(), institutions);
        } else {
            try {
                Map<String, Double> result = Workflow.processQuery(parameter, institutions.keySet());
                mapper.writeValue(response.getWriter(), result);
            } catch (XMLStreamException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }
}
