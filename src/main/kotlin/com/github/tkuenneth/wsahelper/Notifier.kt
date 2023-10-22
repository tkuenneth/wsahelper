package com.github.tkuenneth.wsahelper

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project


object Notifier {
    fun notifySuccess(project: Project?, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("com.github.tkuenneth.wsahelper.group")
            .createNotification(content, NotificationType.INFORMATION)
            .notify(project)
    }
}
