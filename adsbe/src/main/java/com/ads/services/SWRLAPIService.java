package com.ads.services;

import lombok.extern.log4j.Log4j2;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JMA - 22/11/2021 20:14
 **/
@Log4j2
@Service
public class SWRLAPIService {

    public List<String> process(int numberOfObjectives) {
        ArrayList<String> result = new ArrayList<>();
        try {
            // Create an OWL ontology using the OWLAPI
            OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new ClassPathResource("static/ADS.owl").getInputStream());

            // Create SQWRL query engine using the SWRLAPI
            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

            // Create and execute a SQWRL query using the SWRLAPI

            String query = "Algorithm(?alg) ^ "
                    + "minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min," + numberOfObjectives + ")"
                    + "maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max," + numberOfObjectives + ")"
                    + " -> sqwrl:select(?alg) ^ sqwrl:orderBy(?alg)";
            SQWRLResult sqwrlResult = queryEngine.runSQWRLQuery("q1", query);

            // Process the SQWRL result
            log.info("Query: \n" + query + "\n");
            log.info("Result: ");
            while (sqwrlResult.next()) {
                log.info(sqwrlResult.getNamedIndividual("alg").getShortName());
                result.add(sqwrlResult.getNamedIndividual("alg").getShortName());
            }

        } catch (OWLOntologyCreationException | IOException e) {
            log.error("Error creating OWL ontology: " + e.getMessage());
        } catch (SWRLParseException e) {
            log.error("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
        } catch (SQWRLException e) {
            log.error("Error running SWRL rule or SQWRL query: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Error starting application: " + e.getMessage());
        }
        return result;
    }
}
