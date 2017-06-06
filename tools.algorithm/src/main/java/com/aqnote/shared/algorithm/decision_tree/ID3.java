package com.aqnote.shared.algorithm.decision_tree;

/**
 * Created by madding on 5/8/17.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 *
 * 从信息论知识中我们直到，期望信息越小，信息增益越大，从而纯度越高。
 * 所以ID3算法的核心思想就是以信息增益度量属性选择，选择分裂后信息增益最大的属性进行分裂。下面先定义几个要用到的概念。
 *
 *
 */
public class ID3 {
    private ArrayList<String> attribute = new ArrayList<String>(); // 存储属性的名称
    private ArrayList<ArrayList<String>> attributevalue = new ArrayList<ArrayList<String>>(); // 存储每个属性的取值
    private ArrayList<String[]> data = new ArrayList<String[]>();
    ; // 原始数据
    int decatt; // 决策变量在属性集中的索引
    public static final String patternString = "@attribute(.*)[{](.*?)[}]";

    Document xmldoc;
    Element root;

    public ID3() {
        xmldoc = DocumentHelper.createDocument();
        root = xmldoc.addElement("root");
        root.addElement("DecisionTree").addAttribute("value", "null");
    }

    public static void main(String[] args) {
        ID3 inst = new ID3();
        inst.readARFF(
            new File("/Users/madding/scode/com.aqnote/java.tools/tools.algorithm/src/main/resources/weather.arff"));
        inst.setDec("play");
        LinkedList<Integer> ll = new LinkedList<Integer>();
        for (int i = 0; i < inst.attribute.size(); i++) {
            if (i != inst.decatt) { ll.add(i); }
        }
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i = 0; i < inst.data.size(); i++) {
            al.add(i);
        }
        inst.buildDT("DecisionTree", "null", al, ll);
        inst.writeXML("/Users/madding/scode/com.aqnote/java.tools/tools.algorithm/src/main/resources/result.xml");
        return;
    }

    //读取arff文件，给attribute、attributevalue、data赋值
    public void readARFF(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            Pattern pattern = Pattern.compile(patternString);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    attribute.add(matcher.group(1).trim());
                    String[] values = matcher.group(2).split(",");
                    ArrayList<String> al = new ArrayList<String>(values.length);
                    for (String value : values) {
                        al.add(value.trim());
                    }
                    attributevalue.add(al);
                } else if (line.startsWith("@data")) {
                    while ((line = br.readLine()) != null) {
                        if (line == "") { continue; }
                        String[] row = line.split(",");
                        data.add(row);
                    }
                } else {
                    continue;
                }
            }
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //设置决策变量
    public void setDec(int n) {
        if (n < 0 || n >= attribute.size()) {
            System.err.println("决策变量指定错误。");
            System.exit(2);
        }
        decatt = n;
    }

    public void setDec(String name) {
        int n = attribute.indexOf(name);
        setDec(n);
    }

    // 给一个样本（数组中是各种情况），的计数计算它的熵
    //
    public double getEntropy(int[] arr) {
        double entropy = 0.0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            entropy -= arr[i] * Math.log(arr[i] + Double.MIN_VALUE) / Math.log(2);
            sum += arr[i];
        }
        entropy += sum * Math.log(sum + Double.MIN_VALUE) / Math.log(2);
        entropy /= sum;
        return entropy;
    }

    ////给一个样本数组及样本的算术和，计算它的熵
    //public double getEntropy(int[] arr, int sum) {
    //    double entropy = 0.0;
    //    for (int i = 0; i < arr.length; i++) {
    //        entropy -= arr[i] * Math.log(arr[i] + Double.MIN_VALUE) / Math.log(2);
    //    }
    //    entropy += sum * Math.log(sum + Double.MIN_VALUE) / Math.log(2);
    //    entropy /= sum;
    //    return entropy;
    //}

    public boolean infoPure(ArrayList<Integer> subset) {
        String value = data.get(subset.get(0))[decatt];
        for (int i = 1; i < subset.size(); i++) {
            String next = data.get(subset.get(i))[decatt];
            if (!value.equals(next)) {
                return false;
            }
        }
        return true;
    }

    // 给定原始数据的子集(subset中存储行号),当以第index个属性为节点时计算它的信息熵
    public double calNodeEntropy(ArrayList<Integer> subset, int index) {
        int sum = subset.size();
        double entropy = 0.0;
        int[][] info = new int[attributevalue.get(index).size()][];
        for (int i = 0; i < info.length; i++) { info[i] = new int[attributevalue.get(decatt).size()]; }
        int[] count = new int[attributevalue.get(index).size()];
        for (int i = 0; i < sum; i++) {
            int n = subset.get(i);
            String nodevalue = data.get(n)[index];
            int nodeind = attributevalue.get(index).indexOf(nodevalue);
            count[nodeind]++;
            String decvalue = data.get(n)[decatt];
            int decind = attributevalue.get(decatt).indexOf(decvalue);
            info[nodeind][decind]++;
        }
        for (int i = 0; i < info.length; i++) {
            entropy += getEntropy(info[i]) * count[i] / sum;
        }
        return entropy;
    }

    // 构建决策树
    public void buildDT(String name, String value, ArrayList<Integer> subset, LinkedList<Integer> selatt) {
        Element ele = null;

        List<Element> list = root.selectNodes("//" + name);
        Iterator<Element> iter = list.iterator();
        while (iter.hasNext()) {
            ele = iter.next();
            if (ele.attributeValue("value").equals(value)) { break; }
        }
        if (infoPure(subset)) {
            ele.setText(data.get(subset.get(0))[decatt]);
            return;
        }
        int minIndex = -1;
        double minEntropy = Double.MAX_VALUE;
        for (int i = 0; i < selatt.size(); i++) {
            if (i == decatt) { continue; }
            double entropy = calNodeEntropy(subset, selatt.get(i));
            if (entropy < minEntropy) {
                minIndex = selatt.get(i);
                minEntropy = entropy;
            }
        }
        String nodeName = attribute.get(minIndex);
        selatt.remove(new Integer(minIndex));
        ArrayList<String> attvalues = attributevalue.get(minIndex);
        for (String val : attvalues) {
            ele.addElement(nodeName).addAttribute("value", val);
            ArrayList<Integer> al = new ArrayList<Integer>();
            for (int i = 0; i < subset.size(); i++) {
                if (data.get(subset.get(i))[minIndex].equals(val)) {
                    al.add(subset.get(i));
                }
            }
            buildDT(nodeName, val, al, selatt);
        }
    }

    // 把xml写入文件
    public void writeXML(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) { file.createNewFile(); }
            FileWriter fw = new FileWriter(file);
            OutputFormat format = OutputFormat.createPrettyPrint(); // 美化格式
            XMLWriter output = new XMLWriter(fw, format);
            output.write(xmldoc);
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
