package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.CustomerDto;
import lk.ijse.gdse.dto.EmployeeDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public ArrayList<String> getAllEmployeesIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select empId from employee");

        ArrayList<String> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            employeeDtos.add(rst.getString(1));
        }
        return employeeDtos;
    }

    public EmployeeDto findByEmpId(String selectedEmpId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee where empId=?", selectedEmpId);
        if (rst.next()) {
            return new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
