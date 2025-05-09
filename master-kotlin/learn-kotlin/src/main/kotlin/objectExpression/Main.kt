package objectExpression

import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.JFrame as JFrame

fun main() {
    SwingUtilities.invokeAndWait {
        Dashboard()
    }
}

class Dashboard(): JFrame() {
    val helloButton = JButton("Hello")
    init {
        s()
        addListeners()
    }
    fun s() {
        title = "Hello"
        val panel = JPanel()
        panel.layout = FlowLayout()
        panel.add(helloButton)
        add(panel)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 200)
        isVisible = true
    }

    fun addListeners() {
        this.helloButton.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
               println("hell0 >>>>>>")
            }

        })
    }
}