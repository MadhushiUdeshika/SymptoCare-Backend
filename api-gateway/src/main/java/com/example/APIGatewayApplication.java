package com.example;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class APIGatewayApplication {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                String pathInfo = req.getPathInfo();
                if (pathInfo.startsWith("/user-service")) {
                    forwardRequest("http://localhost:8081", req, resp);
                } else if (pathInfo.startsWith("/disease-prediction-service")) {
                    forwardRequest("http://localhost:8082", req, resp);
                } else if (pathInfo.startsWith("/appointment-service")) {
                    forwardRequest("http://localhost:8083", req, resp);
                } else if (pathInfo.startsWith("/payment-processing-service")) {
                    forwardRequest("http://localhost:8084", req, resp);
                } else if (pathInfo.startsWith("/report-generate-service")) {
                    forwardRequest("http://localhost:8085", req, resp);
                } else if (pathInfo.startsWith("/notification-service")) {
                    forwardRequest("http://localhost:8086", req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().println("Service not found.");
                }
            }

            private void forwardRequest(String baseUrl, HttpServletRequest req, HttpServletResponse resp) throws IOException {
                String fullPath = baseUrl + req.getRequestURI().replaceFirst(req.getContextPath(), "");

                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    HttpGet httpGet = new HttpGet(fullPath);
                    try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                        int statusCode = httpResponse.getStatusLine().getStatusCode();
                        resp.setStatus(statusCode);

                        HttpEntity entity = httpResponse.getEntity();
                        if (entity != null) {
                            String responseContent = EntityUtils.toString(entity);
                            resp.getWriter().write(responseContent);
                        }
                    }
                }
            }
        }), "/*");

        server.start();
        server.join();
    }
}
