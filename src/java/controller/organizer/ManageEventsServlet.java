package controller.organizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.entity.Category;
import model.entity.Event;
import model.factory.EventFactory;
import model.service.EventService;

@WebServlet(name = "ManageEventsServlet", urlPatterns = {"/ManageEventsServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class ManageEventsServlet extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
                return;
            }

            String organizerName = (String) session.getAttribute("userName");
            List<Event> events = eventService.getEventsByOrganizer(organizerName);

            request.setAttribute("events", events);
            request.getRequestDispatcher("/View/Organizer/manageEvents.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/View/Organizer/dashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect(request.getContextPath() + "/View/Auth/login.jsp");
                return;
            }

            String organizerName = (String) session.getAttribute("userName");

            if (organizerName == null || organizerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Organizer session name is missing. Please login again.");
            }

            String eventType = required(request, "eventType", "Event Type");

            Event event = EventFactory.createEvent(eventType);

            if (event == null) {
                throw new IllegalArgumentException("Event Type is invalid: " + eventType);
            }

            event.setTitle(required(request, "title", "Event Title"));
            event.setOrganizerName(organizerName);
            event.setDescription(required(request, "description", "Description"));
            event.setDepartmentClub(required(request, "departmentClub", "Department/Club"));
            event.setEventDateTime(parseEventDateTime(required(request, "eventDateTime", "Event Date & Time")));
            event.setLocation(required(request, "location", "Location"));

            try {
                event.setCapacity(Integer.parseInt(required(request, "capacity", "Capacity")));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Capacity must be a number.");
            }

            event.setReservedSeats(0);
            event.setCategory(parseCategory(required(request, "category", "Category")));
            event.setStatus("Open");
            event.setEventType(eventType);

            Part imagePart = request.getPart("eventImage");

            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = saveImage(imagePart);
                event.setImagePath("images/" + fileName);
            } else {
                event.setImagePath("");
            }

            boolean created = eventService.addEvent(event);

            if (created) {
                session.setAttribute("message", "Event created successfully.");
                response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
            } else {
                request.setAttribute("errorMessage", "Failed to create event.");
                request.getRequestDispatcher("/View/Organizer/createEvent.jsp")
                        .forward(request, response);
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/View/Organizer/createEvent.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage",
                    "System error while creating event: " + e.getClass().getSimpleName());
            request.getRequestDispatcher("/View/Organizer/createEvent.jsp")
                    .forward(request, response);
        }
    }

    private String saveImage(Part imagePart) throws IOException {
        String fileName = getFileName(imagePart);

        String deployedRootPath = getServletContext().getRealPath("/");
        File deployedRoot = new File(deployedRootPath);
        File deployedImagesDir = new File(deployedRoot, "images");

        if (!deployedImagesDir.exists()) {
            deployedImagesDir.mkdirs();
        }

        File deployedImageFile = new File(deployedImagesDir, fileName);

        InputStream inputStream = imagePart.getInputStream();

        Files.copy(
                inputStream,
                deployedImageFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

        inputStream.close();

        String sourceRootPath = deployedRootPath;

        if (sourceRootPath.contains(File.separator + "build" + File.separator + "web")) {
            sourceRootPath = sourceRootPath.replace(
                    File.separator + "build" + File.separator + "web",
                    File.separator + "web"
            );

            File sourceImagesDir = new File(sourceRootPath, "images");

            if (!sourceImagesDir.exists()) {
                sourceImagesDir.mkdirs();
            }

            File sourceImageFile = new File(sourceImagesDir, fileName);

            Files.copy(
                    deployedImageFile.toPath(),
                    sourceImageFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        System.out.println("IMAGE SAVED TO: " + deployedImageFile.getAbsolutePath());

        return fileName;
    }

    private String required(HttpServletRequest request, String inputName, String label) {
        String value = request.getParameter(inputName);

        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " is required.");
        }

        return value.trim();
    }

    private LocalDateTime parseEventDateTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Event Date & Time is required.");
        }

        value = value.trim();

        try {
            return LocalDateTime.parse(value);
        } catch (Exception e) {
            // Try another format
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Event Date & Time format is invalid: " + value);
        }
    }

    private Category parseCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category is required.");
        }

        String value = category.trim().toUpperCase();

        if ("EDUCATIONAL".equals(value)) {
            return Category.EDUCATIONAL;
        } else if ("SOCIAL".equals(value)) {
            return Category.SOCIAL;
        } else if ("SPORTS".equals(value)) {
            return Category.SPORTS;
        } else if ("CULTURAL".equals(value)) {
            return Category.CULTURAL;
        } else if ("TECHNICAL".equals(value)) {
            return Category.TECHNICAL;
        }

        throw new IllegalArgumentException("Category is invalid: " + category);
    }

    private String getFileName(Part part) {
        String submittedFileName = part.getSubmittedFileName();

        if (submittedFileName == null || submittedFileName.trim().isEmpty()) {
            return "event_image_" + System.currentTimeMillis() + ".png";
        }

        submittedFileName = submittedFileName.replace("\\", "/");
        submittedFileName = submittedFileName.substring(submittedFileName.lastIndexOf("/") + 1);

        return System.currentTimeMillis() + "_" + submittedFileName;
    }
}