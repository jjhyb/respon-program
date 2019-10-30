package com.yibo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 15:58
 * @Description:
 */

@WebServlet("/SyncServlet")
public class SyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long t1 = System.currentTimeMillis();

        // 执行业务代码
        doSomeThing(request, response);

        System.out.println("sync use:" + (System.currentTimeMillis() - t1));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void doSomeThing(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
        }

        //
        response.getWriter().append("done");
    }
}
