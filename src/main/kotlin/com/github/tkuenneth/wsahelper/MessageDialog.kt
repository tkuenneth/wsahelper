package com.github.tkuenneth.wsahelper

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import javax.swing.*

class MessageDialog(
    private val adbFound: Boolean,
    private val wsaFound: Boolean,
    private val wsaConnected: Boolean
) : DialogWrapper(true) {
    init {
        title = getString("title")
        init()
    }

    override fun createActions(): Array<Action> {
        return arrayOf(okAction)
    }

    override fun createCenterPanel(): JComponent {
        val dialogPanel = JPanel(BorderLayout())
        val box = Box.createVerticalBox()
        box.add(
            createLabel(
                text = getString(key = "adb_found"),
                checked = adbFound
            )
        )
        box.add(
            createLabel(
                text = getString(key = "wsa_found"),
                checked = wsaFound
            )
        )
        box.add(
            createLabel(
                text = getString(key = "wsa_connected"),
                checked = wsaConnected
            )
        )
        dialogPanel.add(box, BorderLayout.CENTER)
        return dialogPanel
    }
}

private fun createLabel(
    text: String,
    checked: Boolean
): JLabel = JLabel().also {
    it.text = "${ if (checked) "\u2705" else "\u274c"} $text"
}
