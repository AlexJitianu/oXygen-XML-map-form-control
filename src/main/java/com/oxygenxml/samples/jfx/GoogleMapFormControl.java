package com.oxygenxml.samples.jfx;

import ro.sync.ecss.extensions.api.editor.AuthorInplaceContext;
import ro.sync.ecss.extensions.api.editor.InplaceHeavyEditor;
import ro.sync.ecss.extensions.api.editor.RendererLayoutInfo;
import ro.sync.exml.view.graphics.Rectangle;

import com.oxygenxml.samples.jfx.bridge.Bridge;

/**
 * Uses a JFX browser  to render Google maps.
 * 
 * @author alex_jitianu
 */
public class GoogleMapFormControl implements	InplaceHeavyEditor {

  /**
   * A form control will be added/removed so the JFX must not exit.
   */
  static {
    javafx.application.Platform.setImplicitExit(false);
  }
  /**
   * We keep this reference because of:
   * 
   * https://bugs.openjdk.java.net/browse/JDK-8170085
   */
  private Bridge installedBridge;
  /**
   * Form controls editing context.
   */
  private AuthorInplaceContext context;
  /**
   * A wrapper over the JFX browser.
   */
  private SwingBrowserPanel jfxPanel = new SwingBrowserPanel(new BrowserInteractor() {
    @Override
    public void alert(String message) {
      System.err.println(message);
    }
    public void pageLoaded() {
      installedBridge = Bridge.install(jfxPanel.getWebEngine(), context);
      
      jfxPanel.executeScript("document.bridgeReady()");
    }
  });

  @Override
  public String getDescription() {
    return "Loads a google map and marks the @lat and @lng attributes of the element it is binded to.";
  }

  @Override
  public void deactivate() {
    // Nothing to do.
  }

  @Override
  public void dispose() {
    jfxPanel.dispose();    
  }

  @Override
  public Object getEditorComponent(final AuthorInplaceContext context, Rectangle arg1) {
    this.context = context;

    jfxPanel.loadURL(getClass().getResource("/map.html"));

    return jfxPanel;
  }

  @Override
  public RendererLayoutInfo getRenderingInfo(AuthorInplaceContext arg0) {
    return new RendererLayoutInfo(1,
        new ro.sync.exml.view.graphics.Dimension(800, 600));
  }}
