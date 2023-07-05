package guru.qa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class JunitTests extends TestBase {

    @BeforeEach
    void setUp() {
        open("https://www.wildberries.ru/");
    }

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }

    @CsvSource(value = {
            "подушка |  подушка 70х70",
            "подушка |  подушка 50х70"
    },
            delimiter = '|')
    @DisplayName("Проверка наличия доступных тегов для искомого предмета")
    @ParameterizedTest(name = "Наличие тега {1} для поиска предмета {0}")
    void searchTagForItem(String item, String tag) {
        $("#searchInput")
                .setValue(item)
                .pressEnter();

        $("#catalog").shouldHave(text(tag));
    }

    @DisplayName("Проверка работы поиска предмета")
    @ParameterizedTest
    @EnumSource(Items.class)
    void searchItem(Items title) {
        $("#searchInput")
                .setValue(title.getTitle())
                .pressEnter();

        $("#catalog").shouldHave(text(title.getTitle()));
    }

    static Stream<Arguments> searchTagsForEachItems() {
        return Stream.of(
                Arguments.of(
                        "подушка",
                        List.of("подушка 70х70", "подушка 50х70", "подушки декоративные")
                ),
                Arguments.of(
                        "одеяло",
                        List.of("одеяло двуспальное", "одеяло 2 спальное")
                )
        );
    }

    @DisplayName("Проверка наличия доступных тегов для кажого из предметов")
    @ParameterizedTest
    @MethodSource("searchTagsForEachItems")
    void searchTagsForEachItems(String item, List<String> tag) {
        $("#searchInput")
                .setValue(item)
                .pressEnter();

        $("#catalog").shouldHave(text(String.join(" ", tag)));
    }

}