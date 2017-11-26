
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.regex.*;


public class Map extends Mapper<Object, Text, Text, Text> {
		private final static IntWritable one = new IntWritable(1);
		private Text dailyUser = new Text();
		private Text day = new Text();
		
		//private Pattern p = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[([\\w/]+)([\\w:]+)\\] \"(GET|POST)\\s([^\\s]+)\"");
		//private Pattern p = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[(\\w/\\w/\\w)([\\w:]+)\\] (GET|POST)\\s([^\\s]+)");
	        //private Pattern p = Pattern.compile("^([\\d.]+) (\\S) (\\S) \\[(\\w:/]+)\\]");// \"(GET|POST)\\s([^\\s]+) ([\\w/.]+)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");	
		//works
		//private Pattern p = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+)\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");
		//works
		//private Pattern p = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+)\\] \"(GET|POST)\\s(.+?)\\s([\\w./]+)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");
		private Pattern p = Pattern.compile("^([\\d.]+) (\\S+) (\\S+) \\[(\\d{2}/\\S{3}/\\d{4})(:\\d{2}:\\d{2}:\\d{2})\\] \"(GET|POST)\\s(.+?)\\s([\\w./]+)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");	
        @Override
		public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
			String[] entries = value.toString().split("\r?\n"); 
			for (int i=0, len=entries.length; i<len; i+=1) {
				Matcher matcher = p.matcher(entries[i]);
				if (matcher.find()) {
					dailyUser.set(matcher.group(1));
					day.set(matcher.group(4));
					context.write(day, dailyUser);
				}
			}
		}
}

