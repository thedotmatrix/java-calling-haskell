import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person 
{
	@XmlAttribute
	String name;
	@XmlAttribute
	int age;
	@XmlElement(name = "person")
	List<Person> children;
	
	public Person()
	{
		this.children = new ArrayList<Person>();
	}
	
	// Pretty printing of recursive data structure
	public String toString()
	{
		return this.toString(0);
	}
	private String toString(int depth)
	{
		String depthtabs = "";
		for(int i=0; i < depth; i++)
		{
			depthtabs += "\t";
		}
		String out = depthtabs + name + " age " + age;
		for(Person p : children)
		{
			out += "\n" + p.toString(depth+1);
		}
		return out;
	}
}
