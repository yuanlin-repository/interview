package github.yuanlin.consistenthash.impl;

import github.yuanlin.consistenthash.Node;

/**
 * @author yuanlin.zhou
 * @date 2025/9/2 17:46
 * @description TODO
 */
public class DefaultNode implements Node {

    int weight;
    String name;
    String ip;

    public DefaultNode(int weight, String name, String ip) {
        this.weight = weight;
        this.name = name;
        this.ip = ip;
    }

    @Override
    public Integer getWeight() {
        return weight;
    }

    @Override
    public String getVirtualMachineName(int index) {
        return new StringBuilder().append(name).append("#").append(index).toString();
    }

    @Override
    public String getResource() {
        return ip;
    }
}
