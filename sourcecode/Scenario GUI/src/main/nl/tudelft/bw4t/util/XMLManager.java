package nl.tudelft.bw4t.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Used for storing classes in XML format and constructing
 * Java objects from the XML files.
 * 
 * @author Nick
 *
 */
public class XMLManager {
	
	/**
	 * Constructs an XML file from the specified Java object.
	 * @param filePath The location to store the XML file in.
	 * @param xmlObject The object to convert into an XML file.
	 * @return Whether the output XML file was succesfully generated.
	 */
	public static boolean toXML(String filePath, Object xmlObject) {
		File file = new File(filePath);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(xmlObject.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(xmlObject, file);
			jaxbMarshaller.marshal(xmlObject, System.out);
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Constructs a Java object from an XML file.
	 * @param filePath The location of the XML file.
	 * @param generatedObjectClass The class that the constructed Java object should have.
	 * @return The Java object generated from the XML file.
	 */
	public static Object fromXML(String filePath, Class<?> generatedObjectClass) {
		File file = new File(filePath);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(generatedObjectClass);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
