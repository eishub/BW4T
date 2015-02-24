package nl.tudelft.bw4t.server.environment;

import repast.simphony.parameter.Parameters;
import repast.simphony.parameter.Schema;

public class BW4TParameters implements Parameters {

    /**
     * this is something of repast
     */
    public BW4TParameters() {
        // needs to be here
    }

    @Override
    public void setValue(String paramName, Object val) {

    }

    @Override
    public boolean isReadOnly(String paramName) {
        return false;
    }

    @Override
    public String getValueAsString(String paramName) {
        return "asd";
    }

    @Override
    public Object getValue(String paramName) {
        return 223;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public String getDisplayName(String paramName) {
        return "asd";
    }

    @Override
    public Parameters clone() {
        return this;
    }
}
