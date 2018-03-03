package nl.tudelft.bw4t.server.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;
import nl.tudelft.bw4t.server.model.zone.Zone;

/**
 * Translates {@link Zone} into a list of {@link Parameter} specifying the navpoint id, name, location and a list of
 * neighbour names.
 */
public class ZoneTranslator implements Java2Parameter<Zone> {

    @Override
    public Parameter[] translate(Zone zone) throws TranslationException {
        Parameter[] params = new Parameter[3]; //new Parameter[5];
        params[0] = new Numeral(zone.getId());
        params[1] = new Identifier(zone.getName());
        //params[2] = new Numeral(zone.getLocation().getX());
        //params[3] = new Numeral(zone.getLocation().getY());
        ParameterList neighbours = new ParameterList();
        for (Zone neighbour : zone.getNeighbours()) {
            neighbours.add(new Identifier(neighbour.getName()));
        }
        params[2] = neighbours; //params[4] = neighbours;
       
        return params;
    }

    @Override
    public Class<? extends Zone> translatesFrom() {
        return Zone.class;
    }

}
