package Models;

import java.util.ArrayList;

public class TreeNode {

    private boolean type_node ; // defines if node is a min=0=enemy or max=1=player node

    private double value_node ;  // result of heuristic

    private int depth_node ; // at what tree level is this node

    private ArrayList<TreeNode> children_node;

    private int nodeIndex;

    private Attack lastAttack;

    public void addChild(TreeNode child){
        children_node.add(child);
    }

    public TreeNode(boolean type_node, double value_node, int depth_node , int nodeIndex) {
        this.type_node = type_node;
        this.value_node = value_node;
        this.depth_node = depth_node;
        this.children_node = new ArrayList<>(0);
        this.nodeIndex = nodeIndex;
    }

    public TreeNode(boolean type_node, double value_node, int depth_node, ArrayList<TreeNode> children_node) {
        this.type_node = type_node;
        this.value_node = value_node;
        this.depth_node = depth_node;
        this.children_node = children_node;
    }

    public TreeNode(boolean type_node, double value_node, int depth_node) {
        this.type_node = type_node;
        this.value_node = value_node;
        this.depth_node = depth_node;
        this.children_node = new ArrayList<>(0);
    }
    public TreeNode() {

    }

    public TreeNode(boolean type_node, double value_node, int depth_node, Attack lastAttack, int nodeIndex) {
        this.type_node = type_node;
        this.value_node = value_node;
        this.depth_node = depth_node;
        this.lastAttack = lastAttack;
        this.nodeIndex = nodeIndex;
        children_node = new ArrayList<>(0);
    }

    public boolean isType_node() {
        return type_node;
    }

    public void setType_node(boolean type_node) {
        this.type_node = type_node;
    }

    public double getValue_node() {
        return value_node;
    }

    public void setValue_node(double value_node) {
        this.value_node = value_node;
    }

    public int getDepth_node() {
        return depth_node;
    }

    public void setDepth_node(int depth_node) {
        this.depth_node = depth_node;
    }

    public ArrayList<TreeNode> getChildren_node() {
        return children_node;
    }

    public void setChildren_node(ArrayList<TreeNode> children_node) {
        this.children_node = children_node;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public Attack getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(Attack lastAttack) {
        this.lastAttack = lastAttack;
    }

}
