package com.github.peterhoburg.jetbrainshelpplugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBScrollPane
import javax.swing.JLabel


class ShowPopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val fileStream = ShowPopupAction::class.java.getResourceAsStream("/help.html")
        val fileContent = fileStream?.bufferedReader()?.use { it.readText() } ?: ""

        val label = JLabel(fileContent)
        val scrollPane = JBScrollPane(label)

        // Build the popup using JBPopupFactory
        val popup = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(scrollPane, label)
            .setTitle("Popup")
            .setResizable(true)
            .setMovable(true)
            .createPopup()

        popup.showInFocusCenter()
    }
}



