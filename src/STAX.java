import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

// Event or Iterator based Stax XML processing
// Stax is not as pretty as I hoped
public class STAX 
{
	XMLInputFactory factory;

	public STAX()
	{
		this.factory = XMLInputFactory.newInstance();
	}

	public Person process(String xmlsource) throws XMLStreamException
	{
		Person root = null;
		List<Person> childstack = new ArrayList<Person>();
		XMLEventReader reader = this.factory.createXMLEventReader(new StringReader(xmlsource));
		while(reader.hasNext())
		{
			XMLEvent e = reader.nextEvent();
			switch (e.getEventType()) 
			{
			case XMLEvent.START_ELEMENT:
				StartElement se = e.asStartElement();
				String name = se.getAttributeByName(new QName("NAME")).getValue();
				int age = Integer.valueOf(se.getAttributeByName(new QName("AGE")).getValue());
				Person p = new Person(name, age);
				if(childstack.isEmpty())
				{
					root = p;
				}
				else
				{
					childstack.get(0).addChild(p);
				}
				childstack.add(0, p);
				break;
			case XMLEvent.END_ELEMENT:
				childstack.remove(0);
				break;
			}
		}
		return root;
	}
}
