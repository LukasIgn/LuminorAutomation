import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.*;
import org.junit.jupiter.api.Test;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.url;

@Epic("Imdb Tests Epic")
@Feature("Check imdb search box for Game of thrones game")
public class ImdbTest {
    @Test()
    @Story("User checks imdb page, searches for game of thrones and verifies if correct page is open.")
    public void findGOTGameTestCase() throws IOException {
        openImdbVideoGame();
        setSuggestionSearchValue();
        findGameFromList();
        checkIfUrlCorrect();
        takeScreenshot();
    }

    @Step("Opens imdb page.")
    public void openImdbVideoGame() {
        open("https://www.imdb.com/");
    }

    @Step("Types: game of thrones, to the search box.")
    public void setSuggestionSearchValue() {
        SelenideElement suggestionSearch = $("#suggestion-search");
        suggestionSearch.setValue("games of thrones");
    }

    @Step("Finds a game from the list and opens said page.")
    public void findGameFromList() {
        $("#nav-search-form .react-autosuggest__container .sc-htpNat").should(disappear);

        ElementsCollection linkElements = $("#nav-search-form").findAll("a").filterBy(attribute("href"));

        for (SelenideElement element : linkElements) {
            String attr = element.getAttribute("href");
            if (attr.contains("tt2231444")) {
                element.click();
                break;
            }
        }
    }

    @Step("Checks if url matches the game url.")
    public void checkIfUrlCorrect() {
        String url = url();
        Assert.assertTrue(url.contains("https://www.imdb.com/title/tt2231444"));
    }

    @Step("Takes screenshot")
    public void takeScreenshot() throws IOException {
        screenshot("imdbGame");
        attachment();
    }

    @Attachment(type = "image/png")
    public byte[] attachment() throws IOException {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }
}
