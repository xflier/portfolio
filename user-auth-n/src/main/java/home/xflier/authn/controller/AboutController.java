package home.xflier.authn.controller;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling requests related to the "About" page and application shutdown.
 * <p>
 * This controller provides endpoints for displaying information about the application and
 * shutting down the application gracefully.
 * </p>
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@RestController
@RequestMapping("/")
public class AboutController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutController.class);

    @Autowired
    private ApplicationContext context;

    /**
     * Handles GET requests to the "about" endpoint.
     * <p>
     * This method retrieves and logs request headers, session attributes, and CSRF token information.
     * </p>
     *
     * @param req the HttpServletRequest object containing request information
     * @return a ResponseEntity containing the result of the operation
     */
    @GetMapping("about")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<String> about(HttpServletRequest req) {

        Enumeration<String> headers = req.getHeaderNames();
        HttpSession session = req.getSession(false);
        String result = "<p>About Page</p>";

        while (headers.hasMoreElements()) {
            String hname = headers.nextElement();
            String hvalue = req.getHeader(hname);
            LOGGER.info("Header : " + hname + " = " + hvalue);
            result = result + "<p>Header : " + hname + " = " + hvalue + "</p>";
        }

        CsrfToken csrfToken = (CsrfToken) req.getAttribute("_csrf");
        if (csrfToken != null) {
            LOGGER.info(
                    csrfToken.getHeaderName() + " = " + csrfToken.getParameterName() + " - " + csrfToken.getToken());
            result = result + "<p>" + csrfToken.getHeaderName() + " = " + csrfToken.getParameterName() + " : "
                    + csrfToken.getToken() + "</p>";
        } else {
            LOGGER.info("No CSRF Token");
        }
        if (session != null) {
            result = result + "<p>session id = " + session.getId() + "</p>";
            LOGGER.info("session id = " + session.getId());
            Enumeration<String> attriNames = session.getAttributeNames();

            while (attriNames.hasMoreElements()) {
                String attr = attriNames.nextElement();
                String attriValue = session.getAttribute(attr).toString();
                result = result + "<p>" + attriValue + "</p>";
                LOGGER.info(attr + " : " + attriValue);
            }
        } else {
            LOGGER.info("No session created!");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Handles GET requests to the "shutdown" endpoint.
     * <p>
     * This method initiates a graceful shutdown of the application.
     * </p>
     *
     * @param req the HttpServletRequest object containing request information
     * @return a ResponseEntity indicating the shutdown status
     */
    @GetMapping("shutdown")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> shutdown(HttpServletRequest req) {
        LOGGER.info("Got a request /shutdown -GET, exit now ...");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100); // Small delay to allow response to be sent
                int exitCode = SpringApplication.exit(context, () -> 0);
                System.exit(exitCode);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        return new ResponseEntity<>("<p>Shutting down ...</p>", HttpStatus.OK);
    }

    /**
     * Handles GET requests to the "hello" endpoint.
     * <p>
     * This method returns a simple "Hello World" message.
     * </p>
     *
     * @return a ResponseEntity containing the "Hello World" message
     */
    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("<p>Hello World</p>", HttpStatus.OK);
    }
    
}
