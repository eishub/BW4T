package nl.tudelft.bw4t.util;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Used for storing classes in XML format and constructing Java objects from the
 * XML files.
 */
public final class XMLManager {

    /**
     * Prevents this class from being instantiated.
     */
    private XMLManager() {
    }

    /**
     * Constructs an XML file from the specified Java object.
     *
     * @param filePath  The location to store the XML file in.
     * @param xmlObject The object to convert into an XML file.
     * @throws javax.xml.bind.JAXBException Thrown if there's an error serializing the object to XML.
     * @throws java.io.FileNotFoundException    Thrown if the given XML file has not been found.
     */
    public static void toXML(final String filePath, final Object xmlObject)
            throws JAXBException, FileNotFoundException {

        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(xmlObject.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(xmlObject, file);

    }

    /**
     * Constructs a Java object from an XML file.
     *
     * @param filePath  The location of the XML file.
     * @param generatedObjectClass  The class that the constructed Java object should have.
     * @return The Java object generated from the XML file.
     * @throws javax.xml.bind.JAXBException Thrown if the given XML file can not be parsed to a BW4TClientConfig object.
     */
    public static Object fromXML(final String filePath,
            final Class<?> generatedObjectClass)
            throws JAXBException {
        File file = new File(filePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(generatedObjectClass);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(file);
    }

}
