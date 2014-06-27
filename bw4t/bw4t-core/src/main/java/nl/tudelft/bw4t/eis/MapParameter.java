package nl.tudelft.bw4t.eis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import nl.tudelft.bw4t.map.NewMap;

import org.apache.log4j.Logger;

import eis.iilang.IILObjectVisitor;
import eis.iilang.IILVisitor;
import eis.iilang.Parameter;

public class MapParameter extends Parameter {
    private static final Logger LOGGER = Logger.getLogger(MapParameter.class);
    private static final long serialVersionUID = -4547448553309283431L;

    /**
     * A reference to the Map in this parameter.
     */
    private NewMap map;

    /**
     * Make a new map parameter from a local file
     * 
     * @param mapfile
     *            the map file
     * @throws FileNotFoundException
     *             if the file does not actually exist
     * @throws JAXBException
     *             if the file cannot be read properly
     */
    public MapParameter(File mapfile) throws FileNotFoundException, JAXBException {
        this.map = NewMap.create(new FileInputStream(mapfile));
    }

    /**
     * Make a new map parameter from a {@link NewMap}
     * 
     * @param map
     *            the map to put in this parameter
     */
    public MapParameter(NewMap map) {
        assert map != null;
        this.map = map;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MapParameter)) {
            return false;
        }
        return this.map.equals(((MapParameter) obj).map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public Object clone() {
        return new MapParameter(this.map);
    }

    @Override
    protected String toXML(int depth) {
        try {
            return NewMap.toXML(this.map);
        } catch (JAXBException e) {
            LOGGER.error("Failed to convert map to XML.", e);
            return "";
        }
    }

    @Override
    public String toProlog() {
        return "";
    }

    @Override
    public void accept(IILVisitor visitor) {
    }

    @Override
    public Object accept(IILObjectVisitor visitor, Object object) {
        return null;
    }

    public NewMap getMap() {
        return this.map;
    }

}
