package com.yibo;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 16:07
 * @Description:
 */

@WebServlet(asyncSupported = true, urlPatterns = { "/AsyncServlet" })
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long t1 = System.currentTimeMillis();

        // 开启异步
        AsyncContext asyncContext = request.startAsync();

        // 执行业务代码
        CompletableFuture.runAsync(() -> doSomeThing(asyncContext,
                asyncContext.getRequest(), asyncContext.getResponse()));

        System.out.println("async use:" + (System.currentTimeMillis() - t1));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void doSomeThing(AsyncContext asyncContext,
                             ServletRequest servletRequest, ServletResponse servletResponse) {

        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }

        //
        try {
            servletResponse.getWriter().append("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 业务代码处理完毕, 通知结束
        asyncContext.complete();
    }
}
