package hung.learn.crud;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    public void init() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
        request.setAttribute("itemList", items);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/hello-servlet.jsp");
        dispatcher.forward(request, response);
    }
}