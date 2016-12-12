/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.datastructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class TreeOrder {

	public static class DepthOrderIterator implements Iterator<Object> {
		// 栈,用来深度遍历树节点,以便回溯
		Stack<TreeNode> stack = new Stack<TreeNode>();

		public DepthOrderIterator(TreeNode rootNode) {
			// 将根节点迭代器入栈
			stack.push(rootNode);
		}

		// 是否有下一元素
		public boolean hasNext() {
			return !stack.empty();
		}

		// 取下一元素
		public Object next() {
			// 如果还有下一个元素,则先取到该元素所对应的迭代器引用,以便取得该节点元素
			if (hasNext()) {
				TreeNode node = (TreeNode) stack.pop();
				for (TreeNode children : node.getSubNodes()) {
					stack.push(children);
				}
				// 返回遍历得到的节点
				return node;
			} else {
				// 如果栈为空
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static class LevelOrderIterator implements Iterator<Object> {
		// 队列，实现层次遍历，存入节点迭代器
		private LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

		public LevelOrderIterator(TreeNode rootNode) {
			// 将根节点迭代器入队
			queue.addLast(rootNode);
		}

		// 是否有下一元素
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		// 取下一元素
		public Object next() {
			// 如果还有下一个元素
			if (hasNext()) {
				TreeNode node = (TreeNode) queue.removeFirst();
				// 获取该节点元素
				for (TreeNode children : node.getSubNodes()) {
					queue.addLast(children);
				}

				return node;
			} else {
				// 如果栈为空
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}