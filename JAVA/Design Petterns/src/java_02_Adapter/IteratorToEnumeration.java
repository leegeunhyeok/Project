package java_02_Adapter;

import java.util.Enumeration;
import java.util.Iterator;

public class IteratorToEnumeration implements Enumeration<String>{
    private Iterator<String> iter;
    
    public IteratorToEnumeration(Iterator<String> iter) {
        this.iter = iter;
    }
    
    public boolean hasMoreElements() {
        return iter.hasNext();
    }
    
    public String nextElement() {
        return iter.next();
    }
}