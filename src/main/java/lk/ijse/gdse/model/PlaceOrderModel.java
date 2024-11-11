package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.PlaceOrderDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderModel {
    public ArrayList<PlaceOrderDto> getAllTires() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire");

        ArrayList<PlaceOrderDto> placeOrderDTOS = new ArrayList<>();

        while (rst.next()){
            PlaceOrderDto placeOrderDto = new PlaceOrderDto();

            placeOrderDto.setTireId(rst.getString(1));
            placeOrderDto.setTireBrand(rst.getString(2));
            placeOrderDto.setTireModel(rst.getString(3));
            placeOrderDto.setTireSize(rst.getString(4));
            placeOrderDto.setYear(Integer.parseInt(rst.getString(5)));
            placeOrderDto.setTirePrice(Double.parseDouble(rst.getString(6)));


            placeOrderDTOS.add(placeOrderDto);
        }
        return placeOrderDTOS;
    }

    public int getQty(String tireId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from tire_stock where tireId = ?",tireId);
//
//        String id = null;
//        if(rst.next()){
//            id = rst.getString(2);
//        }

        ResultSet rst1 = CrudUtil.execute("select * from stock where tireId = ?",tireId);

        int qty = 0;
        if(rst1.next()){
            qty = rst1.getInt(6);
        }
        return qty;
    }

    public PlaceOrderDto getTire(String tireId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from tire where tireId = ?",tireId);

        PlaceOrderDto placeOrderDto = new PlaceOrderDto();

        if(rst.next()){
            placeOrderDto.setTireId(rst.getString(1));
            placeOrderDto.setTireBrand(rst.getString(2));
            placeOrderDto.setTireModel(rst.getString(3));
            placeOrderDto.setTireSize(rst.getString(4));
            placeOrderDto.setYear(Integer.parseInt(rst.getString(5)));
            placeOrderDto.setTirePrice(Double.parseDouble(rst.getString(6)));
        }
        return placeOrderDto;
    }
}
