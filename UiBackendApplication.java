package com.ansible.UIBackend;

import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import scala.Array;
import scala.util.parsing.combinator.testing.Str;

@SpringBootApplication
public class UiBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(UiBackendApplication.class, args);

		Set jiraId = new HashSet<String>();
		String[] command = new String[]{"csh", "-c", "cat /home/ansible/jiraID.txt"};

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);
		try {
			Process p = builder.start();
			try (BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

				String line;

				while ((line = buf.readLine()) != null) {
					jiraId.add(line);
				}
			}

		} catch (IOException e) {
			System.out.println("IOException");
		}

		System.out.println(jiraId);

		// parsing file "JSONExample.json"

		try {


			try {


				Object obj = new JSONParser().parse(new FileReader("/home/ansible/test.json"));


				// typecasting obj to JSONObject
				JSONObject jo = (JSONObject) obj;
//				JSONObject jo2 = (JSONObject) obj2;

				String myJsonString = jo.toString();

				ArrayList branches = new ArrayList<String>();

				String issues = (String) JsonPath.read(jo, "$.issues[0].fields.customfield_10640");

				System.out.println(issues);

				String[] issue = issues.trim().split("\n");

				for(int i=0; i<issue.length; i++){

					String bzr = issue[i].split("\\[")[1].split(":")[0];
					branches.add(bzr);
				}

				System.out.println(branches);

// To use pattern matcher, uncomment the code below
//				Pattern p = Pattern.compile("^\\[(.*? )\\:");
//				for(int i=0; i<issue.length;i++) {
//					Matcher m = p.matcher(issue[i]);
//
//					while (m.find()) {
//						System.out.println(m.group(1));
//					}
//				}

				String parent = (String) JsonPath.read(jo, "$.issues[0].fields.parent.key");
				System.out.println(parent);

//				String clone = (String) JsonPath.read(jo2, "$.issues[0].fields.issuelinks.outwardIssue[0].key");
//				System.out.println(clone);

			} catch (FileNotFoundException fError) {
				System.out.println(fError.getMessage());
			}

		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}

		try {

			JSONObject cloned = (JSONObject) new JSONParser().parse(new FileReader("/home/ansible/clone.json"));
			JSONObject jo2 = (JSONObject) cloned;
//			String clone = (String) JsonPath.read(jo2, "$.issues[0].fields.subtasks[0].key");
//			System.out.println(clone);
////			String jsonPath = "$.issues[0].fields".concat()
//			String clone = (String) JsonPath.read(jo2, "$.issues[0].fields.issuelinks[0].outwardIssue.key");
//			System.out.println(clone);
			JSONArray issueLinks = (JSONArray) JsonPath.read(jo2, "$.issues[0].fields.issuelinks");
			System.out.println(issueLinks);
			for (Object outwardIssue: issueLinks)
			{
				String clone_new = (String) JsonPath.read(outwardIssue, "$.outwardIssue.key");
				System.out.println(clone_new);
				JSONArray subtasks = (JSONArray) JsonPath.read(jo2, "$.issues[0].fields.subtasks");
				int i = 0;
				for(Object fields: subtasks){
					String taskName = (String) JsonPath.read(fields, "$.fields.issuetype.name");
//					System.out.println(taskName);
					if(taskName.equals("Implementation sub-task")){
						String count = Integer.toString(i);
						String subtask = "subtasks".concat("[".concat(count.concat("]")));
						String fetch = "$.issues[0].fields.".concat(subtask.concat(".key"));
//						System.out.println(fetch);
						String impSubTask = (String) JsonPath.read(jo2, fetch);
						System.out.println("Implementation Sub-task is: " + impSubTask);
					}
					i=i+1;
				}
			}
//			for(int i=0; i<issueLinks.size();i++){
//				String fields = "$.issues[0].fields.";
//				String outwardIssue = issueLinks[i];
//			}

		}catch (Exception e){
			System.out.println(e.getMessage());
		}

	}


}