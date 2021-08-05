package com.larryhsiao.req_body_check;

import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.fork.TkMethods;
import org.takes.http.Exit;
import org.takes.http.FtBasic;

import java.io.File;
import java.nio.file.Files;

/**
 * Entry point of req_body_check
 */
public class Main {
    public static void main(String[] args) {
        try {
            final File rootDir = Files.createTempDirectory("temp").toFile();
            new FtBasic(
                new TkFork(
                    new FkRegex(
                        //Language=RegExp
                        "/(?<path>.*)",
                        new TkBodyChecking(rootDir)
                    )
                ),
                8080
            ).start(Exit.NEVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
