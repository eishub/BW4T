package nl.tudelft.bw4t.server.environment;

import repast.simphony.parameter.Parameters;
import repast.simphony.parameter.Schema;

public class BW4TParameters implements Parameters  {

	public BW4TParameters() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setValue(String paramName, Object val) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly(String paramName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValueAsString(String paramName) {
		// TODO Auto-generated method stub
		return "asd";
	}

	@Override
	public Object getValue(String paramName) {
		// TODO Auto-generated method stub
		return 223;
	}

	@Override
	public Schema getSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName(String paramName) {
		// TODO Auto-generated method stub
		return "asd";
	}

	@Override
	public Parameters clone() {
		// TODO Auto-generated method stub
		return this;
	}
}
