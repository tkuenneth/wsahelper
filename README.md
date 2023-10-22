# Windows Subsystem for Android™ helper

This repo contains an IntelliJ IDEA plugin to launch the Windows Subsystem for
Android™ (WSA).

*But wait, isn't WSA launched automatically when I open an Android app on 
Windows 11?*

Yes, that's right. This plugin is helpful if you want to run your Android app on
the Windows Subsystem for Android™ **from within your IDE**. 

To run or debug an app from the IDE, you select a target and press the green 
play button. The Windows Subsystem for Android™ will appear as a target, if it is running and if an 
`adb connect` has been done. These steps can be executed manually, as 
described in the [Test and debug](https://learn.microsoft.com/en-us/windows/android/wsa/#test-and-debug) section of the Windows 
Subsystem for Android™️ documentation. This plugin automates these steps.

Please note that WSA is available only on systems running Windows 11.