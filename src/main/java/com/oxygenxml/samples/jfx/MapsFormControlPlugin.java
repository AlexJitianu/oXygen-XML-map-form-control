package com.oxygenxml.samples.jfx;

import ro.sync.exml.plugin.Plugin;
import ro.sync.exml.plugin.PluginDescriptor;

/**
 * Workspace access plugin. 
 */
public class MapsFormControlPlugin extends Plugin {
  /**
   * The static plugin instance.
   */
  private static MapsFormControlPlugin instance = null;

  /**
   * Constructs the plugin.
   * 
   * @param descriptor The plugin descriptor
   */
  public MapsFormControlPlugin(PluginDescriptor descriptor) {
    super(descriptor);

    if (instance != null) {
      throw new IllegalStateException("Already instantiated!");
    }
    instance = this;
  }
  
  /**
   * Get the plugin instance.
   * 
   * @return the shared plugin instance.
   */
  public static MapsFormControlPlugin getInstance() {
    return instance;
  }
}