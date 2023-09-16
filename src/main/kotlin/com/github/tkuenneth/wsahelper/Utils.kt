package com.github.tkuenneth.wsahelper

import java.io.IOException

fun execute(stdout: StringBuilder, stderr: StringBuilder, cmd: String): Boolean {
    stdout.setLength(0)
    stderr.setLength(0)
    return try {
        val process = Runtime.getRuntime().exec(cmd)
        val inputStream = process.inputStream
        val errorStream = process.errorStream
        try {
            process.waitFor()
        } catch (e: InterruptedException) {
            stderr.append(e.toString())
        }
        while (inputStream.available() > 0) {
            stdout.append(inputStream.read().toChar())
        }
        while (errorStream.available() > 0) {
            stderr.append(errorStream.read().toChar())
        }
        true
    } catch (e: IOException) {
        stderr.append(e.toString())
        false
    }
}
