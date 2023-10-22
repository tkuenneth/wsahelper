package com.github.tkuenneth.wsahelper

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.annotations.NotNull
import java.io.File
import java.util.*
import javax.swing.SwingUtilities
import kotlin.concurrent.thread

private const val PATH =
    "AppData\\Local\\Microsoft\\WindowsApps\\MicrosoftCorporationII.WindowsSubsystemForAndroid_8wekyb3d8bbwe\\WsaClient.exe"

private const val ipAddressAndPort = "127.0.0.1:58526"

class LaunchWSAAction : AnAction() {
    override fun update(@NotNull event: AnActionEvent) {
        event.presentation.setEnabledAndVisible(
            System.getProperty("os.name").contains(other = "windows", ignoreCase = true)
        )
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(@NotNull event: AnActionEvent) {
        thread {
            val stdout = StringBuilder()
            val stderr = StringBuilder()
            val client = File(System.getProperty("user.home"), PATH)
            val adbFound = execute(
                stdout = stdout,
                stderr = stderr,
                cmd = "adb devices"
            )
            if (adbFound) {
                Notifier.notifySuccess(null, getString("adb_found"))
            }
            val ipAddressFound = stdout.toString().contains(ipAddressAndPort)
            val wsaFound = ipAddressFound || execute(
                stdout = stdout,
                stderr = stderr,
                cmd = client.absolutePath
            )
            if (wsaFound) {
                Notifier.notifySuccess(null, getString("wsa_found"))
            }
            val wsaConnected = ipAddressFound || (wsaFound && execute(
                stdout = stdout,
                stderr = stderr,
                cmd = "adb connect $ipAddressAndPort"
            ))
            if (wsaConnected) {
                Notifier.notifySuccess(null, getString("wsa_connected"))
            }
            if (!adbFound || !wsaFound || !wsaConnected) {
                SwingUtilities.invokeLater {
                    MessageDialog(
                        adbFound = adbFound,
                        wsaFound = wsaFound,
                        wsaConnected = wsaConnected
                    ).showAndGet()
                }
            }
        }
    }
}

private val res = Properties().also {
    it.load(LaunchWSAAction::class.java.getResourceAsStream("/messages.properties"))
}

fun getString(key: String): String = res.getProperty(key, "")
