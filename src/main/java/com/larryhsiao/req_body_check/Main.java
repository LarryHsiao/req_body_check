package com.larryhsiao.req_body_check;

import com.larryhsiao.clotho.file.TextFile;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.http.FtCli;

import java.io.File;

/**
 * Entry point of req_body_check
 */
public class Main {
    public static void main(String[] args) {
        try {
            final File rootDir = new File("/home/larryhsiao/cmoney_forum");
           new TextFile(
                new File(rootDir, "temp.json"),
                // language=JSON
                "{\n" +
                    "  \"integer\": 100,\n" +
                    "  \"string\": \"string\",\n" +
                    "  \"boolean\": false,\n" +
                    "  \"string_array\": [\n" +
                    "    \"st\"\n" +
                    "  ],\n" +
                    "  \"object_array\": [\n" +
                    "    {\n" +
                    "      \"string\": \"string\",\n" +
                    "      \"boolean\": false\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}"
           ).value();
            new FtCli(
                new TkFork(
                    new FkRegex(
                        //Language=RegExp
                        "/(?<path>.*)",
                        new TkBodyChecking(rootDir)
                    )
                ),
                args
            ).start(Exit.NEVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
