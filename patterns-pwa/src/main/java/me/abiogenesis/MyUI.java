package me.abiogenesis;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import org.vaadin.leif.headertags.HeaderTagHandler;
import org.vaadin.leif.headertags.Link;
import com.vaadin.ui.*;
import org.apache.commons.io.IOUtils;
import org.vaadin.leif.headertags.Meta;
import org.vaadin.leif.headertags.MetaTags;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@JavaScript("vaadin://js/app.js")
@MetaTags({
    @Meta(name="viewport", content="width=device-width, initial-scale=1"),
    @Meta(name="theme-color", content="#00b4f0")
})
@Link(rel="manifest", href="VAADIN/manifest.json")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                + ", it works!"));
        });

        layout.addComponents(name, button);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            final BootstrapListener listener = new HeaderTagHandler();
            getService().addSessionInitListener(event -> event.getSession().addBootstrapListener(listener));
            getService().addSessionInitListener(event ->
                event.getSession().addRequestHandler(
                    (session, request, response) -> {

                        String pathInfo = request.getPathInfo();

                        if (pathInfo.endsWith("sw.js")) {
                            response.setContentType("application/javascript");
                            try (InputStream in = getClass().getResourceAsStream("/sw.js");
                                 OutputStream out = response.getOutputStream())
                            {
                                IOUtils.copy(in, out);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    })
            );
        }
    }
}
