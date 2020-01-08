public class AVLT{

    private Node headNode;
    private int size;

    AVLT(int[] arr,int n){
        
        int i;
        this.size = n;
        this.headNode = new Node(arr[0]);

        for(i=1;i<n;i++)
            this.insertNode(arr[i]);
    }

    private void balanceTree(){
        this.updateBalance(headNode);
    }

    private void rotateLL(Node X,int dir){
        
        Node A = X;
        Node B = X.leftNode;
        Node P = X.parent;
        Node Br = B.rightNode;

        if(dir == 1)
            P.rightNode = B;
        else
            P.leftNode = B;
        
        B.parent = P;
        B.rightNode = A;
        A.parent = B;
        A.leftNode = Br;
        Br.parent = A;
    }

    private void rotateLR(Node X,int dir){
        Node A = X;
        Node B = X.leftNode;
        Node C = X.leftNode.rightNode;
        Node P = X.parent;
        Node Cr = C.rightNode;
        Node Cl = C.leftNode;

        if(dir == 1)
            P.rightNode = C;
        else
            P.leftNode = C;
        
        C.parent = P;
        C.rightNode = A;
        A.parent = C;
        C.leftNode = B;
        B.parent = C;
        B.rightNode = Cl;
        Cl.parent = B;
        A.leftNode = Cr;
        Cr.parent = A;
    }

    private void rotateRR(Node X,int dir){
        
        Node A = X;
        Node B = X.rightNode;
        Node P = X.parent;
        Node Bl = B.leftNode;

        if(dir == 1)
            P.rightNode = B;
        else
            P.leftNode = B;
        
        B.parent = P;
        B.leftNode = A;
        A.parent = B;
        A.rightNode = Bl;
        Bl.parent = A;
    }

    private void rotateRL(Node X,int dir){
        Node A = X;
        Node B = X.rightNode;
        Node C = X.rightNode.leftNode;
        Node P = X.parent;
        Node Cr = C.rightNode;
        Node Cl = C.leftNode;

        if(dir == 1)
            P.rightNode = C;
        else
            P.leftNode = C;
        
        C.parent = P;
        C.leftNode = A;
        A.parent = C;
        C.rightNode = B;
        B.parent = C;
        B.leftNode = Cr;
        Cr.parent = B;
        A.rightNode = Cl;
        Cl.parent = A;
    }

    private void updateBalance(Node target){
        if(target == null) return;
        target.balance = findDepth(target.leftNode) - findDepth(target.rightNode);
        updateBalance(target.leftNode);
        updateBalance(target.rightNode);
    }

    private int findDepth(Node start){
        if(start == null)
            return 0;
        else
            return Math.max(findDepth(start.rightNode),findDepth(start.leftNode)) + 1;
    }

    public Node inTree(int target){

        Node current = this.headNode;

        while(current != null){
            if(current.value == target)
                return current;
            else{
                if(current.value < target)
                    current = current.rightNode;
                else
                    current = current.leftNode;
            } 
        }
        return null;
    }

    public boolean deleteVal(int num){
        
        Node loc = this.inTree(num);

        if (loc == null) 
            return false;
        else{
            if(loc.leftNode == null)
                swapAppropriate(loc,1);
            else
                swapAppropriate(loc,-1);

            this.size--;
            this.balanceTree();
            return true;
        }
    }

    private void swapAppropriate(Node child, int dir){
        
        Node current;
        int temp;
        
        if(child.rightNode == null && child.leftNode == null){
            if(dir == 1)
                child.parent.rightNode = null;
            else
                child.parent.leftNode = null;
        }
        else{
            if(dir == 1){
                current = child.rightNode;
                while(current.rightNode != null)
                    current = current.rightNode;
                temp = current.value;
                child.value = temp;
                swapAppropriate(current,-1);
                this.balanceTree();
            }
            else{
                current = child.leftNode;
                while(current.leftNode != null)
                    current = current.leftNode;
                temp = current.value;
                child.value = temp;
                swapAppropriate(current,1);
                this.balanceTree();
            }
        }
    }

    public void insertNode(int n){
        
        Node current = this.headNode;
        Node next = this.headNode;
        if(this.inTree(n) == null){
            while(next != null){
                current = next;
                if(n>current.value)
                    next = current.rightNode;
                else
                    next = current.leftNode;
            }
            if(current.value > n){
                current.leftNode = new Node(n);
                current.leftNode.parent = current;
                current.leftNode.level = 1 + current.leftNode.parent.level; 
            }
            else{
                current.rightNode = new Node(n);
                current.rightNode.parent = current;
                current.rightNode.level = 1 + current.rightNode.parent.level;
            }
            this.size++;
            this.balanceTree();
        }
        else
            return;
    }
        
    private class Node{
        public int value;
        public int balance;
        public int level;
        public Node rightNode;
        public Node leftNode;
        public Node parent;

        Node(int val){
            
            this.level = 0;
            this.value = val;
            this.rightNode = null;
            this.leftNode = null;
            this.parent = null;
        }
    }

}