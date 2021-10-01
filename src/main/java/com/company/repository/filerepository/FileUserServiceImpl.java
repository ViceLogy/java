package com.company.repository.filerepository;

import com.company.entity.Order;
import com.company.entity.User;
import com.company.repository.UserRepository;
import com.company.resources.FileSource;
import com.company.resources.GenerateId;
import com.company.resources.Parser;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class FileUserServiceImpl implements UserRepository {

    @Override
    public User getOne(int id) {
        try(BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEUS))){
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line.contains("id=" + id));
                if (line.contains("id=" + id)) {
                    return Parser.parse(line, User.class);
                }
                line = reader.readLine();
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));

        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEUS))) {
            String line = reader.readLine();
            List<User> userList = new ArrayList<>();
            List<String> list = new ArrayList<>();
            while (line != null) {
                list.add(line);
                line = reader.readLine();
            }
            for (int i = 0; i <= list.size() - 1; i++){
                userList.add(Parser.parse(list.get(i), User.class));
            }
            return userList;
        }catch(IOException ex){
            System.out.println(ex.getMessage() +"\n"+ Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (getOne(id) != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEUS));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.POJOFILE, true))) {
                FileSource.POJOFILE.createNewFile();
                String line = reader.readLine();
                while (line != null) {
                    if (line.contains("id=" + id)) {
                        line = reader.readLine();
                    }
                    if (line != null) {
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                        line = reader.readLine();
                    }
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));

            }
            FileSource.FILEUS.delete();
            FileSource.POJOFILE.renameTo(FileSource.FILEUS);
        }
    }



    @Override
    public User update(User user) {
        if (!getOne(user.getId()).equals(user)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FileSource.FILEUS));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.POJOFILE, true))) {
                FileSource.POJOFILE.createNewFile();
                String line = reader.readLine();

                while (line != null) {
                    if (line.contains("id=" + user.getId() + ",")) {
                        line = reader.readLine();
                        writer.write("id=" + user.getId() + ",name=" + user.getName() + ",lastName=" + user.getLastName());
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
            FileSource.FILEUS.delete();
            FileSource.POJOFILE.renameTo(FileSource.FILEUS);
            return Parser.parse("id=" + user.getId() + ",name=" + user.getName() + ",lastName=" + user.getLastName(), User.class);
        }
        return null;
    }

    @Override
    public void create(User user) {
        if (getOne(user.getId()) == null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileSource.FILEUS, true))) {
                writer.newLine();
                writer.write("id=" + GenerateId.generateID(int.class) + ",name=" + user.getName() + ",lastName=" +
                        user.getLastName());
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
            }

        }
    }
}
