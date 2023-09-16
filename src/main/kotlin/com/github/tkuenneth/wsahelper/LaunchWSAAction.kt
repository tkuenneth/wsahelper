package com.github.tkuenneth.wsahelper

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.annotations.NotNull
import java.io.File
import java.util.*

private const val PATH =
    "AppData\\Local\\Microsoft\\WindowsApps\\MicrosoftCorporationII.WindowsSubsystemForAndroid_8wekyb3d8bbwe\\WsaClient.exe"

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
        val stdout = StringBuilder()
        val stderr = StringBuilder()
        val client = File(System.getProperty("user.home"), PATH)
        val adbFound = execute(
            stdout = stdout,
            stderr = stderr,
            cmd = "adb devices"
        )
        val allDone = stdout.toString().contains("127.0.0.1:58526")
        val wsaFound = allDone || execute(
            stdout = stdout,
            stderr = stderr,
            cmd = client.absolutePath
        )
        val wsaConnected = allDone || (wsaFound && execute(
            stdout = stdout,
            stderr = stderr,
            cmd = "adb connect 127.0.0.1:58526"
        ))
        MessageDialog(
            adbFound = adbFound,
            wsaFound = wsaFound,
            wsaConnected = wsaConnected
        ).showAndGet()
    }
}

private val res = Properties().also {
    it.load(LaunchWSAAction::class.java.getResourceAsStream("/messages.properties"))
}

fun getString(key: String): String = res.getProperty(key, "")
