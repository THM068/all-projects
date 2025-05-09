package algorithms


data class Node(var value: Int, var left: Node? = null, var right: Node? = null)

class BinaryTree(rootNode: Node) {
    var root = rootNode

    fun add(i: Int) {
        root = addRecursive(root, i)
    }

    fun addRecursive(current: Node?, value: Int): Node {
        if(current == null) {
            return Node(value)
        }

        if(value < current.value  ) {
            current.left = addRecursive(current.left, value)
        }
        else if(value > current.value ) {
            current.right = addRecursive(current.right, value)
        }


        return current
    }



}