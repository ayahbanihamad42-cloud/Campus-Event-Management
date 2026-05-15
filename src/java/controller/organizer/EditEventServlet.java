package controller.organizer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.entity.Category;
import model.entity.Event;
import model.factory.EventFactory;
import model.service.EventService;

@WebServlet(name = "EditEventServlet", urlPatterns = {"/EditEventServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class EditEventServlet extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));

            Event event = eventService.getEventById(eventId);

            request.setAttribute("event", event);

            request.getRequestDispatcher("/View/Organizer/editEvent.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            String eventType = request.getParameter("eventType");

            Event event = EventFactory.createEvent(eventType);

            if (event == null) {
                throw new IllegalArgumentException("Invalid event type.");
            }

            event.setId(eventId);
            event.setTitle(request.getParameter("title"));
            event.setOrganizerName(request.getParameter("organizerName"));
            event.setDescription(request.getParameter("description"));
            event.setDepartmentClub(request.getParameter("departmentClub"));
            event.setEventDateTime(parseEventDateTime(request.getParameter("eventDateTime")));
            event.setLocation(request.getParameter("location"));
            event.setCapacity(Integer.parseInt(request.getParameter("capacity")));
            event.setReservedSeats(Integer.parseInt(request.getParameter("reservedSeats")));
            event.setCategory(parseCategory(request.getParameter("category")));
            event.setStatus(request.getParameter("status"));
            event.setEventType(eventType);

            Event oldEvent = eventService.getEventById(eventId);

            if (oldEvent != null) {
                event.setImagePath(oldEvent.getImagePath());
            }

            Part imagePart = request.getPart("eventImage");

            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = saveImage(imagePart);
                event.setImagePath("images/" + fileName);
            }

            boolean updated = eventService.updateEvent(event);

            HttpSession session = request.getSession();

            if (updated) {
                session.setAttribute("message", "Event updated successfully.");
            } else {
                session.setAttribute("message", "Failed to update event.");
            }

            response.sendRedirect(request.getContextPath() + "/ManageEventsServlet");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "Invalid event data: " + e.getClass().getSimpleName() + " - " + e.getMessage()
            );

            request.getRequestDispatcher("/View/Organizer/editEvent.jsp")
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

    private LocalDateTime parseEventDateTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Event Date & Time is required.");
        }

        value = value.trim();

        try {
            return LocalDateTime.parse(value);
        } catch (Exception e) {
            // Try browser/local format
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