class SplayTree {
    Node root = null;

    void splay(Node x) {
        if(x == null)
            return;

        while (x.parent != null) {
            Node par = x.parent;
            Node g = par.parent;
            if (g == null) {
                zig(x);
            }
            else if((g.leftChild == par && par.leftChild == x) || (g.rightChild == par && par.rightChild == x)) {
                zigZig(x);
            }
            else {
                zigZag(x);
            }
        }
        root = x;
    }

    void rightRotate(Node x) {
        Node par = x.parent;
        Node rc = x.rightChild;
        Node g = par.parent;
        int g_type = 0;

        if(g != null) {
            if(g.leftChild == par)
                g_type = 1;
            else
                g_type = 2;
        }

        par.leftChild = rc;
        if(rc != null)
            rc.parent = par;
        par.parent = x;
        x.parent = g;
        x.rightChild = par;
        if(g_type == 0)
            root = x;
        else if(g_type == 1)
            g.leftChild = x;
        else
            g.rightChild = x;
    }

    void leftRotate(Node x) {
        Node par = x.parent;
        Node lc = x.leftChild;
        Node g = par.parent;
        int g_type = 0;

        if(g != null) {
            if(g.leftChild == par)
                g_type = 1;
            else
                g_type = 2;
        }

        par.rightChild = lc;
        if(lc != null)
            lc.parent = par;
        par.parent = x;
        x.parent = g;
        x.leftChild = par;
        if(g_type == 0)
            root = x;
        else if(g_type == 1)
            g.leftChild = x;
        else
            g.rightChild = x;
    }

    void zig(Node x) {
        if(x.parent.leftChild == x)
            rightRotate(x);
        else
            leftRotate(x);
    }

    void zigZig(Node x) {
        Node par = x.parent;
        if(par.rightChild == x) {
            leftRotate(par);
            leftRotate(x);
        }
        else {
            rightRotate(par);
            rightRotate(x);
        }
    }

    void zigZag(Node x) {
        Node par = x.parent;
        if(par.rightChild == x) {
            leftRotate(x);
            rightRotate(x);
        }
        else {
            rightRotate(x);
            leftRotate(x);
        }
    }

    void add(long value) {
        if(root == null) {
            root = new Node(value);
            return;
        }
        Node currentNode = root;
        while(value != currentNode.value) {
            if(value > currentNode.value) {
                if(currentNode.rightChild == null) {
                    currentNode.rightChild = new Node(value);
                    currentNode.rightChild.parent = currentNode;
                }
                currentNode = currentNode.rightChild;
            }
            else {
                if(currentNode.leftChild == null) {
                    currentNode.leftChild = new Node(value);
                    currentNode.leftChild.parent = currentNode;
                }
                currentNode = currentNode.leftChild;
            }
        }
        splay(currentNode);
    }

    boolean find(long value) {
        if(root == null)
            return false;
        Node currentNode = root;
        while(currentNode.value != value) {
            if (value > currentNode.value) {
                if (currentNode.rightChild == null)
                    return false;
                currentNode = currentNode.rightChild;
            } else {
                if (currentNode.leftChild == null)
                    return false;
                currentNode = currentNode.leftChild;
            }
        }
        splay(currentNode);
        return true;
    }

    Node getMinimum(Node x) {
        Node currentNode = x;
        while (currentNode.leftChild != null)
            currentNode = currentNode.leftChild;
        return currentNode;
    }

    void del(long value) {
        if(root == null)
            return;
        Node currentNode = root;
        while(currentNode.value != value) {
            if (value > currentNode.value) {
                if (currentNode.rightChild == null)
                    return;
                currentNode = currentNode.rightChild;
            } else {
                if (currentNode.leftChild == null)
                    return;
                currentNode = currentNode.leftChild;
            }
        }
        if (currentNode.leftChild != null && currentNode.rightChild != null) {
            Node minNode = getMinimum(currentNode.rightChild);

            currentNode.leftChild.parent = minNode;
            minNode.leftChild = currentNode.leftChild;

            currentNode.rightChild.parent = currentNode.parent;
            if(currentNode.parent != null) {
                if(currentNode.parent.leftChild == currentNode)
                    currentNode.parent.leftChild = currentNode.rightChild;
                else
                    currentNode.parent.rightChild = currentNode.rightChild;
            }
            else
                root = currentNode.rightChild;

            splay(currentNode.parent);
        } else if (currentNode.leftChild != null) {
            currentNode.leftChild.parent = currentNode.parent;
            if(currentNode.parent != null) {
                if(currentNode.parent.leftChild == currentNode)
                    currentNode.parent.leftChild = currentNode.leftChild;
                else
                    currentNode.parent.rightChild = currentNode.leftChild;
            }
            else
                root = currentNode.leftChild;
            splay(currentNode.parent);
        } else if(currentNode.rightChild != null) {
            currentNode.rightChild.parent = currentNode.parent;
            if(currentNode.parent != null) {
                if(currentNode.parent.leftChild == currentNode)
                    currentNode.parent.leftChild = currentNode.rightChild;
                else
                    currentNode.parent.rightChild = currentNode.rightChild;
            }
            else
                root = currentNode.rightChild;
            splay(currentNode.parent);
        }
        else {
            Node par = currentNode.parent;
            if (par != null) {
               if(par.rightChild == currentNode)
                   par.rightChild = null;
               else
                   par.leftChild = null;
            }
            else
                root = null;
            splay(currentNode.parent);
        }
    }

    long sum(Node x, long l, long r) {
        if(x == null)
            return 0;
        if(x.value > r)
            return sum(x.leftChild, l, r);
        if(x.value < l)
            return sum(x.rightChild, l, r);
        return x.value + sum(x.leftChild, l, r) + sum(x.rightChild, l, r);
    }

    long sum(long l, long r) {
        if(l > r)
            return 0;
        return sum(root, l, r);
    }
}
