/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.datastructure;

import java.util.ArrayList;
import java.util.List;

public class GraphFindCycle {
	
	class ServRel {
		public String useServId;
		public String providerServId;
		
		public ServRel(String useServId, String providerServId) {
			this.useServId = useServId;
			this.providerServId = providerServId;
		}
	}

	public List<String> findRefServ(String servId, List<ServRel> servRelList) {
		List<String> refServList = new ArrayList<String>();
		for (int i = 0; i < servRelList.size(); i++) {
			ServRel rel = (ServRel) servRelList.get(i);
			if (servId.equals(rel.useServId)) {
				refServList.add(rel.providerServId);
			}
		}
		return refServList;
	}
	
	static boolean isCycleServ = false;
	public void findCycleServ(String servId, String cyclePath, List<ServRel> servRelList) {
		String id = servId;
		List<String> refServList = findRefServ(servId, servRelList);
		if (!(refServList.size() > 0)) {
			return;
		} else if (cyclePath.indexOf(servId) > 0) {
			System.out.println(cyclePath + "," + id);
			isCycleServ = true;
			return;
		} else {
			cyclePath = cyclePath + "," + id;
			for (int j = 0; j < refServList.size(); j++) {
				String childServId = (String) refServList.get(j);
				findCycleServ(childServId, cyclePath, servRelList);
			}
		}
	}

	public static void main(String[] args) {
		GraphFindCycle gfc = new GraphFindCycle();
		List<String> servList = new ArrayList<String>();
		servList.add("A");
		servList.add("B");
		servList.add("C");
		servList.add("D");
		servList.add("E");
		servList.add("F");

		List<ServRel> servRelList = new ArrayList<ServRel>();
		servRelList.add(gfc.new ServRel("B", "C"));
		servRelList.add(gfc.new ServRel("A", "B"));
		servRelList.add(gfc.new ServRel("C", "E"));
		servRelList.add(gfc.new ServRel("B", "D"));
		servRelList.add(gfc.new ServRel("E", "F"));
		servRelList.add(gfc.new ServRel("F", ""));
		
		servRelList.add(gfc.new ServRel("X", "C"));
		servRelList.add(gfc.new ServRel("X", "B"));
		servRelList.add(gfc.new ServRel("X", "A"));
		servRelList.add(gfc.new ServRel("X", "D"));
		servRelList.add(gfc.new ServRel("X", "E"));
		servRelList.add(gfc.new ServRel("X", "F"));
		

//		// System.out.println(servRelList.size());
//		for (int i = 0; i < servList.size(); i++) {
//			String servId = (String) servList.get(i);
//			gfc.findCycleServ(servId, "", servRelList);
//			System.out.println(isCycleServ);
//		}
		gfc.findCycleServ("X", "", servRelList);
		System.out.println(isCycleServ);
	}

}
