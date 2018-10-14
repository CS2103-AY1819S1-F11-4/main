package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;
import seedu.address.storage.UserAccountStorage;

/**
 * Creates a user for address book.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";
    private static boolean createIsSuccessful = false;

    //TODO: update MESSAGE_USAGE
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";

    private static final String MESSAGE_SUCCESS = "New user added successfully!";
    private static final String MESSAGE_FAILURE = "Username already exist.";

    private final Accounts newAccount;

    /**
     * Creates an CreateCommand to add the specified {@code Person}
     */
    public CreateCommand(Accounts account) {

        newAccount = account;

        if (!UserAccountStorage.checkDuplicateUser(account.getUsername())) {
<<<<<<< HEAD
            UserAccountStorage.addNewAccount(account.getUsername(), account.getPassword());
            createIsSuccessful = true;
        } else {
            createIsSuccessful = false;
=======
            UserAccountStorage.addNewAccount(newAccount.getUsername(), newAccount.getPassword());
>>>>>>> v1.2-upstream
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
<<<<<<< HEAD
        requireNonNull(model);

        if (createIsSuccessful == true) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }


=======
        return new CommandResult(String.format(MESSAGE_SUCCESS));
>>>>>>> v1.2-upstream
    }
}
