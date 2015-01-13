package com.madding.shared.test.clazz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

import sun.net.httpserver.DefaultHttpServerProvider;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class Test {

    static public LinkedList<String> resolvconf(String paramString, int paramInt1, int paramInt2) {
        LinkedList<String> localLinkedList = new LinkedList<String>();
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/etc/resolv.conf"));
            String str1;
            while ((str1 = localBufferedReader.readLine()) != null) {
                int i = paramInt1;
                if (str1.length() == 0) continue;
                if (str1.charAt(0) == '#') continue;
                if (str1.charAt(0) == ';') continue;
                if (!(str1.startsWith(paramString))) continue;
                String str2 = str1.substring(paramString.length());
                if (str2.length() == 0) continue;
                if ((str2.charAt(0) != ' ') && (str2.charAt(0) != '\t')) continue;
                StringTokenizer localStringTokenizer = new StringTokenizer(str2, " \t");
                while (localStringTokenizer.hasMoreTokens()) {
                    String str3 = localStringTokenizer.nextToken();
                    if (str3.charAt(0) == '#') break;
                    if (str3.charAt(0) == ';') {
                        break;
                    }
                    localLinkedList.add(str3);
                    if (--i == 0) {
                        break;
                    }
                }
                if (--paramInt2 == 0) {
                    break;
                }
            }
            localBufferedReader.close();
        } catch (IOException localIOException) {
        }
        return localLinkedList;
    }

    public static void main(String[] args) throws IOException {

        // List<String> nameservers = resolvconf("nameserver", 1, 5);
        // for (String nameserver : nameservers) {
        // System.out.println(nameserver);
        // }

        HttpServerProvider provider = new DefaultHttpServerProvider();
        HttpServer server = provider.createHttpServer(new InetSocketAddress("127.0.0.1", 8081), 16);
        server.createContext("/", new HttpHandler() {
            
//            @Override
//            public void handle(HttpExchange httpExchange) throws IOException {  
//                String responseMsg = "哈哈哈哈";   //响应信息  
//                InputStream in = httpExchange.getRequestBody(); //获得输入流  
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
//                String temp = null;  
//                while((temp = reader.readLine()) != null) {  
//                    System.out.println("client request:"+temp);  
//                }  
//                httpExchange.sendResponseHeaders(200, responseMsg.getBytes().length); //设置响应头属性及响应信息的长度  
//                OutputStream out = httpExchange.getResponseBody();  //获得输出流  
//                out.write(responseMsg.getBytes());  
//                out.flush();  
//                httpExchange.close();                                 
//                  
//            }  
            
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String result = "";
                InetSocketAddress address = exchange.getRemoteAddress();
                result += address.toString() + "\n";
                
                Headers headers = exchange.getRequestHeaders();
                Set<String> keyset = headers.keySet();
                for(String key : keyset) {
                    result += (key + ": " + headers.getFirst(key)) + "\n";
                }
                
                byte[] b = result.getBytes();
                exchange.sendResponseHeaders(200, result.getBytes().length);
                exchange.getResponseBody().write(b, 0, b.length);
                exchange.close();
            }

        });

        server.start();

    }
}
