package integration_tests.controller;

import org.example.Application;
import org.example.dto.RegisterRequest;
import org.example.entity.Account;
import org.example.service.strategy.sending_message_strategy.EmailSendingMessageStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import test_util.IntegrationTestUtil;
import test_util.starter.DatabaseStarter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.message.ErrorMessage.ACCOUNT_ALREADY_EXISTS;
import static org.example.message.ErrorMessage.TEAM_CAPACITY_IS_EXCEEDED;
import static org.example.message.InfoMessage.REGISTER_SUCCESS;
import static org.example.service.RegisterService.MAX_TEAM_CAPACITY;
import static org.instancio.Select.field;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static test_util.JsonUtil.GSON;
import static test_util.TestAccountUtil.createAccountWithField;
import static test_util.TestAccountUtil.createAccountWithFields;
import static test_util.TestControllerUtil.getContentWithExpectedStatus;
import static test_util.constant.TestGlobalConstants.*;
import static test_util.constant.TestUrlConstants.REGISTER_URL;

@SpringBootTest(classes = {Application.class, IntegrationTestUtil.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@MockBean(EmailSendingMessageStrategy.class)
public class RegisterControllerIntegrationTests implements DatabaseStarter {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IntegrationTestUtil integrationTestUtil;
    @Autowired
    private MessageSource messageSource;

    @Test
    void shouldRegisterAccount() throws Exception {
        RegisterRequest registerRequest = createValidRegisterRequest();

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, OK);

        assertThat(jsonResponse).contains(REGISTER_SUCCESS.getMessage());
    }

    @Test
    void shouldNotRegisterAccount_whenAllFieldsAreInvalid() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("", "", "", "", "", "");

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse)
                .contains(getValidationMessage("validation.firstName.pattern"))
                .contains(getValidationMessage("validation.lastName.pattern"))
                .contains(getValidationMessage("validation.collegeGroup.notBlank"))
                .contains(getValidationMessage("validation.nameTeam.pattern"))
                .contains(getValidationMessage("validation.telegram.pattern"))
                .contains(getValidationMessage("validation.email.notBlank"));
    }

    @Test
    void shouldNotRegisterAccount_whenEmailIsNotValid() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                TEST_FIRSTNAME,
                TEST_LASTNAME,
                TEST_COLLEGE_GROUP,
                TEST_NAME_TEAM,
                TEST_TELEGRAM,
                "asdsad"
        );

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse)
                .contains(messageSource.getMessage("validation.email.notValid", null, LocaleContextHolder.getLocale()));
    }

    @Test
    void shouldNotRegisterAccount_whenTeamCapacityIsExceeded() throws Exception {
        RegisterRequest registerRequest = createValidRegisterRequest();
        saveMaxAllowedAccountsForTeamToDatabase();

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse).contains(
                TEAM_CAPACITY_IS_EXCEEDED.formatWith(TEST_NAME_TEAM, MAX_TEAM_CAPACITY)
        );
    }

    @Test
    void shouldNotRegisterAccount_whenAccountWithGivenFirstNameAndLastNameExists() throws Exception {
        RegisterRequest registerRequest = createValidRegisterRequest();
        saveAccountWithFirstNameAndLastNameToDatabase();

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse).contains(
                ACCOUNT_ALREADY_EXISTS.formatWith("%s %s".formatted(TEST_FIRSTNAME, TEST_LASTNAME))
        );
    }

    @Test
    void shouldNotRegisterAccount_whenAccountWithGivenTelegramExists() throws Exception {
        RegisterRequest registerRequest = createValidRegisterRequest();
        saveAccountWithTelegramToDatabase();

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse).contains(
                ACCOUNT_ALREADY_EXISTS.formatWith(TEST_TELEGRAM)
        );
    }

    @Test
    void shouldNotRegisterAccount_whenAccountWithGivenEmailExists() throws Exception {
        RegisterRequest registerRequest = createValidRegisterRequest();
        saveAccountWithEmailToDatabase();

        String jsonResponse = registerAccountAndGetContentWithExpectedStatus(registerRequest, BAD_REQUEST);

        assertThat(jsonResponse).contains(
                ACCOUNT_ALREADY_EXISTS.formatWith(TEST_EMAIL)
        );
    }

    private String registerAccountAndGetContentWithExpectedStatus(RegisterRequest registerRequest, HttpStatus status) throws Exception {
        ResultActions resultActions = performRegisterRequest(registerRequest);
        return getContentWithExpectedStatus(resultActions, status);
    }

    private ResultActions performRegisterRequest(RegisterRequest registerRequest) throws Exception {
        return mockMvc.perform(post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(GSON.toJson(registerRequest)));
    }

    private void saveMaxAllowedAccountsForTeamToDatabase() {
        for (int i = 0; i < MAX_TEAM_CAPACITY; i++) {
            integrationTestUtil.saveAccountToDatabase(
                    createAccountWithField(field(Account::getNameTeam), TEST_NAME_TEAM)
            );
        }
    }

    private void saveAccountWithFirstNameAndLastNameToDatabase() {
        integrationTestUtil.saveAccountToDatabase(
                createAccountWithFields(
                        field(Account::getFirstName), TEST_FIRSTNAME,
                        field(Account::getLastName), TEST_LASTNAME
                )
        );
    }

    private void saveAccountWithTelegramToDatabase() {
        integrationTestUtil.saveAccountToDatabase(
                createAccountWithField(field(Account::getTelegram), TEST_TELEGRAM)
        );
    }

    private void saveAccountWithEmailToDatabase() {
        integrationTestUtil.saveAccountToDatabase(
                createAccountWithField(field(Account::getEmail), TEST_EMAIL)
        );
    }

    private String getValidationMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private RegisterRequest createValidRegisterRequest() {
        return new RegisterRequest(
                TEST_FIRSTNAME,
                TEST_LASTNAME,
                TEST_COLLEGE_GROUP,
                TEST_NAME_TEAM,
                TEST_TELEGRAM,
                TEST_EMAIL
        );
    }
}
