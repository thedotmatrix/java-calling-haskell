import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

// generic XML processor
public class XmlProcessor<T>
{
	JAXBContext context;
	
	// give it a class as context, and it will attempt to parse any xml data into that class
	public XmlProcessor(Class<?> clazz) throws JAXBException
	{
		this.context = JAXBContext.newInstance(clazz);
	}
	
	@SuppressWarnings("unchecked")
	// its either suppress the type-erasure warning in one place or cast everywhere this method is called
	public T unwrap(String xml) throws JAXBException
	{
		Unmarshaller unwrapper = context.createUnmarshaller(); 
		T object = (T) unwrapper.unmarshal(new StringReader(xml));
		return object;
	}
}
