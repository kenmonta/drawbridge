import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String,TreeMap<String,Integer>> trainMap = new HashMap<String,TreeMap<String,Integer>>();

            BufferedReader trainReader = new BufferedReader(new FileReader(new File("/Users/montanez/jane/ref/wip/train_v3.csv")));
            String s1 = null;

// 0-HANDLE, 1-COUNTRY, 2-ANONYMOUS_C0, 3-ANONYMOUS_C1, 4-ANONYMOUS_C2, 5-ANONYMOUS_5, 6-ANONYMOUS_6, 7-ANONYMOUS_7
            TreeSet<String> colSet = new TreeSet<String>();

            trainReader.readLine();
            while ((s1 = trainReader.readLine()) != null) {
                String[] line = s1.split(",");


                if (!trainMap.containsKey(line[0])) {
                    trainMap.put(line[0],new TreeMap<String,Integer>());
                }

                TreeMap<String,Integer> workingMap = trainMap.get(line[0]);

                // country
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[1]), "COUNTRY");

                // c0
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[2]), "ANONYMOUS_C0");

                // c1
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[3]), "ANONYMOUS_C1");

                // c2
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[4]), "ANONYMOUS_C2");

                // c5
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[5]), "ANONYMOUS_C5");

                // c6
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[6]), "ANONYMOUS_C6");

                // c7
                intAttrAdder(colSet, workingMap, Integer.parseInt(line[7]), "ANONYMOUS_C7");
            }

            trainReader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/montanez/jane/ref/wip/allup_train.csv")));
            writer.write("handle,");

            for (String h1 : colSet) {
                writer.write(h1 + ",");
            }

            writer.write("\n");

            for (String k1 : trainMap.keySet()) {
                writer.write(k1 + toSetString(colSet,trainMap.get(k1)) + "\n");
                writer.flush();
            }

            writer.flush();
            writer.close();
        } catch (Exception e)  {
            e.printStackTrace();
        }

    }

    private static String toSetString (TreeSet<String> colSet, TreeMap<String,Integer> tm) {
        StringBuilder sb = new StringBuilder();
        for (String col : colSet) {
            sb.append("," + (tm.get(col) == null ? 0 : tm.get(col)));
        }
        return sb.toString();
    }

    private static void intAttrAdder (TreeSet<String> colSet, TreeMap<String,Integer> tm, Integer val, String prefix) {
        String key = prefix + "_" + val;
        if (!colSet.contains(key)) {
            colSet.add(key);
        }
        if (!tm.containsKey(key)) {
            tm.put(key,0);
        }
        tm.put(key, tm.get(key) + 1);
    }

    private static String getSetString (HashSet<String> s1) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (String t1 : s1) {
            sb.append(" " + t1);
        }
        sb.append("}");
        return sb.toString();
    }

    private static Map<String,HashSet<String>> addHelper (Map<String,HashSet<String>> m1, String key, String val) {
        if (!m1.containsKey(key)) {
            m1.put(key, new HashSet<String>());
        }
        m1.get(key).add(val);
        return m1;
    }

//    public static void main(String[] args) {
//        try {
//            Map<String,HashSet<String>> cookieMap = new HashMap<String,HashSet<String>>();
//            Map<String,HashSet<String>> devMap = new HashMap<String,HashSet<String>>();
//
//            BufferedReader cookieReader = new BufferedReader(new FileReader(new File("/Users/montanez/jane/ref/wip/cookies_v3.csv")));
//            BufferedReader devReader = new BufferedReader(new FileReader(new File("/Users/montanez/jane/ref/wip/dev_train_v3.csv")));
//
//            String cs = null;
//            while ((cs = cookieReader.readLine()) != null) {
//                String[] t1 = cs.split(",");
//                addHelper(cookieMap,t1[0],t1[1]);
//            }
//
//            cs = null;
//            cookieReader.close();
//
//            while ((cs = devReader.readLine()) != null) {
//                String[] t1 = cs.split(",");
//                addHelper(devMap,t1[0],t1[1]);
//                addHelper(devMap,"_" + t1[0], t1[2] + "," + t1[3]+ "," + t1[4]+ "," + t1[5]+ "," + t1[6]+ "," + t1[7]+ "," + t1[8]);
//            }
//
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/montanez/jane/ref/wip/cookie_train.csv")));
//            writer.write("handle,cookie,country,anonymous_c0,anonymous_c1,anonymous_c2,anonymous_5,anonymous_6,anonymous_7\n");
//            for (String key : devMap.keySet()) {
//                if (!cookieMap.containsKey(key)) { continue; }
//
//                for (String c1 : cookieMap.get(key)) {
//                    writer.write(key + "," + c1 + "," + devMap.get("_"+key).toArray()[0] + "\n");
//                }
//                writer.flush();
//            }
//            writer.flush();
//            writer.close();
//        } catch (Exception e)  {
//            e.printStackTrace();
//        }
//
//    }

//    public static void main(String[] args) {
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/montanez/jane/ref/wip/dev_train_basicls.csv")));
//            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/Users/montanez/jane/ref/wip/cookies_convert_out_nohandle.csv")));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] lines = line.split(",");
//                writer.write(
//                        //lines[0].replace("handle_","") + "," +
//                        lines[1].replace("id_","") + "," +
//                                lines[2].replace("computer_os_type_","") + "," +
//                                lines[3].replace("computer_browser_version_","") + "," +
//                                lines[4].replace("country_","") + "," +
//                                lines[5] + "," +
//                                lines[6].replace("anonymous_c1_", "") + "," +
//                                lines[7].replace("anonymous_c2_", "") + "," +
//                                lines[8] + "," +
//                                lines[9] + "," +
//                                lines[10] + "\n");
//                writer.flush();
//            }
//            reader.close();
//            writer.flush();
//            writer.close();
//        } catch (Exception e)  {
//            e.printStackTrace();
//        }
//
//    }
}
