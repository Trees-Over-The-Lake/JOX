package observer;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An implementation of the Observer pattern
 * @author MysteRys337
 *
 */
public class Signal {
    Map<Object, List<String>> listeners = new HashMap<>();
    Class<?>[] parameters;

    /**
     * Create a signal that can receive a number of parameters
     * @param parameters is the list of parameters this signal can receive
     */
    public Signal(Class<?>... parameters) {
    	this.parameters = parameters;
    }

    /**
     * Connect an Object to this Signal, and connect a function to be called when this signal is notified
     * @param listener is the Object to be referred
     * @param functionName is the name of the function of the Object listener
     */
    public void connect(Object listener,String functionName) {
        List<String> functions = listeners.get(listener);
        if (functions == null) {
        	functions = new ArrayList<String>();
        }
        functions.add(functionName);
        listeners.put(listener, functions);
    }

    /**
     * Disconnect an Object to this Signal
     * @param listener is the Object to be referred
     * @param functionName is the name of the function of the Object listener
     */
    public void disconnect(Object listener, String functionName) {
        List<String> functions = listeners.get(listener);
        if (functions == null) {
        	functions = new ArrayList<String>();
        }
        functions.remove(functionName);
        listeners.put(listener, functions);
    }

    /**
     * Notify all the listeners connected to this signal.
     * @param parameters is all the values that the functions of the listeners will receive
     */
    public void emit_signal(Object... parameters) {
        
        for (Map.Entry<Object, List<String>> entry : this.listeners.entrySet()) {
        	
        	Object       classInstance = entry.getKey();
        	List<String> classFunctionNames = entry.getValue();
        	
        	for ( String functionName : classFunctionNames ) {
        		try {
            		Class<?>[] paramTypes = {HashMap.class};
    				Method methodToBeCalled = classInstance.getClass().getMethod(functionName, paramTypes);
    				methodToBeCalled.invoke(classInstance, parameters);
    			} catch (NoSuchMethodException e) { 
    				System.err.println("[ERROR] : The method \"" + functionName + "\" does not exist in class " + classInstance.getClass().getName());
    		    } 
        		catch (SecurityException e) {
        			e.printStackTrace();
        		}
            	catch (IllegalAccessException e) { e.printStackTrace(); }
            	catch (IllegalArgumentException e) { e.printStackTrace();
    			} catch (InvocationTargetException e) { e.printStackTrace(); }
        	}
        	
        }
    }
}