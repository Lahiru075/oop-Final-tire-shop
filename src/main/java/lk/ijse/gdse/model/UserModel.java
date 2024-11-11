package lk.ijse.gdse.model;

import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.UserDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select usId from user order by usId desc limit 1");
        if (rst.next()){
            String usId = rst.getString(1);
            String subString = usId.substring(1);
            int value = Integer.parseInt(subString);
            int newIdIndex = value + 1; // Increment the number by 1
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    public boolean seveUser(UserDto userDto) throws SQLException {
        return CrudUtil.execute("insert into user values (?,?,?,?)",
                userDto.getUsId(),
                userDto.getRole(),
                userDto.getPassword(),
                userDto.getUsername()
        );
    }
}
