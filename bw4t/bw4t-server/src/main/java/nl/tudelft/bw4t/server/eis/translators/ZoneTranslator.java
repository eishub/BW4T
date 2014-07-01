package nl.tudelft.bw4t.server.eis.translators;

import eis.eis2java.exception.TranslationException;
import eis.eis2java.translation.Java2Parameter;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import eis.iilang.ParameterList;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.bw4t.server.model.zone.Zone;

/**
 * Translates {@link Zone} into a list of {@link Parameter} specifying the navpoint id, name, location and a list of
 * neighbour names.
 */
public class ZoneTranslator implements Java2Parameter<Zone> {

    @Override
    public Parameter[] translate(Zone zone) throws TranslationException {
        List<Parameter> params = new ArrayList<Parameter>();
        params.add(new Numeral(zone.getId()));
        params.add(new Identifier(zone.getName()));
        params.add(new Numeral(zone.getLocation().getX()));
        params.add(new Numeral(zone.getLocation().getY()));
        ParameterList neighbours = new ParameterList();
        for (Zone neighbour : zone.getNeighbours()) {
            neighbours.add(new Identifier(neighbour.getName()));
        }
        params.add(neighbours);

        return params.toArray(new Parameter[params.size()]);
    }

    @Override
    public Class<? extends Zone> translatesFrom() {
        return Zone.class;
    }

}
