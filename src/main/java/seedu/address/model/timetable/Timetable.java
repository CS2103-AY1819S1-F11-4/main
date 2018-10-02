package seedu.address.model.timetable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import seedu.address.model.Entity;
import seedu.address.model.person.Name;

/**
 * Represents a timetable in the address book. Guarantees: nothing at the moment
 */
public class Timetable extends Entity {

    // Identity fields
    private final Name name;
    private final String fileName;
    private final String defaultLocation;
    private final String locationOfFile;
    private final String timetableFolder = "/src/main/java/seedu/address/formatl/timetable/timetables/";
    private final String format;

    // create timetable data
    private final TimetableData matrix;

    /**
     *
     * @param name
     * @param fileName
     * @param format
     */
    public Timetable(Name name, String fileName, String format, String locationFrom) {
        this.name = name;
        this.fileName = fileName + ".csv";
        this.format = format;
        File ans = new File("");
        this.defaultLocation =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + this.fileName;
        locationOfFile = locationFrom + this.fileName;
    }

    public Timetable(Name name, String fileName, String format) {
        this.name = name;
        this.fileName = fileName + ".csv";
        this.format = format;
        File ans = new File("");
        this.defaultLocation =
            ans.getAbsolutePath().replace("\\", "/") + this.timetableFolder + this.fileName;
        locationOfFile = null;
    }

    /**
     *
     * @return
     */
    public Name getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @return
     */
    public String getDefaultLocation() {
        return defaultLocation;
    }

    /**
     *
     */
    public void addTimetable(String locationFrom) {
        String source = locationFrom.replace("\\", "/") + "/" + this.fileName;
        Path from = Paths.get(source);
        Path to = Paths.get(defaultLocation);
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public String[][] readTimetable() {
        String locationFrom = this.defaultLocation;
        String[][] timetableMatrix;
        Scanner inputStream;
        //used code from https://stackoverflow.com/questions/40074840/reading-a-csv-file-into-a-array
        String filePath = locationFrom.replace("\\", "/") + "/" + fileName + ".csv";
        TimetableGenerator timetable = new TimetableGenerator(this.format);
        timetableMatrix = timetable.getNewTimetable();
        try {
            File toRead = new File(filePath);
            if (!toRead.exists() && this.format.equals("horizontal")) {
                inputStream = new Scanner(toRead);
                int i = 0;
                while (inputStream.hasNext()) {
                    String line = inputStream.next();
                    String[] entries = line.split(",");
                    timetableMatrix[i] = entries;
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timetableMatrix;
    }

    /**
     *
     * @param locationTo
     */
    public void getNewTimetable(String locationTo) {
        // used code from https://stackoverflow.com/questions/6271796/issues-of-saving-a-matrix-to-a-csv-file
        String filePath = locationTo.replace("\\", "/") + "/" + this.fileName;
        if (this.format.equals("horizontal")) {
            generateHorizontalTimetable(filePath);
        } else {
            if (this.format.equals("vertical")) {
                generateVerticalTimetable(filePath);
            }
        }
    }

    /**
     *
     * @param filePath
     */
    private void generateHorizontalTimetable(String filePath) {
        TimetableGenerator timetable = new TimetableGenerator(format);
        String[][] newTimetable;

        newTimetable = timetable.getNewTimetable();
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists()) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite, true);
                for (int i = 0; i < timetable.getNoOfDays() + 1; i++) {
                    for (int j = 0; j < timetable.getNoOfTimings() + 1; j++) {
                        if (j == timetable.getNoOfTimings()) {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append('\n');
                            writer.flush();
                        } else {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append(',');
                            writer.flush();
                        }
                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param filePath
     */
    private void generateVerticalTimetable(String filePath) {
        TimetableGenerator timetable = new TimetableGenerator(this.format);
        String[][] newTimetable;

        newTimetable = timetable.getNewTimetable();
        try {
            File toWrite = new File(filePath);
            if (!toWrite.exists()) {
                toWrite.createNewFile();
                FileWriter writer = new FileWriter(toWrite, true);
                for (int i = 0; i < timetable.getNoOfTimings() + 1; i++) {
                    for (int j = 0; j < timetable.getNoOfDays() + 1; j++) {
                        if (j == timetable.getNoOfDays()) {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append('\n');
                            writer.flush();
                        } else {
                            writer.append(newTimetable[i][j]);
                            writer.flush();
                            writer.append(',');
                            writer.flush();
                        }

                    }
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Timetable)) {
            return false;
        }
        Timetable otherTimetable = (Timetable) other;
        return otherTimetable.equals(other);
    }
}
