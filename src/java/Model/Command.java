
package Model;

import java.util.*;

public interface Command {
    public void register(Object o);
    public void run(HashMap<String, String> hm) throws Exception;
}
