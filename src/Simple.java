import javax.xml.stream.XMLStreamException;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class Simple 
{
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

    public static void main(String[] args) 
    {
        init();
        run();
        SimpleHS.INSTANCE.hs_exit();
    }
    
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
    
    public static void run()
    {
    	System.out.println("Getting haskell cstring...");
        String s = SimpleHS.INSTANCE.getjohn_hs();
        System.out.println("Freeing haskell cstring...");
        SimpleHS.INSTANCE.free_hs(s);
        System.out.println("String = " + s);
        System.out.println("Parsing XML String...");
        STAX xml = new STAX();
        try 
        {
			Person p = xml.process(s);
			System.out.println(p.toString());
		} 
        catch (XMLStreamException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}