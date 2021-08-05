package com.larryhsiao.req_body_check;

import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsWithStatus;

import java.io.File;
import java.io.IOException;

/**
 * TkRegex check the request body in same path of request path.
 */
public class TkBodyChecking implements TkRegex {
    private final File root;

    public TkBodyChecking(File root) {this.root = root;}

    @Override
    public Response act(RqRegex req) throws IOException {
        // @todo #1 TkBodyChecking
        return new RsWithStatus(204);
    }
}
