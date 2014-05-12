package nl.tudelft.bw4t.server.environment;

import java.util.LinkedList;

import nl.tudelft.bw4t.map.NewMap;

import eis.eis2java.environment.AbstractEnvironment;
import eis.iilang.Action;
import eis.iilang.Percept;


public class BW4TEnvironment extends AbstractEnvironment {

    @Override
    public boolean isSupportedByEnvironment(Action arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isSupportedByType(Action arg0, String arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    public static BW4TEnvironment getInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    public LinkedList<Percept> getAllPerceptsFrom(String entity) {
        // TODO Auto-generated method stub
        return null;
    }

    public NewMap getMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public Percept performClientAction(String entity, Action action) {
        // TODO Auto-generated method stub
        return null;
    }

}
