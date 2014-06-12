import javax.xml.bind.JAXBException;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class Simple 
{
	// represents the shared haskell library in the bin folder
    public interface SimpleHS extends Library
    {
    	SimpleHS INSTANCE = (SimpleHS) Native.loadLibrary("simple", SimpleHS.class);
    	// hs_init(int* pArgc, char*** pArgv)
    	void hs_init(IntByReference argc, PointerByReference argv);
    	int fibonacci_hs(int i);
    	String helloworld_hs();
    	String getjohn_hs();
    	void free_hs(String s);
    	void hs_exit();
    }

    // initializes the haskell ffi environment
    // runs a basic call to the haskell library and processes it
    // shutsdown the haskell ffi environment
    public static void main(String[] args) 
    {
        init();
        run();
        SimpleHS.INSTANCE.hs_exit();
    }
    
    // just a call to hs_init
    public static void init()
    {
    	// int* argc
        IntByReference pArgc = new IntByReference();
        pArgc.setValue(1);
        // char* argv[]
        String[] noargs = {""};
        StringArray argv = new StringArray(noargs);
        // char*** pArgv
        PointerByReference pArgv = new PointerByReference();
        pArgv.setPointer(argv);
        SimpleHS.INSTANCE.hs_init(pArgc, pArgv);
    }
    
    // gets the xml string for a person (agreed data structure)
    // TODO needs xml schema
    public static void run()
    {
    	System.out.println("Getting haskell cstring...");
        String s = SimpleHS.INSTANCE.getjohn_hs();
        System.out.println("Freeing haskell cstring...");
        SimpleHS.INSTANCE.free_hs(s);
        System.out.println("String = " + s);
        System.out.println("Parsing XML String...");
        try
		{
			XmlProcessor<Person> processor = new XmlProcessor<Person>(Person.class);
			Person p = processor.unwrap(s);
			System.out.println(p.toString());
		} 
        catch (JAXBException e)
		{
        	System.err.println("Could not parse xml string into a person");
			e.printStackTrace();
		}
    }
}