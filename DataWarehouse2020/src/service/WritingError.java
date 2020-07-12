package service;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class WritingError {
	JavaEmail javaEmail = new JavaEmail();
	
	public void writingError(String error) throws IOException{
		Path file = Paths.get("local\\error.txt");
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.APPEND));
			dos.write((LocalTime.now().toString()+"\n").getBytes());
			dos.write((error+"\n").getBytes());
			String endLine = "------------------------------------- \n";
			dos.write(endLine.getBytes());
			
			dos.flush();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void sendError(String error) throws IOException, AddressException, MessagingException {
		writingError(error);
		javaEmail.prepareSending(error);
	}//
	
	public static void main(String[] args) throws IOException {
		WritingError t = new WritingError();
		t.writingError("rew");
	}

}
