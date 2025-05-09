package streams

import algorithms.BinaryTree
import algorithms.Node
import kotlin.test.Test
import kotlin.test.assertEquals

class BinaryTreeTest {
    private val binaryTree = BinaryTree(Node(10, null, null))

    @Test
    fun `Inserting an element`() {
        binaryTree.add(11)

        assertEquals(11, binaryTree.root.right?.value)
    }
}