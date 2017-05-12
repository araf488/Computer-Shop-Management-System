/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerShop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author araf0
 */
public class ProductController {

    public static boolean savePname(String pname) {
        Connection con = null;
        CallableStatement csmt = null;
        boolean t = true;
        try {
            con = DatabaseConnection.java_db();
            csmt = con.prepareCall("{CALL saveProduct(?)}");
            csmt.setString(1, pname);
            t = csmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static boolean saveSale(String pname, String price, String date, String qty, String model) {
        Connection con = null;
        CallableStatement csmt = null;
        boolean t = true;
        try {
            con = DatabaseConnection.java_db();
            csmt = con.prepareCall("{CALL save_sale(getProductid(?),?,?,?,?)}");
            csmt.setString(1, pname);
            csmt.setString(2, price);
            csmt.setString(3, date);
            csmt.setString(4, qty);
            csmt.setString(5, model);
            t = csmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void loadCombo(JComboBox combo) {
        Connection con = null;
        CallableStatement csmt = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnection.java_db();
            csmt = con.prepareCall("{CALL listProduct()}");
            csmt.execute();
            rs = csmt.getResultSet();
            List pList = new ArrayList();
            while (rs.next()) {
                pList.add(rs.getString(1));
            }
            combo.setModel(new DefaultComboBoxModel(pList.toArray()));
            combo.insertItemAt("Select One", 0);
            combo.setSelectedIndex(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
