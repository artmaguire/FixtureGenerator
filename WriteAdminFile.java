package Project1;

import javax.swing.*;
import java.util.Formatter;

public class WriteAdminFile {
    Formatter x;

    public static void main(String[] args) {
        WriteAdminFile admin = new WriteAdminFile();
        admin.openFile();
        admin.addAdminDetails();
        admin.closeAdminFile();
    }

    public void openFile() {
        try {
            x = new Formatter("adminTest.txt");
        } catch (Exception e) {
            System.out.println("Error.");
        }
    }

    public void addAdminDetails() {
        String adminName, adminPassword;
        int adminNum;

        adminNum = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Administer Number."));
        adminName = JOptionPane.showInputDialog(null, "Enter Administrator Name.");
        adminPassword = JOptionPane.showInputDialog(null, "Enter Password.");
        x.format("%d,%s,%s", adminNum, adminName, adminPassword);
    }

    public void closeAdminFile() {
        x.close();
    }
}