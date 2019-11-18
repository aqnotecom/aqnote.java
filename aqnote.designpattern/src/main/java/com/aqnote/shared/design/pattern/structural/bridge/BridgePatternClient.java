/**
 *
 * Software Architecture Design Patterns in Java
 * by Partha Kuchana 
 * 
 * Auerbach Publications
 */
package com.aqnote.shared.design.pattern.structural.bridge;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class BridgePatternClient {

    public static void main(String[] args) {
        // Create an appropriate implementer object
        MessageLogger logger = new ConsoleLogger();

        // Choose required interface object and
        // configure it with the implementer object
        Message msg = new EncryptedMessage(logger);

        msg.log("Test Message");

    }
}

class ConsoleLogger implements MessageLogger {

    public void logMsg(String msg) {
        System.out.println(msg);
    }
}

class EncryptedMessage implements Message {

    private MessageLogger logger;

    public EncryptedMessage(MessageLogger l){
        logger = l;
    }

    public void log(String msg) {
        String str = preProcess(msg);
        logger.logMsg(str);

    }

    private String preProcess(String msg) {
        msg = msg.substring(msg.length() - 1) + msg.substring(0, msg.length() - 1);
        return msg;
    };
}

class FileLogger implements MessageLogger {

    public void logMsg(String msg) {
        FileUtil futil = new FileUtil();
        futil.writeToFile("log.txt", msg, true, true);
    }
}

class MiscUtil {

    public static boolean hasDuplicates(@SuppressWarnings("rawtypes") Vector v) {
        int i = 0;
        int j = 0;
        boolean duplicates = false;

        for (i = 0; i < v.size() - 1; i++) {
            for (j = (i + 1); j < v.size(); j++) {
                if (v.elementAt(i).toString().equalsIgnoreCase(v.elementAt(j).toString())) {
                    duplicates = true;
                }

            }

        }

        return duplicates;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Vector removeDuplicates(Vector s) {
        int i = 0;
        int j = 0;
        boolean duplicates = false;

        Vector v = new Vector();

        for (i = 0; i < s.size(); i++) {
            duplicates = false;
            for (j = (i + 1); j < s.size(); j++) {
                if (s.elementAt(i).toString().equalsIgnoreCase(s.elementAt(j).toString())) {
                    duplicates = true;
                }

            }
            if (duplicates == false) {
                v.addElement(s.elementAt(i).toString().trim());
            }

        }

        return v;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Vector removeDuplicateDomains(Vector s) {
        int i = 0;
        int j = 0;
        boolean duplicates = false;
        String str1 = "";
        String str2 = "";

        Vector v = new Vector();

        for (i = 0; i < s.size(); i++) {
            duplicates = false;
            for (j = (i + 1); j < s.size(); j++) {
                str1 = "";
                str2 = "";
                str1 = s.elementAt(i).toString().trim();
                str2 = s.elementAt(j).toString().trim();
                if (str1.indexOf('@') > -1) {
                    str1 = str1.substring(str1.indexOf('@'));
                }
                if (str2.indexOf('@') > -1) {
                    str2 = str2.substring(str2.indexOf('@'));
                }

                if (str1.equalsIgnoreCase(str2)) {
                    duplicates = true;
                }

            }
            if (duplicates == false) {
                v.addElement(s.elementAt(i).toString().trim());
            }

        }

        return v;
    }

    @SuppressWarnings("rawtypes")
    public static boolean areVectorsEqual(Vector a, Vector b) {
        if (a.size() != b.size()) {
            return false;
        }

        int i = 0;
        int vectorSize = a.size();
        boolean identical = true;

        for (i = 0; i < vectorSize; i++) {
            if (!(a.elementAt(i).toString().equalsIgnoreCase(b.elementAt(i).toString()))) {
                identical = false;
            }
        }

        return identical;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Vector removeDuplicates(Vector a, Vector b) {

        int i = 0;
        int j = 0;
        boolean present = true;
        Vector v = new Vector();

        for (i = 0; i < a.size(); i++) {
            present = false;
            for (j = 0; j < b.size(); j++) {
                if (a.elementAt(i).toString().equalsIgnoreCase(b.elementAt(j).toString())) {
                    present = true;
                }
            }
            if (!(present)) {
                v.addElement(a.elementAt(i));
            }
        }

        return v;
    }

}// end of class

class FileUtil {

    DataOutputStream dos;

    /*
     * Utility method to write a given text to a file
     */
    public boolean writeToFile(String fileName, String dataLine, boolean isAppendMode, boolean isNewLine) {
        if (isNewLine) {
            dataLine = "\n" + dataLine;
        }

        try {
            File outFile = new File(fileName);
            if (isAppendMode) {
                dos = new DataOutputStream(new FileOutputStream(fileName, true));
            } else {
                dos = new DataOutputStream(new FileOutputStream(outFile));
            }

            dos.writeBytes(dataLine);
            dos.close();
        } catch (FileNotFoundException ex) {
            return (false);
        } catch (IOException ex) {
            return (false);
        }
        return (true);

    }

    /*
     * Reads data from a given file
     */
    public String readFromFile(String fileName) {
        String DataLine = "";
        try {
            File inFile = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

            DataLine = br.readLine();
            br.close();
        } catch (FileNotFoundException ex) {
            return (null);
        } catch (IOException ex) {
            return (null);
        }
        return (DataLine);

    }

    public boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }

    /*
     * Reads data from a given file into a Vector
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector fileToVector(String fileName) {
        Vector v = new Vector();
        String inputLine;
        try {
            File inFile = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));

            while ((inputLine = br.readLine()) != null) {
                v.addElement(inputLine.trim());
            }
            br.close();
        } // Try
        catch (FileNotFoundException ex) {
            //
        } catch (IOException ex) {
            //
        }
        return (v);
    }

    /*
     * Writes data from an input vector to a given file
     */
    public void vectorToFile(@SuppressWarnings("rawtypes") Vector v, String fileName) {
        for (int i = 0; i < v.size(); i++) {
            writeToFile(fileName, (String) v.elementAt(i), true, true);
        }
    }

    /*
     * Copies unique rows from a source file to a destination file
     */
    @SuppressWarnings("rawtypes")
    public void copyUniqueElements(String sourceFile, String resultFile) {
        Vector v = fileToVector(sourceFile);
        v = MiscUtil.removeDuplicates(v);
        vectorToFile(v, resultFile);
    }

} // end FileUtil

interface Message {
    public void log(String msg);
}

interface MessageLogger {
    public void logMsg(String msg);
}

class TextMessage implements Message {

    private MessageLogger logger;

    public TextMessage(MessageLogger l){
        logger = l;
    }

    public void log(String msg) {
        String str = preProcess(msg);
        logger.logMsg(str);
    }

    private String preProcess(String msg) {
        return msg;
    };
}
