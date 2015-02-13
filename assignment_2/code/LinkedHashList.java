public class LinkedHashList {

    private HashNode head;
    private HashNode tail;
    private int length;

    public LinkedHashList(String word) {
        head = new HashNode(word);
        tail = head;
        length = 1;
    }

    public void addHashNode(String word) {
        HashNode newNode = new HashNode(word);
        tail.next = newNode;
        tail = newNode;
        length++;
    }

    public int size() {
    	return length;
    }

    public class HashNode {
        private HashNode next;
        private String word; 

        public HashNode(String word) {
            this.word = word;
            next = null;
        }
    }
}