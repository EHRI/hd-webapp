package eu.ehri.helpdesk.nlp;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WorkflowTest {

    @Test
    public void testProcessQuery() throws Exception {
        Map<String, Double> scores = Workflow.processQuery("Yad Vashem", Helpers.getInstitutions().keySet());
        assertEquals(1, scores.size());
        assertNotNull(scores.get("il-002798"));
    }

    @Test
    public void testProcessQuery2() throws Exception {
        Map<String, Double> scores = Workflow.processQuery("Wiener Library", Helpers.getInstitutions().keySet());
        assertEquals(2, scores.size());
        assertNotNull(scores.get("gb-003348"));
        assertNotNull(scores.get("de-002624"));
    }

    @Test
    public void testProcessQuery3() throws Exception {
        Map<String, Double> scores = Workflow.processQuery("BLIBIIDISYUE", Helpers.getInstitutions().keySet());
        assertTrue(scores.isEmpty());
    }
}