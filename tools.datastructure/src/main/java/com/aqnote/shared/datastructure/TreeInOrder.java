/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.datastructure;

public class TreeInOrder {

	/**
	 * 树的前序递归遍历 pre=prefix(前缀)
	 * 
	 * @param node
	 *            要遍历的节点
	 */
	public static void preOrder(TreeNode node) {
		// 如果传进来的节点不为空，则遍历，注，叶子节点的子节点为null
		if (node != null) {
			System.out.print(node.getNodeId() + " ");// 先遍历父节点

			for (int i = 0; i < node.getSubNodes().size(); i++) {
				preOrder((TreeNode) node.getSubNodes().get(i));// 再遍历子节点
			}

		}
	}

	/**
	 * 树的后序递归遍历 post=postfix(后缀)
	 * 
	 * @param node
	 *            要遍历的节点
	 */
	public static void postOrder(TreeNode node) {
		// 如果传进来的节点不为空，则遍历
		if (node != null) {
			// 如果为分支节点，则遍历子节点
			for (int i = 0; i < node.getSubNodes().size(); i++) {
				postOrder((TreeNode) node.getSubNodes().get(i));// 先遍历子节点
			}

			System.out.print(node.getNodeId() + " ");// 后遍历父节点
		}
	}
}