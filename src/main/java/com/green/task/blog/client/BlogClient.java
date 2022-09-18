package com.green.task.blog.client;

public interface BlogClient<Request, Response> {
    Response search(Request request);
}
