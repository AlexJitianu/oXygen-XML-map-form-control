package com.oxygenxml.samples.jfx.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.web.WebEngine;

import javax.swing.SwingUtilities;

import netscape.javascript.JSObject;

import org.apache.log4j.Logger;

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.editor.AuthorInplaceContext;
import ro.sync.ecss.extensions.api.node.AttrValue;

/**
 * A bridge between JavaScript and Java. JavaScript code will be able to invoke 
 * these methods.
 *  
 * @author alex_jitianu
 */
public class Bridge {
  /**
   * Logger for logging.
   */
  private static final Logger logger = Logger.getLogger(Bridge.class.getName());
  
  /**
   * Form control editing context.
   */
  private AuthorInplaceContext context;

  /**
   * Constructor.
   * 
   * @param context Form control editing context.
   */
  private Bridge(AuthorInplaceContext context) {
    this.context = context;
  }
  
  public static Bridge install(WebEngine engine, AuthorInplaceContext context) {
    JSObject window = (JSObject) engine.executeScript("window");
    Bridge value = new Bridge(context);
    window.setMember("bridge", value);
    window.setMember("authorContext", context);
    
    return value;
  }
  
  public String getAttr(String name) {
    return context.getElem().getAttribute(name).getValue();
  }
  
  public void setAttr(final String name, final String value) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        context.getAuthorAccess().getDocumentController().setAttribute(
            name, 
            new AttrValue(value), 
            context.getElem());
      }
    });
  }
  
  public void setText(final String value) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        AuthorDocumentController ctrl = context.getAuthorAccess().getDocumentController();
        ctrl.beginCompoundEdit();
        try {
          ctrl.delete(context.getElem().getStartOffset() + 1, context.getElem().getEndOffset());
          ctrl.insertText(context.getElem().getStartOffset() + 1, value);
        } finally {
          ctrl.endCompoundEdit();
        }
      }
    });
  }
  
  public Object createObject(String className, Object... parameters) {
    try {
      
      Class<?> loadedClass = Class.forName(className);
      
      Constructor<?> constructor = null;
      if (parameters != null) {
        Class[] pc = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
          pc[i] = parameters.getClass();
        }
        constructor = loadedClass.getConstructor(pc);
      } else {
        constructor = loadedClass.getConstructor();
      }
      
      if (constructor != null) {
        Object newInstance = constructor.newInstance(parameters);
        System.out.println("Created " + newInstance);
        return newInstance;
      } else {
        logger.error("Unable to find a proper constructor.");
      }
      
    } catch (Exception e) {
      logger.error(e, e);
    }
    
    return null;
  }
}
