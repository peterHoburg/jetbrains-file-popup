package com.github.peterhoburg.filepopup.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.SearchTextField
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class ShowPopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val helpFileName = "JETBRAINS_FILE_POPUP.html"
        val userHome = System.getProperty("user.home")
        val helpFilePath= "$userHome/$helpFileName"

        val helpFile = java.io.File(helpFilePath)
        val fileContent = if (helpFile.exists() && helpFile.canRead()) {
            helpFile.readText()
        } else {
            "This is populated from \"$helpFilePath\""
        }

        val originalContent = if (fileContent.trim().startsWith("<html", ignoreCase = true))
            fileContent
        else
            "<html>$fileContent</html>"

        val label = JLabel(originalContent)
        val scrollPane = JBScrollPane(label)

        val searchField = SearchTextField()

        searchField.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) = updateHighlight()
            override fun removeUpdate(e: DocumentEvent) = updateHighlight()
            override fun changedUpdate(e: DocumentEvent) = updateHighlight()

            private fun updateHighlight() {
                val query = searchField.text
                if (query.isBlank()) {
                    label.text = originalContent
                } else {
                    label.text = highlightMatchingLines(originalContent, query)
                }
            }
        })

        val panel = JPanel(BorderLayout())
        panel.add(searchField, BorderLayout.NORTH)
        panel.add(scrollPane, BorderLayout.CENTER)

        val popup = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(panel, searchField)
            .setTitle(helpFilePath)
            .setResizable(true)
            .setMovable(true)
            .setRequestFocus(true)
            .createPopup()
        popup.showInFocusCenter()
    }

    private fun highlightMatchingLines(originalHtml: String, query: String): String {
        val lines = originalHtml.split("\n")
        val highlightedLines = lines.map { line ->
            if (line.contains(query, ignoreCase = true)) {
                "<span style=\"background-color:  #f9e79f;\">$line</span>"
            } else {
                line
            }
        }
        return  highlightedLines.joinToString("\n")
    }
}
