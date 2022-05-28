package observer;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class EventManager {
    Map<String, List<Object>> listeners = new HashMap<>();

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void connect(String eventType, Object listener) {
        List<Object> users = listeners.get(eventType);
        users.add(listener);
    }

    public void disconnect(String eventType, Object listener) {
        List<Object> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, HashMap<String,String> content) {
        List<Object> users = listeners.get(eventType);
        for (Object listener : users) {
        	try {
        		Class<?>[] paramTypes = {HashMap.class};
				Method methodToBeCalled = listener.getClass().getMethod(eventType, paramTypes);
				methodToBeCalled.invoke(listener, content);
			} catch (NoSuchMethodException | SecurityException e) { 
				System.err.println("[ERROR] : The method \"" + eventType + "\" does not exist in class " + listener.getClass().getName());
		    } 
        	catch (IllegalAccessException e) { e.printStackTrace(); }
        	catch (IllegalArgumentException e) { e.printStackTrace();
			} catch (InvocationTargetException e) { e.printStackTrace(); }
        }
    }
}