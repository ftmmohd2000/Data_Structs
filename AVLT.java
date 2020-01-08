public class AVLT{

    private Node headNode;
    private int size;

    AVLT(){
        this.headNode = null;
    }

    AVLT(int[] arr,int n){
        
        int i;

        this.headNode = null;

        for(i=0;i<n;i++){
            this.insertNode(arr[i]);
            this.balanceTree();
        }
    }

    private void balanceTree(){

    }

    private int findDepth(Node start){
        if(start == null)
            return 0;
        else{
            if(start == headNode)
                return Math.max(findDepth(start.rightNode),findDepth(start.leftNode));
            else
                return Math.max(findDepth(start.rightNode),findDepth(start.leftNode)) + 1;
        }
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
                changeWiring(loc,1);
            else
                changeWiring(loc,-1);

            this.balanceTree();
            return true;
        }
    }

    private void changeWiring(Node child, int dir){
        
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
                changeWiring(current,-1);
                this.balanceTree();
            }
            else{
                current = child.leftNode;
                while(current.leftNode != null)
                    current = current.leftNode;
                temp = current.value;
                child.value = temp;
                changeWiring(current,1);
                this.balanceTree();
            }
        }
    }
        
    private class Node{
        public int value;
        public int balance;
        public Node rightNode;
        public Node leftNode;
        public Node parent;

        Node(int val){
            
            this.value = val;
            this.rightNode = null;
            this.leftNode = null;
            this.parent = null;
        }
    }

}