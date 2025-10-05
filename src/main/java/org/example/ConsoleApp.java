package org.example;

import org.example.models.User;
import org.example.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ConsoleApp {
    private static class Terminal{
        @FunctionalInterface
        private interface IChecker <T>{
            T check(String input);
        }
        static String requestString(String message){
            return requestInput(message, (String input) -> input);
        }

        static int requestInt(String message, String errorMessage){
            return requestInput(message, (String input) -> {
                Integer result = null;
                try{
                    result = Integer.parseInt(input);
                } catch (Exception e){
                    System.out.println(errorMessage);
                    return null;
                }
                return result;
            });
        }

        static <T> T requestInput(String message, IChecker<T> checker){
            while(true){
                System.out.print(message);
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();
                T result = checker.check(input);
                if(result == null){
                    continue;
                }
                return result;
            }
        }
    }

    private static class Command {
        @FunctionalInterface
        private interface IAction{
            void run();
        }
        private final String name;
        private final IAction action;

        private Command(String name, IAction action){
            this.name = name;
            this.action = action;
        }
    }

    private final UserService userService;
    private final ArrayList<Command> commands;
    private final String menuMessage;
    private boolean runs;

    public ConsoleApp(){
        userService = new UserService();
        this.commands = new ArrayList<>();
        this.commands.add(new Command("Exit", () -> this.runs = false));
        this.commands.add(new Command("Show users list", () -> {
            List<User> users = userService.find();

            if (users.isEmpty()) {
                System.out.println("There is no users");
                return;
            }

            for (User user : users) {
                System.out.println(user);
            }
        }));
        this.commands.add(new Command("Create user", () -> {
            String name = Terminal.requestString("User name: ");
            String email = Terminal.requestString("User email: ");
            int age = Terminal.requestInt(
                    "User age: ",
                    "Wrong value, user age must be a number"
            );

            User user = new User(name, email, age);
            userService.saveUser(user);
            System.out.println("User was saved:\n" + user);
        }));
        this.commands.add(new Command("Update user", () -> {
            int id = Terminal.requestInt(
                    "User id for update: ",
                    "id must be a number"
            );

            User user = userService.findUser(id);
            if (user == null) {
                System.out.println("User with id not exist");
                return;
            }

            String name = Terminal.requestString(
                    "User name (old value is '" + user.getName() + "'): "
            );
            user.setName(name);

            String email = Terminal.requestString(
                    "User email (old value is '" + user.getEmail() + "'): "
            );
            user.setEmail(email);

            int age = Terminal.requestInt(
                    "User age (old value is '" + user.getAge() + "'): ",
                    "Wrong value, user age must be a number"
            );
            user.setAge(age);

            userService.updateUser(user);
            System.out.println("User was saved:\n" + user);
        }));
        this.commands.add(new Command("Delete user", () -> {
            int id = Terminal.requestInt(
                    "User id for delete: ",
                    "id must be a number"
            );

            User user = userService.findUser(id);
            if (user == null) {
                System.out.println("User with id not exist");
                return;
            }

            userService.deleteUser(user);

            System.out.println("User was deleted!");
        }));

        StringBuilder menuSB = new StringBuilder("\n");
        ListIterator<Command> iter = this.commands.listIterator();
        while(iter.hasNext()){
            menuSB.append("(").append(iter.nextIndex()).append(") ").append(iter.next().name).append("\n");
        }
        menuSB.append("\nChoose option: ");
        this.menuMessage = menuSB.toString();
    }

    public void run() {
        this.runs = true;
        while(this.runs){
            int choice = Terminal.requestInt(this.menuMessage, "Wrong value, option must be a number");

            if(choice < 0 || choice >= commands.size()){
                System.out.println("There is no such option!");
                continue;
            }

            commands.get(choice).action.run();
        }
    }
}
