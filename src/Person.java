import java.util.ArrayList;
import java.util.List;

public class Person 
{
	String name;
	int age;
	List<Person> children;
	
	public Person(String name, int age)
	{
		this.name = name;
		this.age = age;
		children = new ArrayList<Person>();
	}
	
	public void addChild(Person p)
	{
		this.children.add(p);
	}
	
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
