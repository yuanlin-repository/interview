package github.yuanlin.consistenthash;

import java.util.*;

/**
 * @author yuanlin.zhou
 * @date 2025/9/2 17:10
 * @description Consistent Hash Implementation
 */
public class ConsistentHash {

    private HashAlgorithm hashAlgorithm;

    private TreeMap<Long, Node> hashRing;

    private List<Node> nodeList;

    public ConsistentHash() {
        this.hashAlgorithm = new DefaultHashAlgorithm();
        this.hashRing = new TreeMap<>();
        this.nodeList = new LinkedList<>();
    }

    public void addHost(Node node) {
        for (int i = 0; i < node.getWeight(); i++) {
            hashRing.put(hashAlgorithm.hashcode(node.getVirtualMachineName(i)), node);
        }
        nodeList.add(node);
    }

    public void removeHost(Node node) {
        for (int i = 0; i < node.getWeight(); i++) {
            hashRing.remove(hashAlgorithm.hashcode(node.getVirtualMachineName(i)));
        }
        nodeList.remove(node);
    }

    public Node getNodeByKey(String key) {
        Map.Entry<Long, Node> e = hashRing.ceilingEntry(hashAlgorithm.hashcode(key));
        if (e == null) {
            return hashRing.firstEntry().getValue();
        }
        return e.getValue();
    }

    public String getResourceByKey(String key) {
        return getNodeByKey(key).getResource();
    }
}
