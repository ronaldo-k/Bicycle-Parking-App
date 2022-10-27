package persistence;

import org.json.JSONObject;
import persistence.parsers.TheftReportParser;

public class TheftReportsJsonReader extends ArrayJsonReader {
    TheftReportParser theftReportParser = new TheftReportParser();

    public TheftReportsJsonReader(String fileName) {
        super(fileName);
    }

    @Override
    public Saveable parseSaveable(JSONObject jsonObject) {
        return theftReportParser.parseSaveable(jsonObject);
    }


}
