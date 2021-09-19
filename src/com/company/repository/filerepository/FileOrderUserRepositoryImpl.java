package com.company.repository.filerepository;

import com.company.entity.Order;
import com.company.repository.OrderRepository;
import com.company.resources.FileSource;
import com.company.resources.GenerateId;
import com.company.resources.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOrderUserRepositoryImpl implements OrderRepository {


    @Override
    public Order getOne(int id) {
        try(BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEORD))) {
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line.contains("id=" + id));
                if (line.contains("id=" + id)) {
                    return Parser.parse(line, Order.class);
                }
                line = reader.readLine();
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage() + "\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }


    @Override
    public List<Order> getAllOrderByUser(int id) {
        try(BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEORD))) {
            String line = reader.readLine();
            List<Order> orderList = new ArrayList<>();
            while (line != null) {
                if (line.contains("userId="+id)) {
                        orderList.add(Parser.parse(line, Order.class));
                }
                line = reader.readLine();
            }
            return orderList;
        }catch (IOException ex){
            System.out.println(ex.getMessage()+ "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (getOne(id) != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEORD));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.POJOFILE, true))) {
                FileSource.POJOFILE.createNewFile();
                String line = reader.readLine();
                while (line != null) {
                    if (line.contains("id=" + id)) {
                        line = reader.readLine();
                    }
                    if (line != null) {

                        writer.write(line);
                        if (reader.readLine() != null) {
                            writer.newLine();
                        }
                        writer.flush();
                    }
                    line = reader.readLine();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
            FileSource.FILEORD.delete();
            FileSource.POJOFILE.renameTo(FileSource.FILEORD);
        }
    }

    @Override
    public Order update(Order order) {
        if (!getOne(order.getId()).equals(order)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEORD));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.POJOFILE, true))) {
                FileSource.POJOFILE.createNewFile();

                String line = reader.readLine();

                while (line != null) {
                    if (line.contains("userId=" + order.getUserId())) {
                        line = reader.readLine();
                        writer.write("id=" + order.getId() + ",userId=" + order.getUserId() + ",description=" +
                                order.getDescription() + ",price=" + order.getPrice());
                        writer.newLine();
                    } else {
                        writer.write(line);
                        writer.newLine();
                        line = reader.readLine();
                    }
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }
            FileSource.FILEORD.delete();
            FileSource.POJOFILE.renameTo(FileSource.FILEORD);
            return Parser.parse("id=" + order.getId() + ",userId=" + order.getUserId() + ",description=" +
                    order.getDescription() + ",price=" + order.getPrice(), order.getClass());
        }
        return null;
    }

    @Override
    public void create(int userId, Order order) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.FILEORD, true))) {
            writer.newLine();
            writer.write("id=" + GenerateId.generateID(int.class) + ",userId=" + userId + ",description=" +
                    order.getDescription() + ",price=" + order.getPrice());
            writer.flush();
        }catch (IOException ex){
            System.out.println(ex.getMessage() + "\n"+ Arrays.toString(ex.getStackTrace()));
        }
    }
}
