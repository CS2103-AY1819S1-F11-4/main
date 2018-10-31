package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
        "Timetable.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }


    /*
    import java.io.IOException;
    import java.net.URL;
    import java.util.logging.Logger;

    import org.jsoup.Jsoup;
    import org.jsoup.nodes.Document;
    import org.jsoup.nodes.Element;

    import com.google.common.base.Charsets;
    import com.google.common.eventbus.Subscribe;
    import com.google.common.io.Resources;
    Loads the Timetable.html file with the timetable of the person selected for jar
    private void loadPersonPage(Person person) {
       URL timetablePage = MainApp.class.getResource(FXML_FILE_FOLDER + SEARCH_PAGE_URL);
       try {
           String location = Resources.toString(timetablePage, Charsets.UTF_8);
           Document document = Jsoup.parse(location, "UTF-8");
           Element element = document.getElementById("timetable");
           element.attr("value", person.getTimetable().getTimetableAsString());
           Platform.runLater(() -> browser.getEngine().loadContent(document.toString()));
       } catch (IOException e) {
           loadPage(timetablePage.toExternalForm());
           e.printStackTrace();
       }
   }
    */
    private void loadPersonPage(Person person) {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + SEARCH_PAGE_URL);
        String timetableString = person.getTimetable().getTimetableAsString();
        loadPage(defaultPage.toExternalForm() + "?name=" + timetableString);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }
}
