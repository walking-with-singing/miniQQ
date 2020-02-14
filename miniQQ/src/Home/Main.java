package Home;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import UI.Login;
import bean.User;
import netTool.Chat;

public class Main {
	public static ExecutorService pool= Executors.newCachedThreadPool();
	public static Socket socket;
	public static Chat chat;
	public static void main(String[] args) {
		new Login();
	}
}
