package controller.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.entity.Event;
import model.service.SearchService;

@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private SearchService searchService = new SearchService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchBy = request.getParameter("searchBy");
        String keyword = request.getParameter("keyword");

        List<Event> events = new ArrayList<Event>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            events = searchService.searchEvents(searchBy, keyword.trim());
        }

        request.setAttribute("events", events);
        request.setAttribute("searchBy", searchBy);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/View/Student/event.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}