package github.yuanlin.consistenthash;

public interface Node {
    public Integer getWeight();

    public String getVirtualMachineName(int w);

    public String getResource();
}
