package controller.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.entity.Event;
import model.service.SearchService;

@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private SearchService searchService = new SearchService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String strategy = request.getParameter("strategy");
        String keyword = request.getParameter("keyword");

        if (strategy == null || strategy.trim().isEmpty()) {
            strategy = request.getParameter("searchBy");
        }

        List<Event> events = new ArrayList<Event>();

        if (strategy != null && !strategy.trim().isEmpty()) {
            if ("availability".equalsIgnoreCase(strategy)) {
                events = searchService.searchEvents(strategy, "");
            } else if (keyword != null && !keyword.trim().isEmpty()) {
                events = searchService.searchEvents(strategy, keyword.trim());
            }
        }

        request.setAttribute("events", events);
        request.setAttribute("strategy", strategy);
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