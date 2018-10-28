package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class AddTimetableCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_timetableAcceptedByModel_addSuccess() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToAddTimetable.getTimetable().downloadTimetableAsCsv();
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST, "default");
        String expectedMessage = String
            .format(AddTimetableCommand.MESSAGE_ADD_TIMETABLE_SUCCESS,
                personToAddTimetable.getStoredLocation());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());

        expectedModel.update(model.getFilteredPersonList().get(0), personToAddTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }

    @Test
    public void execute_timetableAcceptedByModel_addByFileLocationSuccess() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToAddTimetable.getTimetable().downloadTimetableAsCsv();
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST,
            personToAddTimetable.getStoredLocation());
        String expectedMessage = String
            .format(AddTimetableCommand.MESSAGE_ADD_TIMETABLE_SUCCESS,
                personToAddTimetable.getStoredLocation());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs());

        expectedModel.update(model.getFilteredPersonList().get(0), personToAddTimetable);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addTimetableCommand, model, commandHistory, expectedMessage,
            expectedModel);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(outOfBoundIndex,
            "default");

        assertCommandFailure(addTimetableCommand, model, commandHistory,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_timetableNotFound_addFailure() {
        Person personToAddTimetable = model.getFilteredPersonList()
            .get(INDEX_FIRST.getZeroBased());
        personToAddTimetable.getTimetable().downloadTimetableAsCsv();
        File timetable = new File(personToAddTimetable.getStoredLocation());
        if (timetable.exists()) {
            timetable.delete();
        }
        AddTimetableCommand addTimetableCommand = new AddTimetableCommand(INDEX_FIRST, "default");
        assertCommandFailure(addTimetableCommand, model, commandHistory,
            Messages.MESSAGE_TIMETABLE_NOT_FOUND);
    }
}
