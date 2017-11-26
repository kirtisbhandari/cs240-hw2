
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.regex.*;
import java.util.*;

public class Reduce extends Reducer<Text, Text, Text, IntWritable> {
	private IntWritable total = new IntWritable();

        @Override
		public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
			int sum = 0;
		    Set s = new HashSet<String>();	
		    for (Text value : values) {
		    	s.add(value.toString());
		    }
		    total.set(s.size());
		    context.write(key, total);
		}
}
	

