# oXygen-XML-map-form-control

This plugin contributes a sample plugin that contributes an Oxygen author mode form control capable of rendering a Google map. 

Install as Add-On
-----------------

1. In the oXygen menu, open _Help » Install new add-ons..._
2. In the field _Show add-ons from_, add this URL: `https://raw.githubusercontent.com/AlexJitianu/oXygen-XML-map-form-control/master/build/update_site.xml`
3. The _Google Map form control_ add-on will be displayed. Follow the steps to install them both and restart oXygen.
4. Optional: check _Enable automatic updates checking_ in _Options » Preferences » Add-ons_ to get update notifications

Alternative installation method
-----

Download the plugin [ZIP package](https://github.com/AlexJitianu/oXygen-XML-map-form-control/raw/master/build/maps-form-control-1.0-SNAPSHOT-plugin.zip) and unzip it inside `{OxygenInstallDir}/plugins`


How to use it
-----
To use the form control you need a CSS rule like this:

    address:before(4) {
        content: oxy_editor(
                saHeavyFormControlClassName , 
                "com.oxygenxml.samples.jfx.GoogleMapFormControl");
    }

Currently the form control is pretty hard codded (a proof of concept). It will use **@lat** and **@lng** attributes from the bounded element. If needed it can be extended so you can control through a configuration from where the coordinates are taken and how they are stored back.
