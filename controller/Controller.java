package com.ansible.UIBackend.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ansible.UIBackend.domain.Variables;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping(value = "/submit", produces = "application/json", method = RequestMethod.POST)
    public List<Variables> getVariables(@RequestBody Variables variables)
    {
        List<Variables> variablesList = new ArrayList<Variables>();
        Set jiraId = new HashSet<String>();
        String name = variables.getName();
        String tcs = variables.getTcs();
        String password = variables.getPassword();
//        variables.setName(name);
//        variables.setPassword(password);
//        variables.setTcs(tcs);
        variablesList.add(variables);
//        String command = "/bin/bash -c ssh 131.131.131.201";
        String location = " /home/ansible/Downloads/trial";
//        String common = "echo ";
//
//        String commonName = common.concat(name);
//        String commonTcs = common.concat(tcs);
//        String commonPassword = common.concat(password);

        String regName = " sed -i 's/^name:.*/name: ";
        String regTcs = " sed -i 's/^tcs:.*/tcs: ";
        String regPassword = " sed -i 's/^password:.*/password: ";

        String regexName = regName.concat(name.concat("/g' trial"));
        String regexTcs = regTcs.concat(tcs.concat("/g' trial"));
        String regexPassword = regPassword.concat(password.concat("/g' trial"));

//        String tempName = commonName.concat(regexName);
//        String tempTcs = commonTcs.concat(regexTcs);
//        String tempPassword = commonPassword.concat(regexPassword);

//        System.out.println(tempName);

        String commandName = regexName.concat(location);
        String commandTcs = regexTcs.concat(location);
        String commandPassword = regexPassword.concat(location);
//        String commandF = "\"" + command2 + "\"";
//        System.out.println(commandF);
        System.out.println(commandName);

        String command = "cat /home/ansible/jiraID.txt";

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);
        try {
            Process p = builder.start();
            try(BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                String line;

                while ((line=buf.readLine())!=null){
                  jiraId.add(line);
                }
            }

        }catch (IOException e){
            System.out.println("IOException");
        }

        System.out.println(jiraId);
//        try {
////            Runtime.getRuntime().exec(command);
//            Runtime.getRuntime().exec(new String[]{"csh","-c",commandName});
//            Runtime.getRuntime().exec(new String[]{"csh","-c",commandTcs});
//            Runtime.getRuntime().exec(new String[]{"csh","-c",commandPassword});
//            System.out.println("Written to file");
//
////            ProcessBuilder pb = new ProcessBuilder(command2);
////            Process process = pb.start();
////            try {
////                if (process.waitFor() <= 10) {
////                    System.out.println("Success!");
////                    System.exit(0);
////                }
////                else{
////                    System.exit(1);
////                }
////            }catch (InterruptedException e){
////                System.out.println("Interrupted exception");
////            }
//
//        }catch (IOException e){
//            System.out.println("IOException");
//            return null;
//        }
//        Process proc = Runtime.getRuntime().exec(command);
        return variablesList;
    }

//    @GetMapping("/rest/api/2/search?jql=issue={issue_id}")
//    public void getIssues(String issue_id){
//
//    }



}


