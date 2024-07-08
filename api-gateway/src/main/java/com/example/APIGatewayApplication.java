package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                // Forward request and handle response
                // Example using Apache HttpClient or another HTTP client library
            }
        }), "/*");

        server.start();
        server.join();
    }
}
