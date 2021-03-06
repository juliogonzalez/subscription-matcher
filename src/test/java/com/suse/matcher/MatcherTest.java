package com.suse.matcher;

import static org.junit.Assert.assertEquals;

import com.suse.matcher.json.JsonInput;
import com.suse.matcher.json.JsonOutput;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Tests {@link Matcher}.
 */
@RunWith(Parameterized.class)
public class MatcherTest {

    /** The matcher object under test. */
    private Matcher matcher;

    /** List of systems in the current test run. */
    private JsonInput input;

    /** The expected output. */
    private JsonOutput expectedOutput;

    /** The scenario number. */
    private int scenarioNumber;

    /**
     * Loads test data, instantiating multiple {@link MatcherTest} objects
     * with files loaded from resources/subscriptions* JSON files.
     *
     * @return a collection of parameters to the constructor of this class
     * @throws Exception in case anything goes wrong
     */
    @Parameters
    public static Collection<Object[]> loadTestData() throws Exception {
        JsonIO io = new JsonIO();
        Collection<Object[]> result = new LinkedList<>();
        int i = 1;
        boolean moreFiles = true;
        while (moreFiles) {
            try {
                result.add(new Object[] {
                    io.loadInput(getString(i, "input.json")),
                    io.loadOutput(getString(i, "output.json")),
                    i
                });
                i++;
            }
            catch (FileNotFoundException e) {
                moreFiles = false;
            }
        }
        return result;
    }

    /**
     * Returns a string for a JSON scenario file
     *
     * @param scenarioNumber the i
     * @param fileName the filename
     * @return the string
     * @throws IOException if an unexpected condition happens
     */
    private static String getString(int scenarioNumber, String fileName) throws IOException {
        InputStream is = MatcherTest.class.getResourceAsStream("/scenario" + scenarioNumber
                + "/" + fileName);
        if (is == null) {
            throw new FileNotFoundException();
        }
        return IOUtils.toString(is);
    }

    /**
     * Instantiates a new Matcher test.
     *
     * @param inputIn a JSON input data blob
     * @param expectedOutputIn the expected output
     * @param scenarioNumberIn the scenario number
     */
    public MatcherTest(JsonInput inputIn, JsonOutput expectedOutputIn, int scenarioNumberIn) {
        matcher = new Matcher(true);
        input = inputIn;
        expectedOutput = expectedOutputIn;
        scenarioNumber = scenarioNumberIn;
        Drools.resetIdMap();
    }

    /**
     * Tests against scenario data.
     * @throws Exception in case anything goes wrong
     */
    @Test
    public void test() throws Exception {
        Log4J.initConsoleLogging();

        JsonOutput actualOutput = FactConverter.convertToOutput(matcher.match(input));

        JsonIO io = new JsonIO();

        assertEquals("scenario" + scenarioNumber + " timestamp",
                io.toJson(expectedOutput.getTimestamp()),
                io.toJson(actualOutput.getTimestamp()));
        assertEquals("scenario" + scenarioNumber + " matches",
                io.toJson(expectedOutput.getMatches()),
                io.toJson(actualOutput.getMatches()));
        assertEquals("scenario" + scenarioNumber + " subscriptionPolices",
                io.toJson(expectedOutput.getSubscriptionPolicies()),
                io.toJson(actualOutput.getSubscriptionPolicies()));
        assertEquals("scenario" + scenarioNumber + " messages",
                io.toJson(expectedOutput.getMessages()),
                io.toJson(actualOutput.getMessages()));
    }
}
