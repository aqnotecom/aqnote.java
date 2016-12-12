/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.datastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode implements Comparable<Object> {

	// 父节点
	private TreeNode pNode;

	// 数据域，节点编号，不能修改
	private int nodeId;

	// 数据域，节点名字，能修改
	private String nodeName;

	// 节点深度，根默认为0
	private int depth;

	public TreeNode(int nodeId, String nodeName) {
		this.setNodeId(nodeId);
		this.setNodeName(nodeName);
	}

	// 存储子节点
	List<TreeNode> subNodesList = new ArrayList<TreeNode>();

	public TreeNode getPMenuComponent() {
		return pNode;
	}

	public void setPMenuComponent(TreeNode menuComponent) {
		pNode = menuComponent;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	// 修改节点信息
	public void modiNodeInfo(String nodeName) {
		this.setNodeName(nodeName);
	}

	/**
	 * 根据树节点id，在当然节点与子节点中搜索指定的节点
	 * 
	 * @param treeId
	 * @return TreeNode
	 */
	public TreeNode getTreeNode(int treeId) {
		return getNode(this, treeId);
	}

	public int compareTo(Object o) {

		TreeNode temp = (TreeNode) o;

		return this.getNodeId() > temp.getNodeId() ? 1
				: (this.getNodeId() < temp.getNodeId() ? -1 : 0);
	}

	public boolean equals(Object menuComponent) {

		if (!(menuComponent instanceof TreeNode)) {
			return false;
		}
		TreeNode menu = (TreeNode) menuComponent;

		// 如果两个节点的nodeID相应则认为是同一节点
		return this.getNodeId() == menu.getNodeId();
	}

	// 添加子节点
	public void addSubNode(TreeNode menuComponent) {
		// 设置父节点
		menuComponent.setPMenuComponent(this);

		// 设置节点的深度
		menuComponent.setDepth(this.getDepth() + 1);
		subNodesList.add(menuComponent);
	}

	// 删除一个子节点
	public void removeSubNode(TreeNode menuComponent) {
		subNodesList.remove(menuComponent);
	}

	// 获取子节点
	public List<TreeNode> getSubNodes() {
		return subNodesList;
	}

	// 打印节点信息，以树的形式展示，所以它包括了所有子节点信息
	public void print() {
		System.out.println(this.getNodeInfo());
	}

	// 打印节点本身信息，不递归打印子节点信息
	public String toString() {
		return getSefNodeInfo().toString();
	}

	// 递归打印节点信息实现
	protected StringBuffer getNodeInfo() {

		StringBuffer sb = getSefNodeInfo();
		sb.append(System.getProperty("line.separator"));
		// 如果有子节点
		for (Iterator<TreeNode> iter = subNodesList.iterator(); iter.hasNext();) {
			TreeNode node = (TreeNode) iter.next();
			// 递归打印子节点信息
			sb.append(node.getNodeInfo());
		}
		return sb;
	}

	// 节点本身信息，不含子节点信息
	private StringBuffer getSefNodeInfo() {
		StringBuffer sb = new StringBuffer();

		// 打印缩进
		for (int i = 0; i < this.getDepth(); i++) {
			sb.append(' ');
		}
		sb.append("+--");

		sb.append("[nodeId=");
		sb.append(this.getNodeId());
		sb.append(" nodeName=");

		sb.append(this.getNodeName());
		sb.append(']');
		return sb;
	}

	// 为外界提供遍历组合结构的迭代器
	public Iterator<?> createDepthOrderIterator() {
		return new TreeOrder.DepthOrderIterator(this);
	}

	// 为外界提供遍历组合结构的迭代器
	public Iterator<?> createLayerOrderIterator() {
		return new TreeOrder.LevelOrderIterator(this);
	}

	/**
	 * 使用树的先序遍历递归方式查找指定的节点
	 * 
	 * @param treeNode
	 *            查找的起始节点
	 * @param treeId
	 *            节点编号
	 * @return
	 */
	protected TreeNode getNode(TreeNode treeNode, int treeId) {

		// 如果找到，则停止后续搜索，并把查找到的节点返回给上层调用者
		if (treeNode.getNodeId() == treeId) {// 1、先与父节点比对
			return treeNode;
		}

		TreeNode tmp = null;

		// 如果为分支节点，则遍历子节点
		for (int i = 0; i < treeNode.getSubNodes().size(); i++) {// 2、再与子节点比对
			tmp = getNode((TreeNode) treeNode.getSubNodes().get(i), treeId);
			if (tmp != null) {// 如果查找到，则返回上层调用者
				return tmp;
			}
		}

		// 如果没有找到，返回上层调用者
		return null;
	}
}