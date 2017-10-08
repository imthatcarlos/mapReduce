
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class MapReduce {

    public static HashMap<String, Integer> testNameCounts;

    public static void main(String[] args) throws IOException {
        testNameCounts = new HashMap<String, Integer>();

        // Don't need this anymore...
        //parseCoverageReports();

        JobConf conf = new JobConf(MapReduce.class);

        conf.setJobName("test coverage");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }

    //Mapper class
    public static class E_EMapper extends MapReduceBase implements
            Mapper<LongWritable ,        /*Input key Type */
                    Text,                /*Input value Type*/
                    Text,                /*Output key Type*/
                    Text>                /*Output value Type*/
    {
        //Map function
        public void map(LongWritable key, Text value,
                        OutputCollector<Text, Text> output,
                        Reporter reporter) throws IOException
        {
            String line = value.toString();

            String[] values = line.split(":");
            String outKey = values[0] + ":" + values[1];
            String outVal = values[2];

            if (testNameCounts.containsKey(outVal)) {
                Integer count = testNameCounts.get(outVal);
                testNameCounts.put(outVal, count + 1);
            }
            else {
                testNameCounts.put(outVal, 1);
            }

            output.collect(new Text(outKey), new Text(outVal));
        }
    }

    //Reducer class
    public static class E_EReduce extends MapReduceBase implements
            Reducer< Text, Text, Text, Text >
    {

        //Reduce function
        public void reduce( Text key, Iterator <Text> values,
                            OutputCollector<Text, Text> output, Reporter reporter) throws IOException

        {
            ArrayList<String> testNames = new ArrayList<String>();
            while (values.hasNext())
            {
                testNames.add(values.next().toString());
            }

            String value = sortTestNames(testNames);

            value = "(" + value + ")>";
            String outKey = "<" + key.toString();

            output.collect(new Text(outKey), new Text(value));
        }
    }

    public static String sortTestNames(ArrayList<String> names) {

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String p1, String p2) {
                return Integer.valueOf(testNameCounts.get(p1)).compareTo(Integer.valueOf(testNameCounts.get(p2)));
            }
        });

        StringBuilder sb = new StringBuilder();
        for (String str : names) {
            sb.append(str);
            sb.append(",");
        }

        return sb.toString().replaceAll(",$","");
    }

    private static void parseCoverageReports() {
        File folder = new File("reports");
        File[] listOfFiles = folder.listFiles();

        String fName = "input.txt";

        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fName), "utf-8"))) {
                for (File inputFile : listOfFiles) {
                    if (!inputFile.getName().endsWith(".xml")) continue;

                    StringJoiner joiner = new StringJoiner(",");

                    List<String> keys = parseSourceFiles(inputFile);

                    for (String key : keys) {
                        try {
                            writer.append(key);
                            ((BufferedWriter) writer).newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> parseSourceFiles(File inputFile) {
        List<String> data = new ArrayList<String>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList sourceFiles = doc.getElementsByTagName("sourcefile");

            for (int idx = 0; idx < sourceFiles.getLength(); ++idx) {
                Node nNode = sourceFiles.item(idx);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String fName = eElement.getAttribute("name");
                    NodeList lines = eElement.getElementsByTagName("line");

                    for (int j = 0; j < lines.getLength(); ++j) {
                        Node node = lines.item(j);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node;
                            if (element != null) {
                                String ciValue = element.getAttribute("ci");

                                String lineNumber = element.getAttribute("nr");
                                if (Integer.parseInt(ciValue) > 0 ) {
                                    String testName = inputFile.getName().replaceFirst("[.][^.]+$", "");
                                    String key = lineNumber + ":" + fName + ":" + testName;
                                    data.add(key);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}